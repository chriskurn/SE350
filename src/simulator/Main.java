package simulator;

import elevator.common.Elevator;
import elevator.common.ElevatorDirection;
import elevator.common.ElevatorImpl;
import elevator.common.InvalidFloorException;
import simulator.common.IllegalParamException;
import simulator.common.SimulationInformation;

/**
 * Description: main
 * @author Chris Kurn, Patrick Stein
 * @since Version 1.0 - Spring Quarter 2014
 * @see package simulator
 */

public class Main {

	public static void main(String[] args){
		
		System.out.println("Start the Elevator Simulation");
		
		Simulator mySim = Simulator.getInstance();
		/* create mySim object - simInput.xml data loaded via constructor */
		try {
            mySim.buildSimulator("simInput.properties");
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            System.out.println(e);
        }
	    System.out.println("Ended main function.");

	  
	}

}
