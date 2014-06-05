package elevator.testing;

import java.io.IOException;
import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import simulator.Simulator;
import simulator.common.IllegalParamException;
import simulator.common.NullFileException;
import simulator.common.SimulationInformation;
import elevator.common.ElevatorDirection;
import elevator.control.ElevatorController;
import elevator.elements.Elevator;

/**
 * @author Patrick Stein
 * 
 */
public class ElevatorControllerTest {
    private SimulationInformation info;

    /** The sim. */
    private Simulator sim;

    /** The floor number. */
    private int floorNumber;

    /** The elevator. */
    private ArrayList<Elevator> eles = new ArrayList<Elevator>();
    /** Elevator Controller */
    ElevatorController ec;

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
        ec = ElevatorController.getInstance();
        ec.stopAllElevators();
    }

    @Test
    public void validRequestAddTest() throws IllegalParamException {
        ec.addNewRequest(5, ElevatorDirection.UP);
    }

    @Test(expected = IllegalParamException.class)
    public void invalidDirectionRequestTest() throws IllegalParamException {
        ec.addNewRequest(5, ElevatorDirection.IDLE);
    }

    @Test(expected = IllegalParamException.class)
    public void invalidUpperFloorRequestTest() throws IllegalParamException {
        ec.addNewRequest(30, ElevatorDirection.UP);
    }

    @Test(expected = IllegalParamException.class)
    public void invalidLowerFloorRequestTest() throws IllegalParamException {
        ec.addNewRequest(-5, ElevatorDirection.DOWN);
    }

}
