package com._604robotics.robot2014;

import com._604robotics.robot2014.modes.AutonomousMode;
import com._604robotics.robot2014.modes.TeleopMode;
import com._604robotics.robot2014.modules.Dashboard;
import com._604robotics.robot2014.modules.Drive;
import com._604robotics.robot2014.modules.Flower;
import com._604robotics.robot2014.modules.Intake;
import com._604robotics.robot2014.modules.Regulator;
import com._604robotics.robot2014.modules.Rotation;
import com._604robotics.robot2014.modules.Shifter;
import com._604robotics.robotnik.Robot;
import com._604robotics.robotnik.coordinator.ModeMap;
import com._604robotics.robotnik.module.ModuleMap;

public class Robot2014 extends Robot {
    public Robot2014 () {
        this.set(new ModuleMap() {{
            add("Dashboard", new Dashboard());
            add("Drive", new Drive());
            add("Regulator", new Regulator());
            add("Shifter", new Shifter());
            add("Flower", new Flower());
            add("Rotation", new Rotation());
            add("Intake", new Intake());
            add("Flower", new Flower());
        }});
        
        this.set(new ModeMap() {{
            setAutonomousMode(new AutonomousMode());
            setTeleopMode(new TeleopMode());
        }});
    }
}
