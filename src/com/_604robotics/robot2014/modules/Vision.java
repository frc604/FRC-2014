package com._604robotics.robot2014.modules;

import com._604robotics.robotnik.action.Action;
import com._604robotics.robotnik.action.Field;
import com._604robotics.robotnik.action.controllers.ElasticController;
import com._604robotics.robotnik.module.Module;
import com._604robotics.robotnik.trigger.Trigger;
import com._604robotics.robotnik.trigger.TriggerSource;
import com._604robotics.robotnik.trigger.TriggerMap;
import com._604robotics.robotnik.prefabs.trigger.sources.NetworkTrigger;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Timer;

public class Vision extends Module {
    private static final int SNAPPING = 0;
    private static final int PAUSING  = 1;
    private static final int READY    = 2;

    private boolean leftSnapped  = false;
    private boolean rightSnapped = false;
    
    private int state = SNAPPING;
    
    public Vision () {
        this.set(new TriggerMap() {{
            add("Left Target",  new NetworkTrigger("robot2014.vision", "leftTarget",  false));
            add("Right Target", new NetworkTrigger("robot2014.vision", "rightTarget", false));
            
            add("Snapped", new Trigger() {
                public boolean run () {
                    return state > SNAPPING;
                }
            });
            
            add("Ready", new Trigger() {
                public boolean run () {
                    return state >= READY;
                }
            });
        }});
        
        this.set(new ElasticController() {{
            addDefault("Idle");
            
            add("Snap", new Action() {
                private final TriggerSource leftTarget  = trigger("Left Target");
                private final TriggerSource rightTarget = trigger("Right Target");
                
                private final Timer timer = new Timer();
                
                public void begin () {
                    state = SNAPPING;
                    timer.start();
                }
                
                public void run () {
                    if (timer.get() > 0.75) {
                        state = PAUSING;
                    } else {
                        leftSnapped  = leftTarget.get();
                        rightSnapped = rightTarget.get();
                    }
                }
                
                public void end () {
                    timer.stop();
                    timer.reset();
                }
            });
            
            add("Pause", new Action() {
                private final Field leftSide  = field("leftSide",  false);
                private final Field rightSide = field("rightSide", false);
                
                public void begin () {
                    state = PAUSING;
                }
                
                public void run () {
                    if (leftSide.on()) {
                        if (!leftSnapped && DriverStation.getInstance().getMatchTime() < 6)
                            return;
                    } else if (rightSide.on()) {
                        if (!rightSnapped && DriverStation.getInstance().getMatchTime() < 6)
                            return;
                    }
                    
                    state = READY;
                }
            });
        }});
    }
    
    protected void begin () {
        state = SNAPPING;
    }
}
