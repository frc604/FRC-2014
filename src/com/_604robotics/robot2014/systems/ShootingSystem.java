package com._604robotics.robot2014.systems;

import com._604robotics.robotnik.coordinator.Coordinator;
import com._604robotics.robotnik.coordinator.connectors.Binding;
import com._604robotics.robotnik.module.ModuleManager;
import com._604robotics.robotnik.prefabs.trigger.TriggerAnd;
import com._604robotics.robotnik.prefabs.trigger.TriggerNot;
import com._604robotics.robotnik.trigger.TriggerSource;

public class ShootingSystem extends Coordinator {
    protected void apply (ModuleManager modules) {
        bind(modules.getModule("Shooter").getAction("Deploy"), new TriggerAnd(new TriggerSource[] {
            new TriggerNot(modules.getModule("Shooter").getTrigger("Deployed")),
            modules.getModule("Shooter").getAction("Deploy").active()
        }), Binding.Precedence.MAXIMUM);
    }
}
