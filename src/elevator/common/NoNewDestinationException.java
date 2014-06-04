package elevator.common;

/**
 * Description: NoNewDestinationException.
 * 
 * exception designed to handle the case in which there are no new destinations
 * to be moved to as an elevator.
 * 
 * @author Patrick Stein
 * @author Chris Kurn
 * @since Version 1.0 - Spring Quarter 2014
 */

public class NoNewDestinationException extends Exception {

    /**
     * Instantiates a new no new destination exception.
     * 
     * @param msg
     *            the error message
     * */
    public NoNewDestinationException(String msg) {
        super(msg);
    }

}
