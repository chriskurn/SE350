package building.common;

/**
 * A factory for creating Person objects.
 *
 * @author Patrick Stein
 * @author Chris Kurn
 * @see package building.common
 * @since Version 1.0 - Spring Quarter 2014
 */


/** 
 * PersonFactory Class
 */
public class PersonFactory {

    /**
     * Builds the.
     *
     * @param startF the start f
     * @param destF the dest f
     * @return the person
     */
    public static Person build(int startF, int destF) {
        return new PersonImpl(startF, destF);
    }

}
