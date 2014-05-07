package elevator.adding;

import java.util.ArrayList;
import java.util.Collections;
import simulator.common.SimulationInformation;
import elevator.common.Elevator;
import elevator.common.ElevatorDirection;
import elevator.common.InvalidFloorException;

/**
 * Description: ElevatorAdderImpl class
 * @author Patrick Stein
 * @author Chris Kurn 
 * @since Version 1.0 - Spring Quarter 2014
 * @see package elevator.adding
 * @see java.util.ArrayList;
 * @see java.util.Collections;
 * @see import simulator.common.SimulationInformation;
 * @see import elevator.common.Elevator;
 * @see import elevator.common.ElevatorDirection;
 * @see import elevator.common.InvalidFloorException;
 */

public class ElevatorAdderImpl implements ElevatorAdder{

    private int numFloors;
    private ElevatorDirection direction = ElevatorDirection.IDLE;
    private final int STARTINGFLOOR = 1;
    private int currentFloor;
    private ArrayList<Integer> destinations = new ArrayList<Integer>();
    
    public ElevatorAdderImpl(SimulationInformation info) {
        this.setNumFloors(info.numFloors);
        this.setCurrentFloor(this.STARTINGFLOOR);
    }

    private void setNumFloors(int nf) {
        this.numFloors = nf;
        
    }
    private int getNumFloors() {
        return this.numFloors;
    }
    
    /**
     * Method: arraylist conatining the elevator destinations
     */
    private ArrayList<Integer> getDestinations() {
        // TODO Auto-generated method stub
        return this.destinations;
    }
    @Override
    /**
     * Private method: remaining elevators
     * @return returns a boolean.
     */
    public boolean destinationsLeft() {
        return !this.getDestinations().isEmpty();
    }
    @Override
    /**
     * Method: sets the current floor of the elevator
     * @return returns a boolean.
     */
    public void setCurrentFloor(int currentFloor) {
        this.currentFloor = currentFloor;
    }
    @Override
    /**
     * Method: elevator direction method
     */
    public ElevatorDirection getDirection() {
        // TODO Auto-generated method stub
        return this.direction;
    }
    @Override
    /**
     * Method: elevator is idle - stoppedMoving()
     */
    public void stoppedMoving() {
        // TODO Auto-generated method stub
        this.direction = ElevatorDirection.IDLE;
        
    }
    @Override
    /**
     * Method: getCurrentFloor().
     */
    public int getCurrentFloor() {
        // TODO Auto-generated method stub
        return this.currentFloor;
    }

    @Override
    /**
     * Method: getDestination().
     */
    public int getDestination() {
        // TODO Auto-generated method stub
        if(this.getDestinations().isEmpty() == false){
            return this.getDestinations().get(0);
        }else {
            // TODO THROW AN ERROR
            return 0;
        }
    }

    @Override
    public void removeDestination() {
        if(this.getDestinations().isEmpty() == false){
            this.getDestinations().remove(0);
        }
    }
    @Override
    public void addFloor(int floor) throws InvalidFloorException{
        this.addFloorHelper(floor);
    }
    
    /**
     * 
     * @param floor
     * @throws InvalidFloorException
     */
    public synchronized void addFloorHelper(int floor) throws InvalidFloorException {
        //Check if the floor is valid. throw error is it is less than one or it is greater than the number of floors.
        if((floor < 1) || floor > this.getNumFloors()){
            throw new InvalidFloorException(String.format("The floor must be between 1 and %d.",this.getNumFloors()));
        }
        // BEWARE RACE CONDITIONS WITH THIS STATEMENT
        if(floor == this.getCurrentFloor()){
            throw new InvalidFloorException("Cannot add this floor because we are already at it.");
        }
        //The floor must be in the same direction as the current floor destination
        if(floorInSameDirection(floor) == false){
            throw new InvalidFloorException("Cannot add a floor in a different direction of travel.");
        }
        //If the destination already exists we want to ignore it / not add it
        if(destinationExists(floor) == false){
            //add the destination
            this.addFloorToQueue(floor);
            System.out.println("Added floor: "+ floor);
        }else{
            System.out.println("This floor already exists.");
        }
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
    private boolean destinationExists(int floorAdded) {
        return this.getDestinations().contains(floorAdded);
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
    private synchronized void addFloorToQueue(int floorAdded) {
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
        this.notifyAll();
        System.out.println("I am notifying all waiting threads of a elevator being added");
    }

}
