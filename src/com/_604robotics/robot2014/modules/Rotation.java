package com._604robotics.robot2014.modules;

import com._604robotics.robotnik.action.Action;
import com._604robotics.robotnik.action.ActionData;
import com._604robotics.robotnik.action.controllers.ElasticController;
import com._604robotics.robotnik.action.field.FieldMap;
import com._604robotics.robotnik.module.Module;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.Victor;

public class Rotation extends Module {
    private final Victor motor = new Victor(4);
    private final Encoder encoder = new Encoder (5,6);
    
    private final PIDController pidController = new PIDController(0,0,0,encoder,motor);
            
    public Rotation () {
        this.set(new ElasticController () {{
            addDefault("Position", new Action(new FieldMap () {{
                define("angle", 0D);
            }}) {
                public void begin (ActionData data) {
                    pidController.enable(); 
                }
                public void run (ActionData data)  {
                    pidController.setSetpoint(data.get("angle"));
                }
                public void end (ActionData data){
                    pidController.reset();
                }
            });
            
            
           
                
            
        }});
    }
}
