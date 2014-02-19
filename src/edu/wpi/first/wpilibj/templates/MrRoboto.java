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
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.templates.Jaws.State;
import edu.wpi.first.wpilibj.AnalogChannel;

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
    
    Vision vision;
    boolean targetFound = false;
    
    public final double distanceMultiplier = 1;
    public final double pressureMultiplier = 34.7;
    
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
    
    //Encoder ids
    public final int R_ENCODER_ID1 = 1;
    public final int R_ENCODER_ID2 = 2;
    public final int L_ENCODER_ID1 = 3;
    public final int L_ENCODER_ID2 = 4;
    
    //Ultrasonic sensor id
    public final int ULTRASONIC_SENSOR_ID = 1;
    
    //Pressure sensor
    public final int PRESSURE_SENSOR_ID = 2;
    
    //Jaw ids
    public final int bottomJawLeftSolenoidId = 3;
    public final int bottomJawRightSolenoidId = 4;
    public final int topJawSolenoidId = 2;
    public final int rollerTalonId = 7;
    public final int RshooterSolenoidId = 5;
    public final int LshooterSolenoidId = 6;
    
    //Cameralight ID
    public final int cameraLightId = 8;
    
    //Autonomous values
    public static double visionStartTime = -1;
    public static double startTime = -1;
    public static double delay = 1;
    public static double secondDelay = 1.1;
    
    
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
    
    //Joystick
    Joystick mainJoy;
    Joystick shooterJoy;
    
    // Transmission
    Solenoid xmissionSol1;
    Compressor xmissionCompressor;
    
    //Ultrasonic sensor
    AnalogChannel ultraSonicSensor;
    
    //Pressure Sensor
    AnalogChannel pressureSensor;
    
    Solenoid cameraLight;
    
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
        this.xmissionCompressor = new Compressor(PRESSURE_SW_ID, COMPRESSOR_RELAY_ID);
        
        //Construct Camera
        vision = new Vision();
        
        //Construct cameraLight
        cameraLight = new Solenoid(cameraLightId);
        cameraLight.set(true);
        
        //Construct joystick
        mainJoy = new Joystick(1);
        shooterJoy = new Joystick(2);
        
        jaw = new Jaws(bottomJawLeftSolenoidId, bottomJawRightSolenoidId, topJawSolenoidId, rollerTalonId, RshooterSolenoidId, LshooterSolenoidId, shooterJoy);
        
        xmissionCompressor.start();
        
        leftEncoder = new Encoder(L_ENCODER_ID1, L_ENCODER_ID2);
        rightEncoder = new Encoder(R_ENCODER_ID1, R_ENCODER_ID2);
        
        ultraSonicSensor = new AnalogChannel(ULTRASONIC_SENSOR_ID);
        pressureSensor = new AnalogChannel(PRESSURE_SENSOR_ID);
    }
    public void disabledInit(){
        jaw.raiseJaw();
        jaw.closeJaw();
        jaw.shooterReset();
    }

    /**
     * This function is called periodically during autonomous
     */
    public void autonomousPeriodic() {
        SmartDashboard.putNumber("Robot Speed", leftEncoder.getRate());
        SmartDashboard.putNumber("Air Pressure", pressureSensor.getVoltage()*pressureMultiplier-20);
        if(visionStartTime < 0)
            visionStartTime = Timer.getFPGATimestamp();
        if((Timer.getFPGATimestamp() - visionStartTime) < 5)
        {
            if(vision.isTargetHot())
            {
                targetFound = true;
                autoDriveThenShoot();
            }
        }
        else
        {
            if(!targetFound)
                autoDriveThenShoot();
        }
    }
    
    public void autoDriveThenShoot() {
        if(startTime == -1) {
            startTime = Timer.getFPGATimestamp();
            System.out.println("StartTime: " + startTime);
            System.out.println("StartTime + Delay: " + (startTime + delay));
        }
        if(startTime + delay < Timer.getFPGATimestamp()) {
            driveTrain.drive(-0.25, 0);
            System.out.println("Driving");
        }
        else if(startTime + delay > Timer.getFPGATimestamp() && startTime + secondDelay < Timer.getFPGATimestamp())
        {
            System.out.println("Stopping and shooting");
            driveTrain.drive(0, 0);
            jaw.desiredState = State.shotPrep;
        }
        jaw.update();
    }

    /**
     * This function is called periodically during operator control
     */
    public void teleopPeriodic() {
        SmartDashboard.putNumber("Robot Speed", leftEncoder.getRate());
        SmartDashboard.putNumber("Distance to Wall", ultraSonicSensor.getVoltage()*distanceMultiplier);
        SmartDashboard.putNumber("Air Pressure", pressureSensor.getVoltage()*pressureMultiplier);
        SmartDashboard.putBoolean("Low Gear", xmissionSol1.get());
//        double shifterTimer = Timer.getFPGATimestamp();
//        int shifterDelay = 5;
        jaw.update();
        driveTrain.arcadeDrive(mainJoy.getRawAxis(2), mainJoy.getRawAxis(4));
        if(mainJoy.getRawButton(5))
        {
            xmissionSol1.set(true);
        }
        else if(mainJoy.getRawButton(6))
        {
            xmissionSol1.set(false);
        }
//        driveTrain.arcadeDrive(shooterJoy.getRawAxis(2), shooterJoy.getRawAxis(4));
//        if(shooterJoy.getRawButton(3))
//        {
//            if(xmissionSol1.get() == false && shifterTimer > shifterDelay)
//            {
//                xmissionSol1.set(true);
//                shifterTimer = 0;
//            }
//            else if(xmissionSol1.get() == true && shifterTimer > shifterDelay)
//            {
//                xmissionSol1.set(false);
//                shifterTimer = 0;
//            }
//        }
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
