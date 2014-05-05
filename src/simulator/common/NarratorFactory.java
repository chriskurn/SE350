package simulator.common;

import simulator.elements.Narrator;
import simulator.elements.StandardNarratorImpl;

public class NarratorFactory {
    
    public static Narrator build(boolean saveToFile, int sizeOfMessageQueue){
        return new StandardNarratorImpl(saveToFile,sizeOfMessageQueue);
    }

}
