package simulator.testing;

import static org.junit.Assert.*;
import simulator.Simulator;
import simulator.common.IllegalParamException;
import simulator.common.SimulationInformation;
import building.common.Person;
import building.elements.Floor;
import building.elements.FloorFactory;
import elevator.common.ElevatorDirection;
import elevator.common.InvalidFloorException;
import elevator.control.ElevatorController;

import org.junit.Test;


/**
 * Description: Narrator Test.
 *
 * @author Patrick Stein
 * @author Chris Kurn
 * @since Version 1.0 - Spring Quarter 2014
 */

public class NarratorTest {

	@Test
	public void Test() {
        Simulator sim = Simulator.getInstance();
        sim.logEvent("Event!");
	}

}