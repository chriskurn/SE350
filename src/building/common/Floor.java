package building.common;

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
    public void enterFloor(Person p) throws IllegalParamException;
    
    /**
     * Leave floor.
     *
     * @param p the p
     */
    public void leaveFloor(Person p);

}