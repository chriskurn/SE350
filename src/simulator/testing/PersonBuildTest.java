package simulator.testing;

import static org.junit.Assert.*;

import org.junit.Test;

import elevator.common.InvalidFloorException;
import building.Building;
import building.common.Person;
import building.common.PersonFactory;
import simulator.Simulator;
import simulator.common.IllegalParamException;
import simulator.common.NullFileException;
import simulator.common.SimulationInformation;


/**
 * The Class PersonTest
 *
 * @author Patrick Stein
 * @author Chris Kurn
 * @since Version 1.0 - Spring Quarter 2014
 */


/**
 * Create a person object with a StartFloor and DestFloor
 * Return values
 */
public class PersonBuildTest {
	
		@Test
	public void Test() {
			
		int startFloor = 5;
		int destFloor = 17;
		Person P = PersonFactory.build(startFloor, destFloor);
		System.out.println("Destination Floor = " + P.getCurrentFloor());
		fail("Destination Not Found");
	}

}
