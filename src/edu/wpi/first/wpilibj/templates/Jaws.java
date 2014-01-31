/*
 * Class to handle all inputs, states, and transitions of the shooter/intake assembly.
 * 2014 FRC Team 1736 Robot Casserole
 */

package edu.wpi.first.wpilibj.templates;

import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.Talon;

public class Jaws {
    Solenoid bottomJawLeftSolenoid, bottomJawRightSolenoid, topJawSolenoid;
    Talon rollerTalon;
    //Jaw position - false is default and down position, true is up.
    public boolean isUp = false;
    //Jaw angle - false is default and closed position, true is open.
    public boolean isOpen = false;
    
    public double stateTimers[] = { };
    // a state is basically a named integer const that influences decision-making. 
    // they're assigned to these vars accordingly
    public int curState; 
    public int lastState;
    public int nextState;

    public Jaws(int bottomJawLeftSolenoidId, int bottomJawRightSolenoidId, int topJawSolenoidId, int rollerTalonId) {
        bottomJawLeftSolenoid = new Solenoid(bottomJawLeftSolenoidId);
        bottomJawRightSolenoid = new Solenoid(bottomJawRightSolenoidId);
        topJawSolenoid = new Solenoid(topJawSolenoidId);
        rollerTalon = new Talon(rollerTalonId);
    }
    
    public void lowerJaw() {
        if(isUp) {
            bottomJawLeftSolenoid.set(false);
            bottomJawRightSolenoid.set(false);
            isUp = false;
        }
    }
    
    public void raiseJaw() {
        if(!isUp) {
            //True is activated and false is not...as far as I know
            bottomJawLeftSolenoid.set(true);
            bottomJawRightSolenoid.set(true);
            isUp = true;
        }
    }
    
    public void openJaw() {
        if(!isOpen) {
            topJawSolenoid.set(true);
            isOpen = true;
        }
    }
    
    public void closeJaw() {
        if(isOpen) {
            topJawSolenoid.set(false);
            isOpen = false;
        }
    }
//  !!!!!!!!!!!!!!  DEPRECATED  !!!!!!!!!!!!!!!!!!!!!!!!!!!
//    public void intakeRoller() {
//        rollerTalon.set(1);
//    }
//    
//    public void outRoller() {
//        rollerTalon.set(-1);
//    }
//    
//    public void rollerOff() {
//        rollerTalon.set(0);
//    }
//~~~~~~~~~~~!!!ARGS ARE BETTER THAN THIS!!!
    
    public void setRoller(double dir) {
        rollerTalon.set(dir);
    }
    public double getRoller() {
        return rollerTalon.get();
    }
    
    public boolean hasPossession() {
        return false;
    }
    
    public void statesPeriodic() {
        if (isUp && !isOpen && getRoller()==0 /* && not shooting */ ) {
            
        }
    }
    static class States { 
        static final int INIT_POS = 1;
        static final int AUTO_SHOOT = 2;
        static final int FLOOR_IN = 3;
        static final int CATCH_POS = 4;
        static final int POSSESSION = 5;     
    }
}