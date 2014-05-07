package elevator.common;

import java.util.ArrayList;
import java.util.Collections;

import simulator.Simulator;
import simulator.common.IllegalParamException;
import simulator.common.SimulationInformation;


/**
 * Description: Elevator implementation class
 * @author Patrick Stein
 * @author Chris Kurn 
 * @since Version 1.0 - Spring Quarter 2014
 * @see package elevator.common\
 * @see elevator.adding.ElevatorAdder;
 * @see elevator.adding.ElevatorAdderFactory;
 * @see simulator.common.IllegalParamException;
 * @see simulator.common.SimulationInformation;
 */

public class ElevatorImpl implements Elevator, Runnable{
       
    private int defaultFloor = 1;
    private long timeOut;
    private long floorTime;
    private long doorTime;
    private int numFloors;
    private int numPeoplePerElevator;
    private ElevatorDirection direction = ElevatorDirection.IDLE;
    private final int STARTINGFLOOR = 1;
    private int currentFloor;
    private ArrayList<Integer> destinations = new ArrayList<Integer>();

    private Thread myThread;
    private int elevatorId;
    private boolean elevatorOn = true;

    // no caching
    private static volatile int elevatorCount = 1;

    /**
     * Method for getting a unique elevatorID
     * 
     * @return returns an integer that is a unique elevatorID
     */
    private static synchronized int getNewElevatorId() {
        return elevatorCount++;
    }

    public ElevatorImpl(SimulationInformation info)
            throws IllegalParamException {
        this.setTimeout(info.elevatorSleepTime);
        this.setFloorTime(info.floorTime);
        this.setDoorTime(info.doorTime);
        this.setNumFloors(info.numFloors);
        this.setNumpeopleperElevator(info.numPeoplePerElevator);

        this.elevatorId = ElevatorImpl.getNewElevatorId();
        this.setNumFloors(info.numFloors);
        this.setCurrentFloor(this.STARTINGFLOOR);

        this.myThread = new Thread(this);

    }
    @Override
    public void addFloor(int floor, ElevatorDirection dir)
            throws InvalidFloorException {
        if(dir == this.getDirection() || this.getDirection() == ElevatorDirection.IDLE){
            this.addFloor(floor);
        }else{
            throw new InvalidFloorException("Tried to add a floor request to an elevator going in a different direction.");
        }
        
    }

    @Override
    public void run() {
        // TODO Auto-generated method stub
        try {
            // this.moveToAllFloors();
            this.controlState();
        } catch (InterruptedException e) {
            e.printStackTrace();
            Thread.currentThread().interrupt(); // restore interrupted status
        }
    }

    @Override
    public ElevatorDirection getDirection() {
        // TODO Auto-generated method stub
        return this.direction;
    }

    @Override
    public void startElevator() {
        this.myThread.start();
    }

    @Override
    public void stopElevator() {
        // TODO Auto-generated method stub
        this.elevatorOn = false;
    }

    private void moveToAllFloors() throws InterruptedException {
        while (this.destinationsLeft() == true) {
            // Move and update the destination queue
            this.moveToNextDestination();
            this.removeMostRecentDestination();
        }
        this.updateDirection();
    }

    private void moveToNextDestination() throws InterruptedException {
        // get the first destination
        int destination = this.getDestination();
        int curFloor = this.getCurrentFloor();
        int eleId = this.getElevatorId();
        this.updateDirection();
        Simulator.getInstance().logEvent((String.format(
                "Elevator %d moving from floor: %d to floor %d.", eleId,
                curFloor, destination)));
        while (destination != curFloor) {
            Thread.sleep(this.getFloorTime());
            if (this.getDirection() == ElevatorDirection.UP) {
                this.setCurrentFloor(++curFloor);
            } else if(this.getDirection() == ElevatorDirection.DOWN){
                this.setCurrentFloor(--curFloor);
            } else {
                //We shouldn't be moving
                Simulator.getInstance().logEvent(String.format("Elevator %d hit an invalid movement state.",eleId));
                return;
            }
            // This is just to keep it updated with the rest of the program.
            curFloor = this.getCurrentFloor();
            // this may change during moving
            destination = this.getDestination();
            this.updateDirection();
            Simulator.getInstance().logEvent(String.format("Elevator %d Now at floor: %d.",
                    eleId, curFloor));
        }
        Simulator.getInstance().logEvent(String.format(
                "Elevator %d now done moving to floor: %d.", eleId, curFloor));
        this.openDoors();
    }

