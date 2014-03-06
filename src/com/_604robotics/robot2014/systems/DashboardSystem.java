package com._604robotics.robot2014.systems;

import com._604robotics.robotnik.coordinator.Coordinator;
import com._604robotics.robotnik.module.ModuleManager;
import com._604robotics.robotnik.prefabs.outputs.DashboardOutput;

public class DashboardSystem extends Coordinator {
    protected void apply (ModuleManager modules) {
        wire(DashboardOutput.asDouble(),  "currentAngle",      modules.getModule("Rotation").getData("Encoder Angle"));
        wire(DashboardOutput.asBoolean(), "compressorCharged", modules.getModule("Regulator").getTrigger("Charged"));
        wire(DashboardOutput.asBoolean(), "shooterCharged",    modules.getModule("Shooter").getTrigger("Charged"));
        
        wire(modules.getModule("Vision").getAction("Pause"), "leftSide",  modules.getModule("Dashboard").getTrigger("Auton Left Side"));
        wire(modules.getModule("Vision").getAction("Pause"), "rightSide", modules.getModule("Dashboard").getTrigger("Auton Right Side"));
        
        wire(modules.getModule("Rotation").getAction("Manual Angle"), "angle", modules.getModule("Dashboard").getData("Manual Angle"));
        
        wire(modules.getModule("Rotation").getAction("Stow"),   "angle", modules.getModule("Dashboard").getData("Stow Angle"));
        wire(modules.getModule("Rotation").getAction("Shoot"),  "angle", modules.getModule("Dashboard").getData("Shoot Angle"));
        wire(modules.getModule("Rotation").getAction("Ground"), "angle", modules.getModule("Dashboard").getData("Ground Angle"));
        wire(modules.getModule("Rotation").getAction("Truss"),  "angle", modules.getModule("Dashboard").getData("Truss Angle"));
    }
}
