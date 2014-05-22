/**
 * 
 */
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
 * @author Patrick
 *
 */
public class BuildingTests {

    /** The info. */
    private SimulationInformation info;
    
    /** The sim. */
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
    
    @Test
    public void personEnterValidFloor() throws IllegalParamException, InvalidFloorException{
        
        Person p = PersonFactory.build(1, 2);
        Building.getInstance().enterFloor(p, p.getDestinationFloor());
        
        boolean buildingEmpty = !Building.getInstance().isEmpty();
        if(buildingEmpty == true){
            fail();
        }
        
    }
    
   
    @Test(expected = InvalidFloorException.class)
    public void personEnterInvalidFloor() throws IllegalParamException, InvalidFloorException {
        Person p = PersonFactory.build(1, 10);
        Building.getInstance().enterFloor(p, -10);
        
    }
    @Test
    public void testCorrectFloorNumber(){
        int correctFloor = info.numFloors;
        
        assertEquals("Number of floors should be equal",correctFloor,Building.getInstance().getNumberOfFloors());
    }
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
