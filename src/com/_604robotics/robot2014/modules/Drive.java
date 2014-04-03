package com._604robotics.robot2014.modules;

import com._604robotics.robotnik.action.Action;
import com._604robotics.robotnik.action.controllers.ElasticController;
import com._604robotics.robotnik.action.Field;
import com._604robotics.robotnik.data.Data;
import com._604robotics.robotnik.data.DataSource;
import com._604robotics.robotnik.data.DataMap;
import com._604robotics.robotnik.module.Module;
import com._604robotics.robotnik.trigger.Trigger;
import com._604robotics.robotnik.trigger.TriggerMap;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.PIDSource.PIDSourceParameter;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Drive extends Module {
    private final RobotDrive drive = new RobotDrive(2, 1);
    
    private final Encoder leftEncoder = new Encoder(2, 1);
    private final Encoder rightEncoder = new Encoder(3, 4);
    
    private final PIDController pid = new PIDController(0.005, 0D, 0.005, leftEncoder, new PIDOutput () {
        public void pidWrite (double output) {
            drive.setLeftRightMotorOutputs(output, output);
        }
    });
    
    public Drive () {
        leftEncoder.setPIDSourceParameter(PIDSourceParameter.kDistance);
        rightEncoder.setPIDSourceParameter(PIDSourceParameter.kDistance);
        
        leftEncoder.start();
        rightEncoder.start();
        
        pid.setAbsoluteTolerance(25);
        SmartDashboard.putData("Drive PID", pid);
        
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
        
        this.set(new TriggerMap() {{
            add("At Servo Target", new Trigger() {
                private final Timer timer = new Timer();
                private boolean timing = false;
                
                public boolean run () {
                    if (pid.isEnable() && pid.onTarget()) {
                        if (!timing) {
                            timing = true;
                            timer.start();
                        }
                        
                        return timer.get() >= 0.5;
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
            addDefault("Off", new Action() {
                public void run () {
                    drive.tankDrive(0D, 0D);
                }
            });
            
            add("Arcade Drive", new Action() {
                private final Field throttle = field("throttle", 0D);
                private final Field turn     = field("turn",     0D);
                
                public void run () {
                    drive.arcadeDrive(throttle.value(), turn.value());
                }
                
                public void end () {
                    drive.stopMotor();
                }
            });
            
            add("Tank Drive", new Action() {
                private final Field left  = field("left",  0D);
                private final Field right = field("right", 0D);
                
                public void run () {
                    drive.tankDrive(left.value(), right.value());
                }
                
                public void end () {
                    drive.stopMotor();
                }
            });
            
            add("Stick Drive", new Action() {
                private final Field throttle = field("throttle", 0D);
                private final Field turn     = field("turn",     0D);
                
                public void run () {
                    drive.arcadeDrive(throttle.value(), turn.value());
                }
                
                public void end () {
                    drive.stopMotor();
                }
            });
            
            add("Servo", new Action() {
                private final Field clicks = field("clicks", 0D);
                private final DataSource leftClicks = data("Left Drive Clicks");
                
                public void begin () {
                    pid.setSetpoint(clicks.value() + leftClicks.get());
                    pid.enable();
                }
                
                public void end () {
                    pid.reset();
                }
            });
            
            add("Forward", new Action() {
                public void run () {
                    drive.setLeftRightMotorOutputs(1.0, 1.0);
                }
                
                public void end () {
                    drive.stopMotor();
                }
            });
        }});
    }
}
