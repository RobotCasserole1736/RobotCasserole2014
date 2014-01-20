/*----------------------------------------------------------------------------*/
/* Copyright (c) FIRST 2008. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package edu.wpi.first.wpilibj.templates;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.Encoder;

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
    public final int XMISSION_SOL2_ID = 0;
    public final int COMPRESSOR_RELAY_ID = 0;
    public final int PRESSURE_SW_ID = 0;
    
    //Jaw ids
    public final int bottomJawLeftSolenoidId = 0;
    public final int bottomJawRightSolenoidId = 0;
    public final int topJawSolenoidId = 0;
    public final int rollerTalonId = 0;
    
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
    
    //Encoders
    Encoder leftEncoder;
    Encoder rightEncoder;
    
    // Transmission
    Solenoid xmissionSol1, xmissionSol2;
    Compressor xmissionCompressor;
    
    
    // End variable/constant declaration
    
    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
    public void robotInit() {
        // Construct Talons
        this.frontLeftMotor = new Talon(FRONT_LEFT_MTRID);
        this.frontRightMotor = new Talon(FRONT_RIGHT_MTRID);
        this.midLeftMotor = new Talon(MID_LEFT_MTRID);
        this.midRightMotor = new Talon(MID_RIGHT_MTRID);
        this.rearLeftMotor = new Talon(REAR_LEFT_MTRID);
        this.rearRightMotor = new Talon(REAR_RIGHT_MTRID);
        
        // Construct RobotDrive
        this.driveTrain = new RobotDrive6Motor(frontLeftMotor, midLeftMotor, rearLeftMotor, frontRightMotor, midRightMotor, rearRightMotor);
        
        // Construct transmission
        this.xmissionSol1 = new Solenoid(XMISSION_SOL1_ID);
        this.xmissionSol2 = new Solenoid(XMISSION_SOL2_ID);
        this.xmissionCompressor = new Compressor(PRESSURE_SW_ID, COMPRESSOR_RELAY_ID);
        
        
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
    
    }
    
    public boolean isTargetHot() {
        return SmartDashboard.getBoolean("HotTargetFound", false);
    }
    
}
