package com._604robotics.robot2014.modules;

import com._604robotics.robotnik.action.Action;
import com._604robotics.robotnik.action.ActionData;
import com._604robotics.robotnik.action.controllers.ElasticController;
import com._604robotics.robotnik.module.Module;
import com._604robotics.robotnik.trigger.Trigger;
import com._604robotics.robotnik.trigger.TriggerMap;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.Victor;

public class Shooter extends Module {
    private final Solenoid release = new Solenoid(6);
    private final Victor winch = new Victor(4);
    private final DigitalInput limitSwitch = new DigitalInput(7);
    
    private final Timer deployTimer = new Timer();
    
    public Shooter () {
    	this.set(new TriggerMap() {{
            add("Charged", new Trigger() {
                public boolean run() {
                    return !limitSwitch.get();
                }
            });
            
            add("Deployed", new Trigger() {
                public boolean run() {
                    return deployTimer.get() > 0.5;
                }
            });
    	}});
    	
        this.set(new ElasticController () {{
            addDefault("Idle", new Action() {
                public void begin(ActionData data) {
                    release.set(false);
                }

                public void run(ActionData data) {
                    winch.stopMotor();
                }
            });
            
            add("Retract", new Action() {
                public void begin (ActionData data) {
                    release.set(false);
                }

                public void run (ActionData data) {
                    retract();
                }

                public void end (ActionData data) {
                    winch.stopMotor();
                }
            });
            
            add("Deploy", new Action() {
                public void begin (ActionData data) {
                    release.set(true);
                    
                    deployTimer.start();
                }

                public void run (ActionData data) {
                    if (deployTimer.get() > 0.75)
                        retract();
                    else
                        winch.stopMotor();
                }
                
                public void end (ActionData data) {
                    release.set(false);
                    
                    deployTimer.stop();
                    deployTimer.reset();
                }
            });
        }});
    }
    
    private void retract () {
        release.set(false);
        if (limitSwitch.get())
            winch.set(-1D);
        else
            winch.stopMotor();
    }
}
