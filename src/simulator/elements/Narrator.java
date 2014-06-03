package simulator.elements;

/**
 * Description: Narrator Interface
 *
 * @author Patrick Stein
 * @author Chris Kurn
 * @since Version 1.0 - Spring Quarter 2014
 */

public interface Narrator {

    /**
     * Logs an event that occurs in the programs runtime.
     * @param event a string containing some information about the event.
     */
    public void logEvent(String event);

    /**
     * Get method to see if this narrator will be writing to a file.
     * @return returns true if this narrator writes to a file.
     */
    public boolean writeToFile();

    /**
     * Get method to see how large of a queue the messages are being stored in. 
     * @return returns an integer corresponding to the size of the message queue
     */
    public int getMessageQueueLength();
}
