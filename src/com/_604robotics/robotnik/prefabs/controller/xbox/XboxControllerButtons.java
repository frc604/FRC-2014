package com._604robotics.robotnik.prefabs.controller.xbox;

import com._604robotics.robotnik.prefabs.controller.ControllerAxisButton;
import com._604robotics.robotnik.prefabs.controller.ControllerButton;
import edu.wpi.first.wpilibj.Joystick;

public class XboxControllerButtons {
    public final ControllerButton A;
    public final ControllerButton B;
    public final ControllerButton X;
    public final ControllerButton Y;
    public final ControllerButton LB;
    public final ControllerButton RB;
    public final ControllerButton Back;
    
    public final ControllerButton LeftStick;
    public final ControllerButton RightStick;
    
    public final ControllerAxisButton LT;
    public final ControllerAxisButton RT;
    
    public XboxControllerButtons (Joystick joystick) {
        this.A    = new ControllerButton(joystick, 1);
        this.B    = new ControllerButton(joystick, 2);
        this.X    = new ControllerButton(joystick, 3);
        this.Y    = new ControllerButton(joystick, 4);
        this.LB   = new ControllerButton(joystick, 5);
        this.RB   = new ControllerButton(joystick, 6);
        this.Back = new ControllerButton(joystick, 7);
        
        this.LeftStick  = new ControllerButton(joystick,  9);
        this.RightStick = new ControllerButton(joystick, 10);
        
        this.LT = new ControllerAxisButton(joystick, 3,  1);
        this.RT = new ControllerAxisButton(joystick, 3, -1);
    }
}