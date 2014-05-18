package simulator.testing;

import java.io.IOException;
import java.util.Random;
import simulator.Simulator;
import simulator.common.IllegalParamException;
import simulator.common.NullFileException;
import simulator.common.SimulationInformation;

/**
 * @author Patrick Stein
 * @author Chris Kurn
 * @since Version 1.0 - Spring Quarter 2014
 * @see package simulator.testing
 * @see import java.io.IOException;
 * @see import java.util.Random;
 * @see import simulator.Simulator;
 * @see import simulator.common.IllegalParamException;
 * @see import simulator.common.NullFileException;
 * @see import simulator.common.SimulationInformation;
 */

public class BuildBuildingTest {

	/**
	 * Build Building simulation.
	 * Create a new person
	 * Select a start and destination floor
     * Add the person to the start floor *
     * Press the appropriate button (up/down) on that floor
     * [Elevator controller and elevator behaviors will handle things from here]
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		try {
			Simulator.getInstance().buildSimulator("simInput.properties");
		} catch (NullFileException | IllegalParamException | IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		Simulator.getInstance().getSimulationInfo();
		SimulationInformation info = Simulator.getInstance().getSimulationInfo();
		
			
		/**
		 * Read in simulation parameters. Set building boundaries
		 * Floors, People, Simulation Time
		 */
		int numElevators = info.numElevators;
		int minFloor = 1;
		int maxFloor = info.numFloors;
		int peopleMin = info.personPerMin;
		long simTime = info.simRunTime;
		long sysTime = System.currentTimeMillis();	
		long simEnd = (sysTime + simTime);		
		int startFloor;
		int destFloor;
		Random randNum = new Random();
		Random randNum2 = new Random();
		long currentTime = (System.currentTimeMillis() - sysTime);
		
		System.out.println ("Simulation Begins");
		
		/**
		 * Simulation while loop
		 * Creates person/minute, adds a start floor
		 */
		while (currentTime < simEnd) {
			System.out.println ("SystemTime = " + sysTime);
			System.out.println ("Simulation ends = " + simEnd);
			
			do {
				startFloor = randNum.nextInt((maxFloor - minFloor) + 1) + minFloor;
				destFloor = randNum2.nextInt((maxFloor - minFloor) + 1) + minFloor;
			}	while (startFloor == destFloor);
			
			
			try {
				Thread.sleep((60/peopleMin)*1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
			System.out.println ("Creating a new person");	
			
			//create a new person obj
			
			System.out.println ("Start Floor = " + startFloor);
			
			//add person to the start floor
			
			System.out.println ("End Floor = " + destFloor);
			System.out.println ("Adding person to start floor");
			
			//enterFloor();
			
			System.out.println();
			
			currentTime = (System.currentTimeMillis() - sysTime);
		}
		
		System.out.println ("Simulation ends");
	}

}
