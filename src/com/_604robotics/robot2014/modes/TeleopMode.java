package com._604robotics.robot2014.modes;

import com._604robotics.robotnik.coordinator.Coordinator;
import com._604robotics.robotnik.coordinator.connectors.Binding;
import com._604robotics.robotnik.coordinator.connectors.DataWire;
import com._604robotics.robotnik.module.ModuleManager;
import com._604robotics.robotnik.prefabs.controller.joystick.JoystickController;
import com._604robotics.robotnik.prefabs.controller.wheel.WheelController;
import com._604robotics.robotnik.prefabs.controller.xbox.XboxController;
import com._604robotics.robotnik.prefabs.trigger.TriggerOr;
import com._604robotics.robotnik.prefabs.trigger.TriggerToggle;
import com._604robotics.robotnik.trigger.TriggerAccess;

public class TeleopMode extends Coordinator {
    private final WheelController wheel = new WheelController(1);
    private final JoystickController throttle = new JoystickController(2);
    private final XboxController manipulator = new XboxController(3);
    
    public TeleopMode () {
        throttle.axisY.setFactor(-1);
        
        manipulator.leftStick.Y.setFactor(-1);
        manipulator.rightStick.Y.setFactor(-1);
        
        manipulator.leftStick.Y.setDeadband(0.2);
        manipulator.rightStick.Y.setDeadband(0.2);
    }
    
    protected void apply (ModuleManager modules) {
        /* Drive Controller */
        {
            /* Drive */
            {
                this.bind(new Binding(modules.getModule("Drive").getAction("Arcade Drive"), modules.getModule("Dashboard").getTrigger("Arcade Drive")));
                this.fill(new DataWire(modules.getModule("Drive").getAction("Arcade Drive"), "throttle", throttle.axisY));
                this.fill(new DataWire(modules.getModule("Drive").getAction("Arcade Drive"), "turn", wheel.axis));
                
                this.bind(new Binding(modules.getModule("Drive").getAction("Tank Drive"), modules.getModule("Dashboard").getTrigger("Tank Drive")));
                this.fill(new DataWire(modules.getModule("Drive").getAction("Tank Drive"), "left", manipulator.leftStick.Y));
                this.fill(new DataWire(modules.getModule("Drive").getAction("Tank Drive"), "right", manipulator.rightStick.Y));
            }
            
            /* Shifter */
            {
                final TriggerToggle toggle = new TriggerToggle(new TriggerOr(new TriggerAccess[] {
                    throttle.buttons.Button3, manipulator.buttons.LT, manipulator.buttons.RT
                }), false);
                this.bind(new Binding(modules.getModule("Shifter").getAction("Low Gear"), toggle.off));
                this.bind(new Binding(modules.getModule("Shifter").getAction("High Gear"), toggle.on));
            }
        }
    }
}
