package simulator.testing;

import static org.junit.Assert.*;
import java.io.IOException;
import org.junit.Test;
import simulator.Simulator;
import simulator.common.IllegalParamException;
import simulator.common.NullFileException;
import simulator.common.SimulationInformation;

/**
 * Description: Door time input value Test.
 * 
 * Test for the default door time value.
 *
 * @author Patrick Stein
 * @author Chris Kurn
 * @since Version 1.0 - Spring Quarter 2014
 */

public class DoorTimeTest {

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

        long doorTime = info.doorTime;
        
        if(doorTime == 500){
        	System.out.println("Door Time = " + doorTime);
        } else fail("simInput error - Door Time should be 500");
	}

}