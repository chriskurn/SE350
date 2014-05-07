package elevator.adding;

import elevator.common.ElevatorDirection;
import elevator.common.InvalidFloorException;

/**
 * Description: ElevatorAdder Interface
 * @author Patrick Stein
 * @author Chris Kurn 
 * @since Version 1.0 - Spring Quarter 2014
 * @see package elevator.adding
 * @see import elevator.common.ElevatorDirection;
 * @see import elevator.common.InvalidFloorException;
 */

public interface ElevatorAdder {
    public ElevatorDirection getDirection();
    public int getCurrentFloor();
    public void setCurrentFloor(int newFloor);
    public int getDestination();
    public void removeDestination();
    public void addFloor(int floor) throws InvalidFloorException;
    public boolean destinationsLeft();
    public void stoppedMoving();
}
