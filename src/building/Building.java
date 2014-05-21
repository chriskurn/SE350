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
 */

final public class Building {

    /** Add floors in building to to array list. */
    ArrayList<Floor> myFloors = new ArrayList<Floor>();

    /** The number of floors. */
    private int numberOfFloors;

    /** The instance. */
    private static volatile Building instance;

    /**
     * Gets the single instance of Building.
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
     * Create Building.
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
            System.exit(-1);
        }
        // make floors
        this.buildFloors();
        // Call get instance to make sure the elevator controller is alive
        ElevatorController.getInstance().startElevatorController();

    }

    /**
     * Build floors.
     */
    private void buildFloors() {
        int numFloors = this.getNumberOfFloors();
        Simulator.getInstance().logEvent(
                String.format("Building %d number of floors.", numFloors));
        for (int i = 0; i < numFloors; i++) {
            this.getMyFloors().add(FloorFactory.build(i + 1));
        }

    }

    private void checkValidFloor(int f) throws InvalidFloorException {
        if (f > getNumberOfFloors() || f <= 0) {
            throw new InvalidFloorException(
                    "The floor exceeds the number of floors of this building.");
        }

    }

    /**
     * Enter Floors.
     * 
     * @param p
     * @param floor
     *            the floor
     * @throws IllegalParamException
     *             the illegal param exception
     * @throws InvalidFloorException
     *             the invalid floor exception
     */
    public int enterFloor(Person p, int floor) throws IllegalParamException,
            InvalidFloorException {
        int floorEntered = this.getAFloor(floor).enterFloor(p);
        return floorEntered;
    }

    private Floor getAFloor(int floorNumber) throws InvalidFloorException {
        // -1 offset because arrays start at 0!!!
        // Throws a runtime exception for index out of bounds
        checkValidFloor(floorNumber);
        return this.getMyFloors().get(floorNumber - 1);
    }

    /**
     * Gets the my floors.
     * 
     * @return myFloors
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

    public boolean isEmpty() {

        for (Floor f : getMyFloors()) {
            if (f.isEmpty() == false) {
                return false;
            }
        }
        return true;
    }

    /**
     * Method designed to get all of the people on a floor that are waiting for
     * this elevator.
     * 
     * @param dir
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
     * @throws IllegalParamException
     */
    private void setNumberOfFloors(int numF) throws IllegalParamException {
        if (numF < 1) {
            throw new IllegalParamException(
                    "Cannot set the building to have more than %d floors or less than 1 floor.");
        }
        this.numberOfFloors = numF;
    }

}
