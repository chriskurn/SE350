package elevator.control;

import java.util.ArrayList;

import simulator.common.IllegalParamException;
import elevator.elements.Elevator;

/**
 * Description: Elevator RequestHandler Factory.
 * 
 * A factory for creating ElevatorRequestHandler objects.
 * 
 * @author Patrick Stein
 * @author Chris Kurn
 * @since Version 1.0 - Spring Quarter 2014
 */

public class ElevatorRequestHandlerFactory {

    /**
     * Builds the.
     * 
     * @param theElevators
     *            the the elevators
     * @return the elevator request handler
     * @throws IllegalParamException
     *             the illegal param exception
     */
    public static ElevatorRequestHandler build(ArrayList<Elevator> theElevators)
            throws IllegalParamException {

        return new ElevatorRequestHandlerImpl(theElevators);

    }

}
