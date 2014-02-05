/*----------------------------------------------------------------------------*/
/* Copyright (c) FIRST 2008. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package edu.wpi.first.wpilibj.templates;

import edu.wpi.first.wpilibj.AnalogChannel;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class MrRoboto extends IterativeRobot {
    // Constants go here - 0's are probably placeholders
    
    static boolean canAdjustJaw = true;
    
    // Talon motor IDs
    public final int FRONT_LEFT_MTRID = 4;
    public final int FRONT_RIGHT_MTRID = 1;
    public final int MID_LEFT_MTRID = 5;
    public final int MID_RIGHT_MTRID = 2;
    public final int REAR_LEFT_MTRID = 6;
    public final int REAR_RIGHT_MTRID = 3;
    
    // Transmission object IDs
    public final int XMISSION_SOL1_ID = 1;
    public final int COMPRESSOR_RELAY_ID = 8;
    public final int PRESSURE_SW_ID = 14;
    
    //Jaw ids
    public final int bottomJawLeftSolenoidId = 3;
    public final int bottomJawRightSolenoidId = 4;
    public final int upperJawSolenoidId = 2;
    public final int rollerTalonId = 7;
    
    //Launcher ids
    public final int launcherSolenoid5Id = 5;
    public final int launcherSolenoid6Id = 6;
    
    //Sensor ids
    public final int analogchannelId = 1;
    public final int analogchannel2Id = 2;
    
    // Variable/Object declarations go here
    
    // Drivetrain Talon motors + RobotDrive object
    Talon frontLeftMotor;
    Talon frontRightMotor;
    Talon midLeftMotor;
    Talon midRightMotor;
    Talon rearLeftMotor;
    Talon rearRightMotor;
    RobotDrive6Motor driveTrain;
    
    //Jaw
    Jaws jaw;
    //upperJaw
    Solenoid upperJawSol2;
    //lowerJaw
    Solenoid lowerJawSol3;
    Solenoid lowerJawSol4;
    
    //Launcher
    Solenoid launcherSol5;
    Solenoid launcherSol6;
    
    //Encoders
    Encoder leftEncoder;
    Encoder rightEncoder;
    
    // Transmission
    Solenoid xmissionSol1;
    Compressor xmissionCompressor;
    
    
    //Pickup
    Talon pickupMotor;
    
    
    // End variable/constant declaration
    
    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
    
    DigitalInput dgtl;
    AnalogChannel anlg;
    AnalogChannel anlg2;
    
    Joystick mainJoy;
    
    public void robotInit() {
        // Construct Talons
        this.frontLeftMotor = new Talon(FRONT_LEFT_MTRID);
        this.frontRightMotor = new Talon(FRONT_RIGHT_MTRID);
        this.midLeftMotor = new Talon(MID_LEFT_MTRID);
        this.midRightMotor = new Talon(MID_RIGHT_MTRID);
        this.rearLeftMotor = new Talon(REAR_LEFT_MTRID);
        this.rearRightMotor = new Talon(REAR_RIGHT_MTRID);
        this.pickupMotor = new Talon(rollerTalonId);
        
        // Construct RobotDrive
        this.driveTrain = new RobotDrive6Motor(frontLeftMotor, midLeftMotor, rearLeftMotor, frontRightMotor, midRightMotor, rearRightMotor);
        
        // Construct transmission
        this.xmissionSol1 = new Solenoid(XMISSION_SOL1_ID);
        this.xmissionCompressor = new Compressor(PRESSURE_SW_ID, COMPRESSOR_RELAY_ID);
        this.xmissionCompressor.start();
        
        // Construct Jaw/Launcher Solenoids
        this.upperJawSol2 = new Solenoid(upperJawSolenoidId);
        this.lowerJawSol3 = new Solenoid(bottomJawRightSolenoidId);
        this.lowerJawSol4 = new Solenoid(bottomJawLeftSolenoidId);
        this.launcherSol5 = new Solenoid(launcherSolenoid5Id);
        this.launcherSol6 = new Solenoid(launcherSolenoid6Id);
        
        // Construct Analog Sensor
        this.anlg = new AnalogChannel(analogchannelId);
        this.anlg2 = new AnalogChannel(analogchannel2Id);
        
        //drivetrain
        LiveWindow.addActuator("Drivetrain", "FrontRightMotor", frontRightMotor);
        LiveWindow.addActuator("Drivetrain", "MidRightMotor", midRightMotor);
        LiveWindow.addActuator("Drivetrain", "RearRightMotor", rearRightMotor);
        LiveWindow.addActuator("Drivetrain", "FrontLeftMotor", frontLeftMotor);
        LiveWindow.addActuator("Drivetrain", "MidLeftMotor", midLeftMotor);
        LiveWindow.addActuator("Drivetrain", "RearLeftMotor", rearLeftMotor);
        LiveWindow.addActuator("Drivetrain", "ShifterSolenoid", xmissionSol1);
        rightEncoder = new Encoder(1,2);
        LiveWindow.addSensor("Drivetrain", "RightEncoder", rightEncoder);
        leftEncoder = new Encoder(3,4);
        LiveWindow.addSensor("Drivetrain", "LeftEncoder", leftEncoder);
        LiveWindow.addSensor("Distance", "Anlg", anlg);
        
        //upperjaw
        LiveWindow.addActuator("Jaws", "UpperJawSol2", upperJawSol2);
        
        //pickup
        LiveWindow.addActuator("Jaws", "PickupMotor", pickupMotor);
        
        //lowerjaw
        LiveWindow.addActuator("Jaws", "LowerJawSol3", lowerJawSol3);
        LiveWindow.addActuator("Jaws", "LowerJawSol4", lowerJawSol4);
        
        //launcher
        LiveWindow.addActuator("Jaws", "LauncherSol5", launcherSol5);
        LiveWindow.addActuator("Jaws", "LauncherSol6", launcherSol6);
        
        //PneumaticSystem
        LiveWindow.addSensor("Pneumatics", "Pressure Sensor", anlg2);
        
        mainJoy = new Joystick(1);
       
        
        
        
    }

    /**
     * This function is called periodically during autonomous
     */
    public void autonomousPeriodic() {

    }

    /**
     * This function is called periodically during operator control
     */
    public void teleopPeriodic() {
        if(mainJoy.getRawButton(5))
            xmissionSol1.set(true);
        else if(mainJoy.getRawButton(6))
            xmissionSol1.set(false);
        driveTrain.arcadeDrive(mainJoy);
    }
    
    /**
     * This function is called periodically during test mode
     */
    public void testPeriodic() {
        LiveWindow.run();
    }
    
    public boolean isTargetHot() {
        return SmartDashboard.getBoolean("HotTargetFound", false);
    }
    
}
