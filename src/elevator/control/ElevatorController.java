package elevator.control;

import java.util.ArrayList;

import simulator.Simulator;
import simulator.common.IllegalParamException;
import simulator.common.SimulationInformation;
import elevator.common.Elevator;
import elevator.common.ElevatorDirection;
import elevator.common.ElevatorFactory;
import elevator.common.ElevatorRequest;
import elevator.common.InvalidFloorException;

/**
 * Description: class ElevatorController
 * 
 * @author Patrick Stein
 * @author Chris Kurn
 * @since Version 1.0 - Spring Quarter 2014
 * @see package elevator.control
 * @see import java.util.ArrayList;
 * @see import simulator.Simulator;
 * @see import simulator.common.SimulationInformation;
 * @see import elevator.common.Elevator;
 */

final public class ElevatorController implements Runnable {

    private volatile static ElevatorController instance;
    private boolean running;
    private ArrayList<Elevator> elevators;
    private long timeoutTime = 2000;
    private Thread myThread;
    private ArrayList<ElevatorRequest> pendingRequests = new ArrayList<ElevatorRequest>();
    private int numberOfFloors;
    private ElevatorRequestHandler delegate;
    ArrayList<Elevator> myElevators = new ArrayList<Elevator>();

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
                            "Invalid simulation input provided. Cannot continue with this simulation. Please provide valid input");
            // TODO handle this exception
        }
        // This should only be created once
        this.myThread = new Thread(this);
    }

    /**
     * The singleton method for creating and returning a single instance of the
     * ElevatorController
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
     * @throws IllegalParamException
     */
    private void createElevators(SimulationInformation simInfo)
            throws IllegalParamException {
        ArrayList<Elevator> eles = this.getElevators();

        for (int i = 0; i < simInfo.numElevators; i++) {
            eles.add(ElevatorFactory.build(simInfo));
        }

    }

    public void addNewRequest(int floor, ElevatorDirection direction)
            throws IllegalParamException {
        // Make sure it is a valid floor request
        // Direction can't be idle and the 
        if (direction == ElevatorDirection.IDLE || floor <= 0
                || floor >= this.getNumberOfFloors()) {
            throw new IllegalParamException(
                    "Unable to accomdate this request because the elevator direction was idle or the floors exceeded the simulations parameters.");
        }
        // We are good to add the request
        ElevatorRequest req = new ElevatorRequest(floor, direction);
        //if this does not already contain this request, then add it
        if(!this.getPendingRequests().contains(req)){
            this.addNewRequest(req);
            Simulator.getInstance().logEvent(
                    String.format("The following request has been added: %s",
                            req.toString()));
        }
        // TODO Maybe print out a duplicate request has been sent?
    }

    private synchronized void addNewRequest(ElevatorRequest req) {
        this.getPendingRequests().add(req);
        this.notifyAll();
    }

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

    private void handlePendingRequests() {

        // While there are pending requests lets handle them
        while (areTherePendingRequests()) {
            for (ElevatorRequest req : this.getPendingRequests()) {
                // For each request in our list of pending requests
                // Call our delegate to handle this
                try {
                    this.setDelegate(ElevatorRequestHandlerFactory.build(this.getElevators()));
                } catch (IllegalParamException e) {
                    Simulator.getInstance().logEvent("Unable to build delegate in the ElevatorController. Skipping this request.");
                    continue;
                }            
                if(this.getDelegate().handleRequest(req)){
                    //remove from the arraylist
                    synchronized(this){
                        this.getPendingRequests().remove(req);
                    }
                }else{
                    //move to the next request
                    //Maybe mark it in the log?
                    continue;
                }
            }
        }

    }


    private void setDelegate(ElevatorRequestHandler del) throws IllegalParamException {
        if(del == null){
            throw new IllegalParamException("Delegate cannot be set to null.");
        }
        this.delegate = del;
    }
    private ElevatorRequestHandler getDelegate(){
        return this.delegate;
    }

    private synchronized boolean areTherePendingRequests() {
        return !this.getPendingRequests().isEmpty();
    }

    /**
     * Public method to stop the execution of the elevator method
     */
    public void stopDownElevatorController() {
        this.running = false;
    }

    public void startElevatorController() {
        this.running = true;
        this.myThread.start();
    }

    /**
     * Private method for acquiring all of the elevators
     * 
     * @return
     */
    private ArrayList<Elevator> getElevators() {
        return this.elevators;
    }

    /**
     * Private get method for how long the elevator controller will be waiting.
     * 
     * @return a long representing how long the elevator controller will wait
     */
    private long getTimeoutTime() {
        return this.timeoutTime;
    }

    private void setNumberOfFloors(long numFloors) throws IllegalParamException {
        SimulationInformation simInfo = Simulator.getInstance()
                .getSimulationInfo();
        if (numFloors > simInfo.numFloors || numFloors <= 0) {
            throw new IllegalParamException(
                    "Invalid number of floors. Cannot exceed the number of floors in the simulation information or go below 0");
        }
    }

    private int getNumberOfFloors() {
        return this.numberOfFloors;
    }

    private ArrayList<ElevatorRequest> getPendingRequests() {
        return this.pendingRequests;
    }

}
