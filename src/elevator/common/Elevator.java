package elevator.common;

/**
 * Description: interface Elevator
 * 
 * @author Chris Kurn, Patrick Stein
 * @since Version 1.0 - Spring Quarter 2014
 * @see package elevator.movement
 */

public interface Elevator {

    public void startElevator();

    public void stopElevator();

    public void addFloor(int floor) throws InvalidFloorException;

    public void addFloor(int floor, ElevatorDirection dir)
            throws InvalidFloorException;

    public int getCurrentFloor();

    public ElevatorDirection getDirection();

    int getDestination();

    boolean destinationsLeft();

}
