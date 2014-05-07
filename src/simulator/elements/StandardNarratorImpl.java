package simulator.elements;

import java.io.FileNotFoundException;
import java.util.Calendar;

import simulator.common.IllegalParamException;
import simulator.common.NullFileException;

/**
 * Description: StandardNarratorImpl class
 * @author Patrick Stein
 * @author Chris Kurn 
 * @since Version 1.0 - Spring Quarter 2014
 * @see package simulator.elements
 * @see import java.util.Calendar;
 */

public class StandardNarratorImpl implements Narrator{
    
    private final boolean saveToFile;
    private String[] recentMessages;

    public StandardNarratorImpl(boolean stf, int sizeOfMessageQueue){
        //Do you want to save the log to a file
        saveToFile = stf;
        recentMessages = new String[sizeOfMessageQueue];
    }
    @Override
    /**
     * Method that logs elevator events.
     */
    public void logEvent(String event){
        
        System.out.println(String.format("%tT   %s",Calendar.getInstance(),event));
    }
    @Override
    /**
     * Method that writes elevator info to a file.
     */
    public boolean writeToFile() {
        return this.saveToFile;
    }
    @Override
    /**
     * Method retrieves message queue length.
     * @return the message queue length
     */
    public int getMessageQueueLength() {
        return this.recentMessages.length;
    }
    
}
