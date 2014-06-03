package simulator.elements;

import java.io.IOException;
import simulator.common.SimulationInformation;
import simulator.common.IllegalParamException;

/**
 * Description: InputLoader interface.
 *
 * @author Patrick Stein
 * @author Chris Kurn
 * @since Version 1.0 - Spring Quarter 2014
 */

public interface InputLoader {
    
    /**
     * Load input.
     *
     * @return the simulation information
     * @throws IOException Signals that an I/O exception has occurred.
     * @throws IllegalParamException the illegal param exception
     */
    public SimulationInformation loadInput() throws IOException,
            IllegalParamException;

    /**
     * Gets the simulation info.
     * @return the simulation info
     */
    public SimulationInformation getSimulationInfo();

    /**
     * Gets the resource name.
     * @return the resource name
     */
    public String getResourceName();

}
