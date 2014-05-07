package elevator.common;


import elevator.adding.ElevatorAdder;
import elevator.adding.ElevatorAdderFactory;


import simulator.common.IllegalParamException;
import simulator.common.SimulationInformation;

public class ElevatorImpl implements Elevator, Runnable{
    
    private int defaultFloor = 7;
    
    private long timeOut;
    private long floorTime;
    private long doorTime;
    private int numFloors;
    private int numPeoplePerElevator;

    private ElevatorAdder adderDelegate;
    private Thread myThread;
    private int elevatorId;
    private boolean elevatorOn = true;
    
    
    //no caching
    private static volatile int elevatorCount = 1;
    
    /** 
     * Method for getting a unique elevatorID
     * @return returns an integer that is a unique elevatorID
     */
    private static synchronized int getNewElevatorId() {
        return elevatorCount++;
    }
    public ElevatorImpl(SimulationInformation info) throws IllegalParamException{
        this.setTimeout(info.elevatorSleepTime);
        this.setFloorTime(info.floorTime);
        this.setDoorTime(info.doorTime);
        this.setNumFloors(info.numFloors);
        this.setNumpeopleperElevator(info.numPeoplePerElevator);
        
        this.setAdderDelegate(ElevatorAdderFactory.build(info));
        this.elevatorId = ElevatorImpl.getNewElevatorId();
        this.myThread = new Thread(this);
        
    }
    @Override
    public void run() {
        // TODO Auto-generated method stub
        try {
            //this.moveToAllFloors();
            this.controlState();
        } catch (InterruptedException e) {
            e.printStackTrace();
            Thread.currentThread().interrupt(); // restore interrupted status
        }
    }
    @Override
    public int getCurrentFloor() {
        // TODO Auto-generated method stub
        return this.getAdderDelegate().getCurrentFloor();
    }
    @Override
    public ElevatorDirection getDirection() {
        // TODO Auto-generated method stub
        return this.getAdderDelegate().getDirection();
    }
    @Override
    public void addFloor(int floor) throws InvalidFloorException {
        this.getAdderDelegate().addFloor(floor);
        
    }
    @Override
    public void startElevator(){
        this.myThread.start();
    }
    @Override
    public void stopElevator() {
        // TODO Auto-generated method stub
        this.elevatorOn = false;
    }
    private void moveToAllFloors() throws InterruptedException{
        ElevatorAdder adder = this.getAdderDelegate();
        while(adder.destinationsLeft() == true){
            //Move and update the destination queue
            this.moveToNextDestination();
            this.removeMostRecentDestination();
        }
    }  
    
    private void moveToNextDestination() throws InterruptedException {
        //get the first destination
        int destination = this.getCurrentDestination();
        int curFloor = this.getCurrentFloor();
        int eleId = this.getElevatorId();
        System.out.println(String.format("Elevator %d moving from floor: %d to floor %d.",eleId,curFloor,destination));
        while(destination != curFloor){
            Thread.sleep(this.getFloorTime());
            if(this.getDirection() == ElevatorDirection.UP){
                this.setCurrentFloor(++curFloor);
            }else{
                this.setCurrentFloor(--curFloor);
            }
            //This is just to keep it updated with the rest of the program.
            curFloor = this.getCurrentFloor();
            //this may change during moving
            destination = this.getCurrentDestination();
            System.out.println(String.format("Elevator %d Now at floor: %d.",eleId,curFloor));
        }
        System.out.println(String.format("Elevator %d now done moving to floor: %d.",eleId,curFloor));
        
    }
    
    private int getCurrentDestination() {
        return this.getAdderDelegate().getDestination();
    }
    private void setCurrentFloor(int i) {
        this.getAdderDelegate().setCurrentFloor(i);
        
    }
    private int getElevatorId() {
        // TODO Auto-generated method stub
        return this.elevatorId;
    }
    private void removeMostRecentDestination(){
        //Remove the very first element
        this.getAdderDelegate().removeDestination();
    }

    /**
     * This method encapsulates all of the behavior associated with an idle elevator.
     * @throws InterruptedException 
     */
    private void controlState() throws InterruptedException{
        //Continue forever until we are asked to end
        while(this.elevatorOn){
            
            //ask if a destination has been added
            if(this.getAdderDelegate().destinationsLeft()){
                //Move to the new destination
                this.moveToAllFloors();
                //Go to the next idle cycle
                continue;
            }
            synchronized(this){
                try {
                    this.getAdderDelegate().stoppedMoving();
                    System.out.println("Waiting for something to happen.");
                    this.wait(this.getTimeout());
                    //I have timed out
                    this.handleTimeout();
                } catch (InterruptedException e) {
                    System.out.println("I have been interrupted!");
                    this.moveToAllFloors();
                }
            }
        } 
    }
    /**
     * Handle the timeout behavior.
     * Returns to a default floor
     * @throws InterruptedException 
     */
    private void handleTimeout() throws InterruptedException {
        System.out.println(String.format("Elevator %d has timed out. Returning to default floor: %d.",this.elevatorId,this.defaultFloor));
        //add default floor to the elevator queue
        try {
            this.addFloor(this.defaultFloor);
        } catch (InvalidFloorException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        this.moveToAllFloors();
        //enter idle state again
        // NOTE: for this test program the execution just ends
        this.elevatorOn = false;
    }
    // TODO do error checking
    private long getTimeout() {
        return this.timeOut;
    }
    private long getFloorTime() {
        return this.floorTime;
    }
    private void setDoorTime(int dt) {
        this.doorTime = dt;
    }
    private void setNumFloors(int nf) {
        this.numFloors = nf;
    }
    private void setNumpeopleperElevator(int num) {
        this.numPeoplePerElevator = num;
    }
    private void setTimeout(long to) throws IllegalParamException{
        if(to <= 0){
            throw new IllegalParamException("Timeout time cannot be less than or equal to zero.");
        }
        this.timeOut = to;
    }
    private void setFloorTime(long fo){
        this.floorTime = fo;
    }
    private ElevatorAdder getAdderDelegate(){
        return this.adderDelegate;
    }
    private void setAdderDelegate(ElevatorAdder newDel){
        this.adderDelegate = newDel;
    }
}
