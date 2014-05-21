package building.elements;

import java.util.ArrayList;
import java.util.Iterator;

import simulator.Simulator;
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

public class FloorImpl implements Floor {

    /** The my floor. */
    private int myFloor;

    /** The floor peoples. */
    private ArrayList<Person> floorPeoples = new ArrayList<Person>();

    /** The finished people. */
    private ArrayList<Person> finishedPeople = new ArrayList<Person>();

    /**
     * Creates a new floor impl.
     * 
     * @param floorNumber
     *            the floor number of this floor
     * @throws IllegalParamException throws this exception if the floor number is < 1
     */
    public FloorImpl(int floorNumber) throws IllegalParamException {
        this.setMyFloor(floorNumber);
    }

    /*
     * (non-Javadoc)
     * 
     * @see building.common.Floor#enterFloor(building.common.Person)
     */
    @Override
    public int enterFloor(Person p) throws IllegalParamException {
        int thisFloor = getFloor();

        if (p == null) {
            throw new IllegalParamException(String.format(
                    "Cannot add a null to the person array on floor %d.",
                    getFloor()));
        }

        Simulator.getInstance().logEvent(
                String.format("Person %d has entered floor: %d",
                        p.getPersonId(), this.getFloor()));

        // Then this person is done moving yay!
        if (p.getDestinationFloor() == thisFloor) {
            synchronized (this) {
                this.getFinishedPeople().add(p);
                // send message to simulation
                int pid = p.getPersonId();
                String personFinished = String
                        .format("Person %d has finished by entering his destination floor %d.",
                                pid, thisFloor);
                Simulator.getInstance().logEvent(personFinished);
            }
        } else {
            // else he needs to be added to the already waiting people
            synchronized (this) {
                this.getFloorPeople().add(p);
            }
        }
        return thisFloor;
    }

    /**
     * Private get method for getting the people who have finished execution and are chilling
     * @return a list of the people who are done running
     */
    private ArrayList<Person> getFinishedPeople() {
        return this.finishedPeople;
    }

    /*
     * (non-Javadoc)
     * 
     * @see building.common.Floor#getFloor()
     */
    @Override
    public int getFloor() {
        return this.myFloor;
    }

    /**
     * Private get method for the list of people that are waiting for an elevator
     * @return a list of people as said above
     */
    private ArrayList<Person> getFloorPeople() {
        return this.floorPeoples;
    }

    @Override
    public synchronized boolean isEmpty() {
        return getFloorPeople().isEmpty();
    }

    /*
     * (non-Javadoc)
     * 
     * @see building.common.Floor#leaveFloor(building.common.Person)
     */
    @Override
    public ArrayList<Person> leaveFloor(ElevatorDirection dir) {
        int thisFloor = getFloor();
        ArrayList<Person> leavingPeople = new ArrayList<Person>();
        Iterator<Person> p = this.getFloorPeople().iterator();
        while (p.hasNext()) {
            Person person = p.next();
            // If the direction is up and the destination floor is above this
            // floor
            // Or if the direction is down and the destination floor is above this
            // floor
            // Then we are good to leave the floor
            if (dir == ElevatorDirection.UP
                    && person.getDestinationFloor() > thisFloor
                    || dir == ElevatorDirection.DOWN
                    && person.getDestinationFloor() < thisFloor
                    || dir == ElevatorDirection.IDLE) {
                leavingPeople.add(person);
                p.remove();
            }
        }

        return leavingPeople;
    }

    /**
     * Sets the my floor.
     * 
     * @param floor
     *            the new my floor
     * @throws IllegalParamException Thrown if the floor is less than 1. Cannot have a 0 or negative floor.
     */
    private void setMyFloor(int floor) throws IllegalParamException {
        if (floor < 1) {
            throw new IllegalParamException(
                    "Floor cannot be set to below 1 for this application.");
        }
        this.myFloor = floor;
    }

}