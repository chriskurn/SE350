package elevator.elements;

import java.util.ArrayList;

import building.common.Person;
import elevator.common.ElevatorDirection;

/**
 * Description: ElevatorPeoplePickupFactory class.
 * 
 * @author Patrick Stein
 * @author Chris Kurn
 * @since Version 1.0 - Spring Quarter 2014
 */

/**
 * Public class Elevator People Pickup Factory
 * 
 */
public class ElevatorPeoplePickupFactory {

    public static ElevatorPeoplePickup build(ArrayList<Person> people,
            ElevatorDirection dir, int maxPeople, int eleId) {

        return new ElevatorPeoplePickupImpl(people, dir, maxPeople, eleId);

    }

}
