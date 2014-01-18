/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.wpi.first.wpilibj.templates;

import edu.wpi.first.wpilibj.Joystick;

/**
 *
 * @author FIRSTUser
 */
public class Gunman {
    
    private final int UPBUTTON = 1;
    private final int DOWNBUTTON = 1;
    private final int OPENBUTTON = 1;
    private final int CLOSEDBUTTON = 1;
    private final int SHOOTBUTTON = 1;
    private final int PASSBUTTON = 1;
    private final int TOSSBUTTON = 1;
    private final Joystick stickJoy;
    private boolean upNow;
    private boolean downNow;
    private boolean openNow;
    private boolean closedNow;
    private boolean shootNow;       
    private boolean passNow;
    private boolean tossNow;
    private boolean upLast;
    private boolean downLast;
    private boolean openLast;
    private boolean closedLast;
    private boolean passLast;
    private boolean tossLast;
    
    public Gunman(Joystick _joyStick){
        
        stickJoy = _joyStick;
        
        
    }
    public void Poll(){
        
        
        
    }
    
    public boolean IsUpPressed(){
        
             return stickJoy.getRawButton(UPBUTTON);
    }
   public boolean IsDownPressed(){
        
             return stickJoy.getRawButton(DOWNBUTTON);  
   }
   public boolean IsOpenPressed(){
        
             return stickJoy.getRawButton(OPENBUTTON);  
   }
   public boolean IsClosedPressed(){
        
             return stickJoy.getRawButton(CLOSEDBUTTON);  
   }
   public boolean IsShootPressed(){
        
             return stickJoy.getRawButton(SHOOTBUTTON);  
   }
   public boolean IsPassPressed(){
        
             return stickJoy.getRawButton(PASSBUTTON);  
   }
   public boolean IsTossPressed(){
        
             return stickJoy.getRawButton(TOSSBUTTON);  
   }
}
