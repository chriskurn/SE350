package simulator;

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
		SimulationInformation info = mySim.getSimulationInfo();

		for(int i = 0; i < 5; i++){
		    Simulator.getInstance().logEvent("Test event " + i);
		    try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
		}
	    System.out.println("Ended main function.");

	}

}
