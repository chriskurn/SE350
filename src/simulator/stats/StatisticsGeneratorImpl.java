package simulator.stats;

import java.util.ArrayList;

import simulator.Simulator;
import simulator.common.SimulationInformation;
import building.common.Person;

public class StatisticsGeneratorImpl implements StatisticsGenerator{
    
    
    private ArrayList<Person> people;
    private SimulationInformation info;
    

    public StatisticsGeneratorImpl(ArrayList<Person> p,
            SimulationInformation info) {
        setInfo(info);
        setPeople(p);
    }

    @Override
    public void generateStats() {
        Simulator.getInstance().logEvent("Generating Average/Min/Max Wait time by floor (in seconds).");
        averageWaitTimeByFloor();
        Simulator.getInstance().logEvent("Generating Wait/Ride/Total Time by Person");
        waitRideTotalTimeByPerson();
        
    }
    
    
    private void waitRideTotalTimeByPerson() {
        ArrayList<Person> peoples = this.getPeople();
        
        
    }

    private void averageWaitTimeByFloor(){
        ArrayList<FloorStats> floorStats = new ArrayList<FloorStats>();
        ArrayList<Person> peoples = this.getPeople();
        SimulationInformation infos = this.getInfo();
        //Create a new floor stats object for each floor
        for(int i = 1; i <= info.numFloors; i++){
            floorStats.add(new FloorStats(i));
        }
        //Iterate through the people add calculate average, min, and max wait times
        for(Person p: peoples){
            double deltaTimeInSeconds = (p.getElevatorEnterTime() - p.getStartTime()) * 0.001;
            int startF = p.getStartFloor();
            floorStats.get(startF).addEntry(deltaTimeInSeconds);
        }
        String columnHeadings = String.format("%-5s   | %-10s | %-10s | %-10s","Floor", "Average wait time", "Min wait time", "Max wait time");
        Simulator.getInstance().logEvent(columnHeadings);
        for(FloorStats fs: floorStats){
            String row = String.format("%-5s %d | %10d  %13d  %13d","Floor",
                    fs.getFloorNumber(),fs.getAverageWaitTime(),
                    fs.getMinWaitTime(),fs.getMaxWaitTime());
            Simulator.getInstance().logEvent(row);
        }
    }

    /**
     * @return the info
     */
    public SimulationInformation getInfo() {
        return info;
    }

    /**
     * @param info the info to set
     */
    public void setInfo(SimulationInformation i) {
        this.info = i;
    }

    /**
     * @return the people
     */
    public ArrayList<Person> getPeople() {
        return people;
    }

    /**
     * @param people the people to set
     */
    public void setPeople(ArrayList<Person> peps) {
        this.people = peps;
    }
    

}
