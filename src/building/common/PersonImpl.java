package building.common;

import simulator.Simulator;

public class PersonImpl implements Person, Runnable {

    private int startFloor;
    private int destinationFloor;
    private int currentFloor;
    private int personId;
    private PersonState currentState;
    Thread myThread;

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

        this.setStartFloor(startF);
        this.setDestinationFloor(destF);
        this.setPersonId(PersonImpl.getNewPersonId());
        this.myThread = new Thread(this);
        
        Simulator.getInstance().logEvent(String.format("New person created: %s",this.toString()));
    }

    @Override
    public int getPersonId() {
        return this.personId;
    }
    @Override
    public int getCurrentFloor() {
        return this.currentFloor;
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
    public void startPerson() {
        this.myThread.start();
    }

    @Override
    public void run() {
        // TODO Auto-generated method stub
        Simulator.getInstance().logEvent(
                String.format("Person %d has started running",
                        this.getPersonId()));

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
        this.currentState = cs;
    }

    /**
     * @param personId
     *            the personId to set
     */
    private void setPersonId(int pid) {
        this.personId = pid;
    }
    /**
     * @param destinationFloor
     *            the destinationFloor to set
     */
    private void setDestinationFloor(int destF) {
        this.destinationFloor = destF;
    }

    /**
     * @param startFloor
     *            the startFloor to set
     */
    private void setStartFloor(int startF) {
        this.startFloor = startF;
    }

    public String toString() {
        return String
                .format("Person %d with starting floor as %d and destination floor as %d",
                        this.getPersonId(), this.getStartFloor(),
                        this.getDestinationFloor());
    }


}
