package simulator.common;

/**
 * Description: FileReadException class.
 * 
 * @author Patrick Stein
 * @author Chris Kurn
 * @since Version 1.0 - Spring Quarter 2014
 */

@SuppressWarnings("serial")
public class FileReadException extends Exception {

    /**
     * Instantiates a new file read exception.
     * 
     * @param msg
     *            the msg
     */
    public FileReadException(String msg) {
        super(msg);
    }

}
