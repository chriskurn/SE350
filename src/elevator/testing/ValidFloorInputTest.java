package elevator.testing;

import static org.junit.Assert.assertEquals;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

import simulator.Simulator;
import simulator.common.IllegalParamException;
import simulator.common.NullFileException;
import simulator.common.SimulationInformation;
import elevator.common.InvalidFloorException;
import elevator.common.NoNewDestinationException;
import elevator.elements.Elevator;
import elevator.elements.ElevatorImpl;

/**
 * Description: validFloorInputTest class.
 * 
 * @author Patrick Stein
 * @author Chris Kurn
 * @since Version 1.0 - Spring Quarter 2014
 */

public class ValidFloorInputTest {

    /** The info. */
    private SimulationInformation info;

    /** The sim. */
    private Simulator sim;

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
    }

    /**
     * Adds the floor input test.
     * 
     * @throws IllegalParamException
     *             the illegal param exception
     * @throws InvalidFloorException
     *             the invalid floor exception
     * @throws NoNewDestinationException
     *             the no new destination exception
     */
    @Test
    public void addFloorInputTest() throws IllegalParamException,
            InvalidFloorException, NoNewDestinationException {
        Elevator e = new ElevatorImpl(this.info);
        e.addFloor(10);
        assertEquals(e.getDestination(), 10);

    }
}
