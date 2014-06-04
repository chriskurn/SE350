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
    private int numEntries = 1;
    private long minWaitTime;
    private long maxWaitTime;
    private int columnFloorNum;
    private int rowFloorNum;

    private boolean changedMin = false;

    /**
     * Floor stats
     * 
     * @param fn
     */
    FloorStats(int fn) {
        this.floorNumber = fn;
    }

    FloorStats(int rowFloorNumber, int columnFloorNumber) {
        this.columnFloorNum = columnFloorNumber;
        this.rowFloorNum = rowFloorNumber;
    }

    void addEntry(long newEntry) {
        this.updateAverage(newEntry);
        this.setMinWaitTime(newEntry);
        this.setMaxWaitTime(newEntry);
    }

    /**
     * updateAverage
     * 
     * @param numEntry
     */
    void updateAverage(long numEntry) {
        this.averageWaitTime += numEntry;
        this.numEntries++;
    }

    /**
     * getAverageWaitTime()
     * 
     * @return
     */
    long getAverageWaitTime() {
        return this.averageWaitTime / numEntries;
    }

    /**
     * getMaxWaitTime()
     * 
     * @return the maxWaitTime
     */
    long getMaxWaitTime() {
        return maxWaitTime;
    }

    /**
     * setMaxWaitTime
     * 
     * @param maxWaitTime
     *            the maxWaitTime to set
     */
    void setMaxWaitTime(long newMax) {
        long currentMax = this.getMaxWaitTime();
        if (newMax > currentMax) {
            this.maxWaitTime = newMax;
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

        long currentMin = this.getMinWaitTime();
        // Included to make sure that Long.MAX_Value is not printed out for
        // every value
        if (changedMin == false) {
            currentMin = Long.MAX_VALUE;
        }
        // Check to see if it is less than
        if (newMin < currentMin) {
            this.minWaitTime = newMin;
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
     * getRowFloorNum()
     * 
     * @return the rowFloorNum
     */
    long getRowFloorNum() {
        return rowFloorNum;
    }

    /**
     * getColumnFloorNum()
     * 
     * @return the columnFloorNum
     */
    long getColumnFloorNum() {
        return columnFloorNum;
    }

}
