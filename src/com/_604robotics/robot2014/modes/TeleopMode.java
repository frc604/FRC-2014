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
import edu.wpi.first.wpilibj.Joystick;

public class TeleopMode extends Coordinator {
    //private final JoystickController leftDrive  = new JoystickController(1);
    //private final JoystickController rightDrive = new JgoystickController(2);
    
    private final XboxController driverXbox = new XboxController(1);
    private final Joystick manipulator = new Joystick(2);
    private final XboxController manipXbox = new XboxController(manipulator);
    private final JoystickController manipJoystick = new JoystickController(manipulator);
    private final XboxController overrideXbox = new XboxController(3);

    
    public TeleopMode () {
        manipXbox.leftStick.Y.setFactor(-1);
        manipJoystick.axisX.setFactor(-1);
        
        manipXbox.leftStick.Y.setDeadband(0.2);
        manipJoystick.axisY.setDeadband(0.3);
        
        driverXbox.leftStick.X.setFactor(-1);
        driverXbox.rightStick.X.setFactor(-1);
        
        driverXbox.leftStick.Y.setFactor(-1);
        driverXbox.rightStick.Y.setFactor(-1);
        
        driverXbox.leftStick.X.setDeadband(0.2);
        driverXbox.rightStick.X.setDeadband(0.2);
        
        driverXbox.leftStick.Y.setDeadband(0.2);
        driverXbox.rightStick.Y.setDeadband(0.2);
        
        overrideXbox.leftStick.X.setFactor(-1);
        overrideXbox.rightStick.X.setFactor(-1);
        
        overrideXbox.leftStick.Y.setFactor(-1);
        overrideXbox.rightStick.Y.setFactor(-1);
        
        overrideXbox.leftStick.X.setDeadband(0.2);
        overrideXbox.rightStick.X.setDeadband(0.2);
        
        overrideXbox.leftStick.Y.setDeadband(0.2);
        overrideXbox.rightStick.Y.setDeadband(0.2);
        
        
    }
    
