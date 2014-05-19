package elevator.common;

import java.util.ArrayList;
import java.util.Collections;

import building.common.Person;

import simulator.Simulator;
import simulator.common.IllegalParamException;
import simulator.common.SimulationInformation;

/**
 * Description: Elevator implementation class
 * 
 * @author Patrick Stein
 * @author Chris Kurn
 * @since Version 1.0 - Spring Quarter 2014
 * @see package elevator.common
 * @see import java.util.ArrayList;
 * @see import java.util.Collections;
 * @see import building.common.Person;
 * @see import simulator.Simulator;
 * @see import simulator.common.IllegalParamException;
 * @see import simulator.common.SimulationInformation;
 */

public class ElevatorImpl implements Elevator, Runnable {

    private int defaultFloor = 1;
    private long timeOut;
    private long floorTime;
    private long doorTime;
    private int numFloors;
    @SuppressWarnings("unused")
    private int numPeoplePerElevator;
    private ElevatorDirection direction = ElevatorDirection.IDLE;
    private final int STARTINGFLOOR = 1;
    private int currentFloor;
    private ArrayList<Integer> destinations = new ArrayList<Integer>();
    private ArrayList<Person> elevatorPeople = new ArrayList<Person>();

    private Thread myThread;
    private int elevatorId;
    private boolean elevatorOn = true;

    /**
     * method guarantees that communication happens between threads no caching
     */
    private static volatile int elevatorCount = 1;

    /**
     * method for getting a unique elevatorID
     * 
     * @return returns an integer that is a unique elevatorID
     */
    private static synchronized int getNewElevatorId() {
        return elevatorCount++;
    }

    /**
     * Constructor method that creates the initial objects required to run the
     * elevator.
     * 
     * @param info
     *            corresponds to the DTO containing all of the information about
     *            the simulation.
     * @throws IllegalParamException
     *             Throws an exception if it violates the conditions in each set
     *             method. See each set method for each requirement for each
     *             value. Most must be positive integers.
     */
    public ElevatorImpl(SimulationInformation info)
            throws IllegalParamException {
        this.setTimeout(info.elevatorSleepTime);
        this.setFloorTime(info.floorTime);
        this.setDoorTime(info.doorTime);
        this.setNumFloors(info.numFloors);
        this.setNumpeopleperElevator(info.numPeoplePerElevator);
        this.setNumFloors(info.numFloors);
        this.setCurrentFloor(this.STARTINGFLOOR);
        this.elevatorId = ElevatorImpl.getNewElevatorId();
        this.myThread = new Thread(this);

    }

    @Override
    public void run() {
        try {
            this.controlState();
        } catch (InterruptedException e) {
            e.printStackTrace();
            Thread.currentThread().interrupt(); // restore interrupted status
        }
    }

    @Override
    public ElevatorDirection getDirection() {
        return this.direction;
    }

    @Override
    public boolean destinationsLeft() {
        return !this.getDestinations().isEmpty();
    }

    @Override
    public int getCurrentFloor() {
        return this.currentFloor;
    }

    @Override
    public int getDestination() throws NoNewDestinationException {
        synchronized (this) {
            if (this.getDestinations().isEmpty() == false) {
                return this.getDestinations().get(0);
            }
        }
        throw new NoNewDestinationException(
                "There are no more destinations in the queue.");
    }

    @Override
    public void startElevator() {
        this.myThread.start();
    }

    @Override
    public void stopElevator() {
        this.elevatorOn = false;
    }

    @Override
    public void addFloor(int floor) throws InvalidFloorException {
        this.addFloorHelper(floor);
    }

    @Override
    public void addFloor(int floor, ElevatorDirection dir)
            throws InvalidFloorException {
        if (dir == this.getDirection()
                || this.getDirection() == ElevatorDirection.IDLE) {
            this.addFloor(floor);
        } else {
            throw new InvalidFloorException(
                    "Tried to add a floor request to an elevator going in a different direction.");
        }

    }

