package com._604robotics.robot2014.modes;

import com._604robotics.robotnik.coordinator.Coordinator;
import com._604robotics.robotnik.coordinator.connectors.Binding;
import com._604robotics.robotnik.coordinator.connectors.DataWire;
import com._604robotics.robotnik.module.ModuleManager;
import com._604robotics.robotnik.prefabs.controller.joystick.JoystickController;
import com._604robotics.robotnik.prefabs.controller.wheel.WheelController;
import com._604robotics.robotnik.prefabs.trigger.TriggerToggle;

public class TeleopMode extends Coordinator {
    private final WheelController wheel = new WheelController(1);
    private final JoystickController throttle = new JoystickController(2);
    private final JoystickController manipulator = new JoystickController(3);
    
    public TeleopMode () {
        throttle.axisY.setFactor(-1);
    }
    
    protected void apply (ModuleManager modules) {
        /* Drive Controller */
        {
            /* Drive */
            {
                this.fill(new DataWire(modules.getModule("Drive").getAction("Arcade Drive"), "throttle", throttle.axisY));
                this.fill(new DataWire(modules.getModule("Drive").getAction("Arcade Drive"), "wheel", wheel.axis));
            }
            
            /* Shifter */
            {
                final TriggerToggle toggle = new TriggerToggle(throttle.buttons.Button1, false);
                this.bind(new Binding(modules.getModule("Shifter").getAction("Low Gear"), toggle.off));
                this.bind(new Binding(modules.getModule("Shifter").getAction("High Gear"), toggle.on));
            }
        }
    }
}
