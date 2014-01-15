/*----------------------------------------------------------------------------*/
/* Copyright (c) FIRST 2008. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package edu.wpi.first.wpilibj.templates;


import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Talon;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class MrRoboto extends IterativeRobot {
    
    final int JOYSTICKPORT = 1;
    final int LEFTFRONTMOTORCHANNEL = 1;
    final int RIGHTFRONTMOTORCHANNEL = 1;
    final int LEFTMIDMOTORCHANNEL = 1;
    final int RIGHTMIDMOTORCHANNEL = 1;
    final int LEFTREARMOTORCHANNEL = 1;
    final int RIGHTREARMOTORCHANNEL = 1;
    
    Joystick driverJoystick = new Joystick(JOYSTICKPORT);
    Talon leftFrontMotor = new Talon(LEFTFRONTMOTORCHANNEL);
    Talon rightFrontMotor = new Talon(RIGHTFRONTMOTORCHANNEL);
    Talon leftMidMotor = new Talon(LEFTMIDMOTORCHANNEL);
    Talon rightMidMotor = new Talon(RIGHTMIDMOTORCHANNEL);
    Talon leftRearMotor = new Talon(LEFTREARMOTORCHANNEL);
    Talon rightRearMotor = new Talon(RIGHTREARMOTORCHANNEL);
    
    
    
    RobotDrive6Motor driveTrain = new RobotDrive6Motor(leftFrontMotor, leftMidMotor, leftRearMotor, rightFrontMotor, rightMidMotor, rightRearMotor);
    
    
    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
    public void robotInit() {

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
        
        driveTrain.arcadeDrive(driverJoystick);
        
    }
    
    /**
     * This function is called periodically during test mode
     */
    public void testPeriodic() {
    
    }
    
}
