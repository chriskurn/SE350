package elevator.common;

import simulator.Simulator;
import simulator.common.IllegalParamException;
import simulator.common.SimulationInformation;

/**
 * Description: ElevatorRequest class.
 * 
 * ElevatorRequest class which encapsulates the parameters associated with making an elevator request.
 * That is, a floor number and a direction.
 * 
 * @author Patrick Stein
 * @author Chris Kurn
 * @since Version 1.0 - Spring Quarter 2014
 */

/**
 * Elevator Request Class
 */
public class ElevatorRequest {

    /** The floor. */
    private int floor;

    /** The direction. */
    private ElevatorDirection direction;

    /**
     * Instantiates a new elevator request.
     * 
     * @param floorRequestedFrom
     *            the floor requested from
     * @param directionOfTravel
     *            the direction of travel
     * @throws IllegalParamException
     *             the illegal param exception
     */
    public ElevatorRequest(int floorRequestedFrom,
            ElevatorDirection directionOfTravel) throws IllegalParamException {
        setDirection(directionOfTravel);
        setFloor(floorRequestedFrom);

    }

    /**
     * Must be an ElevatorRequest object that is not null This will only return
     * true if the floor and direction are both equal.
     * 
     * @param obj
     *            an object that will hopefully be another ElevatorRequest
     * @return true if the obj has the same floor and the same direction of this
     *         request.
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof ElevatorRequest)) {
            return false;
        }

        ElevatorRequest rhs = (ElevatorRequest) obj;

        if (getFloor() == rhs.getFloor()
                && getDirection() == rhs.getDirection()) {
            return true;
        } else {
            return false;
        }

    }

    /**
     * Gets the direction associated with the request.
     * 
     * @return the direction
     */
    public ElevatorDirection getDirection() {
        return direction;
    }

    /**
     * Gets the floor associated with the request.
     * 
     * @return the floor
     */
    public int getFloor() {
        return floor;
    }

    /**
     * Sets elevator travel direction.
     * 
     * @param directionOfTravel
     *            the new direction
     * @throws IllegalParamException
     *             thrown if someone tries to make the direction IDLE. It cannot
     *             be idle.
     */
    private void setDirection(ElevatorDirection directionOfTravel)
            throws IllegalParamException {
        if (directionOfTravel == ElevatorDirection.IDLE) {
            throw new IllegalParamException(
                    "Cannot provide the idle state for an elevator request.");
        }
        direction = directionOfTravel;

    }

    /**
     * Sets the floor within a building.
     * 
     * @param floorRequestedFrom
     *            the new floor
     * @throws IllegalParamException
     *             thrown if someone wants to set the floor below what is
     *             allowed for the simulation.
     */
    private void setFloor(int floorRequestedFrom) throws IllegalParamException {
        SimulationInformation info = Simulator.getInstance()
                .getSimulationInfo();
        if (floorRequestedFrom > info.numFloors || floorRequestedFrom <= 0) {
            throw new IllegalParamException(
                    "The floor you provided for this elevator request exceeds the floors of the building.");
        }
        floor = floorRequestedFrom;
    }

    /**
     * Public method for toString
     */
    @Override
    public String toString() {
        String dir = getDirection() == ElevatorDirection.UP ? "up" : "down";
        return String.format("Request from floor %d for direction %s.",
                getFloor(), dir);
    }

}
