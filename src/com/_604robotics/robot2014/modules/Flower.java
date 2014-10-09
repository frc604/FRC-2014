package com._604robotics.robot2014.modules;

import com._604robotics.robotnik.action.Action;
import com._604robotics.robotnik.action.ActionData;
import com._604robotics.robotnik.action.controllers.StateController;
import com._604robotics.robotnik.module.Module;
import com._604robotics.robotnik.trigger.Trigger;
import com._604robotics.robotnik.trigger.TriggerMap;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.Timer;

public class Flower extends Module {
    private final Solenoid top = new Solenoid(4); //top solenoid in channel 4
    private final Solenoid sides = new Solenoid(5); //side solenoids in channel 5
    private final Solenoid bottom = new Solenoid(3); //bottom solenoid in channel 3
    
    private final Timer travelTimer = new Timer();
    
    public Flower () {
        this.set(new TriggerMap() {{
            add("Travelling", new Trigger() {
                public boolean run () {
                    return travelTimer.get() < 0.6; //petals always travel for at least .6 seconds
                }
            });
        }});
        
        this.set(new StateController () {{
            addDefault("Close", new Action() {
                public void begin (ActionData data) {
                    top.set(false); //for the closed polition, the top petal is closed
                    sides.set(false); //the sides are closed
                    bottom.set(false); //the bottom is closed
                    
                    travelTimer.reset();
                }
            });
            
            add("Open", new Action() {
                public void begin (ActionData data) {
                    top.set(true); //for the open position, the top petal is open
                    sides.set(true); //the sides are open
                    bottom.set(true); //and the bottom is open
                    
                    travelTimer.reset();
                }
            });
            
            add("Pickup", new Action() {
                public void begin (ActionData data) {
                    top.set(false); //for the pickup position, the top is closed
                    sides.set(false); //the sides are closed
                    bottom.set(false); //and the bottom is closed
                    
                    travelTimer.reset();
                }
            });
            
            add("Drop", new Action() {
                public void begin (ActionData data) {
                    top.set(true); //for the dropping position, the top petal is open
                    sides.set(true); //the sides are opened
                    bottom.set(false); //and the bottom is closed
                    
                    travelTimer.reset();
                }
            });
            
            add("Shoot", new Action() {
                public void begin (ActionData data) {
                    top.set(true); //for the shooting position, the top petal is open
                    sides.set(false); //the sides are closed
                    bottom.set(false); //and the bottom is closed
                    
                    travelTimer.reset();
                }
            });
        }});
    }

    protected void start () {
        travelTimer.start();
    }
    
    protected void end () {
        travelTimer.stop();
        travelTimer.reset();
    }
}
