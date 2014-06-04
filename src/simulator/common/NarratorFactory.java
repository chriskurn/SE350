package simulator.common;

import simulator.elements.Narrator;
import simulator.elements.StandardNarratorImpl;

/**
 * Description: NarratorFactory class.
 *
 * @author Patrick Stein
 * @author Chris Kurn
 * @since Version 1.0 - Spring Quarter 2014
 */

/**
 * Narrator Factory Class Outputs text from simulation
 */
public class NarratorFactory {

    /**
     * Builds a narrator delegate. Currently just being used for extra
     * extensibility.
     * 
     * @return the a new narrator
     */
    public static Narrator build() {
        return new StandardNarratorImpl();
    }

}
