package elevator.control;

import java.util.ArrayList;

import simulator.Simulator;
import simulator.common.SimulationInformation;

import elevator.movement.Elevator;

/**
 * Description: class ElevatorController
 * @author Chris Kurn, Patrick Stein
 * @since Version 1.0 - Spring Quarter 2014
 * @see package elevator.control
 */

public class ElevatorController {
    
    private volatile static ElevatorController instance;
    private ArrayList<Elevator> elevators;
    
    
    private ElevatorController(){
        Simulator sim = Simulator.getInstance();
        SimulationInformation simInfo = sim.getSimulationInfo();
        this.createElevators(simInfo);
    }
    
    /**
     * The singleton method for creating and returning a single instance of the ElevatorController
     * @return returns the one Elevator controller in existence. 
     */
    public static ElevatorController getInstance(){
        
        //Double locking for multi-threaded environment
        if(instance == null){
            synchronized(ElevatorController.class){
                if(instance == null){
                    instance = new ElevatorController();
                }
            }
        }
        return instance;
        
        
    }
    /**
     * Private method to encapsulate the elevator creation logic.
     * @param simInfo 
     */
    private void createElevators(SimulationInformation simInfo){
        ArrayList<Elevator> eles = this.getElevators();
        
        for(int i = 0; i < simInfo.numElevators; i++){
            
        }
        
        
    }

    /**
     * Private method for acquiring all of the elevators
     * @return
     */
    private ArrayList<Elevator> getElevators() {
        return this.elevators;
    }

}
