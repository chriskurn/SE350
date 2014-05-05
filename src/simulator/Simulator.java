package simulator;
import simulator.common.InputLoader;
import simulator.common.InputLoaderFactory;
import simulator.elements.Narrator;

/**
 * Description: simulator
 * @author Chris Kurn, Patrick Stein
 * @since Version 1.0 - Spring Quarter 2014
 */

//singleton
public class Simulator implements Narrator{
    
    private volatile static Simulator instance;
    private String inputFile;
    private InputLoader inputLoaderDelegate;
    private Narrator narratorDelegate;
    private int numFloors;
    private int numElevators;
    
    private Simulator(){
        // TODO fill later
    }

    /**
     * 
     * @return
     */
    public static Simulator getInstance(){
         
        //Double locking for multi-threaded environment
        if(instance == null){
            synchronized(Thread.class){
                if(instance == null){
                    return new Simulator();
                }

            }
        }
        return instance;
        
    }
    
    /**
     * 
     * @param event
     */
    @Override
    public void logEvent(String event){
        
        
    }
    
    /**
     * 
     * @param file
     */
    public void addInputFile(String file){
        this.setInputFile(file);
    }
    
    public void buildSimulator() throws Exception{
        /* Builds the simulation based on the input file. Can throw file not found exception
         * 
         */
        this.setInputLoader(InputLoaderFactory.build(this.getInputFile()));
        this.getInputLoader().loadInput();
        

    }

    public void runSimulator() {
        // TODO Auto-generated method stub
        

    }

    public void endSimulator() {
        // TODO Auto-generated method stub

    }
    /**
     * Private set method for the input loader variable
     * @param il a new instance of a class that implements the InputLoader interface. Must match the file type.
     */
    private void setInputLoader(InputLoader il){
        this.inputLoaderDelegate = il;
    }
    /**
     * Gets the input file associated with this simulation
     * @return return a string of the input file associated with this simulation.
     */
    private String getInputFile(){
        return this.inputFile;
    }
    private void setInputFile(String f){
        this.inputFile = f;
    }
    
    /**
     * Get method to acquire the input loader delegate
     * @return returns the delegate that loads the file input.
     */
    private InputLoader getInputLoader(){
        return this.inputLoaderDelegate;
    }

}
