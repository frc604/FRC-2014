package com._604robotics.robotnik.data.sources;

import com._604robotics.robotnik.data.Data;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class DashboardDataChoice extends Data {
    private final SendableChooser chooser = new SendableChooser();
    
    public DashboardDataChoice (String key, String defaultName, double defaultValue) {
        this.chooser.addDefault(defaultName, Double.valueOf(defaultValue));
        SmartDashboard.putData(key, this.chooser);
    }
    
    public void add (String name, double value) {
        this.chooser.addObject(name, Double.valueOf(value));
    }

    public double run () {
        return ((Double) this.chooser.getSelected()).doubleValue();
    }
}