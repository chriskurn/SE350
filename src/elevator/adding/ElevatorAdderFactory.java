package elevator.adding;

import elevator.common.Elevator;
import simulator.common.SimulationInformation;

/**
 * Description: ElevatorAdderFactory class
 * @author Patrick Stein
 * @author Chris Kurn 
 * @since Version 1.0 - Spring Quarter 2014
 * @see package elevator.adding
 * @see import elevator.common.Elevator;
 * @see import simulator.common.SimulationInformation;
 */

public class ElevatorAdderFactory {
    public static ElevatorAdder build(SimulationInformation info) {
        return new ElevatorAdderImpl(info);
    }
}
