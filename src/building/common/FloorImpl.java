package building.common;

import java.util.ArrayList;

import simulator.Simulator;
import simulator.common.IllegalParamException;

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
     * Instantiates a new floor impl.
     *
     * @param floorNumber the floor number
     */
    public FloorImpl(int floorNumber) {
        this.setMyFloor(floorNumber);
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

    /*
     * (non-Javadoc)
     * 
     * @see building.common.Floor#enterFloor(building.common.Person)
     */
    @Override
    public void enterFloor(Person p) throws IllegalParamException {
        if (p == null) {
            throw new IllegalParamException(
                    "Cannot add a null to the person array. Sorry about that.");
        }
        
        Simulator.getInstance().logEvent(String.format("Person %d has entered floor: %d",p.getPersonId(),this.getFloor()));
        
        //Then this person is done moving yay!
        if(p.getDestinationFloor() == this.getFloor()){
            synchronized (this) {
                this.getFinishedPeople().add(p);
            }
        }else{
            //else he needs to be added to the already waiting people
            synchronized (this) {
                this.getFloorPeople().add(p);
            }
        }
    }

    /**
     * Gets the floor people.
     *
     * @return the floor people
     */
    private ArrayList<Person> getFloorPeople() {
        return this.floorPeoples;
    }
    
    /**
     * Gets the finished people.
     *
     * @return the finished people
     */
    private ArrayList<Person> getFinishedPeople(){
        return this.finishedPeople;
    }

    /*
     * (non-Javadoc)
     * 
     * @see building.common.Floor#leaveFloor(building.common.Person)
     */
    @Override
    public void leaveFloor(Person p) {
        // TODO Auto-generated method stub

    }

    /**
     * Sets the my floor.
     *
     * @param floor the new my floor
     */
    private void setMyFloor(int floor) {
        // TODO Auto-generated method stub
        // error handling
        this.myFloor = floor;
    }

}
