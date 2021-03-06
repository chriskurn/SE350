package simulator.testing;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

import simulator.Simulator;
import simulator.common.IllegalParamException;
import simulator.common.NullFileException;
import simulator.common.SimulationInformation;
import building.common.Person;
import building.common.PersonFactory;

/**
 * Description: PersonBuild Test.
 * 
 * Test creating a person object.
 *
 * @author Patrick Stein
 * @author Chris Kurn
 * @since Version 1.0 - Spring Quarter 2014
 */

/**
 * Create a person object with a StartFloor and DestFloor Return values
 */
public class PersonBuildTest {

    /** The info. */
    @SuppressWarnings("unused")
    private SimulationInformation info;

    /** The sim. */
    @SuppressWarnings("unused")
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

    @Test
    public void Test() {

        int startFloor = 5;
        int destFloor = 17;
        Person P = PersonFactory.build(startFloor, destFloor);
        System.out.println("Destination Floor = " + P.getCurrentFloor());
        // fail("Destination Not Found");
    }

}
