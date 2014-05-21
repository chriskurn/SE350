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
     * TODO Maybe pass the previous direction of travel ?
     */
   public  ArrayList<Person> leaveFloor(ElevatorDirection dir);

}