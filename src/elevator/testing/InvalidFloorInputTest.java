package elevator.testing;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import simulator.Simulator;
import simulator.common.IllegalParamException;
import simulator.common.NullFileException;
import simulator.common.SimulationInformation;
import elevator.common.InvalidFloorException;
import elevator.elements.Elevator;
import elevator.elements.ElevatorImpl;

/**
 * Description: InvalidFloorInputTest class.
 * 
 * Checks for invalid floors (input)
 * 
 * @author Patrick Stein
 * @author Chris Kurn
 * @since Version 1.0 - Spring Quarter 2014
 */

@RunWith(Parameterized.class)
public class InvalidFloorInputTest {

    /** The info. */
    private SimulationInformation info;

    /** The sim. */
    private Simulator sim;

    /** The floor number. */
    private int floorNumber;

    /** The elevator. */
    private Elevator elevator;

    /**
     * InvalidFloorInputTest.
     * 
     * @param floorNum
     *            the floor num
     * @throws IllegalParamException
     *             the illegal param exception
     */
    public InvalidFloorInputTest(int floorNum) throws IllegalParamException {
        this.floorNumber = floorNum;
    }

    /**
     * Load simulation.
     * 
     * @throws NullFileException
     *             the null file exception
     * @throws IllegalParamException
     *             the illegal param exception
     * @throws IOException
     *             Signals that an I/O exception has occurred.
     */
    @Before
    public void loadSimulation() throws NullFileException,
            IllegalParamException, IOException {
        Simulator mySim = Simulator.getInstance();
        mySim.buildSimulator("simInput.properties");
        this.info = mySim.getSimulationInfo();
        this.sim = mySim;
        this.elevator = new ElevatorImpl(this.info);
    }

    /**
     * Data.
     * 
     * @return the collection
     */
    @Parameters
    public static Collection data() {
        return Arrays.asList(new Object[][] { { -1 }, // expected, input
                { 0 }, { 250 }, { -300 }, { 500 }, { 17 } });
    }

    /**
     * Invalid floor numbers test.
     * 
     * @throws InvalidFloorException
     *             the invalid floor exception
     */
    @Test(expected = InvalidFloorException.class)
    public void invalidFloorNumbersTest() throws InvalidFloorException {
        elevator.addFloor(this.floorNumber);

    }

}
