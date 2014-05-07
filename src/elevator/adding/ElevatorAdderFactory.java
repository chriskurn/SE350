package elevator.adding;

import elevator.common.Elevator;
import simulator.common.SimulationInformation;

/**
 * Description: Elevator Adder Factory
 * @author Patrick Stein
 * @author Chris Kurn 
 * @since Version 1.0 - Spring Quarter 2014
 * @see package elevator.adding
 */

public class ElevatorAdderFactory {
    public static ElevatorAdder build(SimulationInformation info) {
        return new ElevatorAdderImpl(info);
    }
}