    protected void apply (ModuleManager modules) {
        /* Shared Controls */
        {
            /* Drive */
            {
                this.bind(new Binding(modules.getModule("Drive").getAction("Tank Drive"), modules.getModule("Dashboard").getTrigger("Tank Drive")));
                this.fill(new DataWire(modules.getModule("Drive").getAction("Tank Drive"), "left", driverXbox.leftStick.Y));
                this.fill(new DataWire(modules.getModule("Drive").getAction("Tank Drive"), "right", driverXbox.rightStick.Y));

                this.bind(new Binding(modules.getModule("Drive").getAction("Arcade Drive"), modules.getModule("Dashboard").getTrigger("Arcade Drive")));
                this.fill(new DataWire(modules.getModule("Drive").getAction("Arcade Drive"), "throttle", driverXbox.leftStick.Y));
                this.fill(new DataWire(modules.getModule("Drive").getAction("Arcade Drive"), "turn", driverXbox.rightStick.X));

                this.bind(new Binding(modules.getModule("Drive").getAction("Stick Drive"), modules.getModule("Dashboard").getTrigger("Stick Drive")));
                this.fill(new DataWire(modules.getModule("Drive").getAction("Stick Drive"), "throttle", driverXbox.leftStick.Y));
                this.fill(new DataWire(modules.getModule("Drive").getAction("Stick Drive"), "turn", driverXbox.leftStick.X));
            }
        }
        
        /* Xbox-Xbox Mode */
        this.group(new Group(modules.getModule("Dashboard").getTrigger("Xbox-Xbox"), new Coordinator() {
            protected void apply(ModuleManager modules) {
                /* Shifter */
                {
                    final TriggerToggle toggle = new TriggerToggle(driverXbox.buttons.RB, false);
                    this.bind(new Binding(modules.getModule("Shifter").getAction("Low Gear"), toggle.off));
                    this.bind(new Binding(modules.getModule("Shifter").getAction("High Gear"), toggle.on));
                }

                /* Shooter */
                {
                    this.bind(new Binding(modules.getModule("Shooter").getAction("Retract"), manipXbox.buttons.RB));
                    this.bind(new Binding(modules.getModule("Shooter").getAction("Deploy"), new TriggerAnd(new TriggerAccess[] {
                        new TriggerOr(new TriggerAccess[] {
                            modules.getModule("Dashboard").getTrigger("Safety Enabled").not(),
                            driverXbox.buttons.LB
                        }),
                        manipXbox.buttons.LeftStick,
                        manipXbox.buttons.RT
                    })));
                }
                
                /* Rotation */
                {
                    /* Manual Operation */
                    {
                        this.fill(new DataWire(modules.getModule("Rotation").getAction("Manual"), "power", manipXbox.leftStick.Y));
                        this.bind(new Binding(modules.getModule("Rotation").getAction("Hold"), manipXbox.buttons.X));
                    }

                    /* Rotation Presets */
                    {
                        this.bind(new Binding(modules.getModule("Rotation").getAction("Ground"),
                            manipXbox.buttons.A));
                        this.bind(new Binding(modules.getModule("Rotation").getAction("Shoot"),
                            manipXbox.buttons.B));
                        this.bind(new Binding(modules.getModule("Rotation").getAction("Stow"),
                            manipXbox.buttons.Y));
                    }
                }

                /* Flower */
                {
                    this.bind(new Binding(modules.getModule("Flower").getAction("Close"),
                        new TriggerOr(new TriggerAccess[] {
                            modules.getModule("Rotation").getAction("Ground").active(),
                            modules.getModule("Rotation").getAction("Stow").active()
                        })));
                    this.bind(new Binding(modules.getModule("Flower").getAction("Shoot"),
                        modules.getModule("Rotation").getAction("Shoot").active()));
                }

                /* Intake */
                {
                    this.bind(new Binding(modules.getModule("Intake").getAction("Suck"),
                        manipXbox.buttons.LT));
                    this.bind(new Binding(modules.getModule("Intake").getAction("Spit"),
                        manipXbox.buttons.LB));
                }
            }
        }));
        
        /* Demo Mode */
        this.group(new Group(modules.getModule("Dashboard").getTrigger("Demo Mode"), new Coordinator() {
            protected void apply(ModuleManager modules) {
                                
                /* Shifter */
                {
                    final TriggerToggle toggle = new TriggerToggle(driverXbox.buttons.RB, false);
                    this.bind(new Binding(modules.getModule("Shifter").getAction("Low Gear"), toggle.off));
                    this.bind(new Binding(modules.getModule("Shifter").getAction("High Gear"), toggle.on));
                }
                
                /* Shooter */
                    this.bind(new Binding(modules.getModule("Shooter").getAction("Retract"), manipXbox.buttons.RB));
                    this.bind(new Binding(modules.getModule("Shooter").getAction("Deploy"), new TriggerAnd(new TriggerAccess[] {
                        new TriggerOr(new TriggerAccess[] {
                            modules.getModule("Dashboard").getTrigger("Safety Enabled").not(),
                            driverXbox.buttons.LB
                        }),
                        manipXbox.buttons.LeftStick,
                        manipXbox.buttons.RT
                    })));
                
                /* Rotation */
                    /* Manual Operation */
                        this.fill(new DataWire(modules.getModule("Rotation").getAction("Manual"), "power", manipXbox.leftStick.Y));
                        this.bind(new Binding(modules.getModule("Rotation").getAction("Hold"), manipXbox.buttons.X));

                    /* Rotation Presets */
                        this.bind(new Binding(modules.getModule("Rotation").getAction("Ground"),
                            manipXbox.buttons.A));
                        this.bind(new Binding(modules.getModule("Rotation").getAction("Shoot"),
                            manipXbox.buttons.B));
                        this.bind(new Binding(modules.getModule("Rotation").getAction("Stow"),
                            manipXbox.buttons.Y));

                /* Flower */
                    this.bind(new Binding(modules.getModule("Flower").getAction("Close"),
                        new TriggerOr(new TriggerAccess[] {
                            modules.getModule("Rotation").getAction("Ground").active(),
                            modules.getModule("Rotation").getAction("Stow").active()
                        })));
                    this.bind(new Binding(modules.getModule("Flower").getAction("Shoot"),
                        modules.getModule("Rotation").getAction("Shoot").active()));

                /* Intake */
                    this.bind(new Binding(modules.getModule("Intake").getAction("Suck"),
                        manipXbox.buttons.LT));
                    this.bind(new Binding(modules.getModule("Intake").getAction("Spit"),
                        manipXbox.buttons.LB));
            }
        }));
        
        /* Xbox-Joystick Mode */
        this.group(new Group(modules.getModule("Dashboard").getTrigger("Xbox-Joystick"), new Coordinator() {
            protected void apply(ModuleManager modules) {
                /* Shifter */
                {
                    final TriggerToggle toggle = new TriggerToggle(driverXbox.buttons.RB, false);
                    this.bind(new Binding(modules.getModule("Shifter").getAction("Low Gear"), toggle.off));
                    this.bind(new Binding(modules.getModule("Shifter").getAction("High Gear"), toggle.on));
                }

                /* Shooter */
                {
                    this.bind(new Binding(modules.getModule("Shooter").getAction("Retract"), manipJoystick.buttons.Button7));
                    this.bind(new Binding(modules.getModule("Shooter").getAction("Deploy"), new TriggerAnd(new TriggerAccess[] {
                        new TriggerOr(new TriggerAccess[] {
                            modules.getModule("Dashboard").getTrigger("Safety Enabled").not(),
                            driverXbox.buttons.LB
                        }),
                        manipJoystick.buttons.Button1
                    })));
                }
                
                /* Rotation */
                {
                    /* Manual Operation */
                    {
                        this.fill(new DataWire(modules.getModule("Rotation").getAction("Manual"), "power", manipJoystick.axisY));
                        this.bind(new Binding(modules.getModule("Rotation").getAction("Hold"), manipJoystick.buttons.Button9));
                    }

                    /* Rotation Presets */
                    {
                        final TriggerAccess rotationEnabled = new TriggerNot(manipJoystick.buttons.Button10);

                        this.bind(new Binding(modules.getModule("Rotation").getAction("Ground"), new TriggerAnd(new TriggerAccess[]{
                            manipJoystick.buttons.Button2, rotationEnabled
                        })));

                        this.bind(new Binding(modules.getModule("Rotation").getAction("Shoot"), new TriggerAnd(new TriggerAccess[]{
                            manipJoystick.buttons.Button3, rotationEnabled
                        })));
                        this.bind(new Binding(modules.getModule("Rotation").getAction("Stow"), new TriggerAnd(new TriggerAccess[]{
                            manipJoystick.buttons.Button6, rotationEnabled
                        })));
                    }
                }

                /* Flower */
                {
                    this.bind(new Binding(modules.getModule("Flower").getAction("Close"), manipJoystick.buttons.Button4, true));
                    this.bind(new Binding(modules.getModule("Flower").getAction("Open"), manipJoystick.buttons.Button5, true));
                    this.bind(new Binding(modules.getModule("Flower").getAction("Close"), manipJoystick.buttons.Button2));
                    this.bind(new Binding(modules.getModule("Flower").getAction("Drop"), manipJoystick.buttons.Button8));
                    this.bind(new Binding(modules.getModule("Flower").getAction("Shoot"), new TriggerOr(new TriggerAccess[]{
                        manipJoystick.buttons.Button3, manipJoystick.buttons.Button11
                    })));
                }

                /* Intake */
                {
                    this.bind(new Binding(modules.getModule("Intake").getAction("Suck"), manipJoystick.buttons.Button2));
                    this.bind(new Binding(modules.getModule("Intake").getAction("Spit"), manipJoystick.buttons.Button8));
                }
            }
        }));

        /* Single Xbox Mode */
        this.group(new Group(modules.getModule("Dashboard").getTrigger("Single Xbox"), new Coordinator() {
            protected void apply(ModuleManager modules) {
                /* Shifter */
                {
                    final TriggerToggle toggle = new TriggerToggle(driverXbox.buttons.RB, false);
                    this.bind(new Binding(modules.getModule("Shifter").getAction("Low Gear"), toggle.off));
                    this.bind(new Binding(modules.getModule("Shifter").getAction("High Gear"), toggle.on));
                }

                /* Shooter */
                {
                    this.bind(new Binding(modules.getModule("Shooter").getAction("Retract"), driverXbox.buttons.A));
                    this.bind(new Binding(modules.getModule("Shooter").getAction("Deploy"), new TriggerAnd(
                            new TriggerAccess[]{
                                driverXbox.buttons.LeftStick, 
                                driverXbox.buttons.RT
                        })));
                }

                /* Rotation Presets */
                    {
                        this.bind(new Binding(modules.getModule("Rotation").getAction("Ground"),
                            driverXbox.buttons.LT));
                        this.bind(new Binding(modules.getModule("Rotation").getAction("Shoot"),
                            driverXbox.buttons.B));
                        this.bind(new Binding(modules.getModule("Rotation").getAction("Stow"),
                            driverXbox.buttons.Y));
                    }

                /* Flower */
                {
                    this.bind(new Binding(modules.getModule("Flower").getAction("Close"), driverXbox.buttons.Y));
                    this.bind(new Binding(modules.getModule("Flower").getAction("Shoot"), driverXbox.buttons.B));
                    this.bind(new Binding(modules.getModule("Flower").getAction("Open"), driverXbox.buttons.X));
                }

                /* Intake */
                {
                    this.bind(new Binding(modules.getModule("Intake").getAction("Suck"), driverXbox.buttons.LT));
                    this.bind(new Binding(modules.getModule("Intake").getAction("Spit"), driverXbox.buttons.LB));
                }
            }
        }));
    }
}
