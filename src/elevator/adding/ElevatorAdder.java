package elevator.adding;

import elevator.common.ElevatorDirection;
import elevator.common.InvalidFloorException;

/**
 * Description: Elevator Adder
 * @author Patrick Stein
 * @author Chris Kurn 
 * @since Version 1.0 - Spring Quarter 2014
 * @see package elevator.adding
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
