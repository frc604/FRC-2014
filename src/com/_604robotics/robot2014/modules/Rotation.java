package com._604robotics.robot2014.modules;

import com._604robotics.robotnik.action.Action;
import com._604robotics.robotnik.action.Field;
import com._604robotics.robotnik.action.controllers.ElasticController;
import com._604robotics.robotnik.data.Data;
import com._604robotics.robotnik.data.DataSource;
import com._604robotics.robotnik.data.DataMap;
import com._604robotics.robotnik.module.Module;
import com._604robotics.robotnik.prefabs.devices.MA3A10;
import com._604robotics.robotnik.trigger.Trigger;
import com._604robotics.robotnik.trigger.TriggerMap;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Rotation extends Module {
    private final MA3A10 encoder = new MA3A10(1, 1);
    private final Victor motor = new Victor(3);
    
    private final PIDController pid = new PIDController(-0.025, 0, -0.025, encoder, motor);
            
    public Rotation () {
        SmartDashboard.putData("Rotation PID", pid);
        
        pid.setAbsoluteTolerance(4);
        
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
            addDefault("Manual", new Action() {
                private final Field power = field("power", 0D);
                
                public void run () {
                    motor.set(power.value());
                }
                
                public void end () {
                    motor.stopMotor();
                }
            });
            
            add("Manual Angle", new AngleAction());
            
            add("Stow",   new AngleAction());
            add("Shoot",  new AngleAction());
            add("Ground", new AngleAction());
            add("Truss",  new AngleAction());
            
            add("Hold", new Action() {
                private final DataSource angle = data("Encoder Angle");
                
                public void begin () {
                    pid.setSetpoint(angle.get());
                    pid.enable();
                }
                
                public void end () {
                    pid.reset();
                }
            });
        }});
    }
    
    private class AngleAction extends Action {
        private final Field angle = field("angle", 0D);
        
        public void begin() {
            pid.setSetpoint(angle.value());
            pid.enable();
        }

        public void run() {
            final double setpoint = angle.value();
            if (setpoint != pid.getSetpoint())
                pid.setSetpoint(setpoint);
        }

        public void end() {
            pid.reset();
        }
    }
}
