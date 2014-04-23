package simulator;
/*
   	Description: 	Object-Oriented Software Development
   					Quarter Programming Project
	Authors:  		Chris Kurn and Patrick Stein
	Class:			SE-350
	Date:			Spring Quarter 2014 (04/22/14)
 */

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		System.out.println("Start");
		
		Simulator mySim = new SimulatorImpl("simInput.xml");
		try {
            mySim.buildSimulator();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
		


	}

}
