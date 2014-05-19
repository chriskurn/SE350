package building.common;

public class PersonImpl implements Person,Runnable{
    
    
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
    
    PersonImpl(int startF, int destF){
        
        this.setStartFloor(startF);
        this.setDestinationFloor(destF);
        
        
        this.setPersonId(PersonImpl.getNewPersonId());
        this.myThread = new Thread(this);
    }
    

    @Override
    public int getFloor() {
        return this.currentFloor;
    }

    @Override
    public int getPersonId() {
        return this.personId;
    }


    @Override
    public void run() {
        // TODO Auto-generated method stub
        
    }

    /**
     * @return the currentState
     */
    private PersonState getCurrentState() {
        return currentState;
    }

    /**
     * @param currentState the currentState to set
     */
    private void setCurrentState(PersonState cs) {
        this.currentState = cs;
    }

    /**
     * @param personId the personId to set
     */
    private void setPersonId(int pid) {
        this.personId = pid;
    }

    /**
     * @return the destinationFloor
     */
    private int getDestinationFloor() {
        return destinationFloor;
    }

    /**
     * @param destinationFloor the destinationFloor to set
     */
    private void setDestinationFloor(int destF) {
        this.destinationFloor = destF;
    }

    /**
     * @return the startFloor
     */
    private int getStartFloor() {
        return startFloor;
    }

    /**
     * @param startFloor the startFloor to set
     */
    private void setStartFloor(int startF) {
        this.startFloor = startF;
    }


}
