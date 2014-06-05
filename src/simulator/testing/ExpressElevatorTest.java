package simulator.testing;

import static org.junit.Assert.fail;

import java.io.IOException;

import org.junit.Test;

import simulator.Simulator;
import simulator.common.IllegalParamException;
import simulator.common.NullFileException;
import simulator.common.SimulationInformation;

/**
 * Description: Express Elevator Test.
 * 
 * Test for an express elevator in the simulation.
 * 
 * @author Patrick Stein
 * @author Chris Kurn
 * @since Version 1.0 - Spring Quarter 2014
 */

public class ExpressElevatorTest {

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

        int expElev = info.numExpressElevators;

        if (expElev == 0) {
            System.out.println("Number of express elevators = " + expElev);
        } else
            fail("simInput error - There are no express elevators in this building.");
    }

}