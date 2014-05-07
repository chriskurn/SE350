package elevator.adding;

import elevator.common.Elevator;
import simulator.common.SimulationInformation;

public class ElevatorAdderFactory {
    public static ElevatorAdder build(SimulationInformation info) {
        return new ElevatorAdderImpl(info);
    }
}
