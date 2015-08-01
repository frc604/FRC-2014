package com._604robotics.robot2014.modules;

import com._604robotics.robot2014.utils.AntiWindupPIDController;
import com._604robotics.robotnik.action.Action;
import com._604robotics.robotnik.action.ActionData;
import com._604robotics.robotnik.action.controllers.ElasticController;
import com._604robotics.robotnik.action.field.FieldMap;
import com._604robotics.robotnik.data.Data;
import com._604robotics.robotnik.data.DataMap;
import com._604robotics.robotnik.module.Module;
import com._604robotics.robotnik.prefabs.devices.MA3A10;
import com._604robotics.robotnik.trigger.Trigger;
import com._604robotics.robotnik.trigger.TriggerMap;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Rotation extends Module {
    private final MA3A10 encoder = new MA3A10(1, 1);
    private final Victor motor = new Victor(3);
    
    private final AntiWindupPIDController pid = new AntiWindupPIDController(-0.025, -0.003, 10, 0.9, -0.020, encoder, motor, 0.01);
    
    private double baseAngle = 196D;
            
    public Rotation () {
        SmartDashboard.putData("Rotation PID", pid);
        
        pid.setAbsoluteTolerance(2);
        
        this.set(new DataMap() {{
            add("Base Angle", new Data() {
                public double run () {
                    return baseAngle;
                }
            });
            
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
        
        this.set(new TriggerMap() {{
            add("At Angle Target", new Trigger() {
                private final Timer timer = new Timer();
                private boolean timing = false;
                
                public boolean run () {
                    if (pid.isEnable() && pid.onTarget()) {
                        if (!timing) {
                            timing = true;
                            timer.start();
                        }
                        
                        return timer.get() >= 0.25;
                    } else {
                        if (timing) {
                            timing = false;
                            
                            timer.stop();
                            timer.reset();
                        }
                        
                        return false;
                    }
                }
            });
        }});
        
        this.set(new ElasticController() {{
            addDefault("Manual", new Action(new FieldMap() {{
                define("power", 0D);
                define("calibrate", false);
            }}) {
                private final Timer calibrateTimer = new Timer();
                private boolean calibrating = false;
                
                public void run (ActionData data) {
                    motor.set(data.get("power"));
                    if (data.is("calibrate")) {
                        if (!calibrating) {
                            calibrating = true;
                            calibrateTimer.start();
                        }
                        
                        if (calibrateTimer.get() > 2) {
                            baseAngle = encoder.getAngle();
                        }
                    } else {
                        calibrating = false;
                        calibrateTimer.stop();
                        calibrateTimer.reset();
                    }
                }
                
                public void end (ActionData data) {
                    motor.stopMotor();
                }
            });
            
            add("Stow",   new AngleAction());
            add("Shoot",  new AngleAction());
            add("Auton",  new AngleAction());
            add("Ground", new AngleAction());
            
            add("Hold", new Action() {
                public void begin (ActionData data) {
                    pid.setSetpoint(data.data("Encoder Angle"));
                    pid.enable();
                }
                
                public void end (ActionData data) {
                    pid.reset();
                }
            });
        }});
    }
    
    private class AngleAction extends Action {
        public AngleAction () {
            super(new FieldMap() {{
                define("angle", 0D);
            }});
        }

        public void begin(ActionData data) {
            pid.setSetpoint(baseAngle + data.get("angle"));
            pid.enable();
        }

        public void run(ActionData data) {
            final double setpoint = baseAngle + data.get("angle");
            if (setpoint != pid.getSetpoint()) {
                pid.setSetpoint(setpoint);
            }
        }

        public void end(ActionData data) {
            pid.reset();
        }
    }
}
