package building.elements;

import java.util.ArrayList;

import simulator.common.IllegalParamException;
import building.common.Person;
import elevator.common.ElevatorDirection;

/**
 * Description: Floor interface class.
 * 
 * @author Patrick Stein
 * @author Chris Kurn
 * @since Version 1.0 - Spring Quarter 2014
 */

public interface Floor {

    /**
     * Takes a person object and attempts to enter this floor with that person.
     * 
     * @param p
     *            The person who wants to enter this floor. Cannot be null.
     * @throws IllegalParamException
     *             throws this exception if the person is null. I don't need no
     *             null people!
     * @return the floor number where this person entered.
     */
    public int enterFloor(Person p) throws IllegalParamException;

    /**
     * Gets the floor number of this floor
     * 
     * @return the floor number
     */
    public int getFloor();

    /**
     * Are there any people on this floor? If there are none then returns true.
     * Else false.
     * 
     * @return Returns true if nobody is on this floor. Else it is false.
     */
    public boolean isEmpty();

    /**
     * This method is associated with gathering people to load into an elevator.
     * It gets anyone looking to go up or down (given the dir parameter) and
     * returns an array full of them.
     * 
     * @param dir
     *            The direction the elevator is going.
     * @return an array full of people that are leaving this floor.
     */
    public ArrayList<Person> leaveFloor(ElevatorDirection dir);

}