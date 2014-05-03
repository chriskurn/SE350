package elevator.movement;

import java.util.Scanner;

/**
 * Description: class ElevatorMoveUpDown
 * @author Chris Kurn, Patrick Stein
 * @since Version 1.0 - Spring Quarter 2014
 * @see package elevator.movement
 */

public class ElevatorMoveUpDown {

    public ElevatorMoveUpDown() {}


	public void selectTheFloor() {
	    Scanner readInScreen = new Scanner(System.in);
	    int moveToFloor;
	    int maxFloor = 10;	//should be set by the xml file    
	    int minFloor = 0;	//should be set by the xml file
	    int idleFloor = (maxFloor-minFloor)/2;
	    int direction;
	
	    System.out.println("Enter your destination floor> ");
	    moveToFloor = readInScreen.nextInt();
	    if (moveToFloor > maxFloor || moveToFloor < minFloor || moveToFloor == 13) {
	        System.out.println("Invalid selection");
	    }
	
	    else if (moveToFloor <= maxFloor && moveToFloor > minFloor && moveToFloor != 13) {
	        for (int i = 1; i <= moveToFloor; i++)
	            System.out.println("Floor: " + i);
	            //Illuminate floor light if avaiable
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