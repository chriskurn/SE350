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

    private int getElevatorId() {
        return elevatorId;
    }

    /**
     * Private method for getting the people array list
     * 
     * @return people array list containing all of the people
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
    public ArrayList<Person> loadPeople(int floor) throws InvalidFloorException {
        ArrayList<Person> peopleAdded = new ArrayList<Person>();
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
            peopleAdded.add(p);
            currentFriends.add(p);
            p.elevatorEntered();
            newFriends.remove(0);
            String logEvent = String.format(
                    "Person %s entered elevator %d [Riders: %s]", p,
                    getElevatorId(), currentFriends);
            Simulator.getInstance().logEvent(logEvent);
        }
        // If anyone is left over, return them to their floor
        returnPeopleToFloor(newFriends, curFloor);

        return peopleAdded;

    }

    /**
     * A helper method that will return people to their respective floor if they
     * try and enter a full elevator.
     * 
     * @param newFriends
     *            An array list of the people left over that need to return to
     *            their floors
     * @param floor
     *            the floor that the people in array list need to return to
     */
    private void returnPeopleToFloor(ArrayList<Person> newFriends, int floor) {
        int curFloor = floor;
        // Put people back on the floor if the elevator ran out of space
        while (!newFriends.isEmpty()) {
            Person p = null;
            try {
                p = newFriends.get(0);
                Building.getInstance().enterFloor(p, curFloor);
                // The person now needs to make his request again
                p.startPerson();
            } catch (IllegalParamException | InvalidFloorException e) {
                String event = String
                        .format("Unable to return %s to floor %d. Setting error status and moving on.",
                                p, curFloor);
                Simulator.getInstance().logEvent(event);
                p.setInvalidStatus();
            }
            newFriends.remove(0);
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

    /**
     * Unload people from elevator.
     */
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
                String logEvent = String.format(
                        "Person %s has left elevator %d [Riders: %s]", person,
                        getElevatorId(), currentPeople);
                Simulator.getInstance().logEvent(logEvent);
            }
        }

    }

}
