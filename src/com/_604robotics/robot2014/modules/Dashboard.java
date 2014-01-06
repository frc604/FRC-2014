package com._604robotics.robot2014.modules;

import com._604robotics.robotnik.module.Module;
import com._604robotics.robotnik.trigger.TriggerMap;
import com._604robotics.robotnik.trigger.sources.DashboardTriggerChoice;

public class Dashboard extends Module {
    public Dashboard () {
        this.set(new TriggerMap() {{
            final DashboardTriggerChoice driveMode = new DashboardTriggerChoice("Drive Mode");
            add("Arcade Drive", driveMode.add("Arcade Drive"));
            add("Tank Drive", driveMode.add("Tank Drive"));
            add("Quix Drive", driveMode.addDefault("Quix Drive"));
        }});
    }
}
