package simulator.testing;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.junit.Test;

import simulator.common.IllegalParamException;
import simulator.common.InputLoaderFactory;
import simulator.common.NullFileException;
import simulator.common.SimulationInformation;
import simulator.elements.InputLoader;

/**
 * Description: Input Loader Test.
 * 
 * Test the file loader.
 * 
 * @author Patrick Stein
 * @author Chris Kurn
 * @since Version 1.0 - Spring Quarter 2014
 */

public class InputLoaderTest {

    /**
     * Input simulation load test.
     */
    @Test
    public void inputSimulationLoadTest() {

        String fn = "validDestConfigFile.properties";
        InputLoader i = null;
        try {
            i = InputLoaderFactory.build(fn);
        } catch (FileNotFoundException | NullFileException
                | IllegalParamException e1) {
            fail("This file should load properly.");
            e1.printStackTrace();
        }

        try {
            i.loadInput();
        } catch (IOException | IllegalParamException e) {
            fail("This file should load properly. Examine the file and the code to make sure they are correct.");
        }
        SimulationInformation si = i.getSimulationInfo();
        

        assertEquals(si.doorTime, 524224);
        assertEquals(si.elevatorSleepTime, 15214000);
        assertEquals(si.floorTime, 502241);
        assertEquals(si.numElevators, 2421);
        assertEquals(si.numExpressElevators, 212132);
        assertEquals(si.numFloors, 125323);
        assertEquals(si.numPeoplePerElevator, 214424);
        
        assertEquals(si.defaultElevatorFlr, 214);
        assertEquals(si.simRunTime, 3000421);
        assertEquals(si.personPerMin, 1524);
    }

}
