package elevator.control;

import java.util.ArrayList;

import simulator.common.IllegalParamException;

import elevator.common.ElevatorDirection;
import elevator.common.ElevatorRequest;
import elevator.common.InvalidFloorException;
import elevator.elements.Elevator;

/**
 * The Class ElevatorRequestHandlerImpl.
 *
 * @author Patrick
 */
public class ElevatorRequestHandlerImpl implements ElevatorRequestHandler {
    
    /** The elevators. */
    private ArrayList<Elevator> elevators;
    

    /**
     * Instantiates a new elevator request handler impl.
     *
     * @param theElevators the the elevators
     * @throws IllegalParamException the illegal param exception
     */
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
                //Try a different one
                continue;
            }
        }
        return false;

    }
    
    /**
     * Gets the elevators.
     *
     * @return the elevators
     */
    private ArrayList<Elevator> getElevators() {
        return this.elevators;
        
    }

    /**
     * Sets the elevators.
     *
     * @param theElevators the new elevators
     * @throws IllegalParamException the illegal param exception
     */
    private void setElevators(ArrayList<Elevator> theElevators) throws IllegalParamException{
        if(theElevators == null){
            throw new IllegalParamException("The elevators cannot be null.");
        }
        this.elevators = theElevators;
        
    }
    

}