    /**
     * Checks if the floor is valid. throw error is it is less than one or it is
     * greater than the number of floors.
     * 
     * @param floor
     *            This floor must satisfy the following conditions: It must be
     *            between 1 and the number of floors in the building. It cannot
     *            be the floor we are currently at. It cannot be a floor that is
     *            in the wrong direction of travel.
     * @throws InvalidFloorException
     *             will throw if the above conditions are not met.
     */
    public synchronized void addFloorHelper(int floor)
            throws InvalidFloorException {
        if ((floor < 1) || floor > this.getNumFloors()) {
            throw new InvalidFloorException(String.format(
                    "The floor must be between 1 and %d.", this.getNumFloors()));
        }
        // BEWARE RACE CONDITIONS WITH THIS STATEMENT
        if (floor == this.getCurrentFloor()) {
            throw new InvalidFloorException(
                    "Cannot add this floor because we are already at it.");
        }
        // The floor must be in the same direction as the current floor
        // destination
        if (floorInSameDirection(floor) == false) {
            throw new InvalidFloorException(
                    "Cannot add a floor in a different direction of travel.");
        }
        // If the destination already exists we want to ignore it / not add it
        if (destinationExists(floor) == false) {
            // add the destination
            this.addFloorToQueue(floor);
            Simulator
                    .getInstance()
                    .logEvent(
                            String.format(
                                    "Elevator %d has added floor: %d. [Floor requests: %s]",
                                    this.getElevatorId(), floor,
                                    this.getFloorRequests()));
        } else {
            Simulator.getInstance().logEvent("This floor already exists.");
        }
    }

    /**
     * A method for adding the floor to a queue
     * 
     * @param floorAdded
     */
    private synchronized void addFloorToQueue(int floorAdded) {
        // Get the list of destinations
        ArrayList<Integer> dests = this.getDestinations();
        // add the floor
        dests.add(floorAdded);
        // make sure it is still sorted
        ElevatorDirection dir = this.getDirection();
        if (dir == ElevatorDirection.UP) {
            // This should be sorted ascending
            Collections.sort(dests);
        } else if (dir == ElevatorDirection.DOWN) {
            // This should be sorted descending
            Collections.sort(dests, Collections.reverseOrder());
        } else {
            this.updateDirection();
        }
        notifyAll();
    }

    /**
     * This method encapsulates all of the behavior associated with an idle
     * elevator.
     * 
     * @throws InterruptedException
     *             throws an interrupted exception if wait() is interrupted.
     */
    private void controlState() throws InterruptedException {
        // Continue forever until we are asked to end
        long currentTimeout = this.getTimeout();
        while (this.elevatorOn) {

            // ask if a destination has been added
            this.moveToAllFloors();
            long waitStartTime = System.currentTimeMillis();
            synchronized (this) {
                try {
                    // TODO Test new variable timeout code
                    wait(currentTimeout);
                } catch (InterruptedException e) {
                    Simulator
                            .getInstance()
                            .logEvent(
                                    String.format(
                                            "Elevator %d has been interrupted. Continuing execution.",
                                            this.getElevatorId()));
                    this.stopElevator();
                }
            }
            // DON'T CALL MOVE DURING A SYNCH BLOCK OR ELEVATORS CANNOT BE ADDED
            // TO THE QUEUE
            // Also calculates the new timeout time
            currentTimeout = this.handleWakeUp(waitStartTime);
        }
    }

    /**
     * Check to see if this floor already exists in the destination queue.
     * 
     * @param floorAdded
     *            the floor you want to check against the queue
     * @return a boolean indicating if the floor already exists in the queue
     */
    private boolean destinationExists(int floorAdded) {
        return this.getDestinations().contains(floorAdded);
    }

    /**
     * A method to determine if the current floor is in the same direction of
     * travel.
     * 
     * @param floorAdded
     *            takes an integer representing the floor that wishes to be
     *            added
     * @return returns a boolean confirming that this floor is on the way to the
     *         current destination.
     */
    private boolean floorInSameDirection(int floorAdded) {
        // if the direction is set to up and the current floor is less than the
        // floor added then we cannot add
        int cf = this.getCurrentFloor();
        if ((this.getDirection() == ElevatorDirection.UP) && cf > floorAdded) {
            return false;
        } else if ((this.getDirection() == ElevatorDirection.DOWN)
                && floorAdded > cf) { // Same thing for down but the floor is
                                      // greater than the above floor
            return false;
        } else {
            // This is fine then. If it is idle. Then everything should be okay.
            return true;
        }
    }

    /**
     * This method retrieves the timeout
     * 
     * @return defaultFloor
     */
    private int getDefaultFloor() {
        return this.defaultFloor;
    }

    /**
     * This method retrieves the destinations from an arraylist
     * 
     * @return destinations
     */
    private ArrayList<Integer> getDestinations() {
        return this.destinations;
    }

