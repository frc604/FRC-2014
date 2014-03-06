package com._604robotics.robot2014.modes;

import com._604robotics.robotnik.coordinator.Coordinator;
import com._604robotics.robotnik.module.ModuleManager;
import com._604robotics.robotnik.prefabs.controller.joystick.JoystickController;
import com._604robotics.robotnik.prefabs.controller.xbox.XboxController;
import com._604robotics.robotnik.prefabs.trigger.TriggerAnd;
import com._604robotics.robotnik.prefabs.trigger.TriggerNot;
import com._604robotics.robotnik.prefabs.trigger.TriggerOr;
import com._604robotics.robotnik.prefabs.trigger.TriggerToggle;
import com._604robotics.robotnik.trigger.TriggerAccess;

public class TeleopMode extends Coordinator {
    //private final JoystickController leftDrive  = new JoystickController(1);
    //private final JoystickController rightDrive = new JoystickController(2);
    
    private final XboxController     driver = new XboxController(1);
    private final JoystickController manipulator = new JoystickController(3);
    
    public TeleopMode () {
        /*leftDrive.axisX.setFactor(-1);
        leftDrive.axisY.setFactor(-1);
        
        rightDrive.axisY.setFactor(-1);*/
        
        manipulator.axisY.setFactor(-1);
        manipulator.axisX.setFactor(-1);
        
        /*leftDrive.axisX.setDeadband(0.3);
        leftDrive.axisY.setDeadband(0.3);
        
        rightDrive.axisY.setDeadband(0.3);*/
        
        manipulator.axisX.setDeadband(0.3);
        manipulator.axisY.setDeadband(0.3);
        
        driver.leftStick.X.setFactor(-1);
        driver.rightStick.X.setFactor(-1);
        
        driver.leftStick.Y.setFactor(-1);
        driver.rightStick.Y.setFactor(-1);
        
        driver.leftStick.X.setDeadband(0.2);
        driver.rightStick.X.setDeadband(0.2);
        
        driver.leftStick.Y.setDeadband(0.2);
        driver.rightStick.Y.setDeadband(0.2);
    }
    
    protected void apply (ModuleManager modules) {
        /* Drive Controller */
        {
            /* Drive */
            {
                bind(modules.getModule("Drive").getAction("Tank Drive"), modules.getModule("Dashboard").getTrigger("Tank Drive"));
                wire(modules.getModule("Drive").getAction("Tank Drive"), "left",  driver.leftStick.Y);
                wire(modules.getModule("Drive").getAction("Tank Drive"), "right", driver.rightStick.Y);
                
                //fill(modules.getModule("Drive").getAction("Tank Drive"), "left", leftDrive.axisY);
                //fill(modules.getModule("Drive").getAction("Tank Drive"), "right", rightDrive.axisY);
                
                bind(modules.getModule("Drive").getAction("Arcade Drive"), modules.getModule("Dashboard").getTrigger("Arcade Drive"));
                wire(modules.getModule("Drive").getAction("Arcade Drive"), "throttle", driver.leftStick.Y);
                wire(modules.getModule("Drive").getAction("Arcade Drive"), "turn",     driver.rightStick.X);
                
                //fill(modules.getModule("Drive").getAction("Arcade Drive"), "throttle", rightDrive.axisY);
                //fill(modules.getModule("Drive").getAction("Arcade Drive"), "turn", leftDrive.axisX);
                
                bind(modules.getModule("Drive").getAction("Stick Drive"), modules.getModule("Dashboard").getTrigger("Stick Drive"));
                wire(modules.getModule("Drive").getAction("Stick Drive"), "throttle", driver.leftStick.Y);
                wire(modules.getModule("Drive").getAction("Stick Drive"), "turn",     driver.rightStick.X);
                
                //fill(modules.getModule("Drive").getAction("Stick Drive"), "throttle", leftDrive.axisY);
                //fill(modules.getModule("Drive").getAction("Stick Drive"), "turn", leftDrive.axisX);
            }
            
            /* Shifter */
            {
                final TriggerToggle toggle = new TriggerToggle(new TriggerOr(new TriggerAccess[] {
                    driver.buttons.RB
                    //leftDrive.buttons.Button1, rightDrive.buttons.Button1
                }), true);
                bind(modules.getModule("Shifter").getAction("Low Gear"),  toggle.off);
                bind(modules.getModule("Shifter").getAction("High Gear"), toggle.on);
            }
        }
        
        /* Manipulator Controller */
        {
            /* Shooter */
            {
                bind(modules.getModule("Shooter").getAction("Retract"), manipulator.buttons.Button7);
                bind(modules.getModule("Shooter").getAction("Deploy"),  manipulator.buttons.Button1);
            }
            
            /* Rotation */
            {
                /* Manual Operation */
                {
                    wire(modules.getModule("Rotation").getAction("Manual"), "power", manipulator.axisY);
                
                    bind(modules.getModule("Rotation").getAction("Hold"),         manipulator.buttons.Button9);
                    bind(modules.getModule("Rotation").getAction("Manual Angle"), manipulator.buttons.Button11);
                }
                
                /* Rotation Presets */
                {
                    final TriggerAccess rotationEnabled = new TriggerNot(manipulator.buttons.Button10);

                    bind(modules.getModule("Rotation").getAction("Ground"), new TriggerAnd(new TriggerAccess[] {
                        new TriggerOr(new TriggerAccess[] {
                            manipulator.buttons.Button2, manipulator.buttons.Button8,
                        }), rotationEnabled
                    }));

                    bind(modules.getModule("Rotation").getAction("Shoot"), new TriggerAnd(new TriggerAccess[] {
                        manipulator.buttons.Button3, rotationEnabled
                    }));
                    bind(modules.getModule("Rotation").getAction("Stow"), new TriggerAnd(new TriggerAccess[] {
                        manipulator.buttons.Button6, rotationEnabled
                    }));
                }
            }
            
            /* Flower */
            {
                bind(modules.getModule("Flower").getAction("Close"),  manipulator.buttons.Button4, true);
                bind(modules.getModule("Flower").getAction("Open"),   manipulator.buttons.Button5);
                bind(modules.getModule("Flower").getAction("Pickup"), manipulator.buttons.Button2);
                bind(modules.getModule("Flower").getAction("Drop"),   manipulator.buttons.Button8);
                bind(modules.getModule("Flower").getAction("Shoot"),  new TriggerOr(new TriggerAccess[] {
                    manipulator.buttons.Button3, manipulator.buttons.Button11
                }));
            }
            
            /* Intake */
            {
                bind(modules.getModule("Intake").getAction("On"), manipulator.buttons.Button2);
            }
            
            /* Truss Macro */
            {
                group(driver.buttons.LB, new Coordinator() {
                    protected void apply(ModuleManager modules) {
                        bind(modules.getModule("Rotation").getAction("Truss"),
                                modules.getModule("Shooter").getTrigger("Deployed").not());
                        
                        bind(modules.getModule("Rotation").getAction("Stow"),
                                modules.getModule("Shooter").getTrigger("Deployed"));
                        
                        bind(modules.getModule("Shooter").getAction("Deploy"),
                                modules.getModule("Shooter").getAction("Deploy").active(), true);
                        
                        bind(modules.getModule("Flower").getAction("Shoot"),
                                modules.getModule("Shooter").getTrigger("Deployed").not());
                        bind(modules.getModule("Flower").getAction("Open"),
                                modules.getModule("Shooter").getTrigger("Deployed"));
                    }
                });
            }
        }
    }
}
