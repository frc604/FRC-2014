package com._604robotics.robot2014.modules;

import com._604robotics.robotnik.data.DataMap;
import com._604robotics.robotnik.data.sources.DashboardData;
import com._604robotics.robotnik.module.Module;
import com._604robotics.robotnik.trigger.TriggerMap;
import com._604robotics.robotnik.trigger.sources.DashboardTriggerChoice;

public class Dashboard extends Module {
    public Dashboard () {
        this.set(new DataMap() {{
            add("Drive Left Scale", new DashboardData("Drive Left Ratio", 1.0));
            add("Drive Right Scale", new DashboardData("Drive Right Ratio", 1.0));
        }});
        
        this.set(new TriggerMap() {{
            final DashboardTriggerChoice driveMode = new DashboardTriggerChoice("Drive Mode");
            add("Tank Drive", driveMode.addDefault("Tank Drive"));
            add("Arcade Drive", driveMode.add("Arcade Drive"));
            add("Stick Drive", driveMode.add("Stick Drive"));
        }});
    }
}
