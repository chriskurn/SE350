package simulator.testing;

import static org.junit.Assert.fail;

import java.io.FileNotFoundException;

import org.junit.Test;

import simulator.common.IllegalParamException;
import simulator.common.InputLoaderFactory;
import simulator.common.NullFileException;
import simulator.elements.InputLoader;

/**
 * Description: Factory Invalid Test
 * 
 * @author Chris Kurn, Patrick Stein
 * @since Version 1.0 - Spring Quarter 2014
 * @see package simulator.testing
 * @see import static org.junit.Assert.fail;
 * @see import java.io.FileNotFoundException;
 * @see import org.junit.Test;
 * @see import simulator.common.IllegalParamException;
 * @see import simulator.common.InputLoaderFactory;
 * @see import simulator.common.NullFileException;
 * @see import simulator.elements.InputLoader;
 */

public class FactoryInvalidTest {

    @Test(expected = FileNotFoundException.class)
    public void propertiesInputLoaderTest() throws FileNotFoundException,
            NullFileException, IllegalParamException {
        // File doesn't exist
        String fn = "thisFileDoesNotExist.properties";
        InputLoader i = InputLoaderFactory.build(fn);
        fail("Exception should have been thrown");

    }

}
