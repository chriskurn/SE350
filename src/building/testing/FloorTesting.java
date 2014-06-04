package building.testing;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.io.IOException;
import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import simulator.Simulator;
import simulator.common.IllegalParamException;
import simulator.common.NullFileException;
import simulator.common.SimulationInformation;
import building.common.Person;
import building.common.PersonFactory;
import building.elements.Floor;
import building.elements.FloorFactory;
import building.elements.FloorImpl;
import elevator.common.ElevatorDirection;

/**
 * Description: Floor Testing class.
 * 
 * @author Patrick Stein
 * @author Chris Kurn
 * @since Version 1.0 - Spring Quarter 2014
 */

public class FloorTesting {

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

    @Test
    public void personEnterFloorTest() {
        Person p = PersonFactory.build(15, 2);
        Floor thisFloor = null;
        try {
            thisFloor = new FloorImpl(15);
            thisFloor.enterFloor(p);
        } catch (IllegalParamException e) {
            e.printStackTrace();
            fail();
        }

        boolean isEmpty = thisFloor.isEmpty();
        if (isEmpty == true) {
            fail();
        }

    }

    @Test
    public void floorCreationTest() throws IllegalParamException {
        int actualFloor = 15;
        Floor thisFloor = FloorFactory.build(actualFloor);

        assertEquals("Floors not equal", actualFloor, thisFloor.getFloor());

    }

    @Test(expected = IllegalParamException.class)
    public void floorInvalidCreationTest() throws IllegalParamException {
        int actualFloor = -15;
        Floor thisFloor = FloorFactory.build(actualFloor);
    }

    @Test
    public void leaveFloorTest() {
        Person p = PersonFactory.build(11, 2);
        Person p2 = PersonFactory.build(11, 2);
        ArrayList<Person> thisPeople = new ArrayList<Person>();
        thisPeople.add(p);
        thisPeople.add(p2);
        Floor thisFloor = null;
        try {
            thisFloor = new FloorImpl(15);
            thisFloor.enterFloor(p);
            thisFloor.enterFloor(p2);
        } catch (IllegalParamException e1) {
            e1.printStackTrace();
            fail();
        }

        ArrayList<Person> floorPeople = thisFloor
                .leaveFloor(ElevatorDirection.DOWN);
        assertEquals("People arrays should be equal.", thisPeople, floorPeople);

    }

}
