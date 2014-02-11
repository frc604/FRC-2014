package com._604robotics.robot2014.modes;

import com._604robotics.robotnik.coordinator.Coordinator;
import com._604robotics.robotnik.coordinator.connectors.Binding;
import com._604robotics.robotnik.coordinator.connectors.DataWire;
import com._604robotics.robotnik.module.ModuleManager;
import com._604robotics.robotnik.prefabs.controller.custom.CustomController;
import com._604robotics.robotnik.prefabs.controller.joystick.JoystickController;
import com._604robotics.robotnik.prefabs.trigger.TriggerOr;
import com._604robotics.robotnik.prefabs.trigger.TriggerToggle;
import com._604robotics.robotnik.trigger.TriggerAccess;

public class TeleopMode extends Coordinator {
    private final CustomController cust = new CustomController("Left");
    
    private final JoystickController leftDrive = new JoystickController(1);
    private final JoystickController rightDrive = new JoystickController(2);
    private final JoystickController manipulator = new JoystickController(3);
    
    public TeleopMode () {
        leftDrive.axisX.setFactor(-1);
        leftDrive.axisY.setFactor(-1);
        rightDrive.axisY.setFactor(-1);
        rightDrive.axisX.setFactor(-1);
        manipulator.axisY.setFactor(-1);
        manipulator.axisX.setFactor(-1);
        
        leftDrive.axisX.setDeadband(0.3);
        leftDrive.axisY.setDeadband(0.3);
        rightDrive.axisX.setDeadband(0.3);
        rightDrive.axisY.setDeadband(0.3);
        manipulator.axisX.setDeadband(0.3);
        manipulator.axisY.setDeadband(0.3);
    }
    
    protected void apply (ModuleManager modules) {
        /* Drive Controller */
        {
            /* Drive */
            {
                this.bind(new Binding(modules.getModule("Drive").getAction("Tank Drive"), modules.getModule("Dashboard").getTrigger("Tank Drive")));
                this.fill(new DataWire(modules.getModule("Drive").getAction("Tank Drive"), "left", leftDrive.axisY));
                this.fill(new DataWire(modules.getModule("Drive").getAction("Tank Drive"), "right", rightDrive.axisY));
                
                this.bind(new Binding(modules.getModule("Drive").getAction("Arcade Drive"), modules.getModule("Dashboard").getTrigger("Arcade Drive")));
                this.fill(new DataWire(modules.getModule("Drive").getAction("Arcade Drive"), "throttle", leftDrive.axisY));
                this.fill(new DataWire(modules.getModule("Drive").getAction("Arcade Drive"), "turn", rightDrive.axisX));
                
                this.bind(new Binding(modules.getModule("Drive").getAction("Stick Drive"), modules.getModule("Dashboard").getTrigger("Stick Drive")));
                this.fill(new DataWire(modules.getModule("Drive").getAction("Stick Drive"), "throttle", leftDrive.axisY));
                this.fill(new DataWire(modules.getModule("Drive").getAction("Stick Drive"), "turn", leftDrive.axisX));
                
                //this.fill(new DataWire(modules.getModule("Drive").getAction("Stick Drive"), "throttle", cust.getAxis("Y")));
                //this.fill(new DataWire(modules.getModule("Drive").getAction("Stick Drive"), "turn", cust.getAxis("X")));
            }
            
            /* Shifter */
            {
                final TriggerToggle toggle = new TriggerToggle(new TriggerOr(new TriggerAccess[] {
                    leftDrive.buttons.Button1, rightDrive.buttons.Button1, cust.getButton("Trigger")
                }), false);
                this.bind(new Binding(modules.getModule("Shifter").getAction("Low Gear"), toggle.off));
                this.bind(new Binding(modules.getModule("Shifter").getAction("High Gear"), toggle.on));
            }
        }
        
        /* Manipulator Controller */
        {
            /* Shooter */
            {
                this.bind(new Binding(modules.getModule("Shooter").getAction("Deploy"), manipulator.buttons.Button1));
            }
            
            /* Rotation */
            {
                this.bind(new Binding(modules.getModule("Rotation").getAction("Manual"), manipulator.buttons.Button8));
                this.fill(new DataWire(modules.getModule("Rotation").getAction("Manual"), "power", manipulator.axisY));
                
                this.bind(new Binding(modules.getModule("Rotation").getAction("Manual Angle"), manipulator.buttons.Button9));
                this.fill(new DataWire(modules.getModule("Rotation").getAction("Manual Angle"), "setpoint", modules.getModule("Dashboard").getData("Manual Rotation Angle")));
            }
            
            /* Flower */
            {
                this.bind(new Binding(modules.getModule("Flower").getAction("Stow"), manipulator.buttons.Button3));
                this.bind(new Binding(modules.getModule("Flower").getAction("Catch"), manipulator.buttons.Button2));
                this.bind(new Binding(modules.getModule("Flower").getAction("Shoot"), manipulator.buttons.Button5));
                this.bind(new Binding(modules.getModule("Flower").getAction("Pickup"), manipulator.buttons.Button4));
            }
            
            /* Intake */
            {
                this.bind(new Binding(modules.getModule("Intake").getAction("On"), manipulator.buttons.Button4));
            }
        }
    }
}
