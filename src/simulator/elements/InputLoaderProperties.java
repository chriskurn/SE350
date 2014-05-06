package simulator.elements;

/**
 * Description: InputLoaderProperties
 * @author Chris Kurn, Patrick Stein
 * @since Version 1.0 - Spring Quarter 2014
 * @see simulator.inputloading
 */

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import simulator.common.SimulationInformation;
import simulator.common.IllegalParamException;
import simulator.common.NullFileException;


// TODO Do error checking on the private set methods
// TODO JUnit testing

public class InputLoaderProperties implements InputLoader {
    
    private String fileName;
    private InputStream input;
    private Properties prop;
    
    private int numElevators;
    private int numFloors;
    private int numExpressElevators;
    private int numPersonsPerElevators;
    private int floorTime;
    private int doorTime;
    private int elevatorSleepTime;
    
    
    private final int MIN_ELEVATORS = 1;
    private final int MIN_FLOORS = 1;

    /**
     * A basic constructor that takes a Properties file name with its full path.
     * @param fn A String containing the full path and name of an properties file.
     * @throws IllegalParamException 
     * @throws NullFileException 
     * @throws FileNotFoundException 
     * @throws NullFileNameException 
     */
    public InputLoaderProperties(String fn) throws IllegalParamException, NullFileException, FileNotFoundException {
        this.setFileName(fn);
        this.setProp(new Properties());
        this.setInput(new FileInputStream(this.getResourceName()));
    }
    
    @Override
    public SimulationInformation loadInput() throws IOException, IllegalParamException {
        // This method will load the input contained within the filename provided at construction time.
        Properties prop = this.getProp();
        prop.load(this.getInput());
        
        this.setDoorTime(prop.getProperty("doorTime"));
        this.setElevatorSleepTime(prop.getProperty("elevatorSleepTime"));
        this.setFloorTime(prop.getProperty("floorTime"));
        this.setNumElevators(prop.getProperty("numElevators"));
        this.setNumExpressElevators(prop.getProperty("numExpressElevators"));
        this.setNumFloors(prop.getProperty("numFloors"));
        this.setNumPersonsPerElevators(prop.getProperty("maxPersonsPerElevator"));
        
        return this.getSimulationInfo();
    }

    @Override
    public SimulationInformation getSimulationInfo() {
        SimulationInformation info = new SimulationInformation();
        
        info.doorTime = this.doorTime;
        info.elevatorSleepTime = this.elevatorSleepTime;
        info.floorTime = this.floorTime;
        info.numElevators = this.numElevators;
        info.numExpressElevators = this.numExpressElevators;
        info.numFloors = this.numFloors;
        info.numPeoplePerElevator = this.numPersonsPerElevators;
        
        return info;
    }
    /**
     * Private method for setting the number of elevators.
     * @param numE takes a string that corresponds to a number that must be greater than 0.
     * @throws IllegalParamException The number of elevators must be greater or equal to 1.
     */
    private void setNumElevators(String numE) throws IllegalParamException{
        int n = Integer.parseInt(numE);
        if(n >= this.MIN_ELEVATORS){
            this.numElevators = n;
        }else {
            throw new IllegalParamException(String.format("Invalid value in file: %s " +
            		"Number of elevators must be greater or equal to 1.",this.getResourceName()));
        }
    }
    /**
     * Private method for setting the number of elevators.
     * @param numF takes a string that corresponds to a number that must be greater than 0.
     * @throws IllegalParamException The number of floors must be greater or equal to 1.
     */
    private void setNumFloors(String numF) throws IllegalParamException{
        int n = Integer.parseInt(numF);
        if(n >= this.MIN_FLOORS){
            this.numFloors = n;
        }else {
            throw new IllegalParamException(String.format("Invalid value in file: %s " +
                    "Number of floors must be greater or equal to 1.",this.getResourceName()));
        }
        
    }
    /**
     * Private set method for the Properties file name.
     * @param fn Takes a string that must correspond to an Properties file. It also cannot be null.
     * @throws NullFileNameException The file name must not be null
     */
    private void setFileName(String fn) throws NullFileException{
        //Make sure the file name exists
        if(fn != null){
            this.fileName = fn;
        }else{
            throw new NullFileException("The filename cannot be null.");
        }
    }
    /**
     * public method for returning the Properties file name.
     * @return returns a string containing the Properties file
     */
    @Override
    public String getResourceName(){
        return this.fileName;
    }
    /**
     * Returns the input stream for the file opened
     * @return the input
     */
    private InputStream getInput() {
        return input;
    }
    /**
     * @param input the input to set
     * @throws IllegalParamException 
     */
    private void setInput(InputStream i) throws IllegalParamException {
        if (i != null){
            this.input = i;
        } else {
            throw new IllegalParamException("Input stream cannot be null.");
        }
    }
    /**
     * @return the prop
     */
    private Properties getProp() {
        return prop;
    }
    /**
     * @param prop the prop to set
     * @throws IllegalParamException 
     */
    private void setProp(Properties p) throws IllegalParamException {
        if(p != null){
            this.prop = p;
        }else{
            throw new IllegalParamException("Properties cannot be null.");
        }
    }
    
    private void setElevatorSleepTime(String attribute) {
        // TODO Auto-generated method stub
        int n = Integer.parseInt(attribute);
        this.elevatorSleepTime = n;
        
    }

    private void setDoorTime(String attribute) {
        // TODO Auto-generated method stub
        int n = Integer.parseInt(attribute);
        this.doorTime = n;
        
    }

    private void setFloorTime(String attribute) {
        // TODO Auto-generated method stub
        int n = Integer.parseInt(attribute);
        this.floorTime = n;
        
    }

    private void setNumPersonsPerElevators(String attribute) {
        // TODO Auto-generated method stub
        int n = Integer.parseInt(attribute);
        this.numPersonsPerElevators = n;
        
    }

    private void setNumExpressElevators(String attribute) {
        // TODO Auto-generated method stub
        int n = Integer.parseInt(attribute);
        this.numExpressElevators = n;
        
    }

}
