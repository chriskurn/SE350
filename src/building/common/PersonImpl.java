package building.common;

import elevator.common.ElevatorDirection;
import elevator.common.ElevatorRequest;
import elevator.control.ElevatorController;
import simulator.Simulator;
import simulator.common.IllegalParamException;

/**
 * Description: PersonImpl
 * 
 * @author Patrick Stein
 * @author Chris Kurn
 * @since Version 1.0 - Spring Quarter 2014
 * @see package building.common
 * @see import simulator.Simulator
 */

public class PersonImpl implements Person {

    private int startFloor;
    private int destinationFloor;
    private int currentFloor;
    private int personId;
    private PersonState currentState;

    private static volatile int personCount = 1;

    /**
     * method for getting a unique personId
     * 
     * @return returns an integer that is a unique personId
     */
    private static synchronized int getNewPersonId() {
        return personCount++;
    }

    PersonImpl(int startF, int destF) {

        setStartFloor(startF);
        setCurrentFloor(startF);
        setDestinationFloor(destF);
        setPersonId(PersonImpl.getNewPersonId());

        Simulator.getInstance().logEvent(
                String.format("New person created: %s", toString()));
    }

    @Override
    public int getPersonId() {
        return personId;
    }

    @Override
    public int getCurrentFloor() {
        return currentFloor;
    }

    @Override
    public int getStartFloor() {
        return startFloor;
    }

    @Override
    public int getDestinationFloor() {
        return destinationFloor;
    }
    @Override
    public void setCurrentFloor(int floorNumber) {
        currentFloor = floorNumber;
    }

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
     * @return the currentState
     */
    private PersonState getCurrentState() {
        return currentState;
    }

    /**
     * @param currentState
     *            the currentState to set
     */
    private void setCurrentState(PersonState cs) {
        currentState = cs;
    }

    /**
     * @param personId
     *            the personId to set
     */
    private void setPersonId(int pid) {
        personId = pid;
    }

    /**
     * @param destinationFloor
     *            the destinationFloor to set
     */
    private void setDestinationFloor(int destF) {
        destinationFloor = destF;
    }

    /**
     * @param startFloor
     *            the startFloor to set
     */
    private void setStartFloor(int startF) {
        startFloor = startF;
    }

    public String toString() {
        return String
                .format("Person %d with starting floor as %d and destination floor as %d",
                        getPersonId(), getStartFloor(),
                        getDestinationFloor());
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
