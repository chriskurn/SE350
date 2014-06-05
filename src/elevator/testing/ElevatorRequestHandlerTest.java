package elevator.testing;

import static org.junit.Assert.assertFalse;

import java.io.IOException;
import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import simulator.Simulator;
import simulator.common.IllegalParamException;
import simulator.common.NullFileException;
import simulator.common.SimulationInformation;
import elevator.common.ElevatorDirection;
import elevator.common.ElevatorRequest;
import elevator.control.ElevatorRequestHandler;
import elevator.control.ElevatorRequestHandlerFactory;
import elevator.elements.Elevator;
import elevator.elements.ElevatorImpl;

public class ElevatorRequestHandlerTest {
    private SimulationInformation info;

    /** The sim. */
    private Simulator sim;

    /** The floor number. */
    private int floorNumber;

    /** The elevator. */
    private ArrayList<Elevator> eles = new ArrayList<Elevator>();

    /** Elevator Request Handler */
    private ElevatorRequestHandler req;

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
        this.eles.add(new ElevatorImpl(this.info));
        this.eles.add(new ElevatorImpl(this.info));
        this.req = ElevatorRequestHandlerFactory.build(eles);
    }

    @Test
    public void validFloorRequestTest() throws IllegalParamException {
        ElevatorRequest newRequest = new ElevatorRequest(5,
                ElevatorDirection.UP);
        req.handleRequest(newRequest);

    }

    @Test(expected = IllegalParamException.class)
    public void invalidRequestTest() throws IllegalParamException {
        ElevatorRequest newRequest = new ElevatorRequest(0,
                ElevatorDirection.UP);
    }
}
