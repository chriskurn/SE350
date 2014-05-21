package elevator.control;

//import java.util.ArrayList;
import elevator.common.ElevatorRequest;

/**
 * Description: The Interface ElevatorRequestHandler.
 *
 * @author Patrick Stein
 * @author Chris Kurn
 * @since Version 1.0 - Spring Quarter 2014
 */

public interface ElevatorRequestHandler {

    
    /**
     * Handle request.
     *
     * @param eleRequest the ele request
     * @return true, if successful
     */
    public boolean handleRequest(ElevatorRequest eleRequest);
    
    
}
