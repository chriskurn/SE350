package elevator.elements;

import java.util.ArrayList;

import building.common.Person;
import elevator.common.ElevatorDirection;
/**
 * @author Patrick Stein
 * 
 */

public class ElevatorPeoplePickupFactory {

    public static ElevatorPeoplePickup build(ArrayList<Person> people,
            ElevatorDirection dir, int maxPeople) {

        return new ElevatorPeoplePickupImpl(people, dir, maxPeople);

    }

}
