package com._604robotics.robot2014.modules;

import com._604robotics.robotnik.action.Action;
import com._604robotics.robotnik.action.ActionData;
import com._604robotics.robotnik.action.controllers.ElasticController;
import com._604robotics.robotnik.module.Module;
import edu.wpi.first.wpilibj.Victor;

public class Intake extends Module {

    private final Victor motor = new Victor(3);

    public Intake() {
        this.set(new ElasticController() {
            {
                addDefault("Off", new Action() {
                    public void run(ActionData data) {
                        motor.set(0D);
                    }
                });

                add("On", new Action() {
                    public void run(ActionData data) {
                        motor.set(1D);
                    }

                    public void end(ActionData data) {
                        motor.set(0D);
                    }
                });
            }
        });
    }
}
