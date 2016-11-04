package com._604robotics.robotnik.prefabs.controller.joystick;

import com._604robotics.robotnik.prefabs.controller.ControllerButton;
import edu.wpi.first.wpilibj.Joystick;

public class JoystickControllerButtons {
    public final ControllerButton Button1;
    public final ControllerButton Button2;
    public final ControllerButton Button3;
    public final ControllerButton Button4;
    public final ControllerButton Button5;
    public final ControllerButton Button6;
    public final ControllerButton Button7;
    public final ControllerButton Button8;
    public final ControllerButton Button9;
    public final ControllerButton Button10;
    public final ControllerButton Button11;
    
    public JoystickControllerButtons (Joystick joystick) {
        this.Button1  = new ControllerButton(joystick, 1);
        this.Button2  = new ControllerButton(joystick, 2);
        this.Button3  = new ControllerButton(joystick, 3);
        this.Button4  = new ControllerButton(joystick, 4);
        this.Button5  = new ControllerButton(joystick, 5);
        this.Button6  = new ControllerButton(joystick, 6);
        this.Button7  = new ControllerButton(joystick, 7);
        this.Button8  = new ControllerButton(joystick, 8);
        this.Button9  = new ControllerButton(joystick, 9);
        this.Button10 = new ControllerButton(joystick, 10);
        this.Button11 = new ControllerButton(joystick, 11);
    }
}