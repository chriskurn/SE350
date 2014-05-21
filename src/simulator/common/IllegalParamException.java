package simulator.common;

/**
 * Description: IllegalParamException class.
 *
 * @author Patrick Stein
 * @author Chris Kurn
 * @since Version 1.0 - Spring Quarter 2014
 */

public class IllegalParamException extends Exception {
    
    /**
     * Instantiates a new illegal param exception.
     *
     * @param msg the msg
     */
    public IllegalParamException(String msg) {
        super(msg);
    }

}
