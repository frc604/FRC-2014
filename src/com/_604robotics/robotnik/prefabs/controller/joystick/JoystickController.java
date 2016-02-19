package com._604robotics.robotnik.prefabs.controller.joystick;

import com._604robotics.robotnik.prefabs.controller.ControllerAxis;
import edu.wpi.first.wpilibj.Joystick;

public class JoystickController {
    public final JoystickControllerButtons buttons;
    
    public final ControllerAxis axisX;
    public final ControllerAxis axisY;
    
    public JoystickController (int port) {
        this(new Joystick(port));
    }
    
    public JoystickController (Joystick joystick) {
        this.buttons = new JoystickControllerButtons(joystick);
        
        this.axisX = new ControllerAxis(joystick, 1);
        this.axisY = new ControllerAxis(joystick, 2);
    }
}