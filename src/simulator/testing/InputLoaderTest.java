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
 * Description: Input Loader Test
 * @author Chris Kurn, Patrick Stein
 * @since Version 1.0 - Spring Quarter 2014
 * TODO add additional tests for blank files
 * @see package simulator.testing;
 * @see import static org.junit.Assert.assertEquals;
 * @see import static org.junit.Assert.fail;
 * @see import java.io.FileNotFoundException;
 * @see import java.io.IOException;
 * @see import org.junit.Test;
 * @see import simulator.common.IllegalParamException;
 * @see import simulator.common.InputLoaderFactory;
 * @see import simulator.common.NullFileException;
 * @see import simulator.common.SimulationInformation;
 * @see import simulator.elements.InputLoader;
 */

public class InputLoaderTest {

    
    @Test
    public void inputSimulationLoadTest(){
        
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
            // TODO Auto-generated catch block
            fail("This file should load properly. Examine the file and the code to make sure they are correct.");
        }
        SimulationInformation si = i.getSimulationInfo();
        
        assertEquals(si.doorTime,5239293);
        assertEquals(si.elevatorSleepTime,6304821);
        assertEquals(si.floorTime,5039893);
        assertEquals(si.numElevators,238);
        assertEquals(si.numExpressElevators,23893);
        assertEquals(si.numFloors,1233);
        assertEquals(si.numPeoplePerElevator,23723);
    }
    
    
}
