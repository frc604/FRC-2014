package com._604robotics.robot2014.modes;

import com._604robotics.robotnik.coordinator.Coordinator;
import com._604robotics.robotnik.module.ModuleManager;
import com._604robotics.robotnik.prefabs.measure.TriggerMeasure;
import com._604robotics.robotnik.prefabs.trigger.TriggerAnd;
import com._604robotics.robotnik.procedure.Procedure;
import com._604robotics.robotnik.procedure.Step;
import com._604robotics.robotnik.trigger.TriggerAccess;

public class AutonomousMode extends Procedure {
    public AutonomousMode () {
        super(new Coordinator() {
            protected void apply (ModuleManager modules) {
                bind(modules.getModule("Rotation").getAction("Shoot"));
                bind(modules.getModule("Shifter").getAction("Low Gear"));
                
                bind(modules.getModule("Shooter").getAction("Retract"));
                bind(modules.getModule("Intake").getAction("On"), new TriggerAnd(new TriggerAccess[] {
                    modules.getModule("Shooter").getTrigger("Charged"),
                    modules.getModule("Shooter").getTrigger("Deployed").not()
                }));
            }
        });
    }
    
    protected void apply (ModuleManager modules) {
        add("Snap", new Step(new TriggerMeasure(modules.getModule("Vision").getTrigger("Snapped")), new Coordinator() {
            protected void apply (ModuleManager modules) {
                bind(modules.getModule("Flower").getAction("Close"));
                bind(modules.getModule("Vision").getAction("Snap"));
            }
        }));
        
        add("Align", new Step(new TriggerMeasure(modules.getModule("Drive").getTrigger("At Servo Target")), new Coordinator() {
            protected void apply (ModuleManager modules) {
                bind(modules.getModule("Drive").getAction("Servo"));
                wire(modules.getModule("Drive").getAction("Servo"), "clicks", 1400);
            }
        }));
        
        add("Pause", new Step(new TriggerMeasure(modules.getModule("Vision").getTrigger("Ready")), new Coordinator() {
            protected void apply (ModuleManager modules) {
                bind(modules.getModule("Vision").getAction("Pause"));
                bind(modules.getModule("Flower").getAction("Shoot"));
            }
        }));
        
        add("Aim", new Step(new TriggerMeasure(modules.getModule("Rotation").getTrigger("At Angle Target"))));
        
        add("Shoot", new Step(new Coordinator() {
            protected void apply (ModuleManager modules) {
                wire(modules.getModule("Drive").getAction("Servo"), "clicks", 1700);
                bind(modules.getModule("Shooter").getAction("Deploy"), true);
            }
        }));
    }
}
