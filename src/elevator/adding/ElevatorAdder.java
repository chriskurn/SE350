package elevator.adding;

import elevator.common.ElevatorDirection;
import elevator.common.InvalidFloorException;

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
