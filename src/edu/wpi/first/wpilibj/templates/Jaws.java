/*
 * Class to handle all inputs, states, and transitions of the shooter/intake assembly.
 * 2014 FRC Team 1736 Robot Casserole
 */

package edu.wpi.first.wpilibj.templates;

import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Timer;

public class Jaws {
    Solenoid bottomJawLeftSolenoid, bottomJawRightSolenoid, topJawSolenoid, LshooterSolenoid, RshooterSolenoid;
    Talon rollerTalon;
    
    int lastState = State.highPossession;
    int currentState = State.highPossession;
    int desiredState = State.highPossession;
    
    Joystick shooterJoy;
    
    //Array of time in seconds that it takes to complete each state
    double[] stateTimers = {0, 0, 0, 0, 0, 3, 3.5, 5, 3};
    double timeInState = 0;
    double lastTime = 0;
    
    //Jaw position - false is default and down position, true is up.
    public boolean jawPos = false;
    //Jaw angle - false is default and closed position, true is open.
    public boolean jawAng = false;
    
    public Jaws(int bottomJawLeftSolenoidId, int bottomJawRightSolenoidId, int topJawSolenoidId, int rollerTalonId, int RshooterSolenoidId, int LshooterSolenoidId, Joystick shooterJoy)
    {   
        RshooterSolenoid = new Solenoid(RshooterSolenoidId);
        LshooterSolenoid = new Solenoid(LshooterSolenoidId);
        bottomJawLeftSolenoid = new Solenoid(bottomJawLeftSolenoidId);
        bottomJawRightSolenoid = new Solenoid(bottomJawRightSolenoidId);
        topJawSolenoid = new Solenoid(topJawSolenoidId);
        rollerTalon = new Talon(rollerTalonId);
    }
    
    static class State{
        static int defense = 0;
        static int floorIntake = 1;
        static int humanIntake = 2;
        static int lowPossession = 3;
        static int highPossession = 4;
        static int floorPass = 5;
        static int highPass = 6;
        static int trussPass = 7;
        static int shoot = 8;
        static int shooterReset = 9;
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
    
    public void update()
    {
        double currentTime = Timer.getFPGATimestamp();
        timeInState += currentTime - lastTime;
        lastTime = currentTime;
        boolean needStateTransition = false;
        if(currentState == State.shoot)
        {
            desiredState = State.shooterReset;
            needStateTransition = true;
        }
        else if(currentState == State.highPass) 
        {
            desiredState = State.defense;
            needStateTransition = true;
        }
        else if(currentState == State.floorPass) 
        {
            desiredState = State.floorIntake;
            needStateTransition = true;
        } 
        else if(currentState == State.trussPass)
        {
            desiredState = State.shooterReset;
            needStateTransition = true;
        }
        else
        {
            desiredState = currentState;
        }
        if(!needStateTransition) 
        {
            //Set state from controller
            if(shooterJoy.getRawButton(1)) 
            {
                desiredState = State.floorPass;
            }
            else if(shooterJoy.getRawButton(4))
            {
                desiredState = State.highPass;
            }
            else if(shooterJoy.getRawButton(2))
            {
                desiredState = State.floorIntake;
            }
            else if(shooterJoy.getRawButton(3))
            {
                desiredState = State.humanIntake;
            }
            else if(shooterJoy.getRawButton(5))
            {
                desiredState = State.trussPass;
            }
            else if(shooterJoy.getRawButton(6))
            {
                desiredState = State.shoot;
            }
            else if(shooterJoy.getRawButton(9) && shooterJoy.getRawButton(10))
            {
                desiredState = State.defense;
            }
        }
        if(timeInState >= stateTimers[currentState])
        {
            if(desiredState == State.defense || desiredState == State.highPossession)
            {
                highPossession();
                currentState = State.defense;
                timeInState = 0;
            }
            else if(desiredState == State.floorIntake)
            {
                floorIntake();
                currentState = State.floorIntake;
                timeInState = 0;
            }
            else if(desiredState == State.floorPass)
            {
                floorPass();
                currentState = State.floorPass;
                timeInState = 0;
            }
            else if(desiredState == State.highPass)
            {
                highPass();
                currentState = State.highPass;
                timeInState = 0;
            }
            else if(desiredState == State.humanIntake)
            {
                humanIntake();
                currentState = State.highPossession;
                timeInState = 0;
            }
            else if(desiredState == State.lowPossession)
            {
                lowPossession();
                currentState = State.lowPossession;
                timeInState = 0;
            }
            else if(desiredState == State.shoot)
            {
                robotShoot();
                currentState = State.shoot;
                timeInState = 0;
            }
            else if(desiredState == State.shooterReset)
            {
                shooterReset();
                currentState = State.shooterReset;
                timeInState = 0;
            }
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
    
    public void offRoller()
    {
        rollerTalon.set(0);
    }
    
    public void shoot()
    {
        RshooterSolenoid.set(true);
        LshooterSolenoid.set(true);
    }
    
    public void shooterReset()
    {
        RshooterSolenoid.set(false);
        LshooterSolenoid.set(false);
    }
    
    public void floorIntake()
    {
        lowerJaw();
        closeJaw();
        intakeRoller();    
    }
    
    public void humanIntake()
    {
        raiseJaw();
        openJaw();
        offRoller();             
    }
    
    public void highPossession()
    {
        raiseJaw();
        closeJaw();
        offRoller();
    }
    
    public void lowPossession()
    {
        lowerJaw();
        closeJaw();
        offRoller();
    }
    
    public void floorPass()
    {
        lowerJaw();
        closeJaw();
        outRoller();
    }
    
    public void highPass()
    {
        raiseJaw();
        closeJaw();
        outRoller();     
    }
    
    public void robotShoot()
    {
        raiseJaw();
        openJaw();
        offRoller();
        shoot();
    }
}