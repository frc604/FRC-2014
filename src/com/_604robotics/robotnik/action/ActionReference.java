package com._604robotics.robotnik.action;

import com._604robotics.robotnik.data.DataRecipient;
import com._604robotics.robotnik.memory.IndexedTable;
import com._604robotics.robotnik.memory.IndexedTable.Slice;
import com._604robotics.robotnik.module.ModuleReference;
import com._604robotics.robotnik.prefabs.trigger.TriggerManual;
import com._604robotics.robotnik.trigger.TriggerAccess;
import com._604robotics.robotnik.trigger.TriggerRecipient;

public class ActionReference implements DataRecipient, TriggerRecipient {
    private final Action action;
    
    private final Slice trigger;
    private final IndexedTable dataTable;
    private final ActionData actionData;
    
    private final TriggerManual activeTrigger = new TriggerManual(false);
    
    public ActionReference (ModuleReference module, Action action, Slice triggered, IndexedTable dataTable) {
        this.action = action;
        
        this.trigger = triggered;
        this.dataTable = dataTable;        
        this.actionData = new ActionData(this.action.getFieldMap(), this.dataTable, module);
    }
    
    public void reset () {
        this.trigger.putNumber(0D);
        this.actionData.reset();
    }
    
    public void sendTrigger (double precedence) {
        final double current = this.trigger.getNumber(0D);
        
        if (precedence > current) {
            this.trigger.putNumber(precedence);
        }
    }
    
    public void sendData (String fieldName, double dataValue) {
        this.dataTable.putNumber(fieldName, dataValue);
    }
    
    public void begin () {
        this.action.begin(this.actionData);
        this.activeTrigger.set(true);
    }
    
    public void run () {
        this.action.run(this.actionData);
    }
    
    public void end () {
        this.action.end(this.actionData);
        this.activeTrigger.set(false);
    }
    
    public TriggerAccess active () {
        return (TriggerAccess) this.activeTrigger;
    }
}