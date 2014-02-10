package com._604robotics.robot2014.modules;

import com._604robotics.robotnik.action.Action;
import com._604robotics.robotnik.action.ActionData;
import com._604robotics.robotnik.action.controllers.StateController;
import com._604robotics.robotnik.module.Module;
import edu.wpi.first.wpilibj.Solenoid;

public class Flower extends Module {
    private final Solenoid top = new Solenoid(3);
    private final Solenoid sides = new Solenoid(4);
    private final Solenoid bottom = new Solenoid(5);
    
    public Flower () {
        this.set(new StateController () {{
            addDefault("Stow", new Action() {
                public void begin(ActionData data) {
                    top.set(false);
                    sides.set(false);
                    bottom.set(false);
                }
            });
            
            add("Catch", new Action() {
                public void begin (ActionData data) {
                    top.set(true);
                    sides.set(true);
                    bottom.set(true);
                }
            });
            
            add("Pickup", new Action() {
                public void begin (ActionData data) {
                    top.set(false);
                    sides.set(true);
                    bottom.set(false);
                }
            });
            
            add("Shoot", new Action() {
                public void begin (ActionData data) {
                    top.set(true);
                    sides.set(false);
                    bottom.set(false);
                }
            });
        }});
    }
}
