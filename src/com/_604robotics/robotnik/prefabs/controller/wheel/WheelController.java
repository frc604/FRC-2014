package com._604robotics.robotnik.prefabs.controller.wheel;

import com._604robotics.robotnik.prefabs.controller.ControllerAxis;
import edu.wpi.first.wpilibj.Joystick;

public class WheelController {
    public final WheelControllerButtons buttons;
    public final ControllerAxis axis;
    
    public WheelController (int port) {
        final Joystick joystick = new Joystick(port);
        
        this.buttons = new WheelControllerButtons(joystick);
        this.axis = new ControllerAxis(joystick, 1);
    }
}