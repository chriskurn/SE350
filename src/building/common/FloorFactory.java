package building.common;

/**
 * Description: FloorFactory class
 * 
 * @author Patrick Stein
 * @author Chris Kurn
 * @since Version 1.0 - Spring Quarter 2014
 * @see package building.common
 */

public class FloorFactory {

    public static Floor build(int floorNumber) {
        return new FloorImpl(floorNumber);
    }

}
