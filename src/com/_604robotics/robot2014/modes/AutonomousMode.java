package com._604robotics.robot2014.modes;

import com._604robotics.robotnik.coordinator.Coordinator;
import com._604robotics.robotnik.coordinator.connectors.Binding;
import com._604robotics.robotnik.coordinator.connectors.DataWire;
import com._604robotics.robotnik.module.ModuleManager;
import com._604robotics.robotnik.prefabs.measure.TriggerMeasure;
import com._604robotics.robotnik.prefabs.trigger.TriggerAnd;
import com._604robotics.robotnik.prefabs.trigger.TriggerNot;
import com._604robotics.robotnik.procedure.Procedure;
import com._604robotics.robotnik.procedure.Step;
import com._604robotics.robotnik.trigger.TriggerAccess;

public class AutonomousMode extends Procedure {
    public AutonomousMode () {
        super(new Coordinator() {
            protected void apply (ModuleManager modules) {
                this.bind(new Binding(modules.getModule("Shifter").getAction("High Gear")));
                
                this.bind(new Binding(modules.getModule("Shooter").getAction("Retract")));
                this.bind(new Binding(modules.getModule("Intake").getAction("Suck"),
                        new TriggerNot(modules.getModule("Shooter").getAction("Deploy").active())));
            }
        });
    }
    
    protected void apply (ModuleManager modules) {
        add("Align", new Step(new TriggerMeasure(new TriggerAnd(new TriggerAccess[] {
            modules.getModule("Drive").getTrigger("At Servo Target"),
            modules.getModule("Shooter").getTrigger("Charged")
        })), new Coordinator() {
            protected void apply (ModuleManager modules) {
                this.bind(new Binding(modules.getModule("Flower").getAction("Close")));
                
                this.bind(new Binding(modules.getModule("Drive").getAction("Servo")));
                this.fill(new DataWire(modules.getModule("Drive").getAction("Servo"), "clicks", 1963));
            }
        }));
        
        add("Pause", new Step(new TriggerMeasure(modules.getModule("Vision").getTrigger("Ready")), new Coordinator() {
            protected void apply (ModuleManager modules) {
                this.bind(new Binding(modules.getModule("Flower").getAction("Shoot")));
            }
        }));
        
        add("Aim", new Step(new TriggerMeasure(new TriggerAnd(new TriggerAccess[] {
            modules.getModule("Rotation").getTrigger("At Angle Target"),
            modules.getModule("Flower").getTrigger("Travelling").not()
        })), new Coordinator() {
            protected void apply (ModuleManager modules) {
                this.bind(new Binding(modules.getModule("Flower").getAction("Shoot")));
                this.bind(new Binding(modules.getModule("Rotation").getAction("Shoot")));
            }
        }));
        
        add("Shoot", new Step(new Coordinator() {
            protected void apply (ModuleManager modules) {
                this.bind(new Binding(modules.getModule("Rotation").getAction("Shoot")));
                this.bind(new Binding(modules.getModule("Shooter").getAction("Deploy"), true));
            }
        }));
    }
}
