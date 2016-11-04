package com._604robotics.robotnik.coordinator.connectors;

import com._604robotics.robotnik.prefabs.trigger.TriggerAlways;
import com._604robotics.robotnik.trigger.TriggerAccess;
import com._604robotics.robotnik.trigger.TriggerRecipient;

public class Binding {
    private final TriggerRecipient recipient;
    private final TriggerAccess trigger;
    
    private final boolean safety;
    
    public Binding (TriggerRecipient recipient) {
        this(recipient, TriggerAlways.getInstance(), false);
    }
    
    public Binding (TriggerRecipient recipient, boolean safety) {
        this(recipient, TriggerAlways.getInstance(), safety);
    }
    
    public Binding (TriggerRecipient recipient, TriggerAccess trigger) {
        this(recipient, trigger, false);
    }
    
    public Binding (TriggerRecipient recipient, TriggerAccess trigger, boolean safety) {
        this.recipient = recipient;
        this.trigger = trigger;
        
        this.safety = safety;
    }

    public TriggerRecipient getRecipient () {
        return this.recipient;
    }

    public TriggerAccess getTrigger () {
        return this.trigger;
    }

    public boolean isSafety () {
        return this.safety;
    }
}