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
    private final RobotDrive drive = new RobotDrive(1, 7);
    
    private final Encoder leftEncoder = new Encoder(2, 3);
    private final Encoder rightEncoder = new Encoder(6, 7);
    
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
                define("wheel", 0D);
                define("throttle", 0D);
            }}) {
                public void run (ActionData data) {
                    drive.arcadeDrive(scale(data.get("throttle")), scale(data.get("wheel")));
                }
                
                public void end (ActionData data) {
                    drive.stopMotor();
                }
                
                private double scale (double x) {
                    return Math.sqrt(Math.abs(x)) * signum(x);
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
            
            add("Quix Drive", new Action(new FieldMap () {{
                define("wheel", 0D);
                define("throttle", 0D);
                
                define("sharp", false);
                define("highGear", false);
            }}) {
                private double oldWheel  = 0D;
                private double quickstop = 0D;
                
                public void run (ActionData data) {
                    final boolean sharp    = data.is("sharp");
                    final boolean highGear = data.is("highGear");
                    
                    double wheel    = data.get("wheel");
                    double throttle = data.get("throttle");
                    
                    wheel  = scaleWheel(wheel, highGear ? 0.5 : 0.6
                                             , highGear ? 3   : 2
                                       );
                    wheel += calculateInertia(wheel, highGear);
                    wheel  = calculateQuickstop(wheel, throttle, sharp, highGear);
                    
                    double left  = throttle + wheel;
                    double right = throttle - wheel;
                    
                    if (sharp) {
                        if (left > 1)
                            right -= left  - 1;
                        else if (right > 1)
                            left  -= right - 1;
                        else if (left < -1)
                            right += -1 - left;
                        else if (right < -1)
                            left  += -1 - right;
                    }
                    
                    drive.setLeftRightMotorOutputs(left, right);
                }
                
                public void end (ActionData data) {
                    drive.stopMotor();
                }
                
                private double scaleWheel (double x, double factor, int iterations) {
                    for (int i = 0; i < iterations; i++)
                        x = Math.sin(Math.PI / 2 * factor * x)
                                / Math.sin(Math.PI / 2 * factor);
                    return x;
                }
                
                private double calculateInertia (double wheel, boolean highGear) {
                    final double delta = wheel - oldWheel;
                    oldWheel = wheel;
                    
                    if (highGear) {
                        return delta * 5.0;
                    } else if (wheel * delta > 0) {
                        return delta * 2.5;
                    } else if (Math.abs(wheel) > 0.65) {
                        return delta * 5.0;
                    } else {
                        return delta * 3.0;
                    }
                }
                
                private double calculateQuickstop (double wheel, double throttle, boolean sharp, boolean highGear) {
                    if (sharp) {
                        quickstop = 0.9 * quickstop + 0.1 * limit(wheel, 1) * 5;
                    } else {
                        final double sensitivity = highGear ? 0.85 : 0.75;
                        wheel = Math.abs(throttle) * wheel * sensitivity - quickstop;

                        if (quickstop > 1) {
                            quickstop -= 1;
                        } else if (quickstop < -1) {
                            quickstop += 1;
                        } else {
                            quickstop = 0;
                        }
                    }
                    
                    return wheel;
                }
            });
        }});
    }     

    private static double signum(double x) {
        return x > 0 ? 1 : (x < 0 ? -1 : 0);
    }
               
    private static double limit(double x, double max) {
        if (Math.abs(x) > max)
            return max * signum(x);
        else
            return x;
    }
}
