package elevator.common;

/**
 * 
 * Description: Elevator interface class
 * @author Patrick Stein
 * @author Chris Kurn 
 * @since Version 1.0 - Spring Quarter 2014
 * @see package elevator.common
 */

public interface Elevator {

    public void startElevator();

    public void stopElevator();

    public void addFloor(int floor) throws InvalidFloorException;

    public void addFloor(int floor, ElevatorDirection dir)
            throws InvalidFloorException;

    public int getCurrentFloor();

    public ElevatorDirection getDirection();

    int getDestination() throws NoNewDestinationException;

    boolean destinationsLeft();

}
