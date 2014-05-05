package simulator;
import java.io.IOException;

import simulator.common.NarratorFactory;
import simulator.common.SimulationInformation;
import simulator.common.IllegalParamException;
import simulator.common.InputLoaderFactory;
import simulator.common.NullFileException;
import simulator.elements.InputLoader;
import simulator.elements.Narrator;

/**
 * Description: Simulator
 * @author Chris Kurn, Patrick Stein
 * @since Version 1.0 - Spring Quarter 2014
 */

//singleton
public class Simulator implements Narrator{
    
    private volatile static Simulator instance;
    private InputLoader inputLoaderDelegate;
    private Narrator narratorDelegate;
    
    private Simulator(){
        // TODO fill later
    }

    /**
     * Singleton method for creating and returning the only instance of the Simulator class.
     * @return returns the only instance of the SImulator class.
     */
    public static Simulator getInstance(){
         
        //Double locking for multi-threaded environment
        if(instance == null){
            synchronized(Simulator.class){
                if(instance == null){
                    instance = new Simulator();
                }
            }
        }
        return instance;
    }
    
    /**
     * Is a method for logging events that occur throughout the runtime of the simulation
     * @param event
     */
    @Override
    public void logEvent(String event){
        this.getNarratorDelegate().logEvent(event);
    }
    
    /**
     * Takes a file name as a string. It will then load and build a simulation based on all relevant simulation information contained in that file.
     * @param file a string containing the file name. Currently only accepts .properties files.
     * @throws NullFileException Will be thrown if the parameter file name is null 
     * @throws IllegalParamException Will be thrown if one of the values in the file violates specifications.
     * @throws IOException This will be thrown if the file tries to read something that is invalid in the file.
     */
    public void buildSimulator(String file) throws NullFileException, IllegalParamException, IOException{
        // Build information based on the file passed in.
        this.setInputLoader(InputLoaderFactory.build(file));
        this.getInputLoader().loadInput();
        
        // Create narrator
        // TODO put parameters in config file maybe?
        this.setNarratorDelegate(NarratorFactory.build(false, 1));
        
        // TODO Create simulation elements such as the building floors, elevators, people, and elevator controller
       
    }

    /**
     * Returns all of the parameters related to elevators. How many floors, how many
     * @return
     */
    public SimulationInformation getSimulationInfo(){
        return this.getInputLoader().getSimulationInfo();  
    }

    /**
     * Runs the simulator
     */
    public void runSimulator() {
        // TODO Auto-generated method stub
        

    }

    /**
     * If needed, will end the simulation 
     */
    public void endSimulator() {
        // TODO Auto-generated method stub

    }
    /**
     * Private set method for the input loader variable
     * @param il a new instance of a class that implements the InputLoader interface. Must match the file type.
     */
    private void setInputLoader(InputLoader il){
     // TODO Error checking
        this.inputLoaderDelegate = il;
    }
    
    /**
     * Get method to acquire the input loader delegate
     * @return returns the delegate that loads the file input.
     */
    private InputLoader getInputLoader(){
        return this.inputLoaderDelegate;
    }
    /**
     * Private get method for narrator delegate.
     * @return returns the narratorDelegate member.
     */
    private Narrator getNarratorDelegate(){
        return this.narratorDelegate;
    }
    private void setNarratorDelegate(Narrator n) {
        // TODO Error checking
        this.narratorDelegate = n;
    }
    
}
