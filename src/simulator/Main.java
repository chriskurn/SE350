package simulator;

import elevator.common.Elevator;
import elevator.common.ElevatorDirection;
import elevator.common.ElevatorImpl;
import elevator.common.InvalidFloorException;
import simulator.common.IllegalParamException;
import simulator.common.SimulationInformation;

/**
 * Description: main elevator runnable
 * 
 * The quarter programming project is to design and implement an object-oriented elevator simulator. This
 * simulator application will model a building, its floors, its elevators, its call boxes, controllers, its people,
 * etc. in order to perform a variety of analyses that will help determine the optimal elevator configuration
 * for a given building. Additionally this application can predict the expected effect of taking an elevator
 * down for repairs on the building’s population.
 * 
 * @author Patrick Stein
 * @author Chris Kurn
 * @since Version 1.0 - Spring Quarter 2014
 * @see package simulator
 * @see import elevator.common.Elevator;
 * @see import elevator.common.ElevatorImpl;
 * @see import elevator.common.InvalidFloorException;
 * @see import simulator.common.IllegalParamException;
 * @see import simulator.common.SimulationInformation;
 */

public class Main {

	public static void main(String[] args){
		
		System.out.println("Start the Elevator Simulation");
		
		Simulator mySim = Simulator.getInstance();
		/* create mySim object - simInput.xml data loaded via constructor */
		try {
            mySim.buildSimulator("simInput.properties");
            mySim.runSimulator();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            System.out.println(e);
        }
	    System.out.println("Ended main function.");

	  
	}

}
