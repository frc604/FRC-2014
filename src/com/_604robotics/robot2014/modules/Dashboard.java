package com._604robotics.robot2014.modules;

import com._604robotics.robotnik.data.DataMap;
import com._604robotics.robotnik.data.sources.DashboardData;
import com._604robotics.robotnik.module.Module;
import com._604robotics.robotnik.trigger.TriggerMap;
import com._604robotics.robotnik.trigger.sources.DashboardTrigger;
import com._604robotics.robotnik.trigger.sources.DashboardTriggerChoice;

public class Dashboard extends Module {
    public Dashboard () {
        this.set(new DataMap() {{
            add("Stow Angle"  , new DashboardData("Stow Angle", 0D));
            add("Shoot Angle" , new DashboardData("Shoot Angle", -49D));
            add("Auton Angle" , new DashboardData("Auton Angle", -54D));
            add("Ground Angle", new DashboardData("Ground Angle", -109D));
        }});
        
        this.set(new TriggerMap() {{
            final DashboardTriggerChoice driveMode = new DashboardTriggerChoice("Drive Mode");
            add("Tank Drive", driveMode.addDefault("Tank Drive"));
            add("Arcade Drive", driveMode.add("Arcade Drive"));
            add("Stick Drive", driveMode.add("Stick Drive"));
            
            final DashboardTriggerChoice controlMode = new DashboardTriggerChoice("Control Mode");
            add("Xbox-Xbox", controlMode.addDefault("Xbox-Xbox"));
            add("Xbox-Joystick", controlMode.add("Xbox-Joystick"));
            add("Single Xbox", controlMode.add("Single Xbox"));
            add("Override", controlMode.add("Override"));
            
            add("Safety Enabled", new DashboardTrigger("Safety Enabled", false));
        }});
    }
}