    private synchronized void setCurrentFloor(int i) {
        this.currentFloor = i;

    }

    private int getElevatorId() {
        // TODO Auto-generated method stub
        return this.elevatorId;
    }

    private void removeMostRecentDestination() {
        // Remove the very first element
        if (this.getDestinations().isEmpty() == false) {
            this.getDestinations().remove(0);
        }
    }

    /**
     * This method encapsulates all of the behavior associated with an idle
     * elevator.
     * 
     * @throws InterruptedException
     */
    private void controlState() throws InterruptedException {
        // Continue forever until we are asked to end
        while (this.elevatorOn) {

            // ask if a destination has been added
            this.moveToAllFloors();
            // Change this maybe?
            synchronized (this) {
                try {
                    wait(this.getTimeout());
                } catch (InterruptedException e) {
                    Simulator.getInstance().logEvent(String
                                    .format("Elevator %d has been interrupted. Continuing execution.",
                                            this.getElevatorId()));
                }
            }
            // DON'T CALL MOVE DURING A SYNCH BLOCK OR ELEVATORS CANNOT BE ADDED TO THE QUEUE
            this.handleWakeUp();
        }
    }

    /**
     * Handle the timeout behavior. Returns to a default floor
     * 
     * @throws InterruptedException
     */
    private void handleWakeUp() throws InterruptedException {

        if (this.destinationsLeft() == false
                && this.getCurrentFloor() != this.getDefaultFloor()) {
            // add default floor to the elevator queue
            Simulator.getInstance().logEvent(String
                            .format("Elevator %d has timed out. Returning to default floor: %d.",
                                    this.elevatorId, this.defaultFloor));
            try {
                this.addFloorHelper(this.getDefaultFloor());
            } catch (InvalidFloorException e) {
                // Even though this floor was added. Don't crash. Just log it
                // and move on.
                Simulator.getInstance().logEvent("Invalid floor added during a timeout");
            }
        }
        this.moveToAllFloors();
    }

    private void openDoors() throws InterruptedException {
        Simulator.getInstance().logEvent(String.format(
                "The doors are now open in elevator %d on floor: %d",
                this.getElevatorId(), this.getCurrentFloor()));
        Thread.sleep(this.getDoorTime());
        Simulator.getInstance().logEvent(String.format(
                "The doors are now closed in elevator %d on floor: %d",
                this.getElevatorId(), this.getCurrentFloor()));
    }

    private long getDoorTime() {
        return this.doorTime;
    }

    // TODO do error checking
    private long getTimeout() {
        return this.timeOut;
    }

    private int getDefaultFloor() {
        // TODO Auto-generated method stub
        return this.defaultFloor;
    }

    private long getFloorTime() {
        return this.floorTime;
    }

    private void setDoorTime(int dt) {
        this.doorTime = dt;
    }

    private void setNumpeopleperElevator(int num) {
        this.numPeoplePerElevator = num;
    }

    private void setTimeout(long to) throws IllegalParamException {
        if (to <= 0) {
            throw new IllegalParamException(
                    "Timeout time cannot be less than or equal to zero.");
        }
        this.timeOut = to;
    }

    private void setFloorTime(long fo) {
        this.floorTime = fo;
    }

    private void setNumFloors(int nf) {
        this.numFloors = nf;

    }

