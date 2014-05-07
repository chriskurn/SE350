package elevator.common;

/**
 * Description: Elevator
 * @author Patrick Stein
 * @author Chris Kurn 
 * @since Version 1.0 - Spring Quarter 2014
 * @see package elevator.common
 */

public interface Elevator{
    
    public void startElevator();
    public void stopElevator();
    public void addFloor(int floor) throws InvalidFloorException;
    public int getCurrentFloor();
    public ElevatorDirection getDirection();
    

}
