package building;

import java.util.ArrayList;

import simulator.Simulator;
import simulator.common.IllegalParamException;
import simulator.common.SimulationInformation;

import building.common.Floor;
import building.common.FloorFactory;
import building.common.Person;
import elevator.common.InvalidFloorException;
import elevator.control.ElevatorController;

/**
 * Description: Building interface class
 * 
 * @author Patrick Stein
 * @author Chris Kurn
 * @since Version 1.0 - Spring Quarter 2014
 * @see package simulator.elements
 * @see import java.util.ArrayList;
 * @import simulator.Simulator;
 * @import simulator.common.IllegalParamException;
 * @import simulator.common.SimulationInformation;
 * @import building.common.Floor;
 * @import building.common.FloorFactory;
 * @import building.common.Person;
 * @import elevator.common.InvalidFloorException;
 * @import elevator.control.ElevatorController;
 * 
 */

final public class Building {

    /**
     * Add floors in building to to array list
     */
	ArrayList<Floor> myFloors = new ArrayList<Floor>();
    private int numberOfFloors;
    private static volatile Building instance;

    
    /**
     * Enter Floors
     * @param p
     * @param floor
     * @throws IllegalParamException
     * @throws InvalidFloorException
     */
    public void enterFloor(Person p, int floor) throws IllegalParamException,
            InvalidFloorException {
        if (floor > this.getNumberOfFloors() || floor <= 0) {
            throw new InvalidFloorException(
                    "The floor exceeds the number of floors of this building.");
        } else {
            // -1 offset because arrays start at 0!!!
            //Throws a runtime exception for index out of bounds
            this.getMyFloors().get(floor-1).enterFloor(p);
        }
    }

    /**
     * Create Building
     */
    private Building() {
        Simulator sim = Simulator.getInstance();
        sim.logEvent("Building being created.");
        SimulationInformation info = sim.getSimulationInfo();
        this.setNumberOfFloors(info.numFloors);
        // make floors
        this.buildFloors();

    }

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
     * Build floors
     */
    private void buildFloors() {
        int numFloors = this.getNumberOfFloors();
        Simulator.getInstance().logEvent(
                String.format("Building %d number of floors.", numFloors));
        for (int i = 0; i < numFloors; i++) {
            this.getMyFloors().add(FloorFactory.build(i + 1));
        }

    }

    /**
     * @return myFloors
     */
    private ArrayList<Floor> getMyFloors() {
        return this.myFloors;
    }

    /**
     * @return the numberOfFloors
     */
    public int getNumberOfFloors() {
        return numberOfFloors;
    }

    /**
     * @param numberOfFloors
     *            the numberOfFloors to set
     */
    private void setNumberOfFloors(int numF) {
        this.numberOfFloors = numF;
    }

}
