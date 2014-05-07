package simulator;

import elevator.common.Elevator;
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
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            System.out.println(e);
        }
		SimulationInformation info = mySim.getSimulationInfo();

		for(int i = 0; i < 5; i++){
		    Simulator.getInstance().logEvent("Test event " + i);
		    try {
                Thread.sleep(20);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
		}
		Elevator ele1 = null;
		Elevator ele2 = null;
		try {
		    ele1 = new ElevatorImpl(info);
		    //ele2 = new ElevatorImpl(info);
        } catch (IllegalParamException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
		
		try {
		   // ele2.addFloor(8);
		    ele1.addFloor(10);
		    ele1.startElevator();
		   // ele2.startElevator();
            Thread.sleep(1000);
           // ele2.addFloor(7);
            ele1.addFloor(9);
        } catch (InvalidFloorException | InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
		
	    System.out.println("Ended main function.");

	  
	}

}
