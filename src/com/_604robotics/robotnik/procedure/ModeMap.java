package com._604robotics.robotnik.procedure;

import com._604robotics.robotnik.coordinator.Coordinator;
import com._604robotics.robotnik.module.ModuleManager;

public class ModeMap {
    private Procedure autonomousMode = new Procedure();
    private Procedure teleopMode = new Procedure();
    
    public void attach (ModuleManager modules) {
        this.autonomousMode.attach(modules);
        this.teleopMode.attach(modules);
    }
    
    protected void setAutonomousMode (Coordinator autonomousMode) {
        this.setAutonomousMode(new Procedure(autonomousMode));
    }
    
    protected void setAutonomousMode (Procedure autonomousMode) {
        this.autonomousMode = autonomousMode;
    }
    
    protected void setTeleopMode (Coordinator teleopMode) {
        this.setTeleopMode(new Procedure(teleopMode));
    }
    
    protected void setTeleopMode (Procedure teleopMode) {
        this.teleopMode = teleopMode;
    }

    public Procedure getAutonomousMode () {
        return this.autonomousMode;
    }

    public Procedure getTeleopMode () {
        return this.teleopMode;
    }
}