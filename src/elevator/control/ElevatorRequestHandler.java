package elevator.control;

import java.util.ArrayList;

import elevator.common.ElevatorRequest;

public interface ElevatorRequestHandler {

    
    public boolean handleRequest(ElevatorRequest eleRequest);
    
    
}
