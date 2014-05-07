package elevator.common;

/**
 * Description: InvalidFloorException class
 * @author Patrick Stein
 * @author Chris Kurn 
 * @since Version 1.0 - Spring Quarter 2014
 * @see package elevator.common
 */

public class InvalidFloorException extends Exception{
    public InvalidFloorException(String msg){
        super(msg);
    }

}
