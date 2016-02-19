package com._604robotics.robotnik.prefabs.controller.wheel;

import com._604robotics.robotnik.prefabs.controller.ControllerButton;
import edu.wpi.first.wpilibj.Joystick;

public class WheelControllerButtons {
    public final ControllerButton LeftPad;
    public final ControllerButton RightPad;
    
    public final ControllerButton Triangle;
    public final ControllerButton Square;
    public final ControllerButton Circle;
    public final ControllerButton Cross;
    
    public final ControllerButton L2;
    public final ControllerButton R2;
    
    public final ControllerButton L3;
    public final ControllerButton R3;
    
    public final ControllerButton Home;
    public final ControllerButton ST;
    public final ControllerButton SE;
    
    public WheelControllerButtons (Joystick joystick) {
        this.LeftPad  = new ControllerButton(joystick, 5);
        this.RightPad = new ControllerButton(joystick, 6);
        
        this.Triangle = new ControllerButton(joystick, 4);
        this.Square   = new ControllerButton(joystick, 1);
        this.Circle   = new ControllerButton(joystick, 3);
        this.Cross    = new ControllerButton(joystick, 2);
        
        this.L2 = new ControllerButton(joystick, 7);
        this.R2 = new ControllerButton(joystick, 8);
        
        this.L3 = new ControllerButton(joystick, 11);
        this.R3 = new ControllerButton(joystick, 12);
        
        this.Home = new ControllerButton(joystick, 13);
        this.ST   = new ControllerButton(joystick, 10);
        this.SE   = new ControllerButton(joystick, 9);
    }
}