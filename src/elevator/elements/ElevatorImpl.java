package elevator.elements;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

import simulator.Simulator;
import simulator.common.IllegalParamException;
import simulator.common.SimulationInformation;
import building.Building;
import building.common.Person;
import elevator.common.ElevatorDirection;
import elevator.common.InvalidFloorException;
import elevator.common.NoNewDestinationException;

/**
 * Description: Elevator implementation class.
 * 
 * @author Patrick Stein
 * @author Chris Kurn
 * @since Version 1.0 - Spring Quarter 2014
 */

public class ElevatorImpl implements Elevator, Runnable {

    /** The default floor. */
    private int defaultFloor;

    /** The current time out. */
    private long currentTimeOut;

    /** The max time out time. */
    private long maxTimeOutTime;

    /** The floor time. */
    private long floorTime;

    /** The door time. */
    private long doorTime;

    /** The num floors. */
    private int numFloors;

    /** The num people per elevator. */
    private int numPeoplePerElevator;

    /** The direction. */
    private ElevatorDirection direction = ElevatorDirection.IDLE;

    /** The startingfloor. */
    private final int STARTINGFLOOR = 1;

    /** The current floor. */
    private int currentFloor;

    /** The destinations. */
    private ArrayList<Integer> destinations = new ArrayList<Integer>();

    /** The elevator people. */
    private ArrayList<Person> elevatorPeople = new ArrayList<Person>();

    /** The my thread. */
    private Thread myThread;

    /** The elevator id. */
    private int elevatorId;

    /** The elevator on. */
    private boolean elevatorOn = true;

    /** method guarantees that communication happens between threads no caching. */
    private static volatile int elevatorCount = 1;

    /**
     * method for getting a unique elevatorID.
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
        setCurrentTimeout(info.elevatorSleepTime);
        setMaxTimeoutTime(info.elevatorSleepTime);
        setFloorTime(info.floorTime);
        setDoorTime(info.doorTime);
        setNumFloors(info.numFloors);
        setNumpeopleperElevator(info.numPeoplePerElevator);
        setNumFloors(info.numFloors);
        setCurrentFloor(STARTINGFLOOR);
        setDefaultFloor(info.defaultElevatorFlr);
        elevatorId = ElevatorImpl.getNewElevatorId();
        myThread = new Thread(this);

    }

    /**
     * Private set method for the default floor assignment
     * @param flr the new value of default floor.
     * @throws IllegalParamException This exception is thrown if the flr parameters
     * exceeds the number of floors for this building or is less than 1.
     */
    private void setDefaultFloor(int flr) throws IllegalParamException{
        
        if(flr < 1 || flr >= getNumFloors()){
            String msg = String.format("The default floor cannot be less than 1 or greater than %d.", getNumFloors());
            throw new IllegalParamException(msg);
        }
        defaultFloor = flr;
        
        
    }

    /**
     * Public method add Floor.
     */
    @Override
    public void addFloor(int floor) throws InvalidFloorException {
        addFloorHelper(floor);
    }

