package com._604robotics.robotnik.data.sources;

import com._604robotics.robotnik.data.Data;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class DashboardData extends Data {
    private final String key;
    private final double defaultValue;
    
    public DashboardData (String key, double defaultValue) {
        this.key = key;
        this.defaultValue = defaultValue;
        
        SmartDashboard.putNumber(key, defaultValue);
    }

    public double run () {
        return SmartDashboard.getNumber(this.key, this.defaultValue);
    }
}