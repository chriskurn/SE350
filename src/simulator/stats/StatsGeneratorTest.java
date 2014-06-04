package simulator.stats;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class StatsGeneratorTest {

    @Test
    public void floorStatsCreationConstructorOneTest() {
        int actualFn = 5;
        FloorStats fs = new FloorStats(actualFn);
        assertEquals("The floor numbers do not match", fs.getFloorNumber(),
                actualFn);
    }

    @Test
    public void floorStatsCreationConstructorTwoTest() {
        int actualRow = 5;
        int actualCol = 10;
        FloorStats fs = new FloorStats(actualRow, actualCol);
        assertEquals("The row numbers do not match.", fs.getRowFloorNum(),
                actualRow);
        assertEquals("The column numbers do not match.",
                fs.getColumnFloorNum(), actualCol);
    }

    @Test
    public void floorStatsUpdateTest() {
        int actualFn = 5;
        FloorStats fs = new FloorStats(actualFn);

        long testTimeOne = 100;
        long testTimeTwo = 50;

        long realAvg = 75;
        fs.addEntry(testTimeOne);
        fs.addEntry(testTimeTwo);
        System.out.println(fs.getAverageWaitTime());

        assertEquals("The average times do not match", fs.getAverageWaitTime(),
                realAvg);
        assertEquals("The maximum times do not match", fs.getMaxWaitTime(),
                testTimeOne);
        assertEquals("The minimum times do not match", fs.getMinWaitTime(),
                testTimeTwo);

    }
}
