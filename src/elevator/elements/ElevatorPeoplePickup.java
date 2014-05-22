package elevator.elements;

import elevator.common.InvalidFloorException;

/**
 * Description: ElevatorPeoplePickup Interface.
 * 
 * @author Patrick Stein
 * @author Chris Kurn
 * @since Version 1.0 - Spring Quarter 2014
 */

public interface ElevatorPeoplePickup {
    /**
     * A method designed to load people in from a given floor.
     * 
     * @throws InvalidFloorException
     *             thrown if the floor given to it is invalid
     */
    public void loadPeople(int floor) throws InvalidFloorException;

    /**
     * Method designed to unload people objects from the elevator to a floor.
     */
    public void unloadPeople(int floor);

}
