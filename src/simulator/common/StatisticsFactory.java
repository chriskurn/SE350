package simulator.common;

import java.util.ArrayList;

import building.common.Person;

import simulator.stats.StatisticsGenerator;
import simulator.stats.StatisticsGeneratorImpl;

public class StatisticsFactory {
    
    public static StatisticsGenerator build(ArrayList<Person> p, SimulationInformation info){
        return new StatisticsGeneratorImpl(p,info);
    }

}
