/**
 * 
 */
package elevator.elements;

import java.util.ArrayList;
import java.util.Iterator;

import simulator.Simulator;
import simulator.common.IllegalParamException;

import building.Building;
import building.common.Person;
import elevator.common.ElevatorDirection;
import elevator.common.InvalidFloorException;

/**
 * @author Patrick
 *
 */
public class ElevatorPeoplePickupImpl implements ElevatorPeoplePickup {
    
    
    private int currentFloor;
    ArrayList<Person> currentPeople;
    ElevatorDirection currentDirection;
    int maxNumberOfPeople;
    

    public ElevatorPeoplePickupImpl(ArrayList<Person> people, int curFloor,
            ElevatorDirection dir, int maxPeople) {
        
        this.setCurrentDirection(dir);
        this.setMaxNumberOfPeople(maxPeople);
        this.setCurrentFloor(curFloor);
        this.setCurrentPeople(people);
    }

    @Override
    public void unloadPeople(){
        int curFloor = getCurrentFloor();
        ElevatorDirection dir = getCurrentDirection();
        ArrayList<Person> currentPeople = getCurrentPeople();
        
        Iterator<Person> p = currentPeople.iterator();
        
        while(p.hasNext()){
            //is this their floor?
            Person person = p.next();
            if(person.getDestinationFloor() == curFloor){
                try {
                    Building.getInstance().enterFloor(person, curFloor);
                } catch (IllegalParamException | InvalidFloorException e) {
                    String eve = String.format("Elevator tried to move a person from the elevator to floor %d. This operation failed. Removing person and continuing on.", curFloor);
                    Simulator.getInstance().logEvent(eve);
                }
                p.remove();
                person.setInvalidStatus();
            }
        }

    }
    
    @Override
    public void loadPeople() throws InvalidFloorException{
        // Ask building to load in people for this floor
        int curFloor = getCurrentFloor();
        ElevatorDirection dir = getCurrentDirection();
        ArrayList<Person> currentFriends = getCurrentPeople();
           
        //Let's get those people on the floor!
        ArrayList<Person> newFriends = Building.getInstance().loadPeople(curFloor,dir);
        //TODO add a check to make sure the elevator is overflowing
        int maxPeeps = this.getMaxNumberOfPeople();
        currentFriends.addAll(newFriends);
        
    }
    private int getCurrentFloor() {
        return currentFloor;
    }

    private void setCurrentFloor(int cFloor) {
        this.currentFloor = cFloor;
    }

    private ArrayList<Person> getCurrentPeople() {
        return currentPeople;
    }

    private void setCurrentPeople(ArrayList<Person> cPeople) {
        this.currentPeople = cPeople;
    }

    private ElevatorDirection getCurrentDirection() {
        return currentDirection;
    }

    private void setCurrentDirection(ElevatorDirection cDir) {
        this.currentDirection = cDir;
    }

    private int getMaxNumberOfPeople() {
        return maxNumberOfPeople;
    }

    private void setMaxNumberOfPeople(int mNum) {
        this.maxNumberOfPeople = mNum;
    }

}
