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
 */

public class BuildBuildingTest {

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
		
			
		//read in # elevators
		int numElevators = info.numElevators;
		int minFloor = 1;
		//read in # floors
		int maxFloor = info.numFloors;
		
		//read in people/min
		int peopleMin = info.personPerMin;
		//read in simulation time
		long simTime = info.simRunTime;
		long sysTime = System.currentTimeMillis();	
		long simEnd = sysTime+simTime;		
		int startFloor;
		int destFloor;
		Random randNum = new Random();
		Random randNum2 = new Random();
		long currentTime = (System.currentTimeMillis() - sysTime);
		
			
		//simulation period
		while (currentTime < simTime) {
			System.out.println ("CurrentTime = " + currentTime);
			System.out.println ("CurrentTime + SimTime = " + simTime);
			
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
			
			//create a new person obj
			System.out.println ("Create a new person");		
			System.out.println ("Start Floor = " + startFloor);
			//add person to the start floor
			System.out.println ("End Floor = " + destFloor);
			System.out.println ("Add person to start floor");
			//enterFloor();
			System.out.println();
			
			currentTime = (System.currentTimeMillis() - sysTime);
		}
		
		System.out.println ("Simulation ends");
	}

}
