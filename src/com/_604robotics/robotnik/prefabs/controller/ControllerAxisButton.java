package com._604robotics.robotnik.prefabs.controller;

import com._604robotics.robotnik.trigger.TriggerAccess;
import com.sun.squawk.util.MathUtils;
import edu.wpi.first.wpilibj.Joystick;

public class ControllerAxisButton implements TriggerAccess {
    private final Joystick joystick;
    private final int axis;
    
    private final int direction;
    
    public ControllerAxisButton (Joystick joystick, int axis, int direction) {
        this.joystick = joystick;
        this.axis = axis;
        
        this.direction = direction;
    }
    
    public boolean get () {
        return this.joystick.getRawAxis(this.axis) * direction > 0.8;
    }
}
