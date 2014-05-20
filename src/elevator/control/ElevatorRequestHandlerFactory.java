package elevator.control;

import java.util.ArrayList;

import simulator.common.IllegalParamException;

import elevator.common.Elevator;

public class ElevatorRequestHandlerFactory {
    
    public static ElevatorRequestHandler build(ArrayList<Elevator> theElevators) throws IllegalParamException{
        
        return new ElevatorRequestHandlerImpl(theElevators);
        
    }

}
