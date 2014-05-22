package simulator.testing;

import static org.junit.Assert.fail;

import java.io.FileNotFoundException;

import org.junit.Test;

import simulator.common.IllegalParamException;
import simulator.common.InputLoaderFactory;
import simulator.common.NullFileException;
import simulator.elements.InputLoader;

/**
 * Description: Factory Invalid Test.
 *
 * @author Patrick Stein
 * @author Chris Kurn
 * @since Version 1.0 - Spring Quarter 2014
 */

public class FactoryInvalidTest {

    /**
     * Properties input loader test.
     *
     * @throws FileNotFoundException the file not found exception
     * @throws NullFileException the null file exception
     * @throws IllegalParamException the illegal param exception
     */
    @Test(expected = FileNotFoundException.class)
    public void propertiesInputLoaderTest() throws FileNotFoundException,
            NullFileException, IllegalParamException {
        // File doesn't exist
        String fn = "thisFileDoesNotExist.properties";
        InputLoader i = InputLoaderFactory.build(fn);
        fail("Exception should have been thrown");

    }

}
