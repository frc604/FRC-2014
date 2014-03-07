package com._604robotics.robot2014.modes;

import com._604robotics.robotnik.coordinator.Coordinator;
import com._604robotics.robotnik.coordinator.connectors.Binding;
import com._604robotics.robotnik.coordinator.connectors.DataWire;
import com._604robotics.robotnik.coordinator.connectors.Group;
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
    //private final JoystickController rightDrive = new JgoystickController(2);
    
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
                this.bind(new Binding(modules.getModule("Drive").getAction("Tank Drive"), modules.getModule("Dashboard").getTrigger("Tank Drive")));
                this.fill(new DataWire(modules.getModule("Drive").getAction("Tank Drive"), "left",  driver.leftStick.Y));
                this.fill(new DataWire(modules.getModule("Drive").getAction("Tank Drive"), "right", driver.rightStick.Y));
                
                //this.fill(new DataWire(modules.getModule("Drive").getAction("Tank Drive"), "left", leftDrive.axisY));
                //this.fill(new DataWire(modules.getModule("Drive").getAction("Tank Drive"), "right", rightDrive.axisY));
                
                this.bind(new Binding(modules.getModule("Drive").getAction("Arcade Drive"), modules.getModule("Dashboard").getTrigger("Arcade Drive")));
                this.fill(new DataWire(modules.getModule("Drive").getAction("Arcade Drive"), "throttle", driver.leftStick.Y));
                this.fill(new DataWire(modules.getModule("Drive").getAction("Arcade Drive"), "turn",     driver.rightStick.X));
                
                //this.fill(new DataWire(modules.getModule("Drive").getAction("Arcade Drive"), "throttle", rightDrive.axisY));
                //this.fill(new DataWire(modules.getModule("Drive").getAction("Arcade Drive"), "turn", leftDrive.axisX));
                
                this.bind(new Binding(modules.getModule("Drive").getAction("Stick Drive"), modules.getModule("Dashboard").getTrigger("Stick Drive")));
                this.fill(new DataWire(modules.getModule("Drive").getAction("Stick Drive"), "throttle", driver.leftStick.Y));
                this.fill(new DataWire(modules.getModule("Drive").getAction("Stick Drive"), "turn",     driver.rightStick.X));
                
                //this.fill(new DataWire(modules.getModule("Drive").getAction("Stick Drive"), "throttle", leftDrive.axisY));
                //this.fill(new DataWire(modules.getModule("Drive").getAction("Stick Drive"), "turn", leftDrive.axisX));
            }
            
            /* Shifter */
            {
                final TriggerToggle toggle = new TriggerToggle(new TriggerOr(new TriggerAccess[] {
                    driver.buttons.RB
                    //leftDrive.buttons.Button1, rightDrive.buttons.Button1
                }), true);
                this.bind(new Binding(modules.getModule("Shifter").getAction("Low Gear"),  toggle.off));
                this.bind(new Binding(modules.getModule("Shifter").getAction("High Gear"), toggle.on));
            }
        }
        
        /* Manipulator Controller */
        {
            /* Shooter */
            {
                this.bind(new Binding(modules.getModule("Shooter").getAction("Retract"), manipulator.buttons.Button7));
                this.bind(new Binding(modules.getModule("Shooter").getAction("Deploy"),  manipulator.buttons.Button1));
            }
            
            /* Rotation */
            {
                /* Manual Operation */
                {
                    this.fill(new DataWire(modules.getModule("Rotation").getAction("Manual"), "power", manipulator.axisY));
                
                    this.bind(new Binding(modules.getModule("Rotation").getAction("Hold"),         manipulator.buttons.Button9));
                    this.bind(new Binding(modules.getModule("Rotation").getAction("Manual Angle"), manipulator.buttons.Button11));
                }
                
                /* Rotation Presets */
                {
                    final TriggerAccess rotationEnabled = new TriggerNot(manipulator.buttons.Button10);

                    this.bind(new Binding(modules.getModule("Rotation").getAction("Ground"), new TriggerAnd(new TriggerAccess[] {
                        new TriggerOr(new TriggerAccess[] {
                            manipulator.buttons.Button2, manipulator.buttons.Button8,
                        }), rotationEnabled
                    })));

                    this.bind(new Binding(modules.getModule("Rotation").getAction("Shoot"), new TriggerAnd(new TriggerAccess[] {
                        manipulator.buttons.Button3, rotationEnabled
                    })));
                    this.bind(new Binding(modules.getModule("Rotation").getAction("Stow"), new TriggerAnd(new TriggerAccess[] {
                        manipulator.buttons.Button6, rotationEnabled
                    })));
                }
            }
            
            /* Flower */
            {
                this.bind(new Binding(modules.getModule("Flower").getAction("Close"),  manipulator.buttons.Button4, true));
                this.bind(new Binding(modules.getModule("Flower").getAction("Open"),   manipulator.buttons.Button5));
                this.bind(new Binding(modules.getModule("Flower").getAction("Pickup"), manipulator.buttons.Button2));
                this.bind(new Binding(modules.getModule("Flower").getAction("Drop"),   manipulator.buttons.Button8));
                this.bind(new Binding(modules.getModule("Flower").getAction("Shoot"),  new TriggerOr(new TriggerAccess[] {
                    manipulator.buttons.Button3, manipulator.buttons.Button11
                })));
            }
            
            /* Intake */
            {
                this.bind(new Binding(modules.getModule("Intake").getAction("On"), manipulator.buttons.Button2));
            }
        }
    }
}
