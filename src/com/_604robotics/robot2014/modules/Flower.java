package com._604robotics.robot2014.modules;

import com._604robotics.robotnik.action.Action;
import com._604robotics.robotnik.action.controllers.StateController;
import com._604robotics.robotnik.module.Module;
import edu.wpi.first.wpilibj.Solenoid;

public class Flower extends Module {
    private final Solenoid top = new Solenoid(4);
    private final Solenoid sides = new Solenoid(5);
    private final Solenoid bottom = new Solenoid(3);
    
    public Flower () {
        this.set(new StateController () {{
            addDefault("Close", new Action() {
                public void begin () {
                    top.set(false);
                    sides.set(false);
                    bottom.set(false);
                }
            });
            
            add("Open", new Action() {
                public void begin () {
                    top.set(true);
                    sides.set(true);
                    bottom.set(true);
                }
            });
            
            add("Pickup", new Action() {
                public void begin () {
                    top.set(false);
                    sides.set(false);
                    bottom.set(false);
                }
            });
            
            add("Pickup", new Action() {
                public void begin () {
                    top.set(false);
                    sides.set(false);
                    bottom.set(false);
                }
            });
            
            add("Drop", new Action() {
                public void begin () {
                    top.set(true);
                    sides.set(true);
                    bottom.set(false);
                }
            });
            
            add("Shoot", new Action() {
                public void begin () {
                    top.set(true);
                    sides.set(false);
                    bottom.set(false);
                }
            });
        }});
    }
}
