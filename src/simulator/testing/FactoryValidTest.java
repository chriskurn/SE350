package simulator.testing;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Test;

import simulator.common.InputLoaderFactory;
import simulator.common.NarratorFactory;
import simulator.elements.InputLoader;
import simulator.elements.Narrator;

/**
 * Description: Factory Valid Test.
 * 
 * Is factory valid test.
 * 
 * @author Patrick Stein
 * @author Chris Kurn
 * @since Version 1.0 - Spring Quarter 2014
 * */

public class FactoryValidTest {

    /**
     * Properties input loader test.
     */
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
