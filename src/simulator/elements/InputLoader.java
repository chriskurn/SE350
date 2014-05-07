package simulator.elements;

import java.io.IOException;
import simulator.common.SimulationInformation;
import simulator.common.IllegalParamException;

/**
 * Description: InputLoader
 * @author Patrick Stein
 * @author Chris Kurn 
 * @since Version 1.0 - Spring Quarter 2014
 * @see package simulator.elements
 */

public interface InputLoader {
    
    public SimulationInformation loadInput() throws IOException, IllegalParamException; 
    public SimulationInformation getSimulationInfo();   
    public String getResourceName();

}
