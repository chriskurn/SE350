package simulator.stats;

import java.util.ArrayList;

import simulator.common.SimulationInformation;
import building.common.Person;

/**
 * Description: Statistics Generator Impl.
 * 
 * @author Patrick Stein
 * @author Chris Kurn
 * @since Version 1.0 - Spring Quarter 2014
 */

public class StatisticsGeneratorImpl implements StatisticsGenerator {

    /**
     * An array list of the people used to generate statistics.
     */
    private ArrayList<Person> people;

    /**
     * A class that holds all relevant information for this simulation
     */
    private SimulationInformation info;

    /**
     * A public constructor that takes an array list of persons and the
     * simulation information used to build the simulation
     * 
     * @param p
     *            an array list of person objects
     * @param info
     *            the simulation information used to build the simulator
     */
    public StatisticsGeneratorImpl(ArrayList<Person> p,
            SimulationInformation info) {
        setInfo(info);
        setPeople(p);
    }

    /**
     * Generate simulation statistics.
     */
    @Override
    public void generateStats() {
        System.out.println();
        System.out.println();
        System.out.printf("Generating: Average/Min/Max Wait time by floor (in seconds).%n");
        printHorizontalLn(65);
        generateAverageWaitTimeByFloorTable();
        System.out.println();
        System.out.printf("Generating: Wait/Ride/Total Time by Person (in seconds).%n");
        printHorizontalLn(86);
        generateWaitRideTotalTimeByPersonTable();
        System.out.println();
        System.out.printf("Generating: Floor to Floor tables (in seconds).%n");
        generateFloorToFloorTables();
        System.out.println();
    }

    /**
     * Generate simulation floor to floor tables.
     */
    private void generateFloorToFloorTables() {
        ArrayList<Person> peoples = this.getPeople();
        SimulationInformation simInfo = this.getInfo();
        int numFloors = simInfo.numFloors;
        FloorStats[][] fStats = new FloorStats[numFloors][numFloors];
        // Generate a 2 dimensional array of empty floor stats
        for (int i = 0; i < numFloors; i++) {
            for (int j = 0; j < numFloors; j++) {
                // The graph should mirror itself
                if (j < i) { // if j is currently less than i then this means
                             // this cell should be mirrored
                    fStats[i][j] = fStats[j][i];
                } else if (j == i) { // if j is equal to i then this cell should
                                     // be equal.

                } else { // Make a new floor if it is part of the upper portion
                         // of the graph
                    fStats[i][j] = new FloorStats(i + 1, j + 1);
                }

            }
        }
        // For each person go through and add his information to the cell in the
        // Fstats array
        // The cell is determined by where the person started and where he ended
        for (Person p : peoples) {
            // Note: subtract 1 because arrays start at 0.
            int startF = p.getStartFloor() - 1;
            int destF = p.getDestinationFloor() - 1;
            long deltaTimeInSeconds = (long) ((p.getElevatorEnterTime() - p
                    .getStartTime()) * 0.001);
            int row = startF < destF ? startF : destF;
            int col = startF > destF ? startF : destF;
            fStats[row][col].addEntry(deltaTimeInSeconds);
        }
        outputFloorToFloorTables(fStats, "avg");
        outputFloorToFloorTables(fStats, "max");
        outputFloorToFloorTables(fStats, "min");
    }

    /**
     * Output simulation floor to floor tables. See project part 4 tables b,c,d
     * for more information
     * 
     * @param fStats
     *            a 2-dimension array representing tables
     * @param stat
     *            a string refering to which stat you want to print out. Choices
     *            are: "avg", "min", and "max".
     */
    private void outputFloorToFloorTables(FloorStats[][] fStats, String stat) {
        String upperCaseStat = stat.toUpperCase();
        System.out.println();
        System.out.println(upperCaseStat
                + " Ride Time from Floor to Floor by Person (in seconds).");
        printHorizontalLn(117);

        String columnStart = String.format("%-5s", "Floor");
        StringBuilder columnHeading = new StringBuilder(columnStart);
        for (int i = 0; i < fStats.length; i++) {
            String str = String.format("%7d", i + 1);
            columnHeading.append(str);
        }
        System.out.println(columnHeading);

        // Iterate through the entire list of floor stats and print them out
        for (int i = 0; i < fStats.length; i++) {
            String rowStart = String.format("%5d", i + 1);
            StringBuilder row = new StringBuilder(rowStart);
            for (int j = 0; j < fStats[i].length; j++) {
                String value = null;
                FloorStats f = fStats[i][j];
                // This is where the start param comes into play
                // This allows this code to be a little more flexible.
                // This also allows for the single storage and printing of the
                // three tables
                // required
                if (f == null) {
                    value = "X";
                } else if (stat.equals("avg")) {
                    value = Long.toString(f.getAverageWaitTime());
                } else if (stat.equals("min")) {
                    value = Long.toString(f.getMinWaitTime());
                } else if (stat.equals("max")) {
                    value = Long.toString(f.getMaxWaitTime());
                }

                row.append(String.format("%7s", value));
            }
            System.out.println(row);
        }

    }

    /**
     * Generate wait ride total time by person table
     */
    private void generateWaitRideTotalTimeByPersonTable() {
        ArrayList<Person> peoples = this.getPeople();

        System.out.printf("%-13s | %-10s | %-10s | %-10s | %-10s | %-10s %n",
                "Person", "Start Floor", "Destination Floor", "Wait Time",
                "Ride Time", "Total Time");
        for (Person p : peoples) {
            int pid = p.getPersonId();
            int startF = p.getStartFloor();
            int destF = p.getDestinationFloor();
            Long waitTime = (long) ((p.getElevatorEnterTime() - p
                    .getStartTime()) * 0.001);
            Long rideTime = (long) ((p.getFinishedTime() - p
                    .getElevatorEnterTime()) * 0.001);
            Long totalTime = waitTime + rideTime;
            System.out
                    .printf("%-7s %-5d | %8d %15d %15d %12d %12d%n", "Person",
                            pid, startF, destF, waitTime, rideTime, totalTime);
        }

    }

    /**
     * Generate average wait time by floor table
     */
    private void generateAverageWaitTimeByFloorTable() {
        ArrayList<FloorStats> floorStats = new ArrayList<FloorStats>();
        ArrayList<Person> peoples = this.getPeople();
        SimulationInformation infos = this.getInfo();
        // Create a new floor stats object for each floor
        for (int i = 1; i <= infos.numFloors; i++) {
            floorStats.add(new FloorStats(i));
        }
        // Iterate through the people add calculate average, min, and max wait
        // times
        for (Person p : peoples) {
            long deltaTimeInSeconds = (long) ((p.getElevatorEnterTime() - p
                    .getStartTime()) * 0.001);
            int startF = p.getStartFloor() - 1;
            floorStats.get(startF).addEntry(deltaTimeInSeconds);
        }
        System.out.printf("%-13s | %-10s | %-10s | %-10s%n", "Floor",
                "Average wait time", "Min wait time", "Max wait time");
        for (FloorStats fs : floorStats) {
            System.out.printf("%-7s %-5d | %10d  %13d  %13d%n", "Floor",
                    fs.getFloorNumber(), fs.getAverageWaitTime(),
                    fs.getMinWaitTime(), fs.getMaxWaitTime());
        }
    }

    /**
     * Simulation Information getInfo()
     * 
     * @return the info
     */
    public SimulationInformation getInfo() {
        return info;
    }

    /**
     * Private method for setting the information.
     * 
     * @param i
     *            the information from the simulation
     */
    public void setInfo(SimulationInformation i) {
        this.info = i;
    }

    /**
     * A public method for obtaining the people array
     * 
     * @return the people
     */
    private ArrayList<Person> getPeople() {
        return people;
    }

    /**
     * A private method for setting the people member
     * 
     * @param peps
     *            the new people array list
     */
    private void setPeople(ArrayList<Person> peps) {
        this.people = peps;
    }

    /**
     * Outputs a horizontal to the console.
     * 
     * @param myLineLen
     *            the length of a line
     */
    public void printHorizontalLn(int myLineLen) {
        for (int i = 0; i <= myLineLen; i++) {
            System.out.print("-");
        }
        System.out.println();

    }

}