    private int getNumFloors() {
        return this.numFloors;
    }

    private ArrayList<Integer> getDestinations() {
        // TODO Auto-generated method stub
        return this.destinations;
    }

    @Override
    public boolean destinationsLeft() {
        return !this.getDestinations().isEmpty();
    }

    @Override
    public int getCurrentFloor() {
        // TODO Auto-generated method stub
        return this.currentFloor;
    }

    @Override
    public int getDestination() {
        // TODO Auto-generated method stub
        if (this.getDestinations().isEmpty() == false) {
            return this.getDestinations().get(0);
        } else {
            // TODO THROW AN ERROR
            return 0;
        }
    }

    @Override
    public void addFloor(int floor) throws InvalidFloorException {
        this.addFloorHelper(floor);
    }

    public synchronized void addFloorHelper(int floor)
            throws InvalidFloorException {
        // Check if the floor is valid. throw error is it is less than one or it
        // is greater than the number of floors.
        if ((floor < 1) || floor > this.getNumFloors()) {
            throw new InvalidFloorException(String.format(
                    "The floor must be between 1 and %d.", this.getNumFloors()));
        }
        // BEWARE RACE CONDITIONS WITH THIS STATEMENT
        if (floor == this.getCurrentFloor()) {
            throw new InvalidFloorException(
                    "Cannot add this floor because we are already at it.");
        }
        // The floor must be in the same direction as the current floor
        // destination
        if (floorInSameDirection(floor) == false) {
            throw new InvalidFloorException(
                    "Cannot add a floor in a different direction of travel.");
        }
        // If the destination already exists we want to ignore it / not add it
        if (destinationExists(floor) == false) {
            // add the destination
            this.addFloorToQueue(floor);
            Simulator.getInstance().logEvent("Added floor: " + floor);
        } else {
            Simulator.getInstance().logEvent("This floor already exists.");
        }
    }

    /**
     * A method to determine if the current floor is in the same direction of
     * travel.
     * 
     * @param floorAdded
     *            takes an integer representing the floor that wishes to be
     *            added
     * @return returns a boolean confirming that this floor is on the way to the
     *         current destination.
     */
    private boolean floorInSameDirection(int floorAdded) {
        // if the direction is set to up and the current floor is less than the
        // floor added then we cannot add
        int cf = this.getCurrentFloor();
        if ((this.getDirection() == ElevatorDirection.UP) && cf > floorAdded) {
            return false;
        } else if ((this.getDirection() == ElevatorDirection.DOWN)
                && floorAdded > cf) { // Same thing for down but the floor is
                                      // greater than the above floor
            return false;
        } else {
            // This is fine then. If it is idle. Then everything should be okay.
            return true;
        }
    }

    private boolean destinationExists(int floorAdded) {
        return this.getDestinations().contains(floorAdded);
    }

    /**
     * A method for determining the direction based on the new floor being added
     * 
     * @param floorAdded
     *            the floor that is being added
     */
    private void updateDirection() {
        int cf = this.getCurrentFloor();
        if (this.destinationsLeft() == false){
            this.direction = ElevatorDirection.IDLE;
        }
        else if (cf > this.getDestination()) {
            this.direction = ElevatorDirection.DOWN;
        } else if (cf < this.getDestination()){
            this.direction = ElevatorDirection.UP;
        }
    }

    private synchronized void addFloorToQueue(int floorAdded) {
        // Get the list of destinations
        ArrayList<Integer> dests = this.getDestinations();
        // add the floor
        dests.add(floorAdded);
        // make sure it is still sorted
        ElevatorDirection dir = this.getDirection();
        if (dir == ElevatorDirection.UP) {
            // This should be sorted ascending
            Collections.sort(dests);
        } else if (dir == ElevatorDirection.DOWN) {
            // This should be sorted descending
            Collections.sort(dests, Collections.reverseOrder());
        } else {
            this.updateDirection();
        }
        notifyAll();
    }

}
