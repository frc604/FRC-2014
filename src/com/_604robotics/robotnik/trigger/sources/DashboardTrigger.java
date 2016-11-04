package com._604robotics.robotnik.trigger.sources;

import com._604robotics.robotnik.trigger.Trigger;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class DashboardTrigger extends Trigger {
    private final String key;
    private final boolean defaultValue;
    
    public DashboardTrigger (String key, boolean defaultValue) {
        this.key = key;
        this.defaultValue = defaultValue;
        
        SmartDashboard.putBoolean(key, defaultValue);
    }

    public boolean run () {
        return SmartDashboard.getBoolean(this.key, this.defaultValue);
    }
}