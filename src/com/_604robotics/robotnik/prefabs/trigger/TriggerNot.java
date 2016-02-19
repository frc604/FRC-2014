package com._604robotics.robotnik.prefabs.trigger;

import com._604robotics.robotnik.trigger.TriggerAccess;

public class TriggerNot implements TriggerAccess {
    private final TriggerAccess trigger;
    
    public TriggerNot (TriggerAccess trigger) {
        this.trigger = trigger;
    }
    
    public boolean get () {
        return !this.trigger.get();
    }
}