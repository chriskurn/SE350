package simulator.common;

import java.io.FileNotFoundException;
import simulator.elements.InputLoader;
import simulator.elements.InputLoaderProperties;

/**
 * Description: InputLoaderFactory
 * @author Patrick Stein
 * @author Chris Kurn 
 * @since Version 1.0 - Spring Quarter 2014
 * @see package simulator.common
 */

public class InputLoaderFactory {
    
    /**
     * Takes a filename and returns an appropriate implementation that loads that file.
     * @param fileName A string that contains the full path to the file. Make sure it has an extension
     * @return Returns a new object that will load the file provided.
     * @throws NullFileException 
     * @throws IllegalParamException 
     * @throws FileNotFoundException 
     */
    public static InputLoader build(String fileName) throws NullFileException, FileNotFoundException, IllegalParamException{
        //get the file name
        String extension = fileName.substring(fileName.lastIndexOf(".") + 1, fileName.length());
        //If it is of type XML then create a new XML file loader
        if(extension.equals("properties")){
            return new InputLoaderProperties(fileName);
        }else{
            return null;
        }
    }

}
