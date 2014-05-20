package simulator.elements;

//import java.io.FileNotFoundException;
import java.util.Calendar;

//import simulator.common.IllegalParamException;
//import simulator.common.NullFileException;

/**
 * Description: StandardNarratorImpl class.
 *
 * @author Patrick Stein
 * @author Chris Kurn
 * @since Version 1.0 - Spring Quarter 2014
 */

public class StandardNarratorImpl implements Narrator {

    /** The save to file. */
    private final boolean saveToFile;
    
    /** The recent messages. */
    private String[] recentMessages;

    /**
     * Instantiates a new standard narrator impl.
     *
     * @param stf the stf
     * @param sizeOfMessageQueue the size of message queue
     */
    public StandardNarratorImpl(boolean stf, int sizeOfMessageQueue) {
        // Do you want to save the log to a file
        saveToFile = stf;
        recentMessages = new String[sizeOfMessageQueue];
    }

    /* (non-Javadoc)
     * @see simulator.elements.Narrator#logEvent(java.lang.String)
     */
    @Override
    /**
     * Method that logs elevator events.
     */
    public void logEvent(String event) {

        System.out.println(String.format("%tT   %s", Calendar.getInstance(),
                event));
    }

    /* (non-Javadoc)
     * @see simulator.elements.Narrator#writeToFile()
     */
    @Override
    /**
     * Method that writes elevator info to a file.
     */
    public boolean writeToFile() {
        return this.saveToFile;
    }

    /* (non-Javadoc)
     * @see simulator.elements.Narrator#getMessageQueueLength()
     */
    @Override
    /**
     * Method retrieves message queue length.
     * @return the message queue length
     */
    public int getMessageQueueLength() {
        return this.recentMessages.length;
    }

}
