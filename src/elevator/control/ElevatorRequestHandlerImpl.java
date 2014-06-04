package elevator.control;

import java.util.ArrayList;

import simulator.common.IllegalParamException;
import elevator.common.ElevatorDirection;
import elevator.common.ElevatorRequest;
import elevator.common.InvalidFloorException;
import elevator.elements.Elevator;

/**
 * Description: The Class ElevatorRequestHandlerImpl.
 * 
 * The building is a singleton facade responsible for managing the floors. It
 * also has methods that allow for movement of people to different floors and
 * people leaving a floor.
 * 
 * @author Patrick Stein
 * @author Chris Kurn
 * @since Version 1.0 - Spring Quarter 2014
 */
public class ElevatorRequestHandlerImpl implements ElevatorRequestHandler {

    /** The elevators. */
    private ArrayList<Elevator> elevators;

    /**
     * Instantiates a new elevator request handler impl.
     * 
     * @param theElevators
     *            the the elevators
     * @throws IllegalParamException
     *             the illegal param exception
     */
    public ElevatorRequestHandlerImpl(ArrayList<Elevator> theElevators)
            throws IllegalParamException {
        this.setElevators(theElevators);
    }

    /**
     * Elevator Request Handler
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
                // Try a different one
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
     * @param theElevators
     *            the new elevators
     * @throws IllegalParamException
     *             Thrown if the array list provided is null.
     */
    private void setElevators(ArrayList<Elevator> theElevators)
            throws IllegalParamException {
        if (theElevators == null) {
            throw new IllegalParamException("The elevators cannot be null.");
        }
        this.elevators = theElevators;

    }

}
