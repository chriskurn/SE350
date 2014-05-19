package elevator.common;

import simulator.common.IllegalParamException;
import simulator.common.SimulationInformation;

/**
 * Description: ElevatorFactory class
 * 
 * @author Patrick Stein
 * @author Chris Kurn
 * @since Version 1.0 - Spring Quarter 2014
 * @see package simulator.elements
 * @see import simulator.common.IllegalParamException;
 * @see import simulator.common.SimulationInformation;
 */

public class ElevatorFactory {

    public static Elevator build(SimulationInformation info)
            throws IllegalParamException {
        return new ElevatorImpl(info);

    }
}
