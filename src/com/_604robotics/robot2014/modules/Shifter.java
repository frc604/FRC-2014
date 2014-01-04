package com._604robotics.robot2014.modules;

import com._604robotics.robotnik.action.Action;
import com._604robotics.robotnik.action.ActionData;
import com._604robotics.robotnik.action.controllers.StateController;
import com._604robotics.robotnik.module.Module;
import edu.wpi.first.wpilibj.Solenoid;

public class Shifter extends Module {
    private final Solenoid solenoid = new Solenoid(2);
    
    public Shifter () {
        this.set(new StateController () {{
            addDefault("Low Gear", new Action() {
                public void begin (ActionData data) {
                    solenoid.set(false);
                }
            });
            
            addDefault("High Gear", new Action() {
                public void begin (ActionData data) {
                    solenoid.set(true);
                }
            });
        }});
    }
}
