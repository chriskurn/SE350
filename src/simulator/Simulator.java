package simulator;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import building.Building;
import building.common.Person;
import building.common.PersonFactory;

//import elevator.common.Elevator;
//import elevator.common.ElevatorDirection;
//import elevator.common.ElevatorImpl;
import elevator.common.InvalidFloorException;
import elevator.control.ElevatorController;

import simulator.common.NarratorFactory;
import simulator.common.SimulationInformation;
import simulator.common.IllegalParamException;
import simulator.common.InputLoaderFactory;
import simulator.common.NullFileException;
import simulator.elements.InputLoader;
import simulator.elements.Narrator;

/**
 * The Class Simulator.
 * 
 * @author Patrick Stein
 * @author Chris Kurn
 * @since Version 1.0 - Spring Quarter 2014
 */

// singleton
public class Simulator implements Narrator {

    /** The instance. */
    private volatile static Simulator instance;

    /** The input loader delegate. */
    private InputLoader inputLoaderDelegate;

    /** The narrator delegate. */
    private Narrator narratorDelegate;

    /** The simulator building. */
    private Building simulatorBuilding;

    /** The running. */
    private boolean running = true;
    private ArrayList<Person> runningPeople = new ArrayList<Person>();
    /**
     * An array list for debugging purposes. If someone gets lost during
     * execution add him/her here.
     */
    private ArrayList<Person> lostPeople = new ArrayList<Person>();

    /**
     * Instantiates a new simulator.
     */
    private Simulator() {
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
     * simulation.
     * 
     * @param event
     *            event corresponds to a string that logs an activity that just
     *            occurred.
     */
    @Override
    public void logEvent(String event) {
        getNarratorDelegate().logEvent(event);
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
        setInputLoader(InputLoaderFactory.build(file));
        getInputLoader().loadInput();

        // Create logging class
        setNarratorDelegate(NarratorFactory.build(false, 1));

        setBuilding(Building.getInstance());
        logEvent("Ended building the simulation");

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
                int floorEntered = getSimulatorBuilding().enterFloor(p,
                        p.getStartFloor());
                if (floorEntered != p.getStartFloor()) {
                    logEvent(String
                            .format("Person %d was put on the wrong starting floor. Skipping this person for now.",
                                    p.getPersonId()));
                } else {
                    // start that person up
                    // all it does is request the floor from the elevator
                    // controller
                    p.startPerson();
                }
            } catch (IllegalParamException | InvalidFloorException e1) {
                // Ignore the person and continue on
                logEvent(String
                        .format("The following person could not enter the building: %s. Skipping this peron and continuing on.",
                                p.toString()));
                logEvent(e1.getMessage());
            }

            // Sleep for whatever the simulation demands to create so many
            // people per minute
            try {
                Thread.sleep((60 / peopleMin) * 1000);
            } catch (InterruptedException e) {
                logEvent("Another thread has tried to interrupt the main thread during the sleep cycle associated with"
                        + " people object creation. Moving on");
            }

            // Get delta time
            currentTime = (System.currentTimeMillis() - startTime);
        }
        logEvent("End of person generation for the simulation");

        simulationEnd();
    }

    /**
     * Private helper method to determine when to shut off all of the simulation related crap.
     */
    private void simulationEnd() {
        // Check to see if we can end the simulation

        do {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                Simulator
                        .getInstance()
                        .logEvent(
                                "An attempt to interrupt the simulation thread was made. Ignoring and continuing on.");
            }
        } while (allPersonsDone() == false);
        // End simulation now that everything is done
        logEvent("The simulator has now ended.");
        ElevatorController.getInstance().shutDownElevatorController();
        logEvent("The elevator controller has been shut down.");
        ElevatorController.getInstance().stopAllElevators();
        logEvent("All elevators have been shut down.");
    }

    /**
     * A test to see if there are people still running
     * 
     * @return returns true if all of the people have finished their trip in the
     *         building
     */
    private boolean allPersonsDone() {
        ArrayList<Person> peopleActive = getRunningPeople();

        for (Person p : peopleActive) {
            if (p.getCurrentFloor() != p.getDestinationFloor()) {
                return false;
            }
        }
        return true;
    }

    /**
     * Private method for getting the collection of running people
     * @return the collection of people
     */
    private ArrayList<Person> getRunningPeople() {
        return runningPeople;
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
        return getInputLoader().getSimulationInfo();
    }

    /**
     * If needed, will end the simulation.
     */
    public void endSimulator() {
        // makes the running variable false to end the simulation.
        running = false;
    }

    /**
     * Private set method for the input loader variable.
     * 
     * @param il
     *            a new instance of a class that implements the InputLoader
     *            interface. Must match the file type.
     * @throws IllegalParamException
     *             the illegal param exception
     */
    private void setInputLoader(InputLoader il) throws IllegalParamException {
        if (il == null) {
            throw new IllegalParamException("Cannot have a null input loader.");
        }
        inputLoaderDelegate = il;
    }

    /**
     * Get method to acquire the input loader delegate.
     * 
     * @return returns the delegate that loads the file input.
     */
    private InputLoader getInputLoader() {
        return inputLoaderDelegate;
    }

    /**
     * Private get method for narrator delegate.
     * 
     * @return returns the narratorDelegate member.
     */
    private Narrator getNarratorDelegate() {
        return narratorDelegate;
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
        narratorDelegate = n;
    }

    /**
     * Sets the building.
     * 
     * @param b
     *            the new building
     * @throws IllegalParamException
     *             the illegal param exception
     */
    private void setBuilding(Building b) throws IllegalParamException {
        if (b == null) {
            throw new IllegalParamException(
                    "The building has returned null. This is very bad!");
        }
        simulatorBuilding = b;
    }

    /**
     * Gets the simulator building.
     * 
     * @return the simulatorBuilding
     */
    private Building getSimulatorBuilding() {
        return simulatorBuilding;
    }

    /*
     * (non-Javadoc)
     * 
     * @see simulator.elements.Narrator#writeToFile()
     */
    @Override
    public boolean writeToFile() {
        return getNarratorDelegate().writeToFile();
    }

    /*
     * (non-Javadoc)
     * 
     * @see simulator.elements.Narrator#getMessageQueueLength()
     */
    @Override
    public int getMessageQueueLength() {
        return getNarratorDelegate().getMessageQueueLength();
    }
}
