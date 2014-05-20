package simulator.common;

import simulator.elements.Narrator;
import simulator.elements.StandardNarratorImpl;

/**
 * Description: NarratorFactory class.
 *
 * @author Patrick Stein
 * @author Chris Kurn
 * @see package simulator.common
 * @see import simulator.elements.Narrator;
 * @see import simulator.elements.StandardNarratorImpl;
 * @since Version 1.0 - Spring Quarter 2014
 */


/**
 * Narrator Factory Class
 * Outputs text from simulation
 */
public class NarratorFactory {

    /**
     * Builds the.
     *
     * @param saveToFile the save to file
     * @param sizeOfMessageQueue the size of message queue
     * @return the narrator
     */
    public static Narrator build(boolean saveToFile, int sizeOfMessageQueue) {
        return new StandardNarratorImpl(saveToFile, sizeOfMessageQueue);
    }

}
