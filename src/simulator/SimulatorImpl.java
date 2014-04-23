package simulator;
import simulator.inputloading.InputLoader;
import simulator.inputloading.InputLoaderFactory;
/*
Description:    Object-Oriented Software Development
                Quarter Programming Project
Authors:        Chris Kurn and Patrick Stein
Class:          SE-350
Date:           Spring Quarter 2014
*/

//singleton
public class SimulatorImpl implements Simulator {
    
    private String inputFile;
    private InputLoader inputLoaderDelegate;
    private int numFloors;
    private int numElevators;
    
    /**
     * Takes an input file and builds a simulation around that file.
     * @param file Must be a string containing the full path of the file on the computer.
     */
    public SimulatorImpl(String file){
        this.inputFile = file;
    }

    @Override
    public void buildSimulator() throws Exception{
        /* Builds the simulation based on the input file. Can throw file not found exception
         * 
         */
        this.setInputLoader(InputLoaderFactory.build(this.getInputFile()));
        this.getInputLoader().loadInput();
        System.out.println(String.format("Number of elevators %s.",this.getInputLoader().getNumElevators()));
        System.out.println(String.format("Number of floors %s.", this.getInputLoader().getNumFloors()));
        

    }

    @Override
    public void runSimulator() {
        // TODO Auto-generated method stub
        

    }

    @Override
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
    /**
     * Get method to acquire the input loader delegate
     * @return returns the delegate that loads the file input.
     */
    private InputLoader getInputLoader(){
        return this.inputLoaderDelegate;
    }

}
