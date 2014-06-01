package simulator.stats;

/**
 * Description: Floor Statistics
 * This class is designed to encapsulate information about the floor.
 * It should not be used outside the package.
 *
 * @author Patrick Stein
 * @author Chris Kurn
 * @since Version 1.0 - Spring Quarter 2014
 */
class FloorStats {
    
    private int floorNumber;
    private double averageWaitTime;
    private int numEntries = 1;
    private double minWaitTime = Double.MAX_VALUE;
    private double maxWaitTime = Double.MIN_VALUE;
    private int columnFloorNum;
    private int rowFloorNum;
    
   /**
    * Floor stats 
    * @param fn
    */
    FloorStats(int fn){
        this.floorNumber = fn;
    }
    
    FloorStats(int rowFloorNumber, int columnFloorNumber) {
        this.columnFloorNum = columnFloorNumber;
        this.rowFloorNum = rowFloorNumber;
    }

    void addEntry(double newEntry){
        this.updateAverage(newEntry);
        this.setMinWaitTime(newEntry);
        this.setMaxWaitTime(newEntry);
    }
    
    /**
     * 
     * @param numEntry
     */
    void updateAverage(double numEntry){
        this.averageWaitTime += numEntry;
        this.numEntries++;
    }
    /**
     * 
     * @return
     */
    double getAverageWaitTime(){
        return this.averageWaitTime/numEntries;
    }
    
    
    /**
     * @return the maxWaitTime
     */
    double getMaxWaitTime() {
        return maxWaitTime;
    }
    /**
     * @param maxWaitTime the maxWaitTime to set
     */
    void setMaxWaitTime(double newMax) {
        double currentMax = this.getMaxWaitTime();
        if(newMax > currentMax){
            this.maxWaitTime = newMax;
        }
    }
    /**
     * Returns the min wait time.
     * @return the minWaitTime
     */
    double getMinWaitTime() {
        return minWaitTime;
    }
    /**
     * Set min wait time
     * @param minWaitTime the minWaitTime to set
     */
    void setMinWaitTime(double newMin) {
        double currentMin = this.getMinWaitTime();
        if(newMin < currentMin){
            this.minWaitTime = newMin;
        }
    }

    /**
     * Returns the floor number
     * @return the floorNumber
     */
    int getFloorNumber() {
        return floorNumber;
    }

    /**
     * @return the rowFloorNum
     */
    int getRowFloorNum() {
        return rowFloorNum;
    }

    /**
     * @return the columnFloorNum
     */
    int getColumnFloorNum() {
        return columnFloorNum;
    }



}
