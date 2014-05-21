package elevator.elements;

import java.util.ArrayList;

import building.common.Person;

import elevator.common.ElevatorDirection;

public class ElevatorPeoplePickupFactory {
    
    
    public static ElevatorPeoplePickup build(ArrayList<Person> people,int curFloor,ElevatorDirection dir, int maxPeople){
        
        return new ElevatorPeoplePickupImpl(people,curFloor,dir, maxPeople);
        
    }

}
