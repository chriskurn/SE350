package simulator;

/**
 * Description: main elevator runnable
 * 
 * The quarter programming project is to design and implement an object-oriented
 * elevator simulator. This simulator application will model a building, its
 * floors, its elevators, its call boxes, controllers, its people, etc. in order
 * to perform a variety of analyses that will help determine the optimal
 * elevator configuration for a given building. Additionally this application
 * can predict the expected effect of taking an elevator down for repairs on the
 * building’s population.
 * 
 * @author Patrick Stein
 * @author Chris Kurn
 * @since Version 1.0 - Spring Quarter 2014
 * @see package simulator
 */

public class Main {

    /**
     * The main java class (runnable) creates a mySim object - data loaded via
     * constructor
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
