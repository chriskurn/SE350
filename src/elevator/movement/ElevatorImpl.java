package elevator.movement;

import java.util.ArrayList;
import java.util.Collections;

import elevator.common.ElevatorDirection;
import elevator.common.InvalidFloorException;

import simulator.common.IllegalParamException;
import simulator.common.SimulationInformation;

public class ElevatorImpl implements Elevator,Mover{
    
    private int defaultFloor;
    
    private long timeOut;
    private long floorTime;
    private long doorTime;
    private int numFloors;
    private int numPeoplePerElevator;
    private int currentFloor;
    private final int STARTINGFLOOR = 1;
    private ArrayList<Integer> destinations;
    
    private Thread myThread;
    private int elevatorId;
    private boolean elevatorOn = true;
    private ElevatorDirection direction = ElevatorDirection.IDLE;
    
    
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
        this.setCurrentFloor(this.STARTINGFLOOR);
    }
    
    
    private void setCurrentFloor(int sf) {
        this.currentFloor = sf;
        
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
    public void move() throws InterruptedException {
        //get the first destination
        int destination = this.getDestinations().get(0);
        int curFloor = this.getCurrentFloor();
        System.out.println(String.format("Moving from floor: %d to floor %d.", curFloor,destination));
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
            destination = this.getDestinations().get(0);
            System.out.println("Now at floor: " + curFloor);
        }
        System.out.println("Now done moving.");
       
    }

    private long getFloorTime() {
        return this.floorTime;
    }

    @Override
    public void stopElevator() {
        // TODO Auto-generated method stub
        this.elevatorOn = false;
        
    }

    @Override
    public void addFloor(int floor) throws InvalidFloorException {
        this.addFloorToDestinations(floor);
        
    }
    
    
    private synchronized void addFloorToDestinations(int floorAdded) throws InvalidFloorException{
        
        //Check if the floor is valid. throw error is it is less than one or it is greater than the number of floors.
        if((floorAdded < 1) || floorAdded > this.getNumFloors()){
            throw new InvalidFloorException(String.format("The floor must be between 1 and %d.",this.getNumFloors()));
        }
        // BEWARE RACE CONDITIONS WITH THIS STATEMENT
        if(floorAdded == this.getCurrentFloor()){
            throw new InvalidFloorException("Cannot add this floor because we are already at it.");
        }
        //The floor must be in the same direction as the current floor destination
        if(floorInSameDirection(floorAdded) == false){
            throw new InvalidFloorException("Cannot add a floor in a different direction of travel.");
        }
        //If the destination already exists we want to ignore it / not add it
        if(destinationExists(floorAdded) == false){
            //add the destination
            this.addFloorToQueue(floorAdded);
            System.out.println("Added floor: "+ floorAdded);
        }else{
            System.out.println("This floor already exists.");
        }
        
    }
    private void addFloorToQueue(int floorAdded) {
        //Get the list of destinations
        ArrayList<Integer> dests = this.getDestinations();
        //add the floor
        dests.add(floorAdded);
        //make sure it is still sorted
        ElevatorDirection dir = this.getDirection();
        if(dir == ElevatorDirection.UP){
            //This should be sorted ascending
            Collections.sort(dests);
        }else if(dir == ElevatorDirection.DOWN){
            //This should be sorted descending
            Collections.sort(dests,Collections.reverseOrder());
        }else if(dir == ElevatorDirection.IDLE){
            this.setDirection(floorAdded);
        }
    }
    /**
     * A method for determining the direction based on the new floor being added
     * DOES NOT CHECK THE STATE. MAY BE AN ISSUE IN THE FUTURE. 
     * I THINK SHOULD ONLY BE CALLED IF STATE IS IDLE.
     * @param floorAdded the floor that is being added
     */
    private void setDirection(int floorAdded) {
        if(this.getDirection() == ElevatorDirection.IDLE){
            int cf = this.getCurrentFloor();
            if(cf > floorAdded){
                this.direction = ElevatorDirection.DOWN;
            }else{
                this.direction = ElevatorDirection.UP;
            }
        }else{
            System.out.println("Tried to change the direction when it was not in IDLE.");
            //throw new IllegalParamException("Cannot change direction ")
        }
    }
    private boolean destinationExists(int floorAdded) {
        return this.getDestinations().contains(floorAdded);
    }
    /**
     * A method to determine if the current floor is in the same direction of travel.
     * @param floorAdded takes an integer representing the floor that wishes to be added
     * @return returns a boolean confirming that this floor is on the way to the current destination.
     */
    private boolean floorInSameDirection(int floorAdded){
        //if the direction is set to up and the current floor is less than the floor added then we cannot add
        int cf = this.getCurrentFloor();
        if((this.getDirection() == ElevatorDirection.UP) && cf > floorAdded){
            return false;
        }else if((this.getDirection() == ElevatorDirection.DOWN) && floorAdded < cf){ //Same thing for down but the floor is greater than the above floor
            return false;
        }else{
            //This is fine then. If it is idle. Then everything should be okay.
            return true;
        }
    }
    
    private int getCurrentFloor() {
        return this.currentFloor;
    }
    private ElevatorDirection getDirection() {
        return this.direction;
    }
    private ArrayList<Integer> getDestinations() {
        return this.destinations;
    }
    private int getNumFloors() {
        return this.numFloors;
    }
}
