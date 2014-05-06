package elevator.movement;

import java.util.ArrayList;

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
    	   	   	
      	 if (destination > 0 || destination < ("this.setNumFloors")) {
             System.out.println("Floor doesnt exist in building.");
         }

         else {
             int elevatorDirection = 0;
             if(currentFloor < destination){
            	 elevatorDirection = 1; // elevator going up;
             } else if (currentFloor > destination) {
            	 elevatorDirection = -1; //elevator going down;
             } else {
            	 elevatorDirection = 0; //elevator is idle;
             }
             for (; currentFloor != destination; currentFloor += destination)
            	 //add floorToDestinations
             	System.out.println("Your flour sir!");
         }
     	
      	 // note: sleep
      	 sleep(this.setFloorTime);
      	 
    }
    
    private void addFloorToDestinations(int newFloor){

    	int direction;
    	
    	// always sorted (Assending/Decending)
    	
    	//sort the list
    	//Collections.sort(this.destinations);
    	
    	this.destinations = new ArrayList<Integer>();
    	
    	for (int i = 0; i < numFloors; i++) {
    		this.destinations.add(i);
    	}
    	
        //List<Integer> up = this.destinations.subList(0, newFloor);
        //List<Integer> down = this.destinations.subList(newFloor+1, this.setNumFloors);
    	
    	//direction = 0 descending, direction = 1 ascending
    	
    	if (direction == 1){
    		Collections.sort(this.destinations);	
    	} else if (direction == 0){
    		Comparator cmp = Collections.reverseOrder();  
    		Collections.sort(this.destinations, cmp);
    		
    	
    }

}
