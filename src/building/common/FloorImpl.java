/**
 * 
 */
package building.common;

import java.util.ArrayList;

import simulator.common.IllegalParamException;

/**
 * @author Patrick Stein
 *
 */
public class FloorImpl implements Floor {
    
    private int myFloor;
    private ArrayList<Person> floorPeoples = new ArrayList<Person>();
    
    
    
    public FloorImpl(int floorNumber) {
        this.setMyFloor(floorNumber);
    }

    /* (non-Javadoc)
     * @see building.common.Floor#getFloor()
     */
    @Override
    public int getFloor() {
        return this.myFloor;
    }

    /* (non-Javadoc)
     * @see building.common.Floor#enterFloor(building.common.Person)
     */
    @Override
    public void enterFloor(Person p) throws IllegalParamException {
        if(p == null){
            throw new IllegalParamException("Cannot add a null to the person array. Sorry about that.");
        }
        synchronized(this){
            this.getFloorPeople().add(p);
        }

    }

    private ArrayList<Person> getFloorPeople() {
        return this.floorPeoples;
    }

    /* (non-Javadoc)
     * @see building.common.Floor#leaveFloor(building.common.Person)
     */
    @Override
    public void leaveFloor(Person p) {
        // TODO Auto-generated method stub

    }
    /**
     * @param myFloor the myFloor to set
     */
    private void setMyFloor(int floor) {
     // TODO Auto-generated method stub
        this.myFloor = floor;
    }

}
