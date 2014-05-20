package elevator.common;

import building.common.Person;

/**
 * Description: Elevator interface class.
 *
 * @author Patrick Stein
 * @author Chris Kurn
 * @since Version 1.0 - Spring Quarter 2014
 */

public interface Elevator {

    /**
     * Method that starts the elevator.
     */
    public void startElevator();

    /**
     * Method that stops the elevator.
     */
    public void stopElevator();

    /**
     * Method that adds floors Throws exceptions if the floor being added is
     * equal or less than zero or the floor being added is greater than the
     * floors in the building.
     *
     * @param floor the floor
     * @throws InvalidFloorException the invalid floor exception
     */
    public void addFloor(int floor) throws InvalidFloorException;

    /**
     * Method that adds floors Throws exceptions if the floor being added is
     * equal or less than zero or the floor being added is greater than the
     * floors in the building.
     *
     * @param floor the floor
     * @param dir the dir
     * @throws InvalidFloorException the invalid floor exception
     */
    public void addFloor(int floor, ElevatorDirection dir)
            throws InvalidFloorException;

    /**
     * Method that retrieves the current floor.
     *
     * @return the current floor
     */
    public int getCurrentFloor();

    /**
     * Method that retrieves the elevators direction.
     *
     * @return elevator direction
     */
    public ElevatorDirection getDirection();

    /**
     * Method that retrieves the elevators destination.
     *
     * @return a destination
     * @throws NoNewDestinationException the no new destination exception
     */
    int getDestination() throws NoNewDestinationException;

    /**
     * Method does the elevator have destinations remaining.
     *
     * @return true or false
     */
    boolean destinationsLeft();

    /**
     * A public method for entering an elevator. It must be on the floor you are
     * on with its doors open
     *
     * @param p the p
     */
    public void enterElevator(Person p);

    /**
     * Doors open.
     *
     * @return true, if successful
     */
    public boolean doorsOpen();

    /**
     * Gets the elevator id.
     *
     * @return the elevator id
     */
    public int getElevatorId();

}
