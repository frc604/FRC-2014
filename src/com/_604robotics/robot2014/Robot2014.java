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
import com._604robotics.robot2014.modules.Shooter;
import com._604robotics.robot2014.systems.FlowerSystem;
import com._604robotics.robot2014.systems.ShootingSystem;
import com._604robotics.robotnik.Robot;
import com._604robotics.robotnik.coordinator.CoordinatorList;
import com._604robotics.robotnik.procedure.ModeMap;
import com._604robotics.robotnik.module.ModuleMap;

public class Robot2014 extends Robot {
    public Robot2014 () {
        this.set(new ModuleMap() {{
            add("Dashboard", new Dashboard());
            add("Drive", new Drive());
            add("Regulator", new Regulator());
            add("Shifter", new Shifter());
            add("Rotation", new Rotation());
            add("Intake", new Intake());
            add("Flower", new Flower());
            add("Shooter", new Shooter());
        }});
        
        this.set(new ModeMap() {{
            setAutonomousMode(new AutonomousMode());
            setTeleopMode(new TeleopMode());
        }});
        
        this.set(new CoordinatorList() {{
            add(new ShootingSystem());
            add(new FlowerSystem());
        }});
    }
}
