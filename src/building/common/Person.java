package building.common;

//import elevator.common.InvalidFloorException;

/**
 * Description: Person interface class.
 *
 * @author Patrick Stein
 * @author Chris Kurn
 * @since Version 1.0 - Spring Quarter 2014
 */

public interface Person {

    /**
     * Gets the current floor.
     *
     * @return the current floor
     */
    public int getCurrentFloor();
    
    public void setCurrentFloor(int floorNumber);
    /**
     * Gets the start floor.
     *
     * @return the start floor
     */
    public int getStartFloor();
    
    /**
     * Gets the destination floor.
     *
     * @return the destination floor
     */
    public int getDestinationFloor();
    
    /**
     * Gets the person id.
     *
     * @return the person id
     */
    public int getPersonId();   
    
    /**
     * Start person.
     */
    public void startPerson();
    
    
    
    public void setInvalidStatus();
    
    public boolean didErrorOccur();
    
}
