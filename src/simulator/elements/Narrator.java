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
     * 
     * @param event
     *            a string containing an event that has occurred.
     */
    public void logEvent(String event);
}
