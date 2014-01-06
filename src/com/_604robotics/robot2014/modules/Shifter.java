package com._604robotics.robot2014.modules;

import com._604robotics.robotnik.action.Action;
import com._604robotics.robotnik.action.ActionData;
import com._604robotics.robotnik.action.controllers.StateController;
import com._604robotics.robotnik.module.Module;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;

public class Shifter extends Module {
    private final DoubleSolenoid solenoid = new DoubleSolenoid(1, 2);
    
    public Shifter () {
        this.set(new StateController () {{
            addDefault("Low Gear", new Action() {
                public void begin (ActionData data) {
                    solenoid.set(Value.kReverse);
                }
            });
            
            addDefault("High Gear", new Action() {
                public void begin (ActionData data) {
                    solenoid.set(Value.kForward);
                }
            });
        }});
    }
}
