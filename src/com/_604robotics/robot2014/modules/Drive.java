package com._604robotics.robot2014.modules;

import com._604robotics.robotnik.action.Action;
import com._604robotics.robotnik.action.ActionData;
import com._604robotics.robotnik.action.controllers.ElasticController;
import com._604robotics.robotnik.action.field.FieldMap;
import com._604robotics.robotnik.module.Module;
import edu.wpi.first.wpilibj.RobotDrive;

public class Drive extends Module {
    private final RobotDrive drive = new RobotDrive(1, 2);
    
    public Drive () {
        this.set(new ElasticController() {{
            addDefault("Arcade Drive", new Action(new FieldMap () {{
                define("throttle", 0D);
                define("wheel", 0D);
            }}) {
                public void run (ActionData data) {
                    drive.arcadeDrive(data.get("throttle"), data.get("wheel"));
                }
                
                public void end (ActionData data) {
                    drive.stopMotor();
                }
            });
        }});
    }
}
