package elevator.testing;
/**
 * Description: validFloorInputTest class
 * @author Patrick Stein
 * @author Chris Kurn 
 * @since Version 1.0 - Spring Quarter 2014
 * @see package elevator.testing
 */

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

import elevator.common.Elevator;
import elevator.common.ElevatorImpl;
import elevator.common.InvalidFloorException;

import simulator.Simulator;
import simulator.common.IllegalParamException;
import simulator.common.NullFileException;
import simulator.common.SimulationInformation;

public class ValidFloorInputTest {
    
    private SimulationInformation info;
    private Simulator sim;
    
    @Before
    public void loadSimulation() throws NullFileException, IllegalParamException, IOException{
        Simulator mySim = Simulator.getInstance();
        mySim.buildSimulator("simInput.properties");
        this.info = mySim.getSimulationInfo();
        this.sim = mySim;
    }
    
    
    @Test
    public void addFloorInputTest() throws IllegalParamException, InvalidFloorException{
        Elevator e = new ElevatorImpl(this.info);
        e.addFloor(10);
        
    }
}