    /**
     * This method retrieves the door time
     * 
     * @return the door time
     */
    private long getDoorTime() {
        return this.doorTime;
    }

    /**
     * This method retrieves an elevator id
     * 
     * @return elevator id
     */
    private int getElevatorId() {
        return this.elevatorId;
    }

    /**
     * There is a race condition here that causes an exception to be thrown.
     * Concurrent Error being thrown at runtime in elevator 3. Not sure if it is
     * still happening. Keep testing.
     * 
     * @return a string representing all of the pending floor requests
     */
    private synchronized String getFloorRequests() {
        if (this.destinationsLeft()) {
            return this.getDestinations().toString();
        } else {
            return "";
        }

    }

    /**
     * This method retrieves the floor time
     * 
     * @return floorTime
     */
    private long getFloorTime() {
        return this.floorTime;
    }

    /**
     * This method retrieves the number of floors
     * 
     * @return number of floors
     */
    private int getNumFloors() {
        return this.numFloors;
    }

    /**
     * This method retrieves the timeout
     * 
     * @return the timeout
     */
    private long getTimeout() {
        return this.timeOut;
    }

    /**
     * Handle the timeout behavior. Returns to a default floor
     * 
     * @throws InterruptedException
     *             throws an interrupted exception if the thread is interrupted
     *             during movement to the default floor.
     */
    private long handleWakeUp(long waitStartTime) throws InterruptedException {

        // NEW: Change to make sure the timeout is being variable so the timeout
        // is
        // TODO handle variable timeout time
        // TODO test new variable timeout time
        long newTimeout = System.currentTimeMillis() - waitStartTime;
        // Timeout is a defined if there are no new floors in the destination
        // queue and the current floor is not the default floor
        if (this.destinationsLeft() == false
                && this.getCurrentFloor() != this.getDefaultFloor()) {
            // add default floor to the elevator queue
            Simulator
                    .getInstance()
                    .logEvent(
                            String.format(
                                    "Elevator %d has timed out. Returning to default floor: %d.",
                                    this.elevatorId, this.defaultFloor));
            try {
                this.addFloorHelper(this.getDefaultFloor());
            } catch (InvalidFloorException e) {
                // Even though this floor was added. Don't crash. Just log it
                // and move on.
                Simulator.getInstance().logEvent(
                        "Invalid floor added during a timeout");
            }
            // Since we have reset to the default floor. Let's reset the
            // timeout.
            newTimeout = this.getTimeout();
        } else if (newTimeout >= this.getTimeout()
                && this.getCurrentFloor() == this.getDefaultFloor()) {
            // Handles the case in which the elevator has already timed out
            // before and has timed out again
            // We want to reset the timeout to it's original amount
            newTimeout = this.getTimeout();
        }
        // Try to move to any floor that we can
        this.moveToAllFloors();
        return newTimeout;
    }

    /**
     * A method to move elevator to all requested floors
     * 
     * @throws InterruptedException
     *             throws if the thread is interrupted during execution.
     */
    private void moveToAllFloors() throws InterruptedException {
        while (this.destinationsLeft() == true) {
            // Move and update the destination queue
            try {
                this.moveToNextDestination();
                this.removeMostRecentDestination();
                this.openDoors();
            } catch (NoNewDestinationException e) {
                // No new destinations to be moved to. Let's break for lunch
                break;
            }
        }
        // We are now done moving to all floors in our queue.
        // Update the location so we can handle new requests
        this.updateDirection();
    }

    /**
     * A method to move elevator to the next destination (floor)
     * 
     * @throws InterruptedException
     *             Thrown if the thread is interrupted during Thread.sleep()
     * @throws NoNewDestinationException
     *             This exception is thrown if there are no new destinations to
     *             move to.
     */
    private void moveToNextDestination() throws InterruptedException,
            NoNewDestinationException {
        // get the first destination
        int destination = this.getDestination();
        int curFloor = this.getCurrentFloor();
        int eleId = this.getElevatorId();
        this.updateDirection();
        while (destination != curFloor) {
            Thread.sleep(this.getFloorTime());
            if (this.getDirection() == ElevatorDirection.UP) {
                this.setCurrentFloor(++curFloor);
            } else if (this.getDirection() == ElevatorDirection.DOWN) {
                this.setCurrentFloor(--curFloor);
            } else {
                // We shouldn't be moving
                Simulator.getInstance().logEvent(
                        String.format(
                                "Elevator %d hit an invalid movement state.",
                                eleId));
                return;
            }
            // This is just to keep it updated with the rest of the program.
            curFloor = this.getCurrentFloor();
            // this may change during moving
            destination = this.getDestination();
            Simulator
                    .getInstance()
                    .logEvent(
                            String.format(
                                    "Elevator %d Now at floor: %d. [Floor requests: %s]",
                                    eleId, curFloor, this.getFloorRequests()));

            this.updateDirection();
        }
        Simulator.getInstance().logEvent(
                String.format("Elevator %d now done moving to floor: %d.",
                        eleId, curFloor));
    }

