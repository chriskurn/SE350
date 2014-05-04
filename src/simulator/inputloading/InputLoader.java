package simulator.inputloading;
import simulator.common.FileReadException;
import simulator.common.IllegalParamException;
import simulator.common.NullFileException;

/**
 * Description: InputLoader
 * @author Chris Kurn, Patrick Stein
 * @since Version 1.0 - Spring Quarter 2014
 */

public interface InputLoader {
    
    public void loadInput() throws IllegalParamException, FileReadException, NullFileException;
    public void readInput() throws NullFileException, IllegalParamException;
    
    public int getNumElevators();
    public int getNumFloors();

}
