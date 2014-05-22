package simulator.testing;

import java.io.IOException;
import java.util.Random;

import static org.junit.Assert.*;

import org.junit.Test;

import elevator.common.InvalidFloorException;
import building.Building;
import building.common.Person;
import building.common.PersonFactory;
import simulator.Simulator;
import simulator.common.IllegalParamException;
import simulator.common.NullFileException;
import simulator.common.SimulationInformation;

/**
 * Description BuildBuilding Test.
 *
 * @author Patrick Stein
 * @author Chris Kurn
 * @since Version 1.0 - Spring Quarter 2014
 */


public class BuildBuildingTest {

	/**
	 * Build Building simulation. Create a new person Select a start and
	 * destination floor Add the person to the start floor * Press the
	 * appropriate button (up/down) on that floor [Elevator controller and
	 * elevator behaviors will handle things from here]
	 *
	 */
 
	@Test
	public void BuildingTest() {
        // TODO Auto-generated method stub

        /**
         * Access simulation parameters file
         */

        try {
            Simulator.getInstance().buildSimulator("simInput.properties");
        } catch (NullFileException | IllegalParamException | IOException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }

        Simulator.getInstance().getSimulationInfo();
        SimulationInformation info = Simulator.getInstance()
                .getSimulationInfo();

        /**
         * Read/Create simulation parameters. Set building boundaries Floors,
         * People, Simulation Time, Start Floor, Destination Floor
         */
        int minFloor = 1;
        int maxFloor = info.numFloors;
        int peopleMin = info.personPerMin;
        long simTime = info.simRunTime;
        long sysTime = System.currentTimeMillis();
        long simEnd = (sysTime + simTime);
        long currentTime = (System.currentTimeMillis() - sysTime);
        int startFloor;
        int destFloor;
        Random randNum = new Random();
        Random randNum2 = new Random();

        /**
         * Simulation Begins Creates person/minute, adds a start floor
         */
        System.out.println("Simulation Begins");

        while (currentTime < simEnd) {
            System.out.println("SystemTime = " + sysTime);
            System.out.println("Simulation ends = " + simEnd);

            do {
                startFloor = randNum.nextInt((maxFloor - minFloor) + 1)
                        + minFloor;
                destFloor = randNum2.nextInt((maxFloor - minFloor) + 1)
                        + minFloor;
            } while (startFloor == destFloor);

            try {
                Thread.sleep((60 / peopleMin) * 1000);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            System.out.println("Creating a new person");

            /**
             * Create Person Object
             */
            Person P = PersonFactory.build(startFloor, destFloor);

            System.out.println("Start Floor = " + startFloor);
            System.out.println("Destination Floor = " + destFloor);
            System.out.println("Adding person to start floor");

			try {
				Building.getInstance().enterFloor (P, destFloor);
			} catch (IllegalParamException | InvalidFloorException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

            System.out.println();

            currentTime = (System.currentTimeMillis() - sysTime);
        }

        System.out.println("Simulation ends");
    }

}
