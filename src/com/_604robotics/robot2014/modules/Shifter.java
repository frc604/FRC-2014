package com._604robotics.robot2014.modules;

import com._604robotics.robotnik.action.Action;
import com._604robotics.robotnik.action.controllers.ElasticController;
import com._604robotics.robotnik.module.Module;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;

public class Shifter extends Module {
    private final DoubleSolenoid solenoid = new DoubleSolenoid(1, 2);
    
    public Shifter () {
        this.set(new ElasticController () {{
            addDefault("Low Gear", new Action() {
                public void begin () {
                    solenoid.set(Value.kReverse);
                }
            });
            
            add("High Gear", new Action() {
                public void begin () {
                    solenoid.set(Value.kForward);
                }
            });
        }});
    }
}
