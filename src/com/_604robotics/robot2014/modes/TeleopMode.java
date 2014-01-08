package com._604robotics.robot2014.modes;

import com._604robotics.robotnik.coordinator.Coordinator;
import com._604robotics.robotnik.coordinator.connectors.Binding;
import com._604robotics.robotnik.coordinator.connectors.DataWire;
import com._604robotics.robotnik.module.ModuleManager;
import com._604robotics.robotnik.prefabs.controller.joystick.JoystickController;
import com._604robotics.robotnik.prefabs.trigger.TriggerOr;
import com._604robotics.robotnik.prefabs.trigger.TriggerToggle;
import com._604robotics.robotnik.trigger.TriggerAccess;

public class TeleopMode extends Coordinator {
    private final JoystickController leftDrive = new JoystickController(1);
    private final JoystickController rightDrive = new JoystickController(2);
    private final JoystickController manipulator = new JoystickController(3);
    
    public TeleopMode () {
        leftDrive.axisY.setFactor(-1);
        rightDrive.axisY.setFactor(-1);
        
        leftDrive.axisY.setDeadband(0.2);
        rightDrive.axisY.setDeadband(0.2);
    }
    
    protected void apply (ModuleManager modules) {
        /* Drive Controller */
        {
            /* Drive */
            {
                this.fill(new DataWire(modules.getModule("Drive").getAction("Tank Drive"), "left", leftDrive.axisY));
                this.fill(new DataWire(modules.getModule("Drive").getAction("Tank Drive"), "right", rightDrive.axisY));
            }
            
            /* Shifter */
            {
                final TriggerToggle toggle = new TriggerToggle(new TriggerOr(new TriggerAccess[] {
                    leftDrive.buttons.Button1, rightDrive.buttons.Button1
                }), false);
                this.bind(new Binding(modules.getModule("Shifter").getAction("Low Gear"), toggle.off));
                this.bind(new Binding(modules.getModule("Shifter").getAction("High Gear"), toggle.on));
            }
        }
    }
}
