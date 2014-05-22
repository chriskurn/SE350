package simulator.testing;

import simulator.Simulator;

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