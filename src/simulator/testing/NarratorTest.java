package simulator.testing;

import org.junit.Test;

import simulator.Simulator;

/**
 * Description: Narrator Test.
 * 
 * Narration output test.
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