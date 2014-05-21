package building.elements;

/**
 * Description: FloorFactory class.
 *
 * @author Patrick Stein
 * @author Chris Kurn
 * @since Version 1.0 - Spring Quarter 2014
 */

public class FloorFactory {

    /**
     * Builds the.
     *
     * @param floorNumber the floor number
     * @return the floor
     */
    public static Floor build(int floorNumber) {
        return new FloorImpl(floorNumber);
    }

}
