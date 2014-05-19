package simulator;

import java.io.IOException;
import java.util.Random;

import building.Building;
import building.common.Person;
import building.common.PersonFactory;

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
 * @see package simulator;
 * @see import java.io.IOException;
 * @see import java.util.Random;
 * @see import building.Building;
 * @see import building.common.Person;
 * @see import building.common.PersonFactory;
 * @see import elevator.common.Elevator;
 * @see import elevator.common.ElevatorDirection;
 * @see import elevator.common.ElevatorImpl;
 * @see import elevator.common.InvalidFloorException;
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
    private Building simulatorBuilding;
    private boolean running = true;

    private Simulator() {
        // TODO fill later
    }

    /**
     * Singleton method for creating and returning the only instance of the
     * Simulator class.
     * 
     * @return returns the only instance of the Simulator class.
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

        // Create logging class
        this.setNarratorDelegate(NarratorFactory.build(false, 1));

        this.setBuilding(Building.getInstance());
        this.logEvent("Ended building the simulation");

    }

    /**
     * Runs the simulation. Currently just adds a bunch of random destinations
     * to a set of elevators.
     * 
     */
    public void runSimulator() {

        SimulationInformation info = getSimulationInfo();

        // Read/Create simulation parameters. Set building boundaries
        // Floors, People, Simulation Time, Start Floor, Destination Floor

        int minFloor = 1;
        int maxFloor = info.numFloors;
        int peopleMin = info.personPerMin;
        long startTime = System.currentTimeMillis();
        long simEnd = info.simRunTime;
        long currentTime = (System.currentTimeMillis() - startTime);
        Random startFloorRandom = new Random();
        Random destFloorRandom = new Random();

        while (currentTime < simEnd && running) {

            int startFloor;
            int destFloor;
            do {
                startFloor = startFloorRandom
                        .nextInt((maxFloor - minFloor) + 1) + minFloor;
                destFloor = destFloorRandom.nextInt((maxFloor - minFloor) + 1)
                        + minFloor;
            } while (startFloor == destFloor);

            // create a new person object
            Person p = PersonFactory.build(startFloor, destFloor);
            try {
                // Add him to the start floor
                this.getSimulatorBuilding().enterFloor(p, p.getStartFloor());
                // start that person up
                p.startPerson();
            } catch (IllegalParamException | InvalidFloorException e1) {
                // Ignore the person and continue on
                this.logEvent(String
                        .format("The following person could not enter the building: %s. Skipping this peron and continuing on.",
                                p.toString()));
                this.logEvent(e1.getMessage());
            }

            // Sleep for whatever the simulation demands to create so many people per minute
            try {
                Thread.sleep((60 / peopleMin) * 1000);
            } catch (InterruptedException e) {
                this.logEvent("Another thread has tried to interrupt the main thread during the sleep cycle associated with" +
                		" people object creation. Moving on");
            }

            // Get delta time
            currentTime = (System.currentTimeMillis() - startTime);
        }
        this.logEvent("End of person generation for the simulation");
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
        // makes the running variable false to end the simulation.
        this.running = false;
    }

    /**
     * Private set method for the input loader variable
     * 
     * @param il
     *            a new instance of a class that implements the InputLoader
     *            interface. Must match the file type.
     * @throws IllegalParamException
     */
    private void setInputLoader(InputLoader il) throws IllegalParamException {
        if (il == null) {
            throw new IllegalParamException("Cannot have a null input loader.");
        }
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

    private void setBuilding(Building b) throws IllegalParamException {
        if (b == null) {
            throw new IllegalParamException(
                    "The building has returned null. This is very bad!");
        }
        this.simulatorBuilding = b;
    }

    /**
     * @return the simulatorBuilding
     */
    private Building getSimulatorBuilding() {
        return simulatorBuilding;
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
