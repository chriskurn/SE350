package building.common;

/**
 * @author Patrick Stein
 * @author Chris Kurn
 * @since Version 1.0 - Spring Quarter 2014
 * @see package building.common
 */


/** 
 * PersonFactory Class
 */
public class PersonFactory {

    public static Person build(int startF, int destF) {
        return new PersonImpl(startF, destF);
    }

}
