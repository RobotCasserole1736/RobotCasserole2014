/*
 * Class to handle all inputs, states, and transitions of the shooter/intake assembly.
 * 2014 FRC Team 1736 Robot Casserole
 */
package edu.wpi.first.wpilibj.templates;

import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.Joystick;

public class Jaws {

    Solenoid bottomJawLeftSolenoid, bottomJawRightSolenoid, topJawSolenoid, shooterLeftSolenoid, shooterRightSolenoid;
    Talon rollerTalon;
    Joystick joy;
    //Jaw position - false is default and down position, true is up.
    public boolean jawPos = false;
    //Jaw angle - false is default and closed position, true is open.
    public boolean jawAng = false;
    final int rollerIn = 1;
    final int rollerOut = -1;
    final int rollerIdle = 0;

    public Jaws(int bottomJawLeftSolenoidId, int bottomJawRightSolenoidId, int topJawSolenoidId, int rollerTalonId, int shooterLeftSolenoidId, int shooterRightSolenoidId, Joystick joy) {
        bottomJawLeftSolenoid = new Solenoid(bottomJawLeftSolenoidId);
        bottomJawRightSolenoid = new Solenoid(bottomJawRightSolenoidId);
        topJawSolenoid = new Solenoid(topJawSolenoidId);
        shooterLeftSolenoid = new Solenoid(shooterLeftSolenoidId);
        shooterRightSolenoid = new Solenoid(shooterRightSolenoidId);
        rollerTalon = new Talon(rollerTalonId);
        this.joy = joy;
    }

    public void lowerJaw() {
        if (jawPos) {
            bottomJawLeftSolenoid.set(false);
            bottomJawRightSolenoid.set(false);
            jawPos = false;
        }
    }

    public void raiseJaw() {
        if (!jawPos) {
            //True is activated and false is not...as far as I know
            bottomJawLeftSolenoid.set(true);
            bottomJawRightSolenoid.set(true);
            jawPos = true;
        }
    }

    public void openJaw() {
        if (!jawAng) {
            topJawSolenoid.set(true);
            jawAng = true;
        }
    }

    public void closeJaw() {
        if (jawAng) {
            topJawSolenoid.set(false);
            jawAng = false;
        }
    }

    ////////Replaced by setRoller////////
//    public void intakeRoller()
//    {
//        rollerTalon.set(1);
//    }
//    
//    public void outRoller()
//    {
//        rollerTalon.set(-1);
//    }
//    
//    public void offRoller()
//    {
//        rollerTalon.set(0);
//    }
    public void setRoller(double dir) {
        rollerTalon.set(dir);
    }

    public void shoot() {
        shooterLeftSolenoid.set(true);
        shooterRightSolenoid.set(true);
    }

    public void resetShooter() {
        shooterLeftSolenoid.set(false);
        shooterRightSolenoid.set(false);
    }
    public void floorIntake() {
        lowerJaw();
        closeJaw();
        setRoller(rollerIn);
    }

    public void humanIntake() {
        raiseJaw();
        openJaw();
        setRoller(rollerIdle);
    }

    public void ballPossession() {
        raiseJaw();
        closeJaw();
        setRoller(rollerIdle);
    }

    public void floorPass() {
        lowerJaw();
        closeJaw();
        setRoller(rollerOut);
    }

    public void highPass() {
        raiseJaw();
        closeJaw();
        setRoller(rollerOut);
    }

    public void highThrow() {
        raiseJaw();
        openJaw();
        setRoller(rollerOut);
    }

    public void update() {
        if (joy.getRawAxis(3) < -0.5) {
            setRoller(1);
        } else if (joy.getRawAxis(3) > 0.5) {
            setRoller(-1);
        } else {
            setRoller(0);
        }
        if (joy.getRawButton(1)) {
            openJaw();
        }
        if (joy.getRawButton(2)) {
            closeJaw();
        }
        if (joy.getRawAxis(2) < -0.5) {
            raiseJaw();
        }
        if (joy.getRawAxis(2) > 0.5) {
            lowerJaw();
        }
        if (joy.getRawButton(5))
        {
           resetShooter(); 
        }
        if (joy.getRawButton(6))
        {
            shoot();
        }
    }
}
