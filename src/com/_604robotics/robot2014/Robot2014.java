package com._604robotics.robot2014;

import com._604robotics.robot2014.modes.AutonomousMode;
import com._604robotics.robot2014.modes.TeleopMode;
import com._604robotics.robot2014.modules.Drive;
import com._604robotics.robot2014.modules.Regulator;
import com._604robotics.robot2014.modules.Shifter;
import com._604robotics.robotnik.Robot;
import com._604robotics.robotnik.coordinator.ModeMap;
import com._604robotics.robotnik.module.ModuleMap;

public class Robot2014 extends Robot {
    public Robot2014 () {
        this.set(new ModuleMap() {{
            add("Drive", new Drive());
            add("Regulator", new Regulator());
            add("Shifter", new Shifter());
        }});
        
        this.set(new ModeMap() {{
            setAutonomousMode(new AutonomousMode());
            setTeleopMode(new TeleopMode());
        }});
    }
}
