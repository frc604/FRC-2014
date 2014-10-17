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
    private final Solenoid release = new Solenoid(6); //new solenoid on channel 6
    private final Victor winch = new Victor(4); //new victor on channel 4
    private final DigitalInput limitSwitch = new DigitalInput(7); //new limit switch on channel 7
    
    private final Timer deployTimer = new Timer();
    
    public Shooter () {
    	this.set(new TriggerMap() {{
            add("Charged", new Trigger() { //checks to see if shooter is charged. Returns.
                public boolean run() {
                    return !limitSwitch.get(); //if limitSwitch is not flipped. return that shooter is charged.
                }
            });
            
            add("Deployed", new Trigger() { //checks to see if shooter is deployed. Returns.
                public boolean run() {
                    return deployTimer.get() > 1; //if deploy timer is greater than 1, return that shooter is deployed
                }
            });
    	}});
    	
        this.set(new ElasticController () {{
            addDefault("Idle", new Action() {
                public void begin(ActionData data) {
                    release.set(false); //solenoid off
                }

                public void run(ActionData data) {
                    winch.stopMotor(); //stop the victor winch
                }
            });
            
            add("Retract", new Action() {
                public void begin (ActionData data) {
                    release.set(false); //solenoid off
                }

                public void run (ActionData data) {
                    release.set(false); //solenoid off
                    if (limitSwitch.get()) //if limit switch is on
                        winch.set(-1D); //start the winch?
                    else
                        winch.stopMotor(); //stop the winch
                }

                public void end (ActionData data) {
                    winch.stopMotor(); //stop the winch
                }
            });
            
            add("Deploy", new Action() {
                public void begin (ActionData data) {
                    release.set(true); //solenoid on
                    
                    deployTimer.start();
                }

                public void run (ActionData data) {
                    winch.stopMotor(); //stop the winch
                }
                
                public void end (ActionData data) {
                    release.set(false); //solenoid off
                    
                    deployTimer.stop();
                    deployTimer.reset();
                }
            });
        }});
    }
}
