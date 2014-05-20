package elevator.common;

/**
 * Description: InvalidFloorException class.
 *
 * @author Patrick Stein
 * @author Chris Kurn
 * @since Version 1.0 - Spring Quarter 2014
 */

public class InvalidFloorException extends Exception {
    
    /**
     * Instantiates a new invalid floor exception.
     *
     * @param msg the msg
     */
    public InvalidFloorException(String msg) {
        super(msg);
    }

}
