package simulator.testing;

import org.junit.Test;

import static org.junit.Assert.*;
import simulator.common.InputLoaderFactory;
import simulator.common.NarratorFactory;
import simulator.elements.InputLoader;
import simulator.elements.Narrator;

/**
 * Description: Factory Valid Test
 * 
 * @author Chris Kurn, Patrick Stein
 * @since Version 1.0 - Spring Quarter 2014
 * @see package simulator.testing
 * @see import org.junit.Test;
 * @see import static org.junit.Assert.*;
 * @see import simulator.common.InputLoaderFactory;
 * @see import simulator.common.NarratorFactory;
 * @see import simulator.elements.InputLoader;
 * @see import simulator.elements.Narrator; TODO add additional tests for blank
 *      files
 */

public class FactoryValidTest {

    @Test
    public void standardNarratorCreationTest() {

        Narrator n = NarratorFactory.build(false, 1);

        assertTrue(n.writeToFile() == false);
        assertTrue(n.getMessageQueueLength() == 1);

    }

    @Test
    public void propertiesInputLoaderTest() {
        String fn = "validDestConfigFile.properties";
        InputLoader i = null;
        try {
            i = InputLoaderFactory.build(fn);
        } catch (Exception e) {
            fail("An exception was thrown on a valid file.");
        }
        assertEquals(fn, i.getResourceName());

    }

}
