package simulator.testing;

import static org.junit.Assert.*;

import org.junit.Test;

import building.common.Person;
import building.common.PersonFactory;

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
public class PersonTest {
	
	
	@Test
	public void Persontest() {

		int startFloor = 5;
		int destFloor = 17;
		Person P = PersonFactory.build(startFloor, destFloor);
		System.out.println("Destination Floor = " + P.getDestinationFloor());	
	}

}