    /*
     * (non-Javadoc)
     * 
     * @see elevator.common.Elevator#addFloor(int,
     * elevator.common.ElevatorDirection)
     */
    @Override
    public void addFloor(int floor, ElevatorDirection dir)
            throws InvalidFloorException {
        if (dir == getDirection() || getDirection() == ElevatorDirection.IDLE) {
            addFloor(floor);
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
    public void addFloorHelper(int floor) throws InvalidFloorException {
        if ((floor < 1) || floor > getNumFloors()) {
            throw new InvalidFloorException(String.format(
                    "The floor must be between 1 and %d.", getNumFloors()));
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
            addFloorToQueue(floor);
            Simulator
                    .getInstance()
                    .logEvent(
                            String.format(
                                    "Elevator %d has added floor: %d. [Floor requests: %s]",
                                    getElevatorId(), floor, getFloorRequests()));
        }
    }

    /**
     * A method for adding the floor to a queue.
     * 
     * @param floorAdded
     *            the floor added
     */
    private synchronized void addFloorToQueue(int floorAdded) {
        // Get the list of destinations
        ArrayList<Integer> dests = getDestinations();
        // add the floor
        dests.add(floorAdded);
        // make sure it is still sorted
        ElevatorDirection dir = getDirection();
        if (dir == ElevatorDirection.UP) {
            // This should be sorted ascending
            Collections.sort(dests);
        } else if (dir == ElevatorDirection.DOWN) {
            // This should be sorted descending
            Collections.sort(dests, Collections.reverseOrder());
        } else {
            updateDirection();
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
        while (elevatorOn) {

            // ask if a destination has been added
            moveToAllFloors();
            long waitStartTime = System.currentTimeMillis();
            synchronized (this) {
                try {
                    wait(getCurrentTimeout());
                } catch (InterruptedException e) {
                    Simulator
                            .getInstance()
                            .logEvent(
                                    String.format(
                                            "Elevator %d has been interrupted. Continuing execution.",
                                            getElevatorId()));
                    stopElevator();
                }
            }
            // DON'T CALL MOVE DURING A SYNCH BLOCK OR ELEVATORS CANNOT BE ADDED
            // TO THE QUEUE
            // Also calculates the new timeout time
            handleWakeUp(waitStartTime);
        }
    }

    /**
     * Check to see if this floor already exists in the destination queue.
     * 
     * @param floorAdded
     *            the floor you want to check against the queue
     * @return a boolean indicating if the floor already exists in the queue
     */
    private synchronized boolean destinationExists(int floorAdded) {
        return getDestinations().contains(floorAdded);
    }

    /*
     * (non-Javadoc)
     * 
     * @see elevator.common.Elevator#destinationsLeft()
     */
    @Override
    public synchronized boolean destinationsLeft() {
        return !getDestinations().isEmpty();
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
        int cf = getCurrentFloor();
        if ((getDirection() == ElevatorDirection.UP) && cf > floorAdded) {
            return false;
        } else if ((getDirection() == ElevatorDirection.DOWN)
                && floorAdded > cf) { // Same thing for down but the floor is
                                      // greater than the above floor
            return false;
        } else {
            // This is fine then. If it is idle. Then everything should be okay.
            return true;
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see elevator.common.Elevator#getCurrentFloor()
     */
    @Override
    public int getCurrentFloor() {
        return currentFloor;
    }

    /**
     * This method retrieves the timeout.
     * 
     * @return the timeout
     */
    private long getCurrentTimeout() {
        return currentTimeOut;
    }

    /**
     * This method retrieves the timeout.
     * 
     * @return defaultFloor
     */
    private int getDefaultFloor() {
        return defaultFloor;
    }

    /*
     * (non-Javadoc)
     * 
     * @see elevator.common.Elevator#getDestination()
     */
    @Override
    public int getDestination() throws NoNewDestinationException {
        synchronized (this) {
            if (getDestinations().isEmpty() == false) {
                return getDestinations().get(0);
            }
        }
        throw new NoNewDestinationException(
                "There are no more destinations in the queue.");
    }

    /**
     * This method retrieves the destinations from an arraylist.
     * 
     * @return destinations
     */
    private ArrayList<Integer> getDestinations() {
        return destinations;
    }

    /*
     * (non-Javadoc)
     * 
     * @see elevator.common.Elevator#getDirection()
     */
    @Override
    public ElevatorDirection getDirection() {
        return direction;
    }

    /**
     * This method retrieves the door time.
     * 
     * @return the door time
     */
    private long getDoorTime() {
        return doorTime;
    }

    /*
     * (non-Javadoc)
     * 
     * @see elevator.common.Elevator#getElevatorId()
     */
    @Override
    public int getElevatorId() {
        return elevatorId;
    }

    private ArrayList<Person> getElevatorPeople() {
        return elevatorPeople;
    }

    /**
     * There is a race condition here that causes an exception to be thrown.
     * Concurrent Error being thrown at runtime in elevator 3. Not sure if it is
     * still happening. Keep testing.
     * 
     * @return a string representing all of the pending floor requests
     */
    private String getFloorRequests() {
        if (destinationsLeft()) {
            synchronized (this) {
                return getDestinations().toString();
            }
        } else {
            return "";
        }

    }

    /**
     * This method retrieves the floor time.
     * 
     * @return floorTime
     */
    private long getFloorTime() {
        return floorTime;
    }

    private int getMaxNumberOfPeople() {
        return numPeoplePerElevator;
    }

    /**
     * Gets the max timeout.
     * 
     * @return the max timeout
     */
    private long getMaxTimeout() {
        return maxTimeOutTime;
    }

    /**
     * Asks all of the people in the elevator for their destinations. And adds
     * any valid ones to the queue. If invalid, will reenter the floor they were
     * just on.
     */
    private void getNewDestinationsFromPeople() {

        ArrayList<Person> currentPeople = getElevatorPeople();
        int curFloor = getCurrentFloor();
        Iterator<Person> p = currentPeople.iterator();

        while (p.hasNext()) {
            Person person = p.next();
            int pid = person.getPersonId();
            int destFloor = person.getDestinationFloor();

            try {
                addFloor(destFloor);
            } catch (InvalidFloorException e) {
                String event = String
                        .format("Person %d tried to request his destination %d. In elevator %d and failed. Moving back to floor %d.",
                                pid, destFloor, getElevatorId(), curFloor);
                Simulator.getInstance().logEvent(event);
                try {
                    Building.getInstance().enterFloor(person, curFloor);
                } catch (IllegalParamException | InvalidFloorException e1) {
                    String eve = String
                            .format("Elevator %d tried to move a person from the elevator to floor %d."
                                    + " This operation failed. Removing person and continuing on.",
                                    getElevatorId(), curFloor);
                    Simulator.getInstance().logEvent(eve);

                    p.remove();

                }
            }

        }
    }

    /**
     * This method retrieves the number of floors.
     * 
     * @return number of floors
     */
    private int getNumFloors() {
        return numFloors;
    }

    /**
     * Handle the timeout behavior. Returns to a default floor
     * 
     * @param waitStartTime
     *            the wait start time
     * @throws InterruptedException
     *             throws an interrupted exception if the thread is interrupted
     *             during movement to the default floor.
     */
    private void handleWakeUp(long waitStartTime) throws InterruptedException {

        // NEW: Change to make sure the timeout is being variable so the timeout
        // is
        long newTimeout = System.currentTimeMillis() - waitStartTime;
        // Timeout is a defined if there are no new floors in the destination
        // queue and the current floor is not the default floor
        if (destinationsLeft()) {
            // if destination left lets return and reset the current timeout
            try {
                setCurrentTimeout(getMaxTimeout());
            } catch (IllegalParamException e) {
                Simulator
                        .getInstance()
                        .logEvent(
                                "Unable to set timeout time to a new value in the elevator class. Continuing with execution.");
            }
            return;
        } else if (destinationsLeft() == false && newTimeout < getMaxTimeout()) {
            // I was woken up unexpectedly with nothing to do
            try {
                setCurrentTimeout(newTimeout);
            } catch (IllegalParamException e) {
                Simulator
                        .getInstance()
                        .logEvent(
                                "Unable to set timeout time to a new value in the elevator class. Continuing with execution.");
            }
            return;
        } else if (destinationsLeft() == false // if there are no
                                               // destinations
                && newTimeout >= getMaxTimeout() // if the delta time has
                                                 // exceeded the maximum
                                                 // timeout time allowed
                && getCurrentFloor() != getDefaultFloor()) { // if the
                                                             // current
                                                             // floor
                                                             // is not
                                                             // the
                                                             // default
                                                             // floor
            // add default floor to the elevator queue
            // and move to it
            Simulator
                    .getInstance()
                    .logEvent(
                            String.format(
                                    "Elevator %d has timed out. Returning to default floor: %d.",
                                    getElevatorId(), getDefaultFloor()));
            try {
                addFloorHelper(getDefaultFloor());
            } catch (InvalidFloorException e) {
                // Even though this floor was added. Don't crash. Just log it
                // and move on.
                Simulator.getInstance().logEvent(
                        "Invalid floor added during a timeout");
            }
            // Since we have reset to the default floor. Let's reset the
            // timeout.

        }
    }

    /**
     * A method to move elevator to all requested floors.
     * 
     * @throws InterruptedException
     *             throws if the thread is interrupted during execution.
     */
    private void moveToAllFloors() throws InterruptedException {

        if (destinationsLeft() == false) {
            updateDirection();
            return;
        } else {
            while (destinationsLeft() == true) {
                // Move and update the destination queue
                try {
                    moveToNextDestination();
                    removeMostRecentDestination();
                    updateDirection();
                    openDoors();
                } catch (NoNewDestinationException e) {
                    // No new destinations to be moved to. Let's break for lunch
                    break;
                }
            }
            // We are now done moving to all floors in our queue.
            // Update the location so we can handle new requests
            updateDirection();
            // Set the max timeout time again
            try {
                setCurrentTimeout(getMaxTimeout());
            } catch (IllegalParamException e) {
                Simulator
                        .getInstance()
                        .logEvent(
                                "Unable to set timeout time to a new value in the elevator class." +
                                " Continuing with execution.");
            }
        }

    }

    /**
     * A method to move elevator to the next destination (floor).
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
        int destination = getDestination();
        int curFloor = getCurrentFloor();
        int eleId = getElevatorId();
        updateDirection();
        while (destination != curFloor) {
            Thread.sleep(getFloorTime());
            // if it is up, increment the floor up
            // if it is down, then increment the floor down
            if (getDirection() == ElevatorDirection.UP) {
                setCurrentFloor(++curFloor);
            } else if (getDirection() == ElevatorDirection.DOWN) {
                setCurrentFloor(--curFloor);
            } else {
                // We shouldn't be moving
                Simulator.getInstance().logEvent(
                        String.format(
                                "Elevator %d hit an invalid movement state.",
                                eleId));
                return;
            }
            // This is just to keep it updated with the rest of the program.
            curFloor = getCurrentFloor();
            // this may change during moving
            destination = getDestination();
            Simulator
                    .getInstance()
                    .logEvent(
                            String.format(
                                    "Elevator %d Now at floor: %d. [Floor requests: %s]",
                                    eleId, curFloor, getFloorRequests()));

            updateDirection();
        }
        Simulator.getInstance().logEvent(
                String.format("Elevator %d now done moving to floor: %d.",
                        eleId, curFloor));
    }

    /**
     * This method opens the doors to the elevator.
     * 
     * @throws InterruptedException
     *             thrown if someone interrupts the elevator during its door
     *             open time
     */
    private void openDoors() {
        ArrayList<Person> currentPeople = getElevatorPeople();
        int curFloor = getCurrentFloor();
        ElevatorDirection dir = getDirection();

        ElevatorPeoplePickup delegate = ElevatorPeoplePickupFactory.build(
                currentPeople, dir, getMaxNumberOfPeople());

        String event = String.format(
                "Elevator %d now unloading people on floor %d.",
                getElevatorId(), curFloor);
        Simulator.getInstance().logEvent(event);
        delegate.unloadPeople(curFloor);

        event = String.format("Elevator %d now accepting people on floor %d.",
                getElevatorId(), getCurrentFloor());
        Simulator.getInstance().logEvent(event);
        try {
            delegate.loadPeople(curFloor);
        } catch (InvalidFloorException e1) {
            event = String
                    .format("Elevator %d unable to load people from floor %d. Continuing with execution.",
                            getElevatorId(), curFloor);
        }
        // Ask for new destinations
        getNewDestinationsFromPeople();
        try {
            Thread.sleep(getDoorTime());
        } catch (InterruptedException e) {
            event = String
                    .format("Elevator %d's thread has been interrupted during open door time. Moving on.",
                            getElevatorId());
            Simulator.getInstance().logEvent(event);
        }
        Simulator.getInstance().logEvent(
                String.format(
                        "The doors are now closed in elevator %d on floor: %d",
                        getElevatorId(), getCurrentFloor()));
    }

    /**
     * Removes the most recent destination from the queue.
     */
    private synchronized void removeMostRecentDestination() {
        // Remove the very first element
        if (getDestinations().isEmpty() == false) {
            getDestinations().remove(0);
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Runnable#run()
     */
    @Override
    public void run() {
        try {
            controlState();
        } catch (InterruptedException e) {
            e.printStackTrace();
            Thread.currentThread().interrupt(); // restore interrupted status
        }
    }

    /**
     * This method sets the current floor.
     * 
     * @param i
     *            the new current floor
     */
    private synchronized void setCurrentFloor(int i) {
        currentFloor = i;
    }

    /**
     * This method sets the time out.
     * 
     * @param to
     *            the timeout must be greater than or equal to 0.
     * @throws IllegalParamException
     *             Thrown if the above parameter condition is not met.
     */
    private void setCurrentTimeout(long to) throws IllegalParamException {
        if (to <= 0) {
            throw new IllegalParamException(
                    "Current timeout time cannot be less than or equal to zero.");
        }
        currentTimeOut = to;
    }

    /**
     * This method sets the door time.
     * 
     * @param dt
     *            the door time integer must be greater than or equal to 0.
     * @throws IllegalParamException
     *             Thrown if the above parameter condition is not met.
     */
    private void setDoorTime(long dt) throws IllegalParamException {
        if (dt <= 0) {
            throw new IllegalParamException(
                    "The doortime must be greater than 0.");
        }
        doorTime = dt;
    }

    /**
     * This method sets the floor time.
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
        floorTime = fo;
    }

    /**
     * Sets the max timeout time.
     * 
     * @param to
     *            the new max timeout time
     * @throws IllegalParamException
     *             thrown if to is less than or equal to 0.
     */
    private void setMaxTimeoutTime(long to) throws IllegalParamException {
        if (to <= 0) {
            throw new IllegalParamException(
                    "Max timeout time cannot be less than or equal to zero.");
        }
        maxTimeOutTime = to;
    }

    /**
     * This method sets the number of floors in the building.
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
        numFloors = nf;

    }

    /**
     * This method sets the number of people in the elevator.
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
        numPeoplePerElevator = num;
    }

    /*
     * (non-Javadoc)
     * 
     * @see elevator.common.Elevator#startElevator()
     */
    @Override
    public void startElevator() {
        elevatorOn = true;
        myThread.start();
    }

    /*
     * (non-Javadoc)
     * 
     * @see elevator.common.Elevator#stopElevator()
     */
    @Override
    public void stopElevator() {
        elevatorOn = false;
    }

    /**
     * A method for determining the direction based on the new floor being
     * added.
     */
    private synchronized void updateDirection() {
        int cf = getCurrentFloor();
        int destination = 0;
        try {
            destination = getDestination();
            if (cf > destination) {
                direction = ElevatorDirection.DOWN;
            } else if (cf < destination) {
                direction = ElevatorDirection.UP;
            }
        } catch (NoNewDestinationException e) {
            direction = ElevatorDirection.IDLE;
        }
    }

}
