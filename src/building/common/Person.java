package building.common;

/**
 * Description: Person interface class.
 * 
 * @author Patrick Stein
 * @author Chris Kurn
 * @since Version 1.0 - Spring Quarter 2014
 */

public interface Person {

    /**
     * This will return true if someone called the person's set invalid status.
     * Note: A person is invalid if he did not reach his destination. If he did,
     * then there is nothing to worry about.
     * 
     * @return true if someone called setInvalidStatus()
     */
    public boolean didErrorOccur();

    /**
     * Gets the current floor that person is on.
     * 
     * @return the current floor
     */
    public int getCurrentFloor();

    /**
     * Gets the floor that person is destined for.
     * 
     * @return the destination floor
     */
    public int getDestinationFloor();

    /**
     * Gets the person id that identifies this person.
     * 
     * @return the person id
     */
    public int getPersonId();

    /**
     * Gets the start floor that person started on.
     * 
     * @return the start floor
     */
    public int getStartFloor();

    /**
     * If a person, for whatever reason, happens to error or bug out during
     * execution call this method.
     */
    public void setInvalidStatus();

    /**
     * Starts up the person's execution. This will basically ask the person to
     * make a request for an elevator.
     */
    public void startPerson();

    /**
     * This is associated and should be called when a person successfully enters
     * an elevator
     */
    public void elevatorEntered();

    /**
     * This is associated and should be called when a person successfully leaves
     * an elevator for their destination floor
     */
    public void leftElevator();

    /**
     * Returns a long associated with when the person began execution
     * 
     * @return getStartTime
     */
    public long getStartTime();

    /**
     * Returns a long associated with when the person entered the elevator
     * 
     * @return getElevatorEnterTime
     */
    public long getElevatorEnterTime();

    /**
     * Returns a long associated with when the person finished execution
     * 
     * @return getFinishedTime
     */
    public long getFinishedTime();

}
