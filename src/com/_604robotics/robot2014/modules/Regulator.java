package com._604robotics.robot2014.modules;

import com._604robotics.robotnik.action.Action;
import com._604robotics.robotnik.action.ActionData;
import com._604robotics.robotnik.action.controllers.ElasticController;
import com._604robotics.robotnik.module.Module;
import com._604robotics.robotnik.trigger.Trigger;
import com._604robotics.robotnik.trigger.TriggerMap;
import edu.wpi.first.wpilibj.Compressor;

/*controls the compressor for pneumatics*/
public class Regulator extends Module {
    private final Compressor compressor = new Compressor(5/*pressure switch channel*/, 1/*compressor Relay Channel*/);
    
    public Regulator () {
        this.set(new TriggerMap() {{
            add("Charged", new Trigger() {
                public boolean run () {
                    return compressor.getPressureSwitchValue(); //how much pressure
                }
            });
        }});
        
        this.set(new ElasticController() {{
            addDefault("On", new Action() {
                public void begin (ActionData data) { //starts the compressor
                    compressor.start();
                }
                
                public void end (ActionData data) { //stops the compressor
                    compressor.stop();
                }
            });
            
            add("Off", new Action() {});
        }});
    }
}