    /**
     * This method opens the doors to the elevator.
     * 
     * @throws InterruptedException
     */
    private void openDoors() throws InterruptedException {
        Simulator.getInstance().logEvent(
                String.format(
                        "The doors are now open in elevator %d on floor: %d",
                        this.getElevatorId(), this.getCurrentFloor()));
        Thread.sleep(this.getDoorTime());
        Simulator.getInstance().logEvent(
                String.format(
                        "The doors are now closed in elevator %d on floor: %d",
                        this.getElevatorId(), this.getCurrentFloor()));
    }

    /**
     * Removes the most recent destination from the queue.
     */
    private void removeMostRecentDestination() {
        // Remove the very first element
        if (this.getDestinations().isEmpty() == false) {
            this.getDestinations().remove(0);
        }
    }

    /**
     * This method sets the current floor
     */
    private synchronized void setCurrentFloor(int i) {
        this.currentFloor = i;
    }

    /**
     * This method sets the door time
     * 
     * @param dt
     *            the door time integer must be greater than or equal to 0.
     * @throws IllegalParamException
     *             Thrown if the above parameter condition is not met.
     */
    private void setDoorTime(long dt) throws IllegalParamException {
        // TODO auto change check for valid
        if (dt <= 0) {
            throw new IllegalParamException(
                    "The doortime must be greater than 0.");
        }
        this.doorTime = dt;
    }

    /**
     * This method sets the floor time
     * 
     * @param fo
     *            the time between floors must be greater than or equal to 0.
     * @throws IllegalParamException
     *             Thrown if the above parameter condition is not met.
     */
    private void setFloorTime(long fo) throws IllegalParamException {
        if (fo <= 0) {
            throw new IllegalParamException(
                    "The floor time should not less than or equal to 0.");
        }
        this.floorTime = fo;
    }

    /**
     * This method sets the number of floors in the building
     * 
     * @param nf
     *            the number of floors must be greater than or equal to 0
     * @throws IllegalParamException
     *             Thrown if the above parameter condition is not met.
     */
    private void setNumFloors(int nf) throws IllegalParamException {
        if (nf <= 0) {
            throw new IllegalParamException(
                    "Number of floors cannot be less than or equal to 0.");
        }
        this.numFloors = nf;

    }

    /**
     * This method sets the number of people in the elevator
     * 
     * @param num
     *            the number of people must be greater than or equal to 0.
     * @throws IllegalParamException
     *             Thrown if the above parameter condition is not met.
     */
    private void setNumpeopleperElevator(int num) throws IllegalParamException {
        if (num <= 0) {
            throw new IllegalParamException(
                    "Number of people per elevator must be greater than zero.");
        }
        this.numPeoplePerElevator = num;
    }

    /**
     * This method sets the time out
     * 
     * @param to
     *            the timeout must be greater than or equal to 0.
     * @throws IllegalParamException
     *             Thrown if the above parameter condition is not met.
     */
    private void setTimeout(long to) throws IllegalParamException {
        if (to <= 0) {
            throw new IllegalParamException(
                    "Timeout time cannot be less than or equal to zero.");
        }
        this.timeOut = to;
    }

    /**
     * A method for determining the direction based on the new floor being added
     * 
     * @param floorAdded
     *            the floor that is being added
     */
    private void updateDirection() {
        int cf = this.getCurrentFloor();
        int destination = 0;
        try {
            destination = this.getDestination();
            if (cf > destination) {
                this.direction = ElevatorDirection.DOWN;
            } else if (cf < destination) {
                this.direction = ElevatorDirection.UP;
            }
        } catch (NoNewDestinationException e) {
            this.direction = ElevatorDirection.IDLE;
        }
    }

    @Override
    public void enterElevator(Person p) {
        // TODO Auto-generated method stub

    }

    @Override
    public boolean doorsOpen() {
        // TODO Auto-generated method stub
        return false;
    }

}
