package building.testing;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import elevator.common.ElevatorDirection;
import elevator.common.InvalidFloorException;

import building.Building;
import building.common.Person;
import building.common.PersonFactory;

import simulator.Simulator;
import simulator.common.IllegalParamException;
import simulator.common.NullFileException;
import simulator.common.SimulationInformation;

/**
 * Description: Building Tests.
 * 
 * The building is a singleton facade responsible for managing the floors.
 * It also has methods that allow for movement of people to different floors and people leaving a floor.
 * 
 * @author Patrick Stein
 * @author Chris Kurn
 * @since Version 1.0 - Spring Quarter 2014
 */
public class BuildingTests {

    /** The simulator info. */
    private SimulationInformation info;
    
    /** The simulator */
    private Simulator sim;

    /**
     * Load simulation.
     *
     * @throws NullFileException the null file exception
     * @throws IllegalParamException the illegal param exception
     * @throws IOException Signals that an I/O exception has occurred.
     */
    @Before
    public void loadSimulation() throws NullFileException,
            IllegalParamException, IOException {
        Simulator mySim = Simulator.getInstance();
        mySim.buildSimulator("simInput.properties");
        this.info = mySim.getSimulationInfo();
        this.sim = mySim; 
    }
    
    /**
     * personEnterValidFloor()
     * @throws IllegalParamException
     * @throws InvalidFloorException
     */
    @Test
    public void personEnterValidFloor() throws IllegalParamException, InvalidFloorException{
        
        Person p = PersonFactory.build(1, 2);
        Building.getInstance().enterFloor(p, p.getDestinationFloor());
        
        boolean buildingEmpty = !Building.getInstance().isEmpty();
        if(buildingEmpty == true){
            fail();
        }
        
    }
    
   /**
    * personEnterInvalidFloor()
    * @throws IllegalParamException
    * @throws InvalidFloorException
    */
    @Test(expected = InvalidFloorException.class)
    public void personEnterInvalidFloor() throws IllegalParamException, InvalidFloorException {
        Person p = PersonFactory.build(1, 10);
        Building.getInstance().enterFloor(p, -10);
        
    }
    
    /**
     * testCorrectFloorNumber()
     */
    @Test
    public void testCorrectFloorNumber(){
        int correctFloor = info.numFloors;
        
        assertEquals("Number of floors should be equal",correctFloor,Building.getInstance().getNumberOfFloors());
    }
    
    /**
     * loadPeopleCorrectlyUp()
     * @throws IllegalParamException
     * @throws InvalidFloorException
     */
    @Test
    public void loadPeopleCorrectlyUp() throws IllegalParamException, InvalidFloorException{
        
        int startFloor = 3;
        
        Person p1 = PersonFactory.build(startFloor, 4);
        Person p2 = PersonFactory.build(startFloor, 8);
        ArrayList<Person> myPeople = new ArrayList<Person>();
        myPeople.add(p1);
        myPeople.add(p2);
        
        Building.getInstance().enterFloor(p1, p1.getStartFloor());
        Building.getInstance().enterFloor(p2, p2.getStartFloor());
        
        
        ArrayList<Person> buildingPeople = Building.getInstance().loadPeople(startFloor, ElevatorDirection.UP);
        
        assertEquals("People arrays should be equal.",myPeople,buildingPeople);
    }
    
    
}
