package elevator.common;

import simulator.common.IllegalParamException;
import simulator.common.SimulationInformation;

public class ElevatorFactory {

    public static Elevator build(SimulationInformation info)
            throws IllegalParamException {
        return new ElevatorImpl(info);

    }
}
