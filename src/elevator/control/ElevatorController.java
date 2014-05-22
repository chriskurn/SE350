package elevator.control;

import java.util.ArrayList;

import simulator.Simulator;
import simulator.common.IllegalParamException;
import simulator.common.SimulationInformation;
import elevator.common.ElevatorDirection;
import elevator.common.ElevatorFactory;
import elevator.common.ElevatorRequest;
import elevator.elements.Elevator;

/**
 * Description: class ElevatorController is a singleton facade designed to
 * manage the elevators and their requests.
 * 
 * 
 * @author Patrick Stein
 * @author Chris Kurn
 * @since Version 1.0 - Spring Quarter 2014
 */

final public class ElevatorController implements Runnable {

    /** The instance. */
    private volatile static ElevatorController instance;

    /** The running. */
    private boolean running;

    /** The timeout time. */
    private long timeoutTime = 2000;

    /** The my thread. */
    private Thread myThread;

    /** The pending requests. */
    private ArrayList<ElevatorRequest> pendingRequests = new ArrayList<ElevatorRequest>();

    /** The number of floors. */
    private int numberOfFloors;

    /** The delegate. */
    private ElevatorRequestHandler delegate;

    /** The my elevators. */
    ArrayList<Elevator> myElevators = new ArrayList<Elevator>();

    /**
     * Instantiates a new elevator controller.
     */
    private ElevatorController() {
        SimulationInformation simInfo = Simulator.getInstance()
                .getSimulationInfo();
        try {
            this.createElevators(simInfo);
            this.setNumberOfFloors(simInfo.numFloors);
        } catch (IllegalParamException e) {
            Simulator
                    .getInstance()
                    .logEvent(
                            "Invalid simulation input provided. Cannot continue with this simulation. Please provide valid input breh.");
            System.exit(1);
        }
        // This should only be created once
        this.myThread = new Thread(this);
        Simulator.getInstance().logEvent("Elevator controller created.");
    }

    /**
     * The singleton method for creating and returning a single instance of the
     * ElevatorController.
     * 
     * @return returns the one Elevator controller in existence.
     */
    public static ElevatorController getInstance() {

        // Double locking for multi-threaded environment
        if (instance == null) {
            synchronized (ElevatorController.class) {
                if (instance == null) {
                    instance = new ElevatorController();
                }
            }
        }
        return instance;

    }

    /**
     * Private method to encapsulate the elevator creation logic.
     * 
     * @param simInfo
     *            the simulation information DTO provided by the Simulator Class
     * @throws IllegalParamException
     *             this exception is thrown if bad information is provided to
     *             the elevator being created. Things such as negative max floor
     *             numbers or negative door time will cause this exception to be
     *             thrown.
     */
    private void createElevators(SimulationInformation simInfo)
            throws IllegalParamException {
        ArrayList<Elevator> eles = this.getElevators();

        for (int i = 0; i < simInfo.numElevators; i++) {
            Elevator ele = ElevatorFactory.build(simInfo);
            eles.add(ele);
            ele.startElevator();
        }

    }

    /**
     * Adds the new request.
     * 
     * @param floor
     *            the floor you want to add to the queue of requests
     * @param direction
     *            the direction in which the request will be going
     * @throws IllegalParamException
     *             thrown if the anything is null or the floor is not in the
     *             range of the building
     */
    public void addNewRequest(int floor, ElevatorDirection direction)
            throws IllegalParamException {
        // Make sure it is a valid floor request
        // Direction can't be idle and the
        int maxFloors = getNumberOfFloors();

        if (direction == ElevatorDirection.IDLE || floor <= 0
                || floor > maxFloors) {
            throw new IllegalParamException(
                    "Unable to accomdate this request because the elevator direction was idle or the floors exceeded the simulations parameters.");
        }
        // We are good to add the request
        ElevatorRequest req = new ElevatorRequest(floor, direction);
        // if this does not already contain this request, then add it
        if (!this.getPendingRequests().contains(req)) {
            this.addNewRequest(req);
            Simulator.getInstance().logEvent(
                    String.format("The following request has been added: %s",
                            req.toString()));
        }
    }

