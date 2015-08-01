package com._604robotics.robot2014.systems;

import com._604robotics.robotnik.coordinator.Coordinator;
import com._604robotics.robotnik.coordinator.connectors.DataWire;
import com._604robotics.robotnik.module.ModuleManager;
import com._604robotics.robotnik.prefabs.outputs.DashboardOutput;

public class DashboardSystem extends Coordinator {
    protected void apply (ModuleManager modules) {
        this.fill(new DataWire(DashboardOutput.asDouble(), "leftDriveClicks",
                modules.getModule("Drive").getData("Left Drive Clicks")));
        this.fill(new DataWire(DashboardOutput.asDouble(), "rightDriveClicks",
                modules.getModule("Drive").getData("Right Drive Clicks")));
        
        this.fill(new DataWire(DashboardOutput.asDouble(), "armBaseAngle",
                modules.getModule("Rotation").getData("Base Angle")));
        
        this.fill(new DataWire(DashboardOutput.asDouble(),  "currentAngle",
                modules.getModule("Rotation").getData("Encoder Angle")));
        this.fill(new DataWire(DashboardOutput.asBoolean(), "compressorCharged",
                modules.getModule("Regulator").getTrigger("Charged")));
        this.fill(new DataWire(DashboardOutput.asBoolean(), "shooterCharged",
                modules.getModule("Shooter").getTrigger("Charged")));
        
        this.fill(new DataWire(modules.getModule("Rotation").getAction("Stow"),
                "angle", modules.getModule("Dashboard").getData("Stow Angle")));
        this.fill(new DataWire(modules.getModule("Rotation").getAction("Shoot"),
                "angle", modules.getModule("Dashboard").getData("Shoot Angle")));
        this.fill(new DataWire(modules.getModule("Rotation").getAction("Auton"),
                "angle", modules.getModule("Dashboard").getData("Auton Angle")));
        this.fill(new DataWire(modules.getModule("Rotation").getAction("Ground"),
                "angle", modules.getModule("Dashboard").getData("Ground Angle")));
    }
}
