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
 * Description: ElevatorPeoplePickupImpl.
 * 
 * @author Patrick Stein
 * @author Chris Kurn
 * @since Version 1.0 - Spring Quarter 2014
 */

public class ElevatorPeoplePickupImpl implements ElevatorPeoplePickup {

    private ArrayList<Person> currentPeople;
    private ElevatorDirection currentDirection;
    private int maxNumberOfPeople;
    private int elevatorId;

    /**
     * Construction method for making a new ElevatorPeoplePickupImpl
     * 
     * @param people
     *            an arraylist of people you want to load into.
     * @param dir
     *            Direction of travel you want to pick people up from
     * @param maxPeople
     *            the man number of people that will fit based on the
     *            simulation.
     */
    public ElevatorPeoplePickupImpl(ArrayList<Person> people,
            ElevatorDirection dir, int maxPeople, int eleId) {

        setCurrentDirection(dir);
        setMaxNumberOfPeople(maxPeople);
        setCurrentPeople(people);
        elevatorId = eleId;
    }

    /**
     * The direction of travel this elevator is going.
     * 
     * @return the direction of travel
     */
    private ElevatorDirection getCurrentDirection() {
        return currentDirection;
    }

    private int getElevatorId(){
        return elevatorId;
    }
    
    /**
     * Private method for getting the people arraylist
     * 
     * @return people arraylist
     */
    private ArrayList<Person> getCurrentPeople() {
        return currentPeople;
    }

    /**
     * Get the max number of people.
     * 
     * @return the max number of people available for an elevator
     */
    private int getMaxNumberOfPeople() {
        return maxNumberOfPeople;
    }

    /**
     * Ask building to load in people for this floor
     */
    @Override
    public void loadPeople(int floor) throws InvalidFloorException {
        int curFloor = floor;
        ElevatorDirection dir = getCurrentDirection();
        int maxPeeps = getMaxNumberOfPeople();
        ArrayList<Person> currentFriends = getCurrentPeople();
        // Let's get those people on the floor!
        ArrayList<Person> newFriends = Building.getInstance().loadPeople(
                curFloor, dir);
        int spotsAvailable = maxPeeps - newFriends.size();
        // If we got space and there are people in that array
        for (int i = 0; i < spotsAvailable && !newFriends.isEmpty(); i++) {
            // Add them and remove them
            Person p = newFriends.get(0);
            currentFriends.add(p);
            p.elevatorEntered();
            newFriends.remove(0);
            String logEvent = String.format("Person %s entered elevator %d [Riders: %s]",
                                        p,getElevatorId(),currentFriends);
            Simulator.getInstance().logEvent(logEvent);
        }

    }

    /**
     * Sets the direction of travel.
     * 
     * @param cDir
     *            new direction of travel.
     */
    private void setCurrentDirection(ElevatorDirection cDir) {
        currentDirection = cDir;
    }

    /**
     * Private set method for setting up the people arraylist
     * 
     * @param cPeople
     *            array list of people
     */
    private void setCurrentPeople(ArrayList<Person> cPeople) {
        currentPeople = cPeople;
    }

    /**
     * Sets the number of people that can be in an elevator.
     * 
     * @param mNum
     *            the max number of people.
     */
    private void setMaxNumberOfPeople(int mNum) {
        maxNumberOfPeople = mNum;
    }

    @Override
    public void unloadPeople(int floor) {
        int curFloor = floor;
        ArrayList<Person> currentPeople = getCurrentPeople();

        Iterator<Person> p = currentPeople.iterator();

        while (p.hasNext()) {
            // is this their floor?
            Person person = p.next();
            if (person.getDestinationFloor() == curFloor) {
                try {
                    Building.getInstance().enterFloor(person, curFloor);
                    // Person event for leaving the elevator
                    person.leftElevator();
                } catch (IllegalParamException | InvalidFloorException e) {
                    String eve = String
                            .format("Elevator tried to move a person from the elevator to floor %d. This operation failed. Removing person and continuing on.",
                                    curFloor);
                    Simulator.getInstance().logEvent(eve);
                    person.setInvalidStatus();
                }
                p.remove();
                String logEvent = String.format("Person %s has left elevator %d [Riders: %s]",
                        person,getElevatorId(),currentPeople);
                Simulator.getInstance().logEvent(logEvent);
            }
        }

    }

}