    /**
     * Adds the new request. Primarily a single point to synchronize and notify
     * all threads waiting.
     * 
     * @param req
     *            the request that needs to be added.
     */
    private synchronized void addNewRequest(ElevatorRequest req) {
        this.getPendingRequests().add(req);
        this.notifyAll();
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Runnable#run()
     */
    @Override
    public void run() {
        // While the elevator controller is running handle all requests
        while (running) {

            handlePendingRequests();

            synchronized (this) {
                try {
                    wait(this.getTimeoutTime());
                } catch (InterruptedException e) {
                    Simulator
                            .getInstance()
                            .logEvent(
                                    "The elevator controller thread has been interrupted. Continuing on.");
                    continue;
                }
            }

        }
    }

    /**
     * Handle pending requests. This method will iterate through the list of
     * requests and assign them to a given elevator delegate.
     */
    private void handlePendingRequests() {

        // While there are pending requests lets handle them
        while (areTherePendingRequests() == true) {

            ElevatorRequest req = getPendingRequests().get(0);
            // For each request in our list of pending requests
            // Call our delegate to handle this
            try {
                this.setDelegate(ElevatorRequestHandlerFactory.build(this
                        .getElevators()));
            } catch (IllegalParamException e) {
                Simulator
                        .getInstance()
                        .logEvent(
                                "Unable to build delegate in the ElevatorController. Skipping this request.");
                continue;
            }
            if (this.getDelegate().handleRequest(req)) {
                // remove from the arraylist
                synchronized (this) {
                    this.getPendingRequests().remove(0);
                }
            } else {
                // move to the next request
                // Maybe mark it in the log?
                continue;
            }

        }

    }

    /**
     * Sets the delegate.
     * 
     * @param del
     *            the new ElevatorRequestHanlder delegate
     * @throws IllegalParamException
     *             the delegate cannot be null
     */
    private void setDelegate(ElevatorRequestHandler del)
            throws IllegalParamException {
        if (del == null) {
            throw new IllegalParamException("Delegate cannot be set to null.");
        }
        this.delegate = del;
    }

    /**
     * Gets the delegate.
     * 
     * @return the delegate
     */
    private ElevatorRequestHandler getDelegate() {
        return this.delegate;
    }

    /**
     * Are there pending requests.
     * 
     * @return true, if successful
     */
    private synchronized boolean areTherePendingRequests() {
        return !this.getPendingRequests().isEmpty();
    }

    /**
     * Public method to stop the execution of the elevator controller.
     */
    public void shutDownElevatorController() {
        this.running = false;
    }

    /**
     * Start elevator controller.
     */
    public void startElevatorController() {
        this.running = true;
        this.myThread.start();
    }

    public void stopElevatorController() {
        this.running = false;
    }

    public void stopAllElevators() {
        // stop it
        for (Elevator e : getElevators()) {
            e.stopElevator();
        }
    }

    /**
     * Private method for acquiring all of the elevators.
     * 
     * @return the elevators
     */
    private ArrayList<Elevator> getElevators() {
        return myElevators;
    }

    /**
     * Private get method for how long the elevator controller will be waiting.
     * 
     * @return a long representing how long the elevator controller will wait
     */
    private long getTimeoutTime() {
        return this.timeoutTime;
    }

    /**
     * Sets the number of floors.
     * 
     * @param numFloors
     *            the new number of floors
     * @throws IllegalParamException
     *             thrown if the number of floors does not match the simulation
     *             information
     */
    private void setNumberOfFloors(int numFloors) throws IllegalParamException {
        SimulationInformation simInfo = Simulator.getInstance()
                .getSimulationInfo();
        if (numFloors > simInfo.numFloors || numFloors <= 0) {
            throw new IllegalParamException(
                    "Invalid number of floors. Cannot exceed the number of floors in the simulation information or go below 0");
        }
        numberOfFloors = numFloors;
    }

    /**
     * Gets the number of floors.
     * 
     * @return the number of floors
     */
    private int getNumberOfFloors() {
        return this.numberOfFloors;
    }

    /**
     * Gets the pending requests.
     * 
     * @return the pending requests
     */
    private ArrayList<ElevatorRequest> getPendingRequests() {
        return this.pendingRequests;
    }

    /**
     * This will only return true if each elevator has no destination left and
     * the elevator controller has no requests left.
     * 
     * @return returns true if any elevators have destinations left and if there
     *         are elevator requests in the ElevatorController's request queue.
     */
    public synchronized boolean elevatorWorkLeft() {
        boolean ecNoRequests = this.getPendingRequests().isEmpty();
        boolean elesNoMovement = false;

        for (Elevator e : this.getElevators()) {
            if (e.destinationsLeft() == true) {
                elesNoMovement = false;
            }
        }
        elesNoMovement = true;

        return (ecNoRequests && elesNoMovement);
    }

}
