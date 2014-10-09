package com._604robotics.robot2014.modules;

import com._604robotics.robotnik.action.Action;
import com._604robotics.robotnik.action.ActionData;
import com._604robotics.robotnik.action.controllers.ElasticController;
import com._604robotics.robotnik.action.field.FieldMap;
import com._604robotics.robotnik.data.Data;
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
    private final RobotDrive drive = new RobotDrive(2, 1); //left, right motors on ports 2,1
    
    private final Encoder leftEncoder = new Encoder(2, 1); //left encoder on channels 2,1
    private final Encoder rightEncoder = new Encoder(3, 4); //right encoder on channels 3,4
    
    private final PIDController pid = new PIDController(0.005, 0D, 0.005, leftEncoder, new PIDOutput () {
        public void pidWrite (double output) {
            drive.setLeftRightMotorOutputs(output, output); //set the speed of the left and right motors
        }
    });
    
    public Drive () {
        leftEncoder.setPIDSourceParameter(PIDSourceParameter.kDistance);
        rightEncoder.setPIDSourceParameter(PIDSourceParameter.kDistance);
        
        leftEncoder.start();
        rightEncoder.start();
        
        pid.setAbsoluteTolerance(25); //set the error range for which onTarget can still return true
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
            add("At Servo Target", new Trigger() {  //I wanna say this will run the servo for .5 seconds. 
                private final Timer timer = new Timer();
                private boolean timing = false;
                
                public boolean run () {
                    if (pid.isEnable() && pid.onTarget()) { //if the pid controller is on and is on within the tolerance
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
                public void run (ActionData data) {
                    drive.tankDrive(0D, 0D); //leftstick and right stick values. not sure what that means exactly.
                }
            });
            
            add("Arcade Drive", new Action(new FieldMap () {{
                define("throttle", 0D); //the throttle stick value
                define("turn", 0D); //the turn stick value
            }}) {
                public void run (ActionData data) {
                    drive.arcadeDrive(data.get("throttle"), data.get("turn")); //sets the throttle and turn stick values
                }
                
                public void end (ActionData data) {
                    drive.stopMotor(); //stops the motor I suppose
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
            
            add("Stick Drive", new Action(new FieldMap () {{ // I'm almost completely sure this is exaclty the same as arcade drive but I could be wrong
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
            
            add("Servo", new Action(new FieldMap() {{
                define("clicks", 0D);
            }}) {
                public void begin (ActionData data) {
                    pid.setSetpoint(data.get("clicks") + data.data("Left Drive Clicks")); //set the clicks and the clicks to the desired value?
                    pid.enable(); //start running the pid controller
                }
                
                public void end (ActionData data) {
                    pid.reset(); //Reset the previous error, the integral term, and disable the controller
                }
            });
            
            add("Forward", new Action() {
                public void run (ActionData data) {
                    drive.setLeftRightMotorOutputs(1.0, 1.0); // the speeds of the left an right motor become 1
                }
                
                public void end (ActionData data) {
                    drive.stopMotor(); //stops the motor
                }
            });
        }});
    }
}
