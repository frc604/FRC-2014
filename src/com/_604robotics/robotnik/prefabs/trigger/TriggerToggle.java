package com._604robotics.robotnik.prefabs.trigger;

import com._604robotics.robotnik.trigger.TriggerAccess;

public class TriggerToggle {
    private final TriggerAccess trigger;
    
    private boolean last = false;
    private boolean state;
    
    private class TriggerState implements TriggerAccess {
        private final boolean which;
        
        public TriggerState (boolean which) {
            this.which = which;
        }
        
        public boolean get () {
            update();
            return state == which;
        }
    }
    
    public final TriggerAccess off = new TriggerState(false);
    public final TriggerAccess on  = new TriggerState(true);
    
    public TriggerToggle (TriggerAccess trigger, boolean defaultValue) {
        this.trigger = trigger;
        this.state = defaultValue;
    }
    
    private void update () {
        final boolean now = trigger.get();
        
        if (!last && now) {
            state = !state;
        }
        
        last = now;
    }
}