package building;

import java.util.ArrayList;

import simulator.Simulator;
import simulator.common.IllegalParamException;
import simulator.common.SimulationInformation;
import building.common.Person;
import building.elements.Floor;
import building.elements.FloorFactory;
import elevator.common.ElevatorDirection;
import elevator.common.InvalidFloorException;
import elevator.control.ElevatorController;

/**
 * Description: Building interface class.
 * 
 * @author Patrick Stein
 * @author Chris Kurn
 * @since Version 1.0 - Spring Quarter 2014
 * 
 * The building is a singleton facade responsible for managing the floors.
 * It also has methods that allow for movement of people to different floors and people leaving a floor.
 * 
 */

final public class Building {

    /** Add floors in building to to array list. */
    ArrayList<Floor> myFloors = new ArrayList<Floor>();

    /** The number of floors. */
    private int numberOfFloors;

    /** The instance. */
    private static volatile Building instance;

    /**
     * Gets the single instance of Building for this singleton.
     * 
     * @return single instance of Building
     */
    public static Building getInstance() {

        if (instance == null) {
            synchronized (ElevatorController.class) {
                if (instance == null) {
                    instance = new Building();
                }
            }
        }
        return instance;

    }

    /**
     * Private constructor for creating the building. 
     */
    private Building() {
        Simulator sim = Simulator.getInstance();
        sim.logEvent("Building being created.");
        SimulationInformation info = sim.getSimulationInfo();
        try {
            this.setNumberOfFloors(info.numFloors);
        } catch (IllegalParamException e) {
            Simulator
                    .getInstance()
                    .logEvent(
                            "Building provided an invalid number of floors. Exiting now.");
            System.exit(1);
        }
        // make floors
        this.buildFloors();
        // Call get instance to make sure the elevator controller is alive
        ElevatorController.getInstance().startElevatorController();

    }

    /**
     * Builds the floors in the building that is being created. Should only be called once!!
     */
    private void buildFloors() {
        int numFloors = this.getNumberOfFloors();
        Simulator.getInstance().logEvent(
                String.format("Building %d number of floors.", numFloors));
        for (int i = 0; i < numFloors; i++) {
            try {
                this.getMyFloors().add(FloorFactory.build(i + 1));
            } catch (IllegalParamException e) {
                Simulator.getInstance().logEvent(String.format("Building was unable to create floor %d. Exiting application.",i));
                System.exit(1);
            }
        }

    }

    /**
     * A function that encapsulates the logic of determining if a given integer is a valid floor in this building.
     * @param f The floor you want to check if it is valid
     * @throws InvalidFloorException Throws this exception if the floor is below or equal to 0 or exceeds the buildings number of floors.
     */
    private void checkValidFloor(int f) throws InvalidFloorException {
        if (f > getNumberOfFloors() || f <= 0) {
            throw new InvalidFloorException(
                    "The floor exceeds the number of floors of this building.");
        }

    }

    /**
     * Takes a person object and enters the floor provided in the floor integer.
     * 
     * @param p The person who wishes to enter the building
     * @param floor an integer representing which floor it is.
     *            
     * @throws IllegalParamException
     *             Thrown if the person is null.
     * @throws InvalidFloorException
     *             Thrown if the floor cannot be in this building. Greater than the current number of floors or less than 1.
     */
    public int enterFloor(Person p, int floor) throws IllegalParamException,
            InvalidFloorException {
        int floorEntered = this.getAFloor(floor).enterFloor(p);
        return floorEntered;
    }

    /**
     * Private method for getting a specific floor out of the floor array.
     * @param floorNumber the floor you want to acquire
     * @return returns the floor based on the integer you provided
     * @throws InvalidFloorException thrown if the floorNumber parameter cannot be in this building.
     * It cannot be greater than the current number of floors or less than 1. 
     */
    private Floor getAFloor(int floorNumber) throws InvalidFloorException {
        // -1 offset because arrays start at 0!!!
        // Throws a runtime exception for index out of bounds
        checkValidFloor(floorNumber);
        return this.getMyFloors().get(floorNumber - 1);
    }

    /**
     * Gets the floor array.
     * 
     * @return myFloors the array of floors member.
     */
    private ArrayList<Floor> getMyFloors() {
        return this.myFloors;
    }

    /**
     * Gets the number of floors.
     * 
     * @return the numberOfFloors
     */
    public int getNumberOfFloors() {
        return numberOfFloors;
    }

    /**
     * Checks to see if the building is empty of people. 
     * @return Returns true if nobody is currently waiting on an elevator on any floor. 
     */
    public boolean isEmpty() {

        for (Floor f : getMyFloors()) {
            if (f.isEmpty() == false) {
                return false;
            }
        }
        return true;
    }

    /**
     * A method for removing people from a given floor that want to go a given direction. 
     * Up or down.
     * @param floor the floor you wish to get people from
     * @param dir the direction those people are waiting for.
     * @return an arraylist of type Person to move to another object
     * @throws InvalidFloorException thrown if the floorNumber parameter cannot be in this building.
     */
    public ArrayList<Person> loadPeople(int floor, ElevatorDirection dir)
            throws InvalidFloorException {
        // ask the specific floor for ask it's nice people leave the floor
        return this.getAFloor(floor).leaveFloor(dir);
    }

    /**
     * Sets the number of floors.
     * 
     * @param numF
     *            the new number of floors
     * @throws IllegalParamException thrown if the number of floors passed is below 1.
     */
    private void setNumberOfFloors(int numF) throws IllegalParamException {
        if (numF < 1) {
            throw new IllegalParamException(
                    "Cannot set the building to have more than %d floors or less than 1 floor.");
        }
        this.numberOfFloors = numF;
    }

}
