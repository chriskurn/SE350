package elevator.movement;


/**
 * Description: class ElevatorMoveUpDown
 * @author Chris Kurn, Patrick Stein
 * @since Version 1.0 - Spring Quarter 2014
 * @see package elevator.movement
 */

public class ElevatorMoveUpDown {

    public ElevatorMoveUpDown() {
    	
    	
    }

	public void moveToFloor() {
	    //int newFloor;
	    //int maxFloor = 10;	//should be set by the xml file    
	    //int minFloor = 0;	//should be set by the xml file
	    //int idleFloor = (maxFloor-minFloor)/2;
	    //int direction;
     
	    SimulationInformation elevatorInfo = Simulator.getInstance().getSimulationInfo();
	
	    /**
	     * This method will move the elevator towards a destination.
	     */
	    public void move(){
	        //Stub for move method
	        System.out.println("I am moving.");
	        try {
	            Thread.sleep(elevatorInfo.floorTime);
	        } catch (InterruptedException e) {
	            // TODO Auto-generated catch block
	            e.printStackTrace();
	        }
	        System.out.println("I am done moving.");
	    }
	    
	    

	
	}
}