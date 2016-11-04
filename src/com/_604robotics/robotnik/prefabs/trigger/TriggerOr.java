package com._604robotics.robotnik.prefabs.trigger;

import com._604robotics.robotnik.trigger.TriggerAccess;

public class TriggerOr implements TriggerAccess {
    private final TriggerAccess[] triggers;
    
    public TriggerOr (TriggerAccess[] triggers) {
        this.triggers = triggers;
    }
    
    public boolean get () {
        for (int i = 0; i < this.triggers.length; i++) {
            if (this.triggers[i].get()) {
                return true;
            }
        }
        
        return false;
    }
}