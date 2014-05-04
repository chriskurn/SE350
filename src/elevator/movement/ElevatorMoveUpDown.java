package elevator.movement;


/**
 * Description: class ElevatorMoveUpDown
 * @author Chris Kurn, Patrick Stein
 * @since Version 1.0 - Spring Quarter 2014
 * @see package elevator.movement
 */

public class ElevatorMoveUpDown {

    public ElevatorMoveUpDown() {}

	public void moveToFloor() {
	    int newFloor;
	    int maxFloor = 10;	//should be set by the xml file    
	    int minFloor = 0;	//should be set by the xml file
	    int idleFloor = (maxFloor-minFloor)/2;
	    int direction;
	    //Simulator.getInstance().getElevatorInformation()
	    //Elevator.numberOfSecondsPerFloor
	    //Floor request comes from elevatorController.java
	
	    System.out.println("Enter your destination floor> ");
	    moveToFloor = readInScreen.nextInt();
	    if (newFloor > maxFloor || newFloor < minFloor) {
	        System.out.println("Invalid selection");
	    }
	
	    else if (newFloor <= maxFloor && newFloor > minFloor) {
	        for (int i = 1; i <= moveToFloor; i++)
	            System.out.println("Floor: " + i);
	            //Illuminate floor light if available
	            elevatorIdlePosition(moveToFloor);
	    }
	}
	
	
	public void elevatorIdlePosition(int idleFloor){
	    for (int i=idleFloor; i>0;i--){
	         System.out.println("Floor: " + i);
	    }
	    System.out.println("Elevator at idle floor");
	
	}
}