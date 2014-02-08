package com._604robotics.robot2014.modules;

import com._604robotics.robotnik.action.Action;
import com._604robotics.robotnik.action.ActionData;
import com._604robotics.robotnik.action.controllers.StateController;
import com._604robotics.robotnik.module.Module;
import com._604robotics.robotnik.prefabs.devices.MA3A10;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.Victor;

public class Rotation extends Module {
    private final MA3A10 encoder = new MA3A10(1);
    private final Victor motor = new Victor(3);
    
    private final PIDController pidController = new PIDController(0, 0, 0,encoder,motor);
            
    public Rotation () {
        this.set(new StateController() {{
            addDefault("Stow", new AngleAction(0D));
            add("Shoot", new AngleAction(45D));
            add("Pickup", new AngleAction(120D));
        }});
    }
    
    private class AngleAction extends Action {
        private final double angle;
        
        public AngleAction (double angle) {
            this.angle = angle;
        }
        
        public void begin(ActionData data) {
            pidController.setSetpoint(angle);
            pidController.enable();
        }

        public void end(ActionData data) {
            pidController.reset();
        }
    }
}
