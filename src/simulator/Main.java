package simulator;

/**
 * Description: main elevator runnable
 * 
 * The quarter programming project is to design and implement an object-oriented
 * elevator simulator. This simulator application will model a building, its
 * floors, its elevators, its call boxes, controllers, its people, etc. in order
 * to perform a variety of analysis that will help determine the optimal
 * elevator configuration for a given building. Additionally this application
 * can predict the expected effect of taking an elevator down for repairs on the
 * building’s population.
 * 
 * Input parameters for the project are below. Note: original values for Sim Run
 * Time and Person Rate were changed per the instructor.
 * 
 * numFloors=16 numElevators=4 maxPersonsPerElevator=8 doorTime=500
 * floorTime=500 elevatorSleepTime=15000 personPerMin=15 simRunTime=300000
 * defaultElevatorFlr=1
 * 
 * @author Patrick Stein
 * @author Chris Kurn
 * @since Version 1.0 - Spring Quarter 2014
 */

public class Main {

    /**
     * The main java class (runnable) creates a mySim object - data loaded via
     * constructor.
     * 
     * @param args
     *            command line arguments. Not currently being used.
     */
    public static void main(String[] args) {

        Simulator mySim = Simulator.getInstance();
        /* create mySim object - simInput.xml data loaded via constructor */
        try {
            mySim.buildSimulator("simInput.properties");
            mySim.runSimulator();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e);
        }

    }

}
