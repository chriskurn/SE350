package simulator.testing;

import static org.junit.Assert.*;
import java.io.IOException;
import org.junit.Test;
import simulator.Simulator;
import simulator.common.IllegalParamException;
import simulator.common.NullFileException;
import simulator.common.SimulationInformation;

/**
 * Description: Default Person Per Minute Input Test.
 * 
 * Test for the correct number of people per. minute input.
 *
 * @author Patrick Stein
 * @author Chris Kurn
 * @since Version 1.0 - Spring Quarter 2014
 */

public class PersonPerMinuteInputTest {

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

        int peoplePerMin = info.personPerMin;
        
        if(peoplePerMin ==15){
        	System.out.println("Default value for people per minute = " + peoplePerMin);
        } else fail("simInput error - Default value for people per minute is incorrect.");
	}

}