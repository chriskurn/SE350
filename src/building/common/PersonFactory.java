/**
 * 
 */
package building.common;

/**
 * @author Patrick Stein
 *
 */
public class PersonFactory {
    
    public static Person build(int startF, int destF){
        return new PersonImpl(startF,destF);
    }

}
