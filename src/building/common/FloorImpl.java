package building.common;

import java.util.ArrayList;
import java.util.Iterator;

import elevator.common.ElevatorDirection;

import simulator.Simulator;
import simulator.common.IllegalParamException;

/**
 * Description: Floor interface class
 * 
 * @author Patrick Stein
 * @author Chris Kurn
 * @since Version 1.0 - Spring Quarter 2014
 * @see package building.common
 * @see import java.util.ArrayList;
 * @see import simulator.Simulator;
 * @see import simulator.common.IllegalParamException;
 */

public class FloorImpl implements Floor {

    private int myFloor;
    private ArrayList<Person> floorPeoples = new ArrayList<Person>();
    private ArrayList<Person> finishedPeople = new ArrayList<Person>();

    public FloorImpl(int floorNumber) {
        this.setMyFloor(floorNumber);
    }

    /*
     * (non-Javadoc)
     * 
     * @see building.common.Floor#getFloor()
     */
    @Override
    public int getFloor() {
        return this.myFloor;
    }

    /*
     * (non-Javadoc)
     * 
     * @see building.common.Floor#enterFloor(building.common.Person)
     */
    @Override
    public int enterFloor(Person p) throws IllegalParamException {
        int thisFloor = getFloor();
        
        if (p == null) {
            throw new IllegalParamException(
                    String.format("Cannot add a null to the person array on floor %d.",getFloor()));
        }
        
        Simulator.getInstance().logEvent(String.format("Person %d has entered floor: %d",p.getPersonId(),this.getFloor()));
        
        //Then this person is done moving yay!
        if(p.getDestinationFloor() == thisFloor){
            synchronized (this) {
                this.getFinishedPeople().add(p);
                //send message to simulation
                int pid = p.getPersonId();
                String personFinished = String.format("Person %d has finished by entering his destination floor %d.", pid,thisFloor);
                Simulator.getInstance().logEvent(personFinished);
                Simulator.getInstance().finishedExecution(pid);
            }
        }else{
            //else he needs to be added to the already waiting people
            synchronized (this) {
                this.getFloorPeople().add(p);
            }
        }
        return getFloor();
    }

    /*
     * (non-Javadoc)
     * 
     * @see building.common.Floor#leaveFloor(building.common.Person)
     */
    @Override
    public ArrayList<Person> leaveFloor(ElevatorDirection dir) {
        int thisFloor = getFloor();
        ArrayList<Person> leavingPeople = new ArrayList<Person>();
        Iterator<Person> p = this.getFloorPeople().iterator();
        while(p.hasNext()){
            Person person = p.next();
            //If the direction is up and the destination floor is above this floor
            //Or if the direction is down and the destion floor is above this floor
            //Then we are good to leave the floor
            if(dir == ElevatorDirection.UP && person.getDestinationFloor() > thisFloor ||
                    dir == ElevatorDirection.DOWN && person.getDestinationFloor() < thisFloor || 
                    dir == ElevatorDirection.IDLE){
                leavingPeople.add(person);
                p.remove();
            }
            //TODO error handle
            
        }

        return leavingPeople;
    }

    /**
     * @param myFloor
     *            the myFloor to set
     */
    private void setMyFloor(int floor) {
        // TODO Auto-generated method stub
        // error handling
        this.myFloor = floor;
    }
    private ArrayList<Person> getFloorPeople() {
        return this.floorPeoples;
    }
    private ArrayList<Person> getFinishedPeople(){
        return this.finishedPeople;
    }

}
