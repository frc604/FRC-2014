package com._604robotics.robotnik.trigger.sources;

import com._604robotics.robotnik.trigger.Trigger;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class DashboardTriggerChoice {
    private final SendableChooser chooser = new SendableChooser();
    
    private class DashboardTriggerOption extends Trigger {
        public boolean run () {
            return chooser.getSelected() == this;
        }
    }
    
    public DashboardTriggerChoice (String key) {
        SmartDashboard.putData(key, this.chooser);
    }
    
    public Trigger addDefault (String name) {
        final DashboardTriggerOption option = new DashboardTriggerOption();
        this.chooser.addDefault(name, option);
        return option;
    }
    
    public Trigger add (String name) {
        final DashboardTriggerOption option = new DashboardTriggerOption();
        this.chooser.addObject(name, option);
        return option;
    }
}