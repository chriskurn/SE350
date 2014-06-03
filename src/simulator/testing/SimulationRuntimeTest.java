package simulator.testing;

import static org.junit.Assert.*;
import java.io.IOException;
import org.junit.Test;
import simulator.Simulator;
import simulator.common.IllegalParamException;
import simulator.common.NullFileException;
import simulator.common.SimulationInformation;

/**
 * Description: Check the simulation runtime test.
 * 
 * Test for the input value for runtime.
 *
 * @author Patrick Stein
 * @author Chris Kurn
 * @since Version 1.0 - Spring Quarter 2014
 */

public class SimulationRuntimeTest {

	@Test
	public void Test() {

        try {
            Simulator.getInstance().buildSimulator("simInput.properties");
        } catch (NullFileException | IllegalParamException | IOException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }

        Simulator.getInstance().getSimulationInfo();
        SimulationInformation info = Simulator.getInstance()
                .getSimulationInfo();

        long simulationRunTime = info.simRunTime;
        
        if(simulationRunTime == 300000){
        	System.out.println("Simulation Run Time = " + simulationRunTime);
        } else fail("simInput error - Simulation Run Time should be 300000");
	}

}