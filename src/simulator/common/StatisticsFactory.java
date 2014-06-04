package simulator.common;

import java.util.ArrayList;

import simulator.stats.StatisticsGenerator;
import simulator.stats.StatisticsGeneratorImpl;
import building.common.Person;

/**
 * Description: Statistics Factory class.
 * 
 * @author Patrick Stein
 * @author Chris Kurn
 * @since Version 1.0 - Spring Quarter 2014
 */

public class StatisticsFactory {

    /**
     * Returns a new stats generator delegate to be generate stats on Person
     * objects.
     * 
     * @param p
     *            an arraylist of person objects that have gone through the
     *            simulation
     * @param info
     *            The information pertaining to the current simulation
     * @return a new StatisticsGeneratorImpl based on the parameters above
     */
    public static StatisticsGenerator build(ArrayList<Person> p,
            SimulationInformation info) {
        return new StatisticsGeneratorImpl(p, info);
    }

}
