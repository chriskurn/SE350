package simulator.elements;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import simulator.common.SimulationInformation;
import simulator.common.IllegalParamException;
import simulator.common.NullFileException;

/**
 * Description: InputLoaderProperties class.
 *
 * @author Patrick Stein
 * @author Chris Kurn
 * @since Version 1.0 - Spring Quarter 2014
 */

public class InputLoaderProperties implements InputLoader {

    /** The file name. */
    private String fileName;
    
    /** The input. */
    private InputStream input;
    
    /** The prop. */
    private Properties prop;

    /** The num elevators. */
    private int numElevators;
    
    /** The num floors. */
    private int numFloors;
    
    /** The num express elevators. */
    private int numExpressElevators;
    
    /** The num persons per elevators. */
    private int numPersonsPerElevators;
    
    /** The person per min. */
    private int personPerMin;
    
    /** The floor time. */
    private long floorTime;
    
    /** The door time. */
    private long doorTime;
    
    /** The elevator sleep time. */
    private long elevatorSleepTime;
    
    /** The sim run time. */
    private long simRunTime;

    /** The min elevators. */
    private final int MIN_ELEVATORS = 1;
    
    /** The min floors. */
    private final int MIN_FLOORS = 1;
    
    /** The min execution. */
    private final long MIN_EXECUTION = 5000;

    /**
     * A basic constructor that takes a Properties file name with its full path.
     *
     * @param fn            A String containing the full path and name of an properties
     *            file.
     * @throws IllegalParamException             thrown if the properties member or input member is null
     * @throws NullFileException the null file exception
     * @throws FileNotFoundException             thrown if the filename provided does not open an existing
     *             file
     */
    public InputLoaderProperties(String fn) throws IllegalParamException,
            NullFileException, FileNotFoundException {
        this.setFileName(fn);
        this.setProp(new Properties());
        this.setInput(new FileInputStream(this.getResourceName()));
    }

    /* (non-Javadoc)
     * @see simulator.elements.InputLoader#loadInput()
     */
    @Override
    public SimulationInformation loadInput() throws IOException,
            IllegalParamException {
        // This method will load the input contained within the filename
        // provided at construction time.
        Properties prop = this.getProp();
        prop.load(this.getInput());

        this.setDoorTime(prop.getProperty("doorTime"));
        this.setElevatorSleepTime(prop.getProperty("elevatorSleepTime"));
        this.setFloorTime(prop.getProperty("floorTime"));
        this.setNumElevators(prop.getProperty("numElevators"));
        this.setNumExpressElevators(prop.getProperty("numExpressElevators"));
        this.setNumFloors(prop.getProperty("numFloors"));
        this.setNumPersonsPerElevators(prop
                .getProperty("maxPersonsPerElevator"));
        this.setPersonPerMin(prop.getProperty("personPerMin"));
        this.setSimRunTime(prop.getProperty("simRunTime"));

        return this.getSimulationInfo();
    }

    /* (non-Javadoc)
     * @see simulator.elements.InputLoader#getSimulationInfo()
     */
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
        info.personPerMin = this.personPerMin;
        info.simRunTime = this.simRunTime;

