package com._604robotics.robotnik.prefabs.controller.xbox;

import com._604robotics.robotnik.prefabs.controller.ControllerAxis;
import edu.wpi.first.wpilibj.Joystick;

public class XboxControllerStick {
    public final ControllerAxis X;
    public final ControllerAxis Y;
    
    public XboxControllerStick (Joystick joystick, int x, int y) {
        this.X = new ControllerAxis(joystick, x);
        this.Y = new ControllerAxis(joystick, y);
    }
}