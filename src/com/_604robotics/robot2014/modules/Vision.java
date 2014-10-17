package com._604robotics.robot2014.modules;

import com._604robotics.robotnik.action.Action;
import com._604robotics.robotnik.action.ActionData;
import com._604robotics.robotnik.action.controllers.ElasticController;
import com._604robotics.robotnik.action.field.FieldMap;
import com._604robotics.robotnik.module.Module;
import com._604robotics.robotnik.trigger.Trigger;
import com._604robotics.robotnik.trigger.TriggerMap;
import com._604robotics.robotnik.trigger.sources.NetworkTrigger;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Timer;

public class Vision extends Module {
    private static final int SNAPPING = 0;
    private static final int PAUSING  = 1;
    private static final int READY    = 2;

    private boolean leftTarget  = false;
    private boolean rightTarget = false;
    
    private int state = SNAPPING;
    
    public Vision () {
        this.set(new TriggerMap() {{
            add("Left Target",  new NetworkTrigger("robot2014.vision", "leftTarget",  false));
            add("Right Target", new NetworkTrigger("robot2014.vision", "rightTarget", false));
            
            add("Snapped", new Trigger() {
                public boolean run () {
                    return state > SNAPPING; //if the state is greater than 0, is snapped
                }
            });
            
            add("Ready", new Trigger() {
                public boolean run () {
                    return state >= READY; //if the state is greater or equal to 2, is ready
                }
            });
        }});
        
        this.set(new ElasticController() {{
            addDefault("Idle"); //nothing doing anything by default
            
            add("Snap", new Action() {
                private final Timer timer = new Timer();
                
                public void begin (ActionData data) {
                    state = SNAPPING; //set the state to snapping
                    timer.start(); //start the timer
                }
                
                public void run (ActionData data) {
                    if (timer.get() > 1.5) { //if 1.5 miliseconds have passed
                        state = PAUSING; //the state is to be set to pausing
                    } else {
                        leftTarget  = data.trigger("Left Target");
                        rightTarget = data.trigger("Right Target");
                    }
                }
                
                public void end (ActionData data) {
                    timer.stop(); //stop the timer
                    timer.reset(); //reset it to 0
                }
            });
            
            add("Pause", new Action(new FieldMap() {{
                define("leftSide" , false);
                define("rightSide", false);
            }}) {
                public void begin (ActionData data) {
                    state = PAUSING;
                }
                
                public void run (ActionData data) {
                    if (data.is("leftSide")) {
                        if (!leftTarget && DriverStation.getInstance().getMatchTime() < 6)
                            return;
                    } else if (data.is("rightSide")) {
                        if (!rightTarget && DriverStation.getInstance().getMatchTime() < 6)
                            return;
                    }
                    
                    state = READY;
                }
            });
        }});
    }
    
    protected void start () {
        state = SNAPPING;
    }
}
