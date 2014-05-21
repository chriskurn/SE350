package elevator.common;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

import building.Building;
import building.common.Person;

import simulator.Simulator;
import simulator.common.IllegalParamException;
import simulator.common.SimulationInformation;

/**
 * Description: Elevator implementation class.
 *
 * @author Patrick Stein
 * @author Chris Kurn
 * @since Version 1.0 - Spring Quarter 2014
 */

public class ElevatorImpl implements Elevator, Runnable {

    /** The default floor. */
    private int defaultFloor = 1;
    
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
    
    /** Is the elevator moving?*/
    private boolean isMoving = false;
    
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
        this.setCurrentTimeout(info.elevatorSleepTime);
        this.setMaxTimeoutTime(info.elevatorSleepTime);
        this.setFloorTime(info.floorTime);
        this.setDoorTime(info.doorTime);
        this.setNumFloors(info.numFloors);
        this.setNumpeopleperElevator(info.numPeoplePerElevator);
        this.setNumFloors(info.numFloors);
        this.setCurrentFloor(this.STARTINGFLOOR);
        this.elevatorId = ElevatorImpl.getNewElevatorId();
        this.myThread = new Thread(this);

    }

    /* (non-Javadoc)
     * @see java.lang.Runnable#run()
     */
    @Override
    public void run() {
        try {
            this.controlState();
        } catch (InterruptedException e) {
            e.printStackTrace();
            Thread.currentThread().interrupt(); // restore interrupted status
        }
    }

    /* (non-Javadoc)
     * @see elevator.common.Elevator#getDirection()
     */
    @Override
    public ElevatorDirection getDirection() {
        return this.direction;
    }

    /* (non-Javadoc)
     * @see elevator.common.Elevator#getElevatorId()
     */
    @Override
    public int getElevatorId() {
        return this.elevatorId;
    }

    /* (non-Javadoc)
     * @see elevator.common.Elevator#destinationsLeft()
     */
    @Override
    public synchronized boolean destinationsLeft() {
        return !this.getDestinations().isEmpty();
    }

    /* (non-Javadoc)
     * @see elevator.common.Elevator#getCurrentFloor()
     */
    @Override
    public int getCurrentFloor() {
        return this.currentFloor;
    }

    /* (non-Javadoc)
     * @see elevator.common.Elevator#getDestination()
     */
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

    /* (non-Javadoc)
     * @see elevator.common.Elevator#startElevator()
     */
    @Override
    public void startElevator() {
        elevatorOn = true;
        this.myThread.start();
    }

    /* (non-Javadoc)
     * @see elevator.common.Elevator#stopElevator()
     */
    @Override
    public void stopElevator() {
        this.elevatorOn = false;
    }

    /* (non-Javadoc)
     * @see elevator.common.Elevator#addFloor(int)
     */
    @Override
    public void addFloor(int floor) throws InvalidFloorException {
        this.addFloorHelper(floor);
    }

    /* (non-Javadoc)
     * @see elevator.common.Elevator#addFloor(int, elevator.common.ElevatorDirection)
     */
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
    public void addFloorHelper(int floor) throws InvalidFloorException {
        if ((floor < 1) || floor > this.getNumFloors()) {
            throw new InvalidFloorException(String.format(
                    "The floor must be between 1 and %d.", this.getNumFloors()));
        }
        // BEWARE RACE CONDITIONS WITH THIS STATEMENT
        // TODO test this I am changing it to open the doors if they are on the same floor
        if (floor == this.getCurrentFloor()) {
            openDoors();
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
        }
    }

    /**
     * A method for adding the floor to a queue.
     *
     * @param floorAdded the floor added
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
        while (this.elevatorOn) {

            // ask if a destination has been added
            this.moveToAllFloors();
            long waitStartTime = System.currentTimeMillis();
            synchronized (this) {
                try {
                    wait(this.getCurrentTimeout());
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
            this.handleWakeUp(waitStartTime);
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
     * This method retrieves the timeout.
     *
     * @return defaultFloor
     */
    private int getDefaultFloor() {
        return this.defaultFloor;
    }

    /**
     * This method retrieves the destinations from an arraylist.
     *
     * @return destinations
     */
    private ArrayList<Integer> getDestinations() {
        return this.destinations;
    }

    /**
     * This method retrieves the door time.
     *
     * @return the door time
     */
    private long getDoorTime() {
        return this.doorTime;
    }

    /**
     * There is a race condition here that causes an exception to be thrown.
     * Concurrent Error being thrown at runtime in elevator 3. Not sure if it is
     * still happening. Keep testing.
     * 
     * @return a string representing all of the pending floor requests
     */
    private String getFloorRequests() {
        if (this.destinationsLeft()) {
            synchronized (this) {
                return this.getDestinations().toString();
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
        return this.floorTime;
    }

    /**
     * This method retrieves the number of floors.
     *
     * @return number of floors
     */
    private int getNumFloors() {
        return this.numFloors;
    }

    /**
     * This method retrieves the timeout.
     *
     * @return the timeout
     */
    private long getCurrentTimeout() {
        return this.currentTimeOut;
    }

    /**
     * Gets the max timeout.
     *
     * @return the max timeout
     */
    private long getMaxTimeout() {
        return this.maxTimeOutTime;
    }
    private ArrayList<Person> getElevatorPeople() {
        return elevatorPeople;
    }

    /**
     * Handle the timeout behavior. Returns to a default floor
     *
     * @param waitStartTime the wait start time
     * @throws InterruptedException             throws an interrupted exception if the thread is interrupted
     *             during movement to the default floor.
     */
    private void handleWakeUp(long waitStartTime) throws InterruptedException {

        // NEW: Change to make sure the timeout is being variable so the timeout
        // is
        long newTimeout = System.currentTimeMillis() - waitStartTime;
        // Timeout is a defined if there are no new floors in the destination
        // queue and the current floor is not the default floor
        if (this.destinationsLeft()) {
            // if destination left lets return and reset the current timeout
            try {
                this.setCurrentTimeout(this.getMaxTimeout());
            } catch (IllegalParamException e) {
                Simulator
                        .getInstance()
                        .logEvent(
                                "Unable to set timeout time to a new value in the elevator class. Continuing with execution.");
            }
            return;
        } else if (this.destinationsLeft() == false
                && newTimeout < this.getMaxTimeout()) {
            // I was woken up unexpectedly with nothing to do
            try {
                this.setCurrentTimeout(newTimeout);
            } catch (IllegalParamException e) {
                Simulator
                        .getInstance()
                        .logEvent(
                                "Unable to set timeout time to a new value in the elevator class. Continuing with execution.");
            }
            return;
        } else if (this.destinationsLeft() == false // if there are no
                                                    // destinations
                && newTimeout >= this.getMaxTimeout() // if the delta time has
                                                      // exceeded the maximum
                                                      // timeout time allowed
                && this.getCurrentFloor() != this.getDefaultFloor()) { // if the
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

        }
    }

    /**
     * A method to move elevator to all requested floors.
     *
     * @throws InterruptedException             throws if the thread is interrupted during execution.
     */
    private void moveToAllFloors() throws InterruptedException {

        if (this.destinationsLeft() == false) {
            this.updateDirection();
            return;
        } else {
            while (this.destinationsLeft() == true) {
                // Move and update the destination queue
                try {
                    this.moveToNextDestination();
                    this.removeMostRecentDestination();
                    this.updateDirection();
                    this.openDoors();
                } catch (NoNewDestinationException e) {
                    // No new destinations to be moved to. Let's break for lunch
                    break;
                }
            }
            // We are now done moving to all floors in our queue.
            // Update the location so we can handle new requests
            this.updateDirection();
            // Set the max timeout time again
            try {
                this.setCurrentTimeout(this.getMaxTimeout());
            } catch (IllegalParamException e) {
                Simulator
                        .getInstance()
                        .logEvent(
                                "Unable to set timeout time to a new value in the elevator class. Continuing with execution.");
            }
        }

    }

    /**
     * A method to move elevator to the next destination (floor).
     *
     * @throws InterruptedException             Thrown if the thread is interrupted during Thread.sleep()
     * @throws NoNewDestinationException             This exception is thrown if there are no new destinations to
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
            // if it is up, increment the floor up
            // if it is down, then increment the floor down
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
     * @throws InterruptedException the interrupted exception
     */
    private void openDoors() {
        Simulator.getInstance().logEvent(
                String.format(
                        "The doors are now open in elevator %d on floor: %d",
                        this.getElevatorId(), this.getCurrentFloor()));
        unloadPeople();
        loadPeople();
        //Ask for new destinations
        getNewDestinationsFromPeople();
        try {
            Thread.sleep(this.getDoorTime());
        } catch (InterruptedException e) {
            String event = String.format("Elevator %d's thread has been interrupted during open door time. Moving on.", getElevatorId());
            Simulator.getInstance().logEvent(event);
        }
        Simulator.getInstance().logEvent(
                String.format(
                        "The doors are now closed in elevator %d on floor: %d",
                        this.getElevatorId(), this.getCurrentFloor()));
    }

    private void getNewDestinationsFromPeople() {
        
        ArrayList<Person> currentPeople = getElevatorPeople();
        int curFloor = getCurrentFloor();
        Iterator<Person> p = currentPeople.iterator();
        
        while(p.hasNext()){
            Person person = p.next();
            int pid = person.getPersonId();
            int destFloor = person.getDestinationFloor();
            
            try {
                this.addFloor(destFloor);
            } catch (InvalidFloorException e) {
                String event = String.format("Person %d tried to request his destination %d." +
                        " in elevator %d and failed. Moving back to floor %d.", pid,destFloor,getElevatorId(),curFloor);
                try {
                    Building.getInstance().enterFloor(person,curFloor);
                } catch (IllegalParamException | InvalidFloorException e1) {
                    String eve = String.format("Elevator %d tried to move a person from the elevator to floor %d." +
                            " This operation failed. Removing person and continuing on.", getElevatorId(),curFloor);
                    Simulator.getInstance().logEvent(eve);
                    // TODO add to lost  people pile
                }
            }
            
        }
        
    }

    private void unloadPeople() {
        //TODO delegate this
        int curFloor = getCurrentFloor();
        ElevatorDirection dir = getDirection();
        ArrayList<Person> currentPeople = getElevatorPeople();
        
        String event = String.format(
                "Elevator %d now unloading people on floor %d.",
                getElevatorId(), curFloor, dir);
        Simulator.getInstance().logEvent(event);
        
        Iterator<Person> p = currentPeople.iterator();
        
        while(p.hasNext()){
            //is this their floor?
            Person person = p.next();
            if(person.getDestinationFloor() == curFloor){
                try {
                    Building.getInstance().enterFloor(person, curFloor);
                } catch (IllegalParamException | InvalidFloorException e) {
                    String eve = String.format("Elevator %d tried to move a person from the elevator to floor %d. This operation failed. Removing person and continuing on.", getElevatorId(),curFloor);
                    Simulator.getInstance().logEvent(eve);
                }
                // TODO java.util.ConcurrentModificationExceptiom
                p.remove();
            }
        }
        
    }

    private void loadPeople() {
        //TODO delegate this
        // Ask building to load in people for this floor
        int curFloor = getCurrentFloor();
        ElevatorDirection dir = getDirection();
        ArrayList<Person> currentFriends = getElevatorPeople();
        
        String event = String.format(
                "Elevator %d now loading people on floor %d for direction %s",
                getElevatorId(), curFloor, dir);
        Simulator.getInstance().logEvent(event);     
        
        //Let's get those people on the floor!
        try {
            ArrayList<Person> newFriends = Building.getInstance().loadPeople(curFloor,dir);
            //TODO add a check to make sure the elevator is overflowing
            currentFriends.addAll(newFriends);
            
        } catch (InvalidFloorException e) {
            Simulator.getInstance().logEvent(String.format("Elevator %d unable to load people on floor %d.", getElevatorId(),curFloor));
        }
    }

    /**
     * Removes the most recent destination from the queue.
     */
    private synchronized void removeMostRecentDestination() {
        // Remove the very first element
        if (this.getDestinations().isEmpty() == false) {
            this.getDestinations().remove(0);
        }
    }

    /**
     * @return the isMoving
     */
    public boolean isMoving() {
        return isMoving;
    }

    /**
     * @param isMoving the isMoving to set
     */
    public void setMoving(boolean isMoving) {
        this.isMoving = isMoving;
    }

    /**
     * This method sets the current floor.
     *
     * @param i the new current floor
     */
    private synchronized void setCurrentFloor(int i) {
        this.currentFloor = i;
    }

    /**
     * This method sets the door time.
     *
     * @param dt            the door time integer must be greater than or equal to 0.
     * @throws IllegalParamException             Thrown if the above parameter condition is not met.
     */
    private void setDoorTime(long dt) throws IllegalParamException {
        if (dt <= 0) {
            throw new IllegalParamException(
                    "The doortime must be greater than 0.");
        }
        this.doorTime = dt;
    }

    /**
     * This method sets the floor time.
     *
     * @param fo            the time between floors must be greater than or equal to 0.
     * @throws IllegalParamException             Thrown if the above parameter condition is not met.
     */
    private void setFloorTime(long fo) throws IllegalParamException {
        if (fo <= 0) {
            throw new IllegalParamException(
                    "The floor time should not less than or equal to 0.");
        }
        this.floorTime = fo;
    }

    /**
     * This method sets the number of floors in the building.
     *
     * @param nf            the number of floors must be greater than or equal to 0
     * @throws IllegalParamException             Thrown if the above parameter condition is not met.
     */
    private void setNumFloors(int nf) throws IllegalParamException {
        if (nf <= 0) {
            throw new IllegalParamException(
                    "Number of floors cannot be less than or equal to 0.");
        }
        this.numFloors = nf;

    }

    /**
     * This method sets the number of people in the elevator.
     *
     * @param num            the number of people must be greater than or equal to 0.
     * @throws IllegalParamException             Thrown if the above parameter condition is not met.
     */
    private void setNumpeopleperElevator(int num) throws IllegalParamException {
        if (num <= 0) {
            throw new IllegalParamException(
                    "Number of people per elevator must be greater than zero.");
        }
        this.numPeoplePerElevator = num;
    }

    /**
     * This method sets the time out.
     *
     * @param to            the timeout must be greater than or equal to 0.
     * @throws IllegalParamException             Thrown if the above parameter condition is not met.
     */
    private void setCurrentTimeout(long to) throws IllegalParamException {
        if (to <= 0) {
            throw new IllegalParamException(
                    "Current timeout time cannot be less than or equal to zero.");
        }
        this.currentTimeOut = to;
    }

    /**
     * Sets the max timeout time.
     *
     * @param to the new max timeout time
     * @throws IllegalParamException the illegal param exception
     */
    private void setMaxTimeoutTime(long to) throws IllegalParamException {
        if (to <= 0) {
            throw new IllegalParamException(
                    "Max timeout time cannot be less than or equal to zero.");
        }
        this.maxTimeOutTime = to;
    }

    /**
     * A method for determining the direction based on the new floor being added.
     */
    private synchronized void updateDirection() {
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

}
