package elevator.movement;

/**
 * Description: interface Elevator
 * @author Chris Kurn, Patrick Stein
 * @since Version 1.0 - Spring Quarter 2014
 * @see package elevator.movement
 */

public interface Elevator {
    
    public void startElevator();
    public void stopElevator();
    public void addFloor(int floor);

}


//moveToFloor(dest)
