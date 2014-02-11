package com._604robotics.robot2014.modules;

import com._604robotics.robotnik.action.Action;
import com._604robotics.robotnik.action.ActionData;
import com._604robotics.robotnik.action.controllers.StateController;
import com._604robotics.robotnik.action.field.FieldMap;
import com._604robotics.robotnik.data.Data;
import com._604robotics.robotnik.data.DataMap;
import com._604robotics.robotnik.module.Module;
import com._604robotics.robotnik.prefabs.devices.MA3A10;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Rotation extends Module {
    private final MA3A10 encoder = new MA3A10(2, 1);
    private final Victor motor = new Victor(3);
    
    private final PIDController pid = new PIDController(-0.05, 0, 0, encoder, motor);
            
    public Rotation () {
        SmartDashboard.putData("Rotation PID", pid);
        
        this.set(new DataMap() {{
            add("Encoder Ticks", new Data() {
                public double run () {
                    return encoder.getRaw();
                }
            });
            
            add("Encoder Angle", new Data() {
                public double run () {
                    return encoder.getAngle();
                }
            });
        }});
        
        this.set(new StateController() {{
            add("Manual", new Action(new FieldMap() {{
                define("power", 0D);
            }}) {
                public void run (ActionData data) {
                    motor.set(data.get("power"));
                }
                
                public void end (ActionData data) {
                    motor.stopMotor();
                }
            });
            
            add("Manual Angle", new Action(new FieldMap() {{
                define("setpoint", 0D);
            }}) {
                public void begin (ActionData data) {
                    pid.setSetpoint(data.get("setpoint"));
                    pid.enable();
                }
                
                public void run (ActionData data) {
                    final double setpoint = data.get("setpoint");
                    if (setpoint != pid.getSetpoint())
                        pid.setSetpoint(setpoint);
                }
                
                public void end (ActionData data) {
                    pid.reset();
                }
            });
            
            addDefault("Stow", new AngleAction(215D));
            add("Shoot", new AngleAction(180D));
            add("Pickup", new AngleAction(108D));
        }});
    }
    
    private class AngleAction extends Action {
        private final double angle;
        
        public AngleAction (double angle) {
            this.angle = angle;
        }
        
        public void begin(ActionData data) {
            pid.setSetpoint(angle);
            pid.enable();
        }

        public void end(ActionData data) {
            pid.reset();
        }
    }
}
