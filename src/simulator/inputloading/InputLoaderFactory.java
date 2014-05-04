package simulator.inputloading;

import simulator.common.NullFileException;
/*
Description:    Object-Oriented Software Development
                Quarter Programming Project
Authors:        Chris Kurn and Patrick Stein
Class:          SE-350
Date:           Spring Quarter 2014
*/

public class InputLoaderFactory {
    
    /**
     * Takes a filename and returns an appropriate implementation that loads that file.
     * @param fileName A string that contains the full path to the file. Make sure it has an extension
     * @return Returns a new object that will load the file provided.
     * @throws NullFileException 
     */
    public static InputLoader build(String fileName) throws NullFileException{
        //get the file name
        String extension = fileName.substring(fileName.lastIndexOf(".") + 1, fileName.length());
        //If it is of type XML then create a new XML file loader
        if(extension.equals("xml")){
            return new InputLoaderXML(fileName);
        }else{
            return null;
        }
    }

}
