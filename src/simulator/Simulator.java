package simulator;

import java.io.FileNotFoundException;

/**
 * interface Simulator
 * 
 * @author Chris Kurn, Patrick Stein
 * @since Version 1.0 - Spring Quarter 2014
 * @see package simulator
 */

//singleton
public interface Simulator {
    
    public void buildSimulator() throws Exception;
    public void runSimulator();
    public void endSimulator();
    
    //public Simulator getInstance();
    //public void logEvent(String event);

}
