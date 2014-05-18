package simulator.testing;
import java.util.Random;

/**
 * @author Patrick Stein
 * @author Chris Kurn
 * @since Version 1.0 - Spring Quarter 2014
 * @see package simulator.testing
 */

public class BuildBuildingTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		//read in # elevators
		int numElevators = 4;
		int minFloor = 1;
		//read in # floors
		int maxFloor = 10;
		//read in people/min
		int peopleMin = 10;
		//read in simulation time
		long simTime = 60000;
		long sysTime = System.currentTimeMillis();	
		long simEnd = sysTime+simTime;		
		int startFloor;
		int destFloor;
		Random randNum = new Random();
		Random randNum2 = new Random();
		
	
		//simulation period
		while (System.currentTimeMillis() < simEnd) {
			System.out.println ("CurrentTime = " + System.currentTimeMillis());
			System.out.println ("CurrentTime+SimTime = " + simEnd);
			
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
		}
		
		System.out.println ("Simulation ends");
	}

}
