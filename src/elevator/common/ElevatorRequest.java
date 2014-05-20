package elevator.common;

import simulator.Simulator;
import simulator.common.IllegalParamException;
import simulator.common.SimulationInformation;

/**
 * Description: ElevatorRequest class.
 *
 * @author Patrick Stein
 * @author Chris Kurn
 * @see package elevator.common;
 * @see import simulator.Simulator;
 * @see import simulator.common.IllegalParamException;
 * @see import simulator.common.SimulationInformation;
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
     * @param floorRequestedFrom the floor requested from
     * @param directionOfTravel the direction of travel
     * @throws IllegalParamException the illegal param exception
     */
    public ElevatorRequest(int floorRequestedFrom,
            ElevatorDirection directionOfTravel) throws IllegalParamException {
        this.setDirection(directionOfTravel);
        this.setFloor(floorRequestedFrom);

    }

    /**
     * Sets the floor within a building.
     *
     * @param floorRequestedFrom the new floor
     * @throws IllegalParamException the illegal param exception
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
     * Sets elevator travel direction.
     *
     * @param directionOfTravel the new direction
     * @throws IllegalParamException the illegal param exception
     */
    private void setDirection(ElevatorDirection directionOfTravel)
            throws IllegalParamException {
        if (directionOfTravel == ElevatorDirection.IDLE) {
            throw new IllegalParamException(
                    "Cannot provide the idle state for an elevator request.");
        }
        this.direction = directionOfTravel;

    }

    /**
     * Gets the floor.
     *
     * @return the floor
     */
    public int getFloor() {
        return this.floor;
    }

    /**
     * Gets the direction.
     *
     * @return the direction
     */
    public ElevatorDirection getDirection() {
        return this.direction;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    public String toString() {
        String dir = this.getDirection() == ElevatorDirection.UP ? "up"
                : "down";
        return String.format("Request from floor %d for direction %s.",
                this.getFloor(), dir);
    }

    /**
     * Must be an ElevatorRequest object that is not null This will only return
     * true if the floor and direction are both equal.
     *
     * @param obj the obj
     * @return true, if successful
     */
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

        if (this.getFloor() == rhs.getFloor()
                && this.getDirection() == rhs.getDirection()) {
            return true;
        } else {
            return false;
        }

    }

}
