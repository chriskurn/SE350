package simulator.elements;

import elevator.common.InvalidFloorException;

/**
 * Description: Person interface class
 * 
 * @author Patrick Stein
 * @author Chris Kurn
 * @since Version 1.0 - Spring Quarter 2014
 * @see package simulator.elements
 * @see import elevator.common.InvalidFloorException;
 */

public interface Person {
	
	public void setFloor(int floor) throws InvalidFloorException;
}
