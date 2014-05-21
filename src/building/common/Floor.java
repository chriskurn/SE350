package building.common;

import java.util.ArrayList;

import elevator.common.ElevatorDirection;

import simulator.common.IllegalParamException;

/**
 * Description: Floor itnerface class
 * 
 * @author Patrick Stein
 * @author Chris Kurn
 * @since Version 1.0 - Spring Quarter 2014
 * @see package simulator.elements
 */

public interface Floor {

    public int getFloor();
    public int enterFloor(Person p) throws IllegalParamException;
    public  ArrayList<Person> leaveFloor(ElevatorDirection dir);

}