package elevator.common;

/**
 * Description: InvalidFloorException class.
 * 
 * Designed to be thrown when someone passed
 * or finds an invalid floor in the application.
 * 
 * @author Patrick Stein
 * @author Chris Kurn
 * @since Version 1.0 - Spring Quarter 2014
 */

@SuppressWarnings("serial")
final public class InvalidFloorException extends Exception {

    /**
     * Instantiates a new invalid floor exception.
     * 
     * @param msg
     *            the msg
     */
    public InvalidFloorException(String msg) {
        super(msg);
    }

}
