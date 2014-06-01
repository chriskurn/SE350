package elevator.common;

import simulator.common.IllegalParamException;
import simulator.common.SimulationInformation;
import elevator.elements.Elevator;
import elevator.elements.ElevatorImpl;

/**
 * Description: ElevatorFactory class.
 *
 * @author Patrick Stein
 * @author Chris Kurn
 * @since Version 1.0 - Spring Quarter 2014
 */

/**
 * ElevatorFactory class Builds an elevator
 */
public class ElevatorFactory {

    /**
     * Builds a new elevator.
     * 
     * @param info
     *            the simulation information required to build an elevator. This
     *            is a DTO provided by Simulator.
     * @return a new elevator based on the DTO provided
     * @throws IllegalParamException
     *             thrown if any of the in the DTO are null or if the floor
     *             cannot possible be in the building.
     */
    public static Elevator build(SimulationInformation info)
            throws IllegalParamException {
        return new ElevatorImpl(info);

    }
}
