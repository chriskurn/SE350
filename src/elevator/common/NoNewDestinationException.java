package elevator.common;

/**
 * Description: NoNewDestinationException class.
 *
 * @author Patrick Stein
 * @author Chris Kurn
 * @since Version 1.0 - Spring Quarter 2014
 */
@SuppressWarnings("serial")
public class NoNewDestinationException extends Exception {

    /**
     * Instantiates a new no new destination exception.
     *
     * @param msg the msg
     */
    public NoNewDestinationException(String msg) {
        super(msg);
    }

}