        return info;
    }

    /**
     * Sets the sim run time.
     *
     * @param attribute the new sim run time
     * @throws IllegalParamException the illegal param exception
     */
    private void setSimRunTime(String attribute) throws IllegalParamException {
        // TODO Auto-generated method stub
        int n = Integer.parseInt(attribute);
        if (n <= MIN_EXECUTION) {
            throw new IllegalParamException(
                    "The simulation run time must be greater than or equal to 5 seconds.");
        }
        this.simRunTime = n;

    }

    /**
     * Sets the person per min.
     *
     * @param attribute the new person per min
     * @throws IllegalParamException the illegal param exception
     */
    private void setPersonPerMin(String attribute) throws IllegalParamException {
        // TODO Auto-generated method stub
        int n = Integer.parseInt(attribute);
        if (n <= 1) {
            throw new IllegalParamException(
                    "Number of persons per minute must be greater than or equal to 1.");
        }
        this.personPerMin = n;
    }

    /**
     * Private method for setting the number of elevators.
     * 
     * @param numE
     *            takes a string that corresponds to a number that must be
     *            greater than 0.
     * @throws IllegalParamException
     *             The number of elevators must be greater or equal to 1.
     */
    private void setNumElevators(String numE) throws IllegalParamException {
        int n = Integer.parseInt(numE);
        if (n >= this.MIN_ELEVATORS) {
            this.numElevators = n;
        } else {
            throw new IllegalParamException(
                    String.format(
                            "Invalid value in file: %s "
                                    + "Number of elevators must be greater or equal to 1.",
                            this.getResourceName()));
        }
    }

    /**
     * Private method for setting the number of elevators.
     * 
     * @param numF
     *            takes a string that corresponds to a number that must be
     *            greater than 0.
     * @throws IllegalParamException
     *             The number of floors must be greater or equal to 1.
     */
    private void setNumFloors(String numF) throws IllegalParamException {
        int n = Integer.parseInt(numF);
        if (n >= this.MIN_FLOORS) {
            this.numFloors = n;
        } else {
            throw new IllegalParamException(
                    String.format(
                            "Invalid value in file: %s "
                                    + "Number of floors must be greater or equal to 1.",
                            this.getResourceName()));
        }

    }

    /**
     * Private set method for the Properties file name.
     *
     * @param fn            Takes a string that must correspond to an Properties file. It
     *            also cannot be null.
     * @throws NullFileException the null file exception
     */
    private void setFileName(String fn) throws NullFileException {
        // Make sure the file name exists
        if (fn != null) {
            this.fileName = fn;
        } else {
            throw new NullFileException("The filename cannot be null.");
        }
    }

    /**
     * public method for returning the Properties file name.
     * 
     * @return returns a string containing the Properties file
     */
    @Override
    public String getResourceName() {
        return this.fileName;
    }

    /**
     * Returns the input stream for the file opened.
     *
     * @return the inputstream associated with the file being read.
     */
    private InputStream getInput() {
        return input;
    }

    /**
     * Sets the input.
     *
     * @param i the new input
     * @throws IllegalParamException             the input stream cannot be set to null.
     */
    private void setInput(InputStream i) throws IllegalParamException {
        if (i != null) {
            this.input = i;
        } else {
            throw new IllegalParamException("Input stream cannot be null.");
        }
    }

    /**
     * Gets the java properties class associated with the filename.
     * 
     * @return the prop
     */
    private Properties getProp() {
        return prop;
    }

    /**
     * Sets the properties member.
     *
     * @param p the new prop
     * @throws IllegalParamException             throws an exception if the parameter p is null.
     */
    private void setProp(Properties p) throws IllegalParamException {
        if (p != null) {
            this.prop = p;
        } else {
            throw new IllegalParamException("Properties cannot be null.");
        }
    }

    /**
     * A function for translating a string attribute to a number for the member
     * elevator sleep time.
     * 
     * @param attribute
     *            a string attribute containing the number a number
     * @throws IllegalParamException
     *             throws an exception if the integer in the string is less than
     *             or equal to 0.
     */
    private void setElevatorSleepTime(String attribute)
            throws IllegalParamException {
        int n = Integer.parseInt(attribute);
        if (n <= 0) {
            throw new IllegalParamException(
                    "Elevator sleep time cannot be less than or equal to zero.");
        }
        this.elevatorSleepTime = n;

    }

    /**
     * A function for translating a string attribute to a number for the member
     * door time.
     * 
     * @param attribute
     *            a string attribute containing the number a number
     * @throws IllegalParamException
     *             throws an exception if the integer in the string is less than
     *             or equal to 0.
     */
    private void setDoorTime(String attribute) throws IllegalParamException {
        int n = Integer.parseInt(attribute);
        if (n <= 0) {
            throw new IllegalParamException(
                    "Door time cannot be less than or equal to zero.");
        }
        this.doorTime = n;

    }

    /**
     * A function for translating a string attribute to a number for the member
     * floor time.
     * 
     * @param attribute
     *            a string attribute containing the number a number
     * @throws IllegalParamException
     *             throws an exception if the integer in the string is less than
     *             or equal to 0.
     */
    private void setFloorTime(String attribute) throws IllegalParamException {
        int n = Integer.parseInt(attribute);
        if (n <= 0) {
            throw new IllegalParamException(
                    "Floor time cannot be less than or equal to zero.");
        }
        this.floorTime = n;

    }

    /**
     * A function for translating a string attribute to a number for the member
     * number of persons in an elevator.
     * 
     * @param attribute
     *            a string attribute containing the number a number
     * @throws IllegalParamException
     *             throws an exception if the integer in the string is less than
     *             or equal to 0.
     */
    private void setNumPersonsPerElevators(String attribute)
            throws IllegalParamException {
        int n = Integer.parseInt(attribute);
        if (n <= 0) {
            throw new IllegalParamException(
                    "Number of persons per elevator cannot be less than or equal to zero.");
        }
        this.numPersonsPerElevators = n;

    }

    /**
     * A function for translating a string attribute to a number for the member
     * number of express elevators.
     * 
     * @param attribute
     *            a string attribute containing the number a number
     * @throws IllegalParamException
     *             throws an exception if the integer in the string is less than
     *             0.
     */
    private void setNumExpressElevators(String attribute)
            throws IllegalParamException {
        int n = Integer.parseInt(attribute);
        if (n < 0) {
            throw new IllegalParamException(
                    "Number of express elevators cannot be less than zero.");
        }
        this.numExpressElevators = n;

    }

}
