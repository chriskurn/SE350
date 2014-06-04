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

    public static StatisticsGenerator build(ArrayList<Person> p,
            SimulationInformation info) {
        return new StatisticsGeneratorImpl(p, info);
    }

}
