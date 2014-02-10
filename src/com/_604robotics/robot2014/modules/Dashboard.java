package com._604robotics.robot2014.modules;

import com._604robotics.robotnik.data.DataMap;
import com._604robotics.robotnik.data.sources.DashboardData;
import com._604robotics.robotnik.module.Module;
import com._604robotics.robotnik.trigger.TriggerMap;
import com._604robotics.robotnik.trigger.sources.DashboardTriggerChoice;

public class Dashboard extends Module {
    public Dashboard () {
        this.set(new DataMap() {{
            add("Manual Rotation Angle", new DashboardData("Manual Rotation Angle", 0D));
        }});
        
        this.set(new TriggerMap() {{
            final DashboardTriggerChoice driveMode = new DashboardTriggerChoice("Drive Mode");
            add("Tank Drive", driveMode.add("Tank Drive"));
            add("Arcade Drive", driveMode.add("Arcade Drive"));
            add("Stick Drive", driveMode.addDefault("Stick Drive"));
        }});
    }
}
