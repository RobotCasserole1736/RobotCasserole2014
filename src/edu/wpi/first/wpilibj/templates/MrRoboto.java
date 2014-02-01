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
    public final int XMISSION_SOL1_ID = 0;
    public final int COMPRESSOR_RELAY_ID = 0;
    public final int PRESSURE_SW_ID = 0;
    
    //Jaw ids
    public final int bottomJawLeftSolenoidId = 0;
    public final int bottomJawRightSolenoidId = 0;
    public final int upperJawSolenoidId = 0;
    public final int topJawSolenoidId = 0;
    public final int rollerTalonId = 0;
    
    //Launcher ids
    public final int launcherSolenoid5Id = 0;
    public final int launcherSolenoid6Id = 0;
    
    //Sensor ids
    public final int analogchannelId = 0;
    public final int analogchannel2Id = 0;
    
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
        frontRightMotor = new Talon(1);
        LiveWindow.addActuator("Right1", "FrontRightMotor", frontRightMotor);
        midRightMotor = new Talon(2);
        LiveWindow.addActuator("Right2", "MidRightMotor", midRightMotor);
        rearRightMotor = new Talon(3);
        LiveWindow.addActuator("Right3", "RearRightMotor", rearRightMotor);
        frontLeftMotor = new Talon(4);
        LiveWindow.addActuator("Left1", "FrontLeftMotor", frontLeftMotor);
        midLeftMotor = new Talon(5);
        LiveWindow.addActuator("Left2", "MidLeftMotor", midLeftMotor);
        rearLeftMotor = new Talon(6);
        LiveWindow.addActuator("Left3", "RearLeftMotor", rearLeftMotor);
        xmissionSol1 = new Solenoid(1);
        LiveWindow.addActuator("Shifter", "XmissionSol1", xmissionSol1);
        rightEncoder = new Encoder(1,2);
        LiveWindow.addSensor("Right", "RightEncoder", rightEncoder);
        leftEncoder = new Encoder(3,4);
        LiveWindow.addSensor("Left", "LeftEncoder", leftEncoder);
        anlg = new AnalogChannel(1,2);
        LiveWindow.addSensor("Distance", "Anlg", anlg);
        
        //upperjaw
        upperJawSol2 = new Solenoid(2);
        LiveWindow.addActuator("Open/Close", "UpperJawSol2", upperJawSol2);
        
        //pickup
        pickupMotor = new Talon(7);
        LiveWindow.addActuator("Roller", "PickupMotor", pickupMotor);
        
        //lowerjaw
        lowerJawSol3 = new Solenoid(3);
        LiveWindow.addActuator("Up/DownRight", "LowerJawSol3", lowerJawSol3);
        lowerJawSol4 = new Solenoid(4);
        LiveWindow.addActuator("Up/DownLeft", "LowerJawSol4", lowerJawSol4);
        
        //launcher
        launcherSol5 = new Solenoid(5);
        LiveWindow.addActuator("ShooterRight", "LauncherSol5", launcherSol5);
        launcherSol6 = new Solenoid(6);
        LiveWindow.addActuator("WhooterLeft", "LauncherSol6", launcherSol6);
        
        //PneumaticSystem
        xmissionCompressor = new Compressor(14,8);
        LiveWindow.addActuator("Test", "XmissionCompressor", xmissionCompressor);
        dgtl = new DigitalInput(14);
        LiveWindow.addActuator("PressureSwitch", "Dgtl", dgtl);
        anlg2 = new AnalogChannel(3,4);
        LiveWindow.addSensor("PressureSensor", "Anlg2", anlg2);
        
        
       
        
        
        
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
