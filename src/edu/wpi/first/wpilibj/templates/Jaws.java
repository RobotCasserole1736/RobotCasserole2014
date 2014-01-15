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
    
    public Jaws(int bottomJawLeftSolenoidId, int bottomJawRightSolenoidId, int topJawSolenoidId, int rollerTalonId)
    {
        bottomJawLeftSolenoid = new Solenoid(bottomJawLeftSolenoidId);
        bottomJawRightSolenoid = new Solenoid(bottomJawRightSolenoidId);
        topJawSolenoid = new Solenoid(topJawSolenoidId);
        rollerTalon = new Talon(rollerTalonId);
        
    }
}