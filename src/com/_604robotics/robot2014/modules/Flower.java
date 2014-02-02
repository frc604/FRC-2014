package com._604robotics.robot2014.modules;

import com._604robotics.robotnik.action.Action;
import com._604robotics.robotnik.action.ActionData;
import com._604robotics.robotnik.action.controllers.StateController;
import com._604robotics.robotnik.module.Module;
import edu.wpi.first.wpilibj.Solenoid;

public class Flower extends Module {
    private final Solenoid top = new Solenoid(3);
    private final Solenoid left = new Solenoid(4);
    private final Solenoid right = new Solenoid(5);
    private final Solenoid bottom = new Solenoid(6);
    
    public Flower () {
        this.set(new StateController () {{
            addDefault("Stow", new Action() {
                public void begin (ActionData data) {
                    top.set(false);
                    left.set(false);
                    right.set(false);
                    bottom.set(false);
                }
            });
            
            add("Catch", new Action() {
                public void begin (ActionData data) {
                    top.set(true);
                    left.set(true);
                    right.set(true);
                    bottom.set(true);
                }
            });
            
            add("Pickup", new Action() {
                public void begin (ActionData data) {
                    top.set(false);
                    left.set(true);
                    right.set(true);
                    bottom.set(false);
                }
            });
            
            add("Shoot", new Action() {
                public void begin (ActionData data) {
                    top.set(true);
                    left.set(false);
                    right.set(false);
                    bottom.set(false);
                }
            });
        }});
    }
}
