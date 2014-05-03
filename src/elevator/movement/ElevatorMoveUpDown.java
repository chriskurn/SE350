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
        int direction;

        System.out.println("Enter the floor#");
        moveToFloor = readInScreen.nextInt();
        if (moveToFloor > maxFloor || moveToFloor < 0 || moveToFloor == 13) {
            System.out.println("Floor doesn't exist in this building");
        }

        else if (moveToFloor <= 100 && moveToFloor > 0 && moveToFloor != 13) {
            for (int i = 1; i <= moveToFloor; i++);
        }
    }

}