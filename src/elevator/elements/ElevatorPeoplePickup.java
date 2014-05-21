/**
 * 
 */
package elevator.elements;

import elevator.common.InvalidFloorException;

/**
 * @author Patrick Stein
 *
 */
public interface ElevatorPeoplePickup {
    
    public void unloadPeople();
    public void loadPeople() throws InvalidFloorException;

}
