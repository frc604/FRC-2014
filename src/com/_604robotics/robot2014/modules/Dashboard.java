package com._604robotics.robot2014.modules;

import com._604robotics.robotnik.data.DataMap;
import com._604robotics.robotnik.data.sources.DashboardData;
import com._604robotics.robotnik.module.Module;
import com._604robotics.robotnik.trigger.TriggerMap;
import com._604robotics.robotnik.trigger.sources.DashboardTriggerChoice;

public class Dashboard extends Module {
    public Dashboard () {
        this.set(new DataMap() {{
            add("Manual Angle", new DashboardData("Manual Angle", 157D));
            
            add("Stow Angle"  , new DashboardData("Stow Angle"  , 205D));
            add("Shoot Angle" , new DashboardData("Shoot Angle" , 163D));
            add("Ground Angle", new DashboardData("Ground Angle", 96D));
        }});
        
        this.set(new TriggerMap() {{
            final DashboardTriggerChoice driveMode = new DashboardTriggerChoice("Drive Mode");
            add("Tank Drive", driveMode.addDefault("Tank Drive"));
            add("Arcade Drive", driveMode.add("Arcade Drive"));
            add("Stick Drive", driveMode.add("Stick Drive"));
            
            final DashboardTriggerChoice autonSide = new DashboardTriggerChoice("Auton Side");
            add("Auton Left Side", autonSide.addDefault("Autonomous: Left Side"));
            add("Auton Right Side", autonSide.add("Autonomous: Right Side"));
        }});
    }
}
