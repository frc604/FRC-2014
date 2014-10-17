package com._604robotics.robot2014.modules;

import com._604robotics.robotnik.action.Action;
import com._604robotics.robotnik.action.ActionData;
import com._604robotics.robotnik.action.controllers.ElasticController;
import com._604robotics.robotnik.module.Module;
import edu.wpi.first.wpilibj.Victor;

public class Intake extends Module {
    private final Victor motor = new Victor(5); //adds intake victor to channel 5

    public Intake() {
        this.set(new ElasticController() {{
            addDefault("Off", new Action() {
                public void run(ActionData data) { //stops intake
                    motor.stopMotor();
                }
            });

            add("On", new Action() {
                public void run(ActionData data) { //runs the intake
                    motor.set(-1D);
                }

                public void end(ActionData data) { //stops the intake
                    motor.stopMotor();
                }
            });
        }});
    }
}
