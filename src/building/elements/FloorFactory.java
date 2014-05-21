package building.elements;

import simulator.common.IllegalParamException;

/**
 * Description: FloorFactory class.
 * 
 * @author Patrick Stein
 * @author Chris Kurn
 * @since Version 1.0 - Spring Quarter 2014
 */

public class FloorFactory {

    /**
     * Builds a floor given a floor number
     * 
     * @param floorNumber
     *            the floor number of this new floor
     * @return a floor corresponding to the floor number given.
     * @throws IllegalParamException
     *             thrown if the floor number is less than 1.
     */
    public static Floor build(int floorNumber) throws IllegalParamException {
        return new FloorImpl(floorNumber);
    }

}
