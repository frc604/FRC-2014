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
import com._604robotics.robot2014.modules.Vision;
import com._604robotics.robot2014.systems.DashboardSystem;
import com._604robotics.robot2014.systems.ShootingSystem;
import com._604robotics.robotnik.Robot;
import com._604robotics.robotnik.coordinator.CoordinatorList;
import com._604robotics.robotnik.procedure.ModeMap;
import com._604robotics.robotnik.module.ModuleMap;

public class Robot2014 extends Robot {
    public Robot2014 () {
        this.set(new ModuleMap() {{  //adds each module to a hashtable named moduleTable. Not sure what a hashtable is exactly. Figure this out.
            add("Dashboard", new Dashboard());
            add("Vision", new Vision());
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
            add(new DashboardSystem());
            add(new ShootingSystem());
        }});
    }
}
