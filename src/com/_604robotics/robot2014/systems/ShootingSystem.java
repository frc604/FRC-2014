package com._604robotics.robot2014.systems;

import com._604robotics.robotnik.coordinator.Coordinator;
import com._604robotics.robotnik.coordinator.connectors.Binding;
import com._604robotics.robotnik.module.ModuleManager;
import com._604robotics.robotnik.prefabs.trigger.TriggerAnd;
import com._604robotics.robotnik.prefabs.trigger.TriggerNot;
import com._604robotics.robotnik.trigger.TriggerAccess;

public class ShootingSystem extends Coordinator {
    protected void apply (ModuleManager modules) {
        this.bind(new Binding(modules.getModule("Shooter").getAction("Retract"), new TriggerAnd(new TriggerAccess[] {
            modules.getModule("Shooter").getTrigger("Charged").not(),
            new TriggerNot(modules.getModule("Shooter").getAction("Deploy").active())
        }), true));
    }
}
