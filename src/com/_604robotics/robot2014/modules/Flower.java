package com._604robotics.robot2014.modules;

import com._604robotics.robotnik.action.Action;
import com._604robotics.robotnik.action.ActionData;
import com._604robotics.robotnik.action.controllers.StateController;
import com._604robotics.robotnik.action.field.FieldMap;
import com._604robotics.robotnik.module.Module;
import com._604robotics.robotnik.trigger.Trigger;
import com._604robotics.robotnik.trigger.TriggerMap;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.Timer;

public class Flower extends Module {
    private final Solenoid top = new Solenoid(4);
    private final Solenoid sides = new Solenoid(5);
    private final Solenoid bottom = new Solenoid(3);
    
    private final Timer travelTimer = new Timer();
    
    public Flower () {
        this.set(new TriggerMap() {{
            add("Travelling", new Trigger() {
                public boolean run () {
                    return travelTimer.get() < 0.6;
                }
            });
        }});
        
        this.set(new StateController () {{
            addDefault("Close", new Action() {
                public void begin (ActionData data) {
                    top.set(false);
                    sides.set(false);
                    bottom.set(false);
                    
                    travelTimer.reset();
                }
            });
            
            add("Open", new Action() {
                public void begin (ActionData data) {
                    top.set(true);
                    sides.set(true);
                    bottom.set(true);
                    
                    travelTimer.reset();
                }
            });
            
            add("Drop", new Action() {
                public void begin (ActionData data) {
                    top.set(false);
                    sides.set(false);
                    bottom.set(false);
                    
                    travelTimer.reset();
                }
            });
            
            add("Shoot", new Action() {
                public void begin (ActionData data) {
                    top.set(true);
                    sides.set(false);
                    bottom.set(false);
                    
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
