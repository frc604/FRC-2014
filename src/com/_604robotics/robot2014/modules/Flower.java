package com._604robotics.robot2014.modules;

import com._604robotics.robotnik.action.Action;
import com._604robotics.robotnik.action.ActionData;
import com._604robotics.robotnik.action.controllers.StateController;
import com._604robotics.robotnik.action.field.FieldMap;
import com._604robotics.robotnik.module.Module;
import edu.wpi.first.wpilibj.Solenoid;

public class Flower extends Module {
    private final Solenoid top = new Solenoid(4);
    private final Solenoid sides = new Solenoid(5);
    private final Solenoid bottom = new Solenoid(3);
    
    public Flower () {
        this.set(new StateController () {{
            addDefault("Close", new Action() {
                public void begin (ActionData data) {
                    top.set(false);
                    sides.set(false);
                    bottom.set(false);
                }
            });
            
            add("Open", new Action() {
                public void begin (ActionData data) {
                    top.set(true);
                    sides.set(true);
                    bottom.set(true);
                }
            });
            
            add("Pickup", new Action(new FieldMap() {{
                define("sides", false);
            }}) {
                public void begin (ActionData data) {
                    top.set(false);
                    bottom.set(false);
                }
                
                public void run (ActionData data) {
                    sides.set(data.is("sides"));
                }
            });
            
            add("Pickup", new Action() {
                public void begin (ActionData data) {
                    top.set(false);
                    sides.set(false);
                    bottom.set(false);
                }
            });
            
            add("Drop", new Action() {
                public void begin (ActionData data) {
                    top.set(true);
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
