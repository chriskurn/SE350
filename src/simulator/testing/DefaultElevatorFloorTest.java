package simulator.testing;

import static org.junit.Assert.*;
import java.io.IOException;
import org.junit.Test;
import simulator.Simulator;
import simulator.common.IllegalParamException;
import simulator.common.NullFileException;
import simulator.common.SimulationInformation;

/**
 * Description: Default Elevator Floor Test.
 * 
 * Test for the default floor.
 *
 * @author Patrick Stein
 * @author Chris Kurn
 * @since Version 1.0 - Spring Quarter 2014
 */

public class DefaultElevatorFloorTest {

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

        int defaultElevatorFloor = info.defaultElevatorFlr;
        
        if(defaultElevatorFloor ==1){
        	System.out.println("Default Elevator Floor = " + defaultElevatorFloor);
        } else fail("simInput error - Default Elevator Floor Value missing");
	}

}