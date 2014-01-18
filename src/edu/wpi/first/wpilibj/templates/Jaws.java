/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.wpi.first.wpilibj.templates;

import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.Talon;

/**
 *
 * @author FIRSTUser
 */
public class Jaws {
    
  
 private Talon roller;
 private Solenoid positionActuator1;
 private Solenoid positionActuator2;
 private Solenoid angleActuator;
 private Solenoid shooterActuator1;
 private Solenoid shooterActuator2;
 private final boolean JAWPOSEXTENDED = true;
 private final boolean JAWANGEXTENDED = true;
 private final boolean JAWSHOOTEXTENDED = true;
 private final double INTAKEVALUE = 1.0;
 private final double STOPVALUE = 0.0;
 private final double EJECTVALUE = -1.0;

 public Jaws(Talon _roller, Solenoid _positionAcuator1, Solenoid _positionAcuator2, Solenoid _angleActuator, Solenoid _shooterActuator1, Solenoid _shooterActuator2) {
     
     roller = _roller;
     positionActuator1 = _positionAcuator1;       
     positionActuator2 = _positionAcuator2;
     angleActuator = _angleActuator;
     shooterActuator1 = _shooterActuator1;
     shooterActuator2 = _shooterActuator2;
             

     
 }

 public void jawsUp(){
     
     positionActuator1.set(JAWPOSEXTENDED);
     positionActuator2.set(JAWPOSEXTENDED);
     
 }

 public void jawsDown(){
     
     positionActuator1.set(! JAWPOSEXTENDED);
     positionActuator2.set(! JAWPOSEXTENDED);
     
 }   
 public void jawsOpen(){
     
     angleActuator.set(JAWANGEXTENDED);
  
     
 }

 public void jawsClosed(){
     
     angleActuator.set(! JAWANGEXTENDED);
     
     
 }   
public void shooterFire(){
     
     shooterActuator1.set(JAWSHOOTEXTENDED);
     shooterActuator2.set(JAWSHOOTEXTENDED);
     
 }

 public void shooterReset(){
     
     shooterActuator1.set(! JAWSHOOTEXTENDED);
     shooterActuator2.set(! JAWSHOOTEXTENDED);
     
 }           

public void rollerIntake(){
    
    roller.set(INTAKEVALUE);
    
}

public void rollerStop(){
    
    roller.set(STOPVALUE);
    
}

public void rollerEject(){
    
    roller.set(EJECTVALUE);
    
    
    
}




}
