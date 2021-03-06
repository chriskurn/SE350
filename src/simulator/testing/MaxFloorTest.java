package simulator.testing;

import static org.junit.Assert.fail;

import java.io.IOException;

import org.junit.Test;

import simulator.Simulator;
import simulator.common.IllegalParamException;
import simulator.common.NullFileException;
import simulator.common.SimulationInformation;

/**
 * Description: Maximum Floor Test.
 * 
 * Test for the maximum floor of the building.
 * 
 * @author Patrick Stein
 * @author Chris Kurn
 * @since Version 1.0 - Spring Quarter 2014
 */

public class MaxFloorTest {

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

        int maxFloor = info.numFloors;

        if (maxFloor != 0) {
            System.out.println("MaxFloor = " + maxFloor);
        } else
            fail("simInput error - MaxFloor Value missing");
    }

}