package simulator.common;

/**
 * Description: NullFileException class.
 * 
 * @author Patrick Stein
 * @author Chris Kurn
 * @since Version 1.0 - Spring Quarter 2014
 */

@SuppressWarnings("serial")
public class NullFileException extends Exception {

    /**
     * Instantiates a new null file exception.
     * 
     * @param msg
     *            the error msg
     */
    public NullFileException(String msg) {
        super(msg);
    }

}
