package elevator.common;

/**
 * Description: NoNewDestinationException class
 * 
 * @author Patrick Stein
 * @author Chris Kurn
 * @since Version 1.0 - Spring Quarter 2014
 * @see package elevator.common
 */
public class NoNewDestinationException extends Exception {

    public NoNewDestinationException(String msg) {
        super(msg);
    }

}
