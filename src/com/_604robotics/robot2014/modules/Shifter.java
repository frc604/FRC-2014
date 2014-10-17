package com._604robotics.robot2014.modules;

import com._604robotics.robotnik.action.Action;
import com._604robotics.robotnik.action.ActionData;
import com._604robotics.robotnik.action.controllers.ElasticController;
import com._604robotics.robotnik.module.Module;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;



public class Shifter extends Module {
    private final DoubleSolenoid solenoid = new DoubleSolenoid(1/*forward channel*/, 2/*reverse channel*/);
    
    public Shifter () {
        this.set(new ElasticController () {{
            addDefault("Low Gear", new Action() {
                public void begin (ActionData data) {
                    solenoid.set(Value.kReverse); //puts the solenoid in reverse throwing the drive into low gear
                }
            });
            
            add("High Gear", new Action() {
                public void begin (ActionData data) {
                    solenoid.set(Value.kForward); //puts the solenoid forwards throwing the drive into high gear
                }
            });
        }});
    }
}
