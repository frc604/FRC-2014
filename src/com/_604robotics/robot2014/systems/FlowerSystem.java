package com._604robotics.robot2014.systems;

import com._604robotics.robotnik.coordinator.Coordinator;
import com._604robotics.robotnik.coordinator.connectors.Binding;
import com._604robotics.robotnik.module.ModuleManager;

public class FlowerSystem extends Coordinator {
    protected void apply (ModuleManager modules) {
        this.bind(new Binding(modules.getModule("Rotation").getAction("Stow"), modules.getModule("Flower").getAction("Stow").active()));
        this.bind(new Binding(modules.getModule("Rotation").getAction("Stow"), modules.getModule("Flower").getAction("Catch").active()));
        this.bind(new Binding(modules.getModule("Rotation").getAction("Pickup"), modules.getModule("Flower").getAction("Pickup").active()));
        this.bind(new Binding(modules.getModule("Rotation").getAction("Shoot"), modules.getModule("Flower").getAction("Shoot").active())); 
    }
}
