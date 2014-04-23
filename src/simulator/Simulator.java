package simulator;

import java.io.FileNotFoundException;
/*
   	Description: 	Object-Oriented Software Development
   					Quarter Programming Project
	Authors:  		Chris Kurn and Patrick Stein
	Class:			SE-350
	Date:			Spring Quarter 2014
 */

//singleton
public interface Simulator {
    
    public void buildSimulator() throws Exception;
    public void runSimulator();
    public void endSimulator();
    
    //public Simulator getInstance();
    //public void logEvent(String event);

}
