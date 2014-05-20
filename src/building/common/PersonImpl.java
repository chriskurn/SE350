package building.common;

import simulator.Simulator;

/**
 * Description: PersonImpl.
 *
 * @author Patrick Stein
 * @author Chris Kurn
 * @since Version 1.0 - Spring Quarter 2014
 */

public class PersonImpl implements Person, Runnable {

    /** The start floor. */
    private int startFloor;
    
    /** The destination floor. */
    private int destinationFloor;
    
    /** The current floor. */
    private int currentFloor;
    
    /** The person id. */
    private int personId;
    
    /** The current state. */
    private PersonState currentState;
    
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

        this.setStartFloor(startF);
        this.setDestinationFloor(destF);
        this.setPersonId(PersonImpl.getNewPersonId());
        this.myThread = new Thread(this);
        
        Simulator.getInstance().logEvent(String.format("New person created: %s",this.toString()));
    }

    /* (non-Javadoc)
     * @see building.common.Person#getPersonId()
     */
    @Override
    public int getPersonId() {
        return this.personId;
    }
    
    /* (non-Javadoc)
     * @see building.common.Person#getCurrentFloor()
     */
    @Override
    public int getCurrentFloor() {
        return this.currentFloor;
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
    public void startPerson() {
        this.myThread.start();
    }

    /* (non-Javadoc)
     * @see java.lang.Runnable#run()
     */
    @Override
    public void run() {
        // TODO Auto-generated method stub
        Simulator.getInstance().logEvent(
                String.format("Person %d has started running",
                        this.getPersonId()));

    }

    /**
     * Gets the current state.
     *
     * @return the currentState
     */
    private PersonState getCurrentState() {
        return currentState;
    }

    /**
     * Sets the current state.
     *
     * @param cs the new current state
     */
    private void setCurrentState(PersonState cs) {
        this.currentState = cs;
    }

    /**
     * Sets the person id.
     *
     * @param pid the new person id
     */
    private void setPersonId(int pid) {
        this.personId = pid;
    }
    
    /**
     * Sets the destination floor.
     *
     * @param destF the new destination floor
     */
    private void setDestinationFloor(int destF) {
        this.destinationFloor = destF;
    }

    /**
     * Sets the start floor.
     *
     * @param startF the new start floor
     */
    private void setStartFloor(int startF) {
        this.startFloor = startF;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    public String toString() {
        return String
                .format("Person %d with starting floor as %d and destination floor as %d",
                        this.getPersonId(), this.getStartFloor(),
                        this.getDestinationFloor());
    }


}
