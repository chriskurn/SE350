package simulator;

import java.io.IOException;

import elevator.common.Elevator;
import elevator.common.ElevatorDirection;
import elevator.common.ElevatorImpl;
import elevator.common.InvalidFloorException;

import simulator.common.NarratorFactory;
import simulator.common.SimulationInformation;
import simulator.common.IllegalParamException;
import simulator.common.InputLoaderFactory;
import simulator.common.NullFileException;
import simulator.elements.InputLoader;
import simulator.elements.Narrator;

/**
 * 
 * @author Patrick Stein
 * @author Chris Kurn
 * @since Version 1.0 - Spring Quarter 2014
 * @see package simulator
 * @see import java.io.IOException;
 * @see import simulator.common.NarratorFactory;
 * @see import simulator.common.SimulationInformation;
 * @see import simulator.common.IllegalParamException;
 * @see import simulator.common.InputLoaderFactory;
 * @see import simulator.common.NullFileException;
 * @see import simulator.elements.InputLoader;
 * @see import simulator.elements.Narrator;
 */

// singleton
public class Simulator implements Narrator {

    private volatile static Simulator instance;
    private InputLoader inputLoaderDelegate;
    private Narrator narratorDelegate;

    private Simulator() {
        // TODO fill later
    }

    /**
     * Singleton method for creating and returning the only instance of the
     * Simulator class.
     * 
     * @return returns the only instance of the SImulator class.
     */
    public static Simulator getInstance() {

        // Double locking for multi-threaded environment
        if (instance == null) {
            synchronized (Simulator.class) {
                if (instance == null) {
                    instance = new Simulator();
                }
            }
        }
        return instance;
    }

    /**
     * Is a method for logging events that occur throughout the runtime of the
     * simulation
     * 
     * @param event
     *            event corresponds to a string that logs an activity that just
     *            occurred.
     */
    @Override
    public void logEvent(String event) {
        this.getNarratorDelegate().logEvent(event);
    }

    /**
     * Takes a file name as a string. It will then load and build a simulation
     * based on all relevant simulation information contained in that file.
     * 
     * @param file
     *            a string containing the file name. Currently only accepts
     *            .properties files.
     * @throws NullFileException
     *             Will be thrown if the parameter file name is null
     * @throws IllegalParamException
     *             Will be thrown if one of the values in the file violates
     *             specifications.
     * @throws IOException
     *             This will be thrown if the file tries to read something that
     *             is invalid in the file.
     */
    public void buildSimulator(String file) throws NullFileException,
            IllegalParamException, IOException {
        // Build information based on the file passed in.
        this.setInputLoader(InputLoaderFactory.build(file));
        this.getInputLoader().loadInput();

        // Create narrator
        // TODO put parameters in config file maybe?
        this.setNarratorDelegate(NarratorFactory.build(false, 1));

    }

    /**
     * Runs the simulation. Currently just adds a bunch of random destinations
     * to a set of elevators.
     * 
     */
    public void runSimulator() {
        Elevator ele1 = null;
        Elevator ele2 = null;
        Elevator ele3 = null;
        Elevator ele4 = null;

        SimulationInformation info = this.getSimulationInfo();

        try {
            ele1 = new ElevatorImpl(info);
            ele2 = new ElevatorImpl(info);
            ele3 = new ElevatorImpl(info);
            ele4 = new ElevatorImpl(info);
        } catch (IllegalParamException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        ele1.startElevator();
        ele2.startElevator();
        ele3.startElevator();
        ele4.startElevator();

        try {
            Thread.sleep(1000);
            ele1.addFloor(11, ElevatorDirection.UP);
            ele2.addFloor(14, ElevatorDirection.UP);
            Thread.sleep(1000);
            ele2.addFloor(13, ElevatorDirection.UP);
            ele2.addFloor(15, ElevatorDirection.UP);
            Thread.sleep(30000);
            ele3.addFloor(5, ElevatorDirection.UP);
            Thread.sleep(2000);
            ele3.addFloor(3, ElevatorDirection.UP);
        } catch (InvalidFloorException | InterruptedException e) {
            // TODO Auto-generated catch block
            Simulator.getInstance().logEvent(e.toString());
        }

        try {
            Thread.sleep(2000);
            ele3.addFloor(16);
            Thread.sleep(2000);
            ele3.addFloor(1);
        } catch (InvalidFloorException | InterruptedException e) {
            // TODO Auto-generated catch block
            Simulator.getInstance().logEvent(e.toString());
        }
        try {
            Thread.sleep(5000);
            ele3.addFloor(2);
            Thread.sleep(1000);
            ele3.addFloor(5);
            Thread.sleep(500);
            ele3.addFloor(3);
        } catch (InvalidFloorException | InterruptedException e) {
            // TODO Auto-generated catch block
            Simulator.getInstance().logEvent(e.toString());
        }

    }

    /**
     * Returns all of the parameters related to elevators. How many floors, how
     * many people can be in an elevator. Also, contains information related to
     * the simulation.
     * 
     * @return A SimulationInformation DTO containing relevant information to
     *         building and managing the simulation.
     */
    public SimulationInformation getSimulationInfo() {
        return this.getInputLoader().getSimulationInfo();
    }

    /**
     * If needed, will end the simulation
     */
    public void endSimulator() {
        // TODO Auto-generated method stub

    }

    /**
     * Private set method for the input loader variable
     * 
     * @param il
     *            a new instance of a class that implements the InputLoader
     *            interface. Must match the file type.
     */
    private void setInputLoader(InputLoader il) {
        // TODO Error checking
        this.inputLoaderDelegate = il;
    }

    /**
     * Get method to acquire the input loader delegate
     * 
     * @return returns the delegate that loads the file input.
     */
    private InputLoader getInputLoader() {
        return this.inputLoaderDelegate;
    }

    /**
     * Private get method for narrator delegate.
     * 
     * @return returns the narratorDelegate member.
     */
    private Narrator getNarratorDelegate() {
        return this.narratorDelegate;
    }

    /**
     * Private set method for setting the narrator delegate.
     * 
     * @param n
     *            This object must implement the Narrator interface and not be
     *            null.
     * @throws IllegalParamException
     *             THis exception will be thrown in n is null.
     */
    private void setNarratorDelegate(Narrator n) throws IllegalParamException {
        if (n == null) {
            throw new IllegalParamException(
                    "The narrator cannot be set to null.");
        }
        this.narratorDelegate = n;
    }

    @Override
    public boolean writeToFile() {
        return this.getNarratorDelegate().writeToFile();
    }

    @Override
    public int getMessageQueueLength() {
        return this.getNarratorDelegate().getMessageQueueLength();
    }

}
