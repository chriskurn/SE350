package simulator.elements;

import java.util.concurrent.TimeUnit;

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
    
    /** The narration creation time. */
    private final long narrationCreationTime;

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
        narrationCreationTime = System.currentTimeMillis();
    }

    /**
     * Method that logs elevator events.
     * @see simulator.elements.Narrator#logEvent(java.lang.String)
     */
    @Override
    public void logEvent(String event) {

        String logMessage = getCurrentElapsedTime() + " " + event;
        System.out.println(logMessage);
    }
    private String getCurrentElapsedTime(){
        
        long l = System.currentTimeMillis() - getNarrationCreationTime();
        
        long hr = TimeUnit.MILLISECONDS.toHours(l);
        long min = TimeUnit.MILLISECONDS.toMinutes(l - TimeUnit.HOURS.toMillis(hr));
        
        long sec = TimeUnit.MILLISECONDS.toSeconds(l - TimeUnit.HOURS.toMillis(hr) -
                                                   TimeUnit.MINUTES.toMillis(min));
        
        long ms = TimeUnit.MILLISECONDS.toMillis(l - TimeUnit.HOURS.toMillis(hr) -
                                                  TimeUnit.MINUTES.toMillis(min) -
                                                  TimeUnit.SECONDS.toMillis(sec));
        return String.format("%02d:%02d:%02d.%03d", hr, min, sec, ms);
        
    }
    private long getNarrationCreationTime(){
        return narrationCreationTime;
    }

 
    /**
     * Method that writes elevator info to a file.
     * @see simulator.elements.Narrator#writeToFile()
     */
    @Override
    public boolean writeToFile() {
        return saveToFile;
    }
   
    /**
     * Method retrieves message queue length.
     * @see simulator.elements.Narrator#getMessageQueueLength()
     * @return the message queue length
     */
    @Override
    public int getMessageQueueLength() {
        return recentMessages.length;
    }

}
