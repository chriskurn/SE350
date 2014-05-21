package building.common;

import elevator.common.ElevatorDirection;
import elevator.control.ElevatorController;
import simulator.Simulator;
import simulator.common.IllegalParamException;

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
    
    /** The my thread. */
    Thread myThread;
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
     * @param startF the start f
     * @param destF the dest f
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
    public void setInvalidStatus() {
        this.errorOccured = true;
        
    }
    @Override
    public boolean didErrorOccur() {
        return this.errorOccured;
    }

    /* (non-Javadoc)
     * @see building.common.Person#getPersonId()
     */
    @Override
    public int getPersonId() {
        return personId;
    }
    
    /* (non-Javadoc)
     * @see building.common.Person#getCurrentFloor()
     */
    @Override
    public int getCurrentFloor() {
        return currentFloor;
    }
    
    /* (non-Javadoc)
     * @see building.common.Person#getStartFloor()
     */
    @Override
    public int getStartFloor() {
        return startFloor;
    }
    /* (non-Javadoc)
     * @see building.common.Person#getDestinationFloor()
     */
    @Override
    public int getDestinationFloor() {
        return destinationFloor;
    }
    /* (non-Javadoc)
     * @see building.common.Person#startPerson()
     */
    @Override
    public void setCurrentFloor(int floorNumber) {
        currentFloor = floorNumber;
    }

    /* (non-Javadoc)
     * @see java.lang.Runnable#run()
     */
    @Override
    public void startPerson() {
        int destFloor = getDestinationFloor();
        int curFloor = getCurrentFloor();

        ElevatorDirection dir = (destFloor > curFloor) ? ElevatorDirection.UP
                : ElevatorDirection.DOWN;

        try {
            ElevatorController.getInstance().addNewRequest(
                    curFloor, dir);
        } catch (IllegalParamException e) {
            String event = String
                    .format("Person %d was unable to make a valid floor request to floor %d from current floor %d. Skipping person.",
                            getPersonId(), destFloor, curFloor);
            Simulator.getInstance().logEvent(event);
        }
    }

    /**
     * Sets the person id.
     *
     * @param pid the new person id
     */
    private void setPersonId(int pid) {
        personId = pid;
    }

    /**
     * Sets the destination floor.
     *
     * @param destF the new destination floor
     */
    private void setDestinationFloor(int destF) {
        destinationFloor = destF;
    }

    /**
     * Sets the start floor.
     *
     * @param startF the new start floor
     */
    private void setStartFloor(int startF) {
        startFloor = startF;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    public String toString() {
        return String
                .format("Person %d with starting floor as %d and destination floor as %d [Current floor: %d]",
                        getPersonId(), getStartFloor(),
                        getDestinationFloor(),getCurrentFloor());
    }
    
    
    public boolean equals(Object obj){
        
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

        if (getPersonId() == rhs.getPersonId()) {
            return true;
        } else {
            return false;
        }
        
        
    }

}
