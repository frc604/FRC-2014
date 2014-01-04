package com._604robotics.robot2014;

import com._604robotics.robot2014.modes.AutonomousMode;
import com._604robotics.robot2014.modes.TeleopMode;
import com._604robotics.robotnik.Robot;
import com._604robotics.robotnik.coordinator.ModeMap;

public class Robot2014 extends Robot {
    public Robot2014 () {
        this.set(new ModeMap() {{
            setAutonomousMode(new AutonomousMode());
            setTeleopMode(new TeleopMode());
        }});
    }
}
