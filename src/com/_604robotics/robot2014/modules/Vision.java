package com._604robotics.robot2014.modules;

import com._604robotics.robotnik.action.Action;
import com._604robotics.robotnik.action.ActionData;
import com._604robotics.robotnik.action.controllers.ElasticController;
import com._604robotics.robotnik.logging.Logger;
import com._604robotics.robotnik.module.Module;
import com._604robotics.robotnik.trigger.Trigger;
import com._604robotics.robotnik.trigger.TriggerMap;
import com._604robotics.robotnik.trigger.sources.DashboardTriggerChoice;
import com._604robotics.robotnik.trigger.sources.NetworkTrigger;
import edu.wpi.first.wpilibj.Timer;

public class Vision extends Module {
    private boolean ready = false;
    
    public Vision () {
        this.set(new TriggerMap() {{
            final DashboardTriggerChoice autonSide = new DashboardTriggerChoice("Auton Side");
            add("Left Side", autonSide.addDefault("Autonomous: Left Side"));
            add("Right Side", autonSide.add("Autonomous: Right Side"));
            
            add("Left Target",  new NetworkTrigger("robot2014.vision", "leftTarget",  false));
            add("Right Target", new NetworkTrigger("robot2014.vision", "rightTarget", false));
            
            add("Ready", new Trigger() {
                public boolean run () {
                    return ready;
                }
            });
        }});
        
        this.set(new ElasticController() {{
            addDefault("Monitor", new Action() {
                private final Timer timeout = new Timer();
                
                public void begin (ActionData data) {
                    ready = false;
                    
                    timeout.start();
                }
                
                public void run (ActionData data) {
                    if (data.trigger("Left Side") && data.trigger("Left Target")) {
                        ready = true;
                    } else if (data.trigger("Right Side") && data.trigger("Right Target")) {
                        ready = true;
                    } else if (timeout.get() > 7.0) {
                        if (ready != true) {
                            Logger.warn("Vision timed out! Dashboard setting is " +
                                    (data.trigger("Left Side")  ? "Left Side"  :
                                     data.trigger("Right Side") ? "Right Side" :
                                                                  "No Side") + ".");
                        }
                        ready = true;
                    }
                }
                
                public void end (ActionData data) {
                    ready = false;
                    
                    timeout.stop();
                    timeout.reset();
                }
            });            
        }});
    }
}
