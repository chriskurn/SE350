package building.common;

import java.util.ArrayList;

import elevator.common.ElevatorDirection;

import simulator.common.IllegalParamException;

/**
 * Description: Floor interface class.
 *
 * @author Patrick Stein
 * @author Chris Kurn
 * @since Version 1.0 - Spring Quarter 2014
 */

public interface Floor {
    
    /**
     * Are there any people on this floor? If there are none then returns true. Else false.
     * @return
     */
    public boolean isEmpty();

    /**
     * Gets the floor.
     *
     * @return the floor
     */
    public int getFloor();
    
    /**
     * Enter floor.
     *
     * @param p the p
     * @throws IllegalParamException the illegal param exception
     */
    public int enterFloor(Person p) throws IllegalParamException;
    
    /**
     * Leave floor.
     */
   public  ArrayList<Person> leaveFloor(ElevatorDirection dir);

}