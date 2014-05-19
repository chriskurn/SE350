package simulator.common;

/**
 * Description: SimulationInformation class
 * 
 * @author Patrick Stein
 * @author Chris Kurn
 * @since Version 1.0 - Spring Quarter 2014
 * @see package simulator.common The purpose of this class is to encapsulate all
 *      of the data associated with building and running the simulation.
 */

/**
 * Defines simulation parameters for project.
 * Parameters are read in from external file: simInput.properties
 */
public class SimulationInformation {

    public int numElevators;
    public int numFloors;
    public int numExpressElevators;
    public int numPeoplePerElevator;
    public int personPerMin;
    public long floorTime;
    public long doorTime;
    public long elevatorSleepTime;
    public long simRunTime;

}
