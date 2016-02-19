package com._604robotics.robotnik.prefabs.controller;

import com._604robotics.robotnik.trigger.TriggerAccess;
import edu.wpi.first.wpilibj.Joystick;

public class ControllerButton implements TriggerAccess {
    private final Joystick joystick;
    private final int button;
    
    public ControllerButton (Joystick joystick, int button) {
        this.joystick = joystick;
        this.button = button;
    }
    
    public boolean get () {
        return this.joystick.getRawButton(this.button);
    }
}
