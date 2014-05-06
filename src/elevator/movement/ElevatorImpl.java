package elevator.movement;

import simulator.common.IllegalParamException;
import simulator.common.SimulationInformation;

public class ElevatorImpl implements Elevator,Mover{
    
    private int defaultFloor;
    
    private long timeOut;
    private long floorTime;
    private long doorTime;
    private int numFloors;
    private int numPeoplePerElevator;
    private ArrayList<Integer> destinations;
    
    private Thread myThread;
    private int elevatorId;
    private boolean elevatorOn = true;
    
    //no caching
    private static volatile int elevatorCount = 1;
    
    /** 
     * Method for getting a unique elevatorID
     * @return returns an integer that is a unique elevatorID
     */
    private static synchronized int getElevatorId() {
        return elevatorCount++;
    }
    
    public ElevatorImpl(SimulationInformation info) throws IllegalParamException{
        this.setTimeout(info.elevatorSleepTime);
        this.setFloorTime(info.floorTime);
        this.setDoorTime(info.doorTime);
        this.setNumFloors(info.numFloors);
        this.setNumpeopleperElevator(info.numPeoplePerElevator);
        this.elevatorId = ElevatorImpl.getElevatorId();
        this.destinations = new ArrayList<Integer>();
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
    
    public void startElevator(){
        this.myThread.start();
    }

    @Override
    public void move() {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void stopElevator() {
        // TODO Auto-generated method stub
        this.elevatorOn = false;
        
    }

    @Override
    public void addFloor(int floor) {
        // TODO Auto-generated method stub
        
    }
    
    
    private void moveToFloor(int currentFloor, int destination) throws InterruptedException{
    	// note: sleep
    }
    
    private void addFloorToDestinations(int newFloor){
    	// add a destination to array.list
    	// Note: this.destinations = new ArrayList<Integer>();
    	// always sorted (Assending/Decending)
    }

}
