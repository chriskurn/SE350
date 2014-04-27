package simulator;

/**
 * Description: main
 * @author Chris Kurn, Patrick Stein
 * @since Version 1.0 - Spring Quarter 2014
 * @see package simulator
 */

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		System.out.println("Start");
		
		Simulator mySim = new SimulatorImpl("simInput.xml");
		/** create mySim object - simInput.xml data loaded via constructor */
	    protected PrintStream out;
		try {
            mySim.buildSimulator();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
		


	}

}
