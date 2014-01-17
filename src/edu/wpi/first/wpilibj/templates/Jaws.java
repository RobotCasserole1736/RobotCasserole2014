/*
 * Class to handle all inputs, states, and transitions of the shooter/intake assembly.
 * 2014 FRC Team 1736 Robot Casserole
 */

package edu.wpi.first.wpilibj.templates;

import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.Joystick;

public class Jaws {
    Solenoid bottomJawLeftSolenoid, bottomJawRightSolenoid, topJawSolenoid, shooterLeftSolenoid, shooterRightSolenoid ;
    Talon rollerTalon;
    Joystick joy;
    //Jaw position - false is default and down position, true is up.
    public boolean jawPos = false;
    //Jaw angle - false is default and closed position, true is open.
    public boolean jawAng = false;
    public boolean shoot = false;
    public Jaws(int bottomJawLeftSolenoidId, int bottomJawRightSolenoidId, int topJawSolenoidId, int rollerTalonId, Joystick joy, int shooterLeftSolenoidId, int shooterRightSolenoidId)
    {
        bottomJawLeftSolenoid = new Solenoid(bottomJawLeftSolenoidId);
        bottomJawRightSolenoid = new Solenoid(bottomJawRightSolenoidId);
        topJawSolenoid = new Solenoid(topJawSolenoidId);
        rollerTalon = new Talon(rollerTalonId);
        shooterLeftSolenoid = new Solenoid(shooterLeftSolenoidId);
        shooterRightSolenoid = new Solenoid(shooterRightSolenoidId);
        
        this.joy = joy;
    }
    
    public void lowerJaw()
    {
        if(jawPos)
        {
            bottomJawLeftSolenoid.set(false);
            bottomJawRightSolenoid.set(false);
            jawPos = false;
        }
    }
    
    public void raiseJaw()
    {
        if(!jawPos)
        {
            //True is activated and false is not...as far as I know
            bottomJawLeftSolenoid.set(true);
            bottomJawRightSolenoid.set(true);
            jawPos = true;
        }
    }
    
    public void openJaw()
    {
        if(!jawAng)
        {
            topJawSolenoid.set(true);
            jawAng = true;
        }
    }
    
    public void closeJaw()
    {
        if(jawAng)
        {
            topJawSolenoid.set(false);
            jawAng = false;
        }
    }
    
    public void intakeRoller()
    {
        rollerTalon.set(1);
    }
    
    public void outRoller()
    {
        rollerTalon.set(-1);
    }
    
    public void rollerOff()
    {
        rollerTalon.set(0);
    }
    public void shoot()
    {
        if (shoot)
            shooterLeftSolenoid.set(true);
            shooterRightSolenoid.set(true);
            shoot = false;
    }
    public void shooterReset()
    {
        if (!shoot)
            shooterLeftSolenoid.set(false);
            shooterRightSolenoid.set(false);
    }
    public void floorIntake()
    {
        lowerJaw();
        jawAng = false ;
        rollerTalon.set(1);
    }
    public void humanIntake()
    {
        raiseJaw();
        jawAng = true ;
        rollerTalon.set(0);
    }
    public void possession()
    {
        raiseJaw();
        jawAng = false ;
        rollerTalon.set(0);
    }
    public void passFloor()
    {
        lowerJaw();
        jawAng = false ;
        rollerTalon.set(-1);
    }
    public void passHigh()
    {
        raiseJaw();
        jawAng = false ;
        rollerTalon.set(1);
    }
}