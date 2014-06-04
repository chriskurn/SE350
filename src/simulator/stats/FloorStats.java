package simulator.stats;

/**
 * Description: Floor Statistics This class is designed to encapsulate
 * information about the floor. It should not be used outside the package.
 * 
 * @author Patrick Stein
 * @author Chris Kurn
 * @since Version 1.0 - Spring Quarter 2014
 */
class FloorStats {

    private int floorNumber;
    private long averageWaitTime;
    private int numEntries;
    private long minWaitTime;
    private long maxWaitTime;
    private int columnFloorNum;
    private int rowFloorNum;

    private boolean changedMin = false;

    /**
     * Constructor that initializes the values for a new FloorStats object.
     * 
     * @param fn
     *            an integer that represents the appropriate floor number for
     *            these stats.
     */
    FloorStats(int fn) {
        floorNumber = fn;
    }

    /**
     * Constructor that initializes the values for a column Floor Number and a
     * row floor number. Note: This only makes sense by looking at the tables
     * required for project part 4.
     * 
     * @param rowFloorNumber
     *            a row number that also represents a floor number
     * @param columnFloorNumber
     *            a column number that also represents a floor number
     */
    FloorStats(int rowFloorNumber, int columnFloorNumber) {
        columnFloorNum = columnFloorNumber;
        rowFloorNum = rowFloorNumber;
    }

    /**
     * This method is designed to encapsulate if someone were to add a new time
     * entry. It will call all of the appropriate methods to make sure this
     * class is up-to-date.
     * 
     * @param newEntry
     *            a long representing a time measurement (in milliseconds).
     */
    void addEntry(long newEntry) {
        updateAverage(newEntry);
        setMinWaitTime(newEntry);
        setMaxWaitTime(newEntry);
    }

    /**
     * updateAverage takes a new time entry and updates the overall average for
     * this floor.
     * 
     * @param numEntry
     *            a long representing a time measurement (in milliseconds).
     */
    void updateAverage(long numEntry) {
        averageWaitTime += numEntry;
        numEntries++;
    }

    /**
     * Returns the average travel time to this floor
     * 
     * @return returns a long representing the average travel time, in
     *         milliseconds, to this floor.
     */
    long getAverageWaitTime() {
        return averageWaitTime / getNumEntries();
    }

    /**
     * Returns the max wait time for this floor
     * 
     * @return the maxWaitTime
     */
    long getMaxWaitTime() {
        return maxWaitTime;
    }

    /**
     * Sets the max wait time for this floor
     * 
     * @param maxWaitTime
     *            the maxWaitTime to set
     */
    void setMaxWaitTime(long newMax) {
        long currentMax = getMaxWaitTime();
        if (newMax > currentMax) {
            maxWaitTime = newMax;
        }
    }

    /**
     * Returns the min wait time.
     * 
     * @return the minWaitTime
     */
    long getMinWaitTime() {
        return minWaitTime;
    }

    /**
     * Set min wait time
     * 
     * @param minWaitTime
     *            the minWaitTime to set
     */
    void setMinWaitTime(long newMin) {

        long currentMin = getMinWaitTime();
        // Included to make sure that Long.MAX_Value is not printed out for
        // every value
        if (changedMin == false) {
            currentMin = Long.MAX_VALUE;
        }
        // Check to see if it is less than
        if (newMin < currentMin) {
            minWaitTime = newMin;
            changedMin = true;
        }
    }

    /**
     * Returns the floor number
     * 
     * @return the floorNumber
     */
    long getFloorNumber() {
        return floorNumber;
    }

    /**
     * Gets the row floor number member
     * 
     * @return the rowFloorNum
     */
    long getRowFloorNum() {
        return rowFloorNum;
    }

    /**
     * Gets the column floor number member
     * 
     * @return the columnFloorNum
     */
    long getColumnFloorNum() {
        return columnFloorNum;
    }
    /**
     * 
     * @return
     */
    long getNumEntries(){
        return numEntries;
    }

}
