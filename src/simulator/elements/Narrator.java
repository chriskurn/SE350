package simulator.elements;

/**
 * Description: Standard Narrator impl
 * @author Patrick Stein
 * @author Chris Kurn 
 * @since Version 1.0 - Spring Quarter 2014
 * @see package simulator.elements
 */

public interface Narrator {

    public void logEvent(String event);
    
    public boolean writeToFile();
    public int getMessageQueueLength();
}
