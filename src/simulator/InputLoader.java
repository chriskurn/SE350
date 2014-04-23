package simulator;
/*
   	Description: 	Object-Oriented Software Development
   					Quarter Programming Project
	Authors:  		Chris Kurn and Patrick Stein
	Class:			SE-350
	Date:			Spring Quarter 2014
 */


public interface InputLoader {
    
    public void loadInput() throws Exception;
    
    public int getNumElevators();
    public int getNumFloors();

}
