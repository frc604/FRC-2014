package com._604robotics.robot2014.systems;

import com._604robotics.robotnik.coordinator.Coordinator;
import com._604robotics.robotnik.module.ModuleManager;
import com._604robotics.robotnik.prefabs.sinks.DashboardSink;

public class DashboardSystem extends Coordinator {
    protected void apply (ModuleManager modules) {
        wire(new DashboardSink.Number("currentAngle"),       modules.getModule("Rotation").getData("Encoder Angle"));
        wire(new DashboardSink.Boolean("compressorCharged"), modules.getModule("Regulator").getTrigger("Charged"));
        wire(new DashboardSink.Boolean("shooterCharged"),    modules.getModule("Shooter").getTrigger("Charged"));
        
        wire(modules.getModule("Vision").getAction("Pause").getField("leftSide"), modules.getModule("Dashboard").getTrigger("Auton Left Side"));
        wire(modules.getModule("Vision").getAction("Pause").getField("rightSide"), modules.getModule("Dashboard").getTrigger("Auton Right Side"));
        
        wire(modules.getModule("Rotation").getAction("Manual Angle").getField("angle"), modules.getModule("Dashboard").getData("Manual Angle"));
        
        wire(modules.getModule("Rotation").getAction("Stow").getField("angle"),   modules.getModule("Dashboard").getData("Stow Angle"));
        wire(modules.getModule("Rotation").getAction("Shoot").getField("angle"),  modules.getModule("Dashboard").getData("Shoot Angle"));
        wire(modules.getModule("Rotation").getAction("Ground").getField("angle"), modules.getModule("Dashboard").getData("Ground Angle"));
        wire(modules.getModule("Rotation").getAction("Truss").getField("angle"),  modules.getModule("Dashboard").getData("Truss Angle"));
    }
}
