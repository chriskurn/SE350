package building.common;

import simulator.Simulator;
import simulator.common.IllegalParamException;
import elevator.common.ElevatorDirection;
import elevator.control.ElevatorController;

/**
 * Description: PersonImpl.
 * 
 * @author Patrick Stein
 * @author Chris Kurn
 * @since Version 1.0 - Spring Quarter 2014
 */

public class PersonImpl implements Person {

    private boolean errorOccured;

    /** The start floor. */
    private int startFloor;

    /** The destination floor. */
    private int destinationFloor;

    /** The current floor. */
    private int currentFloor;

    /** The person id. */
    private int personId;

    /** The start time. */
    private long startTime;

    /** The elevator entered time. */
    private long elevatorEnteredTime;

    /** The finished time. */
    private long finishedTime;

    /** The person count. */
    private static volatile int personCount = 1;

    /**
     * method for getting a unique personId.
     * 
     * @return returns an integer that is a unique personId
     */
    private static synchronized int getNewPersonId() {
        return personCount++;
    }

    /**
     * Instantiates a new person impl.
     * 
     * @param startF
     *            the start f
     * @param destF
     *            the dest f
     */
    PersonImpl(int startF, int destF) {

        setStartFloor(startF);
        setCurrentFloor(startF);
        setDestinationFloor(destF);
        setPersonId(PersonImpl.getNewPersonId());

        Simulator.getInstance().logEvent(
                String.format("New person created: %s", toString()));
    }

    @Override
    public boolean didErrorOccur() {
        return errorOccured;
    }

    @Override
    public boolean equals(Object obj) {

        //The basic three checks for any object equality
        if (obj == null) {
            return false;
        }
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof PersonImpl)) {
            return false;
        }

        PersonImpl rhs = (PersonImpl) obj;

        // if the ID is the same then they are equal. This is because ID is unique to each person
        if (getPersonId() == rhs.getPersonId()) {
            return true;
        } else {
            return false;
        }

    }

    /**
     * Get current floor.
     */
    @Override
    public int getCurrentFloor() {
        return currentFloor;
    }

    /**
     * Get destination floor.
     */
    @Override
    public int getDestinationFloor() {
        return destinationFloor;
    }

    /**
     * Get person ID.
     */
    @Override
    public int getPersonId() {
        return personId;
    }

    /**
     * Get start floor.
     */
    @Override
    public int getStartFloor() {
        return startFloor;
    }

    /**
     * Set current floor.
     * 
     * @param floorNumber
     */
    private void setCurrentFloor(int floorNumber) {
        currentFloor = floorNumber;
    }

    /**
     * Sets the destination floor.
     * 
     * @param destF
     *            the new destination floor
     */
    private void setDestinationFloor(int destF) {
        destinationFloor = destF;
    }

    /**
     * Check for invalid status.
     */
    @Override
    public void setInvalidStatus() {
        errorOccured = true;

    }

    /**
     * Sets the person id.
     * 
     * @param pid
     *            the new person id
     */
    private void setPersonId(int pid) {
        personId = pid;
    }

    /**
     * Sets the start floor.
     * 
     * @param startF
     *            the new start floor
     */
    private void setStartFloor(int startF) {
        startFloor = startF;
    }

    /**
     * Public start person method
     */
    @Override
    public void startPerson() {
        int destFloor = getDestinationFloor();
        int curFloor = getCurrentFloor();

        ElevatorDirection dir = (destFloor > curFloor) ? ElevatorDirection.UP
                : ElevatorDirection.DOWN;

        try {
            ElevatorController.getInstance().addNewRequest(curFloor, dir);
            this.setStartTime();
        } catch (IllegalParamException e) {
            String event = String
                    .format("Person %d was unable to make a valid floor request to floor %d from current floor %d. Skipping person.",
                            getPersonId(), destFloor, curFloor);
            Simulator.getInstance().logEvent(event);
        }
    }

    /**
     * Private method for setting the start time.
     */
    private void setStartTime() {
        this.startTime = System.currentTimeMillis();
    }

    /**
     * Public method toString
     */
    @Override
    public String toString() {
        return String
                .format("Person %d with starting floor as %d and destination floor as %d [Current floor: %d]",
                        getPersonId(), getStartFloor(), getDestinationFloor(),
                        getCurrentFloor());
    }

    // TODO Maybe have a check to make sure this is only called once?

    /**
     * Public method for time elevator entered.
     */
    @Override
    public void elevatorEntered() {
        this.elevatorEnteredTime = System.currentTimeMillis();
    }

    /**
     * Public method for time remaining.
     */
    @Override
    public void leftElevator() {
        this.finishedTime = System.currentTimeMillis();
        this.setCurrentFloor(this.getDestinationFloor());
    }

    /**
     * Public method for geting start time.
     */
    @Override
    public long getStartTime() {
        return this.startTime;
    }

    /**
     * Public method for getting the elevator enter time.
     */
    @Override
    public long getElevatorEnterTime() {
        return this.elevatorEnteredTime;
    }

    /**
     * Public method for getting the finished time.
     */
    @Override
    public long getFinishedTime() {
        return this.finishedTime;
    }

}
