package elevator.common;

import simulator.common.IllegalParamException;
import simulator.common.SimulationInformation;

/**
 * Description: ElevatorFactory class.
 *
 * @author Patrick Stein
 * @author Chris Kurn
 * @see package simulator.elements
 * @see import simulator.common.IllegalParamException;
 * @see import simulator.common.SimulationInformation;
 * @since Version 1.0 - Spring Quarter 2014
 */

/**
 * ElevatorFactory class Builds an elevator
 */
public class ElevatorFactory {

    /**
     * Builds the.
     *
     * @param info the info
     * @return the elevator
     * @throws IllegalParamException the illegal param exception
     */
    public static Elevator build(SimulationInformation info)
            throws IllegalParamException {
        return new ElevatorImpl(info);

    }
}
