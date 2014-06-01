package building.common;

/**
 * Description: PersonFactory class.
 * 
 * A factory for creating Person objects.
 *
 * @author Patrick Stein
 * @author Chris Kurn
 * @since Version 1.0 - Spring Quarter 2014
 */

/**
 * PersonFactory Class
 */
public class PersonFactory {

    /**
     * Builds the a person based on a starting floor and a destination floor.
     * 
     * @param startF
     *            the start floor of that person
     * @param destF
     *            the destination floor of that person.
     * @return a new object of type person using those parameters to define
     *         him/her.
     */
    public static Person build(int startF, int destF) {
        return new PersonImpl(startF, destF);
    }

}
