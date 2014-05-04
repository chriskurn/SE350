package simulator.inputloading;

import simulator.common.FileReadException;
import simulator.common.NullFileException;
/*
   	Description: 	Object-Oriented Software Development
   					Quarter Programming Project
	Authors:  		Chris Kurn and Patrick Stein
	Class:			SE-350
	Date:			Spring Quarter 2014
 */
import simulator.common.IllegalParamException;


public interface InputLoader {
    
    public void loadInput() throws IllegalParamException, FileReadException, NullFileException;
    public void readInput() throws NullFileException, IllegalParamException;
    
    public int getNumElevators();
    public int getNumFloors();

}
