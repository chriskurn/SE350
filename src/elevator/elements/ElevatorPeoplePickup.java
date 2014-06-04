package elevator.elements;

import java.util.ArrayList;

import building.common.Person;
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
     * @return A list of all of the new people the elevator obtained
     */
    public ArrayList<Person> loadPeople(int floor) throws InvalidFloorException;

    /**
     * Method designed to unload people objects from the elevator to a floor.
     */
    public void unloadPeople(int floor);

}
