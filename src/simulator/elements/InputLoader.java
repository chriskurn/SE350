package simulator.elements;

import java.io.IOException;

import simulator.common.SimulationInformation;
import simulator.common.IllegalParamException;

/**
 * Description: InputLoader
 * @author Chris Kurn, Patrick Stein
 * @since Version 1.0 - Spring Quarter 2014
 */

public interface InputLoader {
    
    public SimulationInformation loadInput() throws IOException, IllegalParamException;
    
    public SimulationInformation getSimulationInfo();

}
