package simulator.common;

/**
 * Description: SimulationInformation class.
 * 
 * The purpose of this class is to encapsulate all of the data associated with
 * building and running the simulation. external file: simInput.properties
 * 
 * @author Patrick Stein
 * @author Chris Kurn
 * @since Version 1.0 - Spring Quarter 2014
 */

public class SimulationInformation {

    /** The num elevators. */
    public int numElevators;

    /** The num floors. */
    public int numFloors;

    /** The num express elevators. */
    public int numExpressElevators;

    /** The num people per elevator. */
    public int numPeoplePerElevator;

    /** The person per min. */
    public int personPerMin;

    /** The floor time. */
    public long floorTime;

    /** The door time. */
    public long doorTime;

    /** The elevator sleep time. */
    public long elevatorSleepTime;

    /** The sim run time. */
    public long simRunTime;
    /** The default floor elevators go to after timeout */
    public int defaultElevatorFlr;

}
