package com._604robotics.robot2014.modules;

import com._604robotics.robotnik.action.Action;
import com._604robotics.robotnik.action.ActionData;
import com._604robotics.robotnik.action.controllers.ElasticController;
import com._604robotics.robotnik.module.Module;
import com._604robotics.robotnik.trigger.Trigger;
import com._604robotics.robotnik.trigger.TriggerMap;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.Victor;

public class Shooter extends Module {
    private final Solenoid lock = new Solenoid(7);
    private final Victor winch = new Victor(4);
    private final DigitalInput limitSwitch = new DigitalInput(6);
    
    public Shooter () {
    	this.set(new TriggerMap() {{
            add("Charged", new Trigger() {
                public boolean run() {
                    return !limitSwitch.get();
                }
            });
    	}});
    	
        this.set(new ElasticController () {{
            addDefault("Idle", new Action() {
                public void begin(ActionData data) {
                    lock.set(false);
                }

                public void run(ActionData data) {
                    winch.stopMotor();
                }
            });
            
            add("Retract", new Action() {
                public void begin(ActionData data) {
                    lock.set(false);
                }

                public void run(ActionData data) {
                    winch.set(-0.8);
                }

                public void end(ActionData data) {
                    winch.stopMotor();
                }
            });
            
            add("Deploy", new Action() {
                public void begin(ActionData data) {
                    lock.set(true);
                }

                public void run(ActionData data) {
                    winch.stopMotor();
                }
            });
        }});
    }
}
