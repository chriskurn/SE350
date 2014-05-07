package simulator.common;

import simulator.elements.Narrator;
import simulator.elements.StandardNarratorImpl;

/**
 * Description: NarratorFactory class
 * @author Patrick Stein
 * @author Chris Kurn 
 * @since Version 1.0 - Spring Quarter 2014
 * @see package simulator.common
 * @see import simulator.elements.Narrator;
 * @see import simulator.elements.StandardNarratorImpl;
 */

public class NarratorFactory {
    
    public static Narrator build(boolean saveToFile, int sizeOfMessageQueue){
        return new StandardNarratorImpl(saveToFile,sizeOfMessageQueue);
    }

}
