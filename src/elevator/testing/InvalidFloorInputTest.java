package elevator.testing;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import elevator.common.Elevator;
import elevator.common.ElevatorImpl;
import elevator.common.InvalidFloorException;

import simulator.Simulator;
import simulator.common.IllegalParamException;
import simulator.common.NullFileException;
import simulator.common.SimulationInformation;

@RunWith(Parameterized.class)
public class InvalidFloorInputTest {

    private SimulationInformation info;
    private Simulator sim;
    private int floorNumber;
    private Elevator elevator;
    
    public InvalidFloorInputTest(int floorNum) throws IllegalParamException{
        this.floorNumber = floorNum;
    }
    
    @Before
    public void loadSimulation() throws NullFileException, IllegalParamException, IOException{
        Simulator mySim = Simulator.getInstance();
        mySim.buildSimulator("simInput.properties");
        this.info = mySim.getSimulationInfo();
        this.sim = mySim;
        this.elevator = new ElevatorImpl(this.info);
    }
    
    @Parameters
    public static Collection data() {
        return Arrays.asList(new Object[][] { 
                {-1}, //expected, input
                {0}, 
                {250}, 
                {-300}, 
                {1},
                {17} });
    }
    
    @Test (expected = InvalidFloorException.class)
    public void invalidFloorNumbersTest() throws InvalidFloorException{
        elevator.addFloor(this.floorNumber);
        
    }

}
