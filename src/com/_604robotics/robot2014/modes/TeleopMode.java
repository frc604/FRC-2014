package com._604robotics.robot2014.modes;

import com._604robotics.robotnik.coordinator.Coordinator;
import com._604robotics.robotnik.coordinator.connectors.Binding;
import com._604robotics.robotnik.coordinator.connectors.DataWire;
import com._604robotics.robotnik.module.ModuleManager;
import com._604robotics.robotnik.prefabs.controller.xbox.XboxController;

public class TeleopMode extends Coordinator {
    private final XboxController driver = new XboxController(1);
    private final XboxController manipulator = new XboxController(2);
    
    public TeleopMode () {
        driver.leftStick.Y.setFactor(-1);
        driver.rightStick.Y.setFactor(-1);
        
        driver.leftStick.Y.setDeadband(0.2);
        driver.rightStick.Y.setDeadband(0.2);
    }
    
    protected void apply (ModuleManager modules) {
        /* Drive Controller */
        {
            /* Drive */
            {
                this.fill(new DataWire(modules.getModule("Drive").getAction("Tank Drive"), "left", driver.leftStick.Y));
                this.fill(new DataWire(modules.getModule("Drive").getAction("Tank Drive"), "right", driver.rightStick.Y));
            }
            
            /* Shifter */
            {
                this.bind(new Binding(modules.getModule("Shifter").getAction("Low Gear"), driver.buttons.LT));
                this.bind(new Binding(modules.getModule("Shifter").getAction("High Gear"), driver.buttons.RT));
            }
        }
    }
}
