package com._604robotics.robot2014.modules;

import com._604robotics.robotnik.action.Action;
import com._604robotics.robotnik.action.ActionData;
import com._604robotics.robotnik.action.controllers.ElasticController;
import com._604robotics.robotnik.action.field.FieldMap;
import com._604robotics.robotnik.data.Data;
import com._604robotics.robotnik.data.DataMap;
import com._604robotics.robotnik.module.Module;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.RobotDrive;

public class Drive extends Module {
    private final RobotDrive drive = new RobotDrive(2, 1);
    
    private final Encoder leftEncoder = new Encoder(1, 2);
    private final Encoder rightEncoder = new Encoder(3, 4);
    
    public Drive () {
        leftEncoder.start();
        rightEncoder.start();
        
        this.set(new DataMap() {{
            add("Left Drive Clicks", new Data() {
                public double run () {
                    return leftEncoder.get();
                }
            });
            
            add("Right Drive Clicks", new Data() {
                public double run () {
                    return rightEncoder.get();
                }
            });
            
            add("Left Drive Rate", new Data() {
                public double run () {
                    return leftEncoder.getRate();
                }
            });
            
            add("Right Drive Rate", new Data() {
                public double run () {
                    return rightEncoder.getRate();
                }
            });
        }});
        
        this.set(new ElasticController() {{
            addDefault("Off", new Action() {
                public void run (ActionData data) {
                    drive.stopMotor();
                }
            });
            
            add("Arcade Drive", new Action(new FieldMap () {{
                define("throttle", 0D);
                define("turn", 0D);
            }}) {
                public void run (ActionData data) {
                    drive.arcadeDrive(data.get("throttle"), data.get("turn"));
                }
                
                public void end (ActionData data) {
                    drive.stopMotor();
                }
            });
            
            add("Tank Drive", new Action(new FieldMap () {{
                define("left", 0D);
                define("right", 0D);
            }}) {
                public void run (ActionData data) {
                    drive.tankDrive(data.get("left"), data.get("right"));
                }
                
                public void end (ActionData data) {
                    drive.stopMotor();
                }
            });
        }});
    }
}
