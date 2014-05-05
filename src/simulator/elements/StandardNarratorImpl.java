package simulator.elements;

import java.util.Calendar;

/**
 * Description: Standard Narrator Impl
 * @author Chris Kurn, Patrick Stein
 * @since Version 1.0 - Spring Quarter 2014
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
    public void logEvent(String event){
        
        System.out.println(String.format("%tT   %s",Calendar.getInstance(),event));
    }
    
}
