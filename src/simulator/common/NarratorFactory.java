package simulator.common;

import simulator.elements.Narrator;
import simulator.elements.StandardNarratorImpl;

/**
 * Description: simulator.elements.Narrator
 * @author Patrick Stein
 * @author Chris Kurn 
 * @since Version 1.0 - Spring Quarter 2014
 * @see package simulator.common
 */

public class NarratorFactory {
    
    public static Narrator build(boolean saveToFile, int sizeOfMessageQueue){
        return new StandardNarratorImpl(saveToFile,sizeOfMessageQueue);
    }

}
