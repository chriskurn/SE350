/**
 * 
 */
package elevator.control;

import java.util.ArrayList;

import simulator.Simulator;
import simulator.common.IllegalParamException;

import elevator.common.Elevator;
import elevator.common.ElevatorDirection;
import elevator.common.ElevatorRequest;
import elevator.common.InvalidFloorException;

/**
 * @author Patrick
 *
 */
public class ElevatorRequestHandlerImpl implements ElevatorRequestHandler {
    
    private ArrayList<Elevator> elevators;
    

    public ElevatorRequestHandlerImpl(ArrayList<Elevator> theElevators) throws IllegalParamException {
        this.setElevators(theElevators);
    }

    /* (non-Javadoc)
     * @see elevator.control.ElevatorRequestHandler#handleRequests(java.util.ArrayList)
     */
    @Override
    public boolean handleRequest(ElevatorRequest eleRequest) {
        
        ElevatorDirection requestedDirection = eleRequest.getDirection();
        int targetFloor = eleRequest.getFloor();

        for (Elevator e : this.getElevators()) {
            // are you going in the same direction?
            // and are you within range the range of that elevator?
            try {
                if (requestedDirection == e.getDirection()) {
                    e.addFloor(targetFloor, requestedDirection);
                    return true;
                } else if (e.getDirection() == ElevatorDirection.IDLE) {
                    e.addFloor(targetFloor, requestedDirection);
                    return true;
                } else {
                    // It's implied but I wanted to make sure that this was fine
                    continue;
                }
            } catch (InvalidFloorException exception) {
                // unable to add try a different elevator
                Simulator
                        .getInstance()
                        .logEvent(
                                String.format(
                                        "Unable to add target floor: %d with direction: %s to elevator: %d.",
                                        targetFloor, requestedDirection,
                                        e.getElevatorId()));
                continue;
            }
        }
        return false;

    }
    
    private ArrayList<Elevator> getElevators() {
        return this.elevators;
        
    }

    private void setElevators(ArrayList<Elevator> theElevators) throws IllegalParamException{
        if(theElevators == null){
            throw new IllegalParamException("The elevators cannot be null.");
        }
        this.elevators = theElevators;
        
    }
    

}
