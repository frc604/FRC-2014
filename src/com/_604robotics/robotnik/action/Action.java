package com._604robotics.robotnik.action;

import com._604robotics.robotnik.action.field.FieldMap;
import com._604robotics.robotnik.module.ModuleReference;

public class Action {
    private final FieldMap fieldMap;
    
    public Action () {
        this.fieldMap = new FieldMap();
    }
    
    public Action (FieldMap fieldMap) {
        this.fieldMap = fieldMap;
    }
    
    public void begin (ActionData data) {}
    public void run (ActionData data) {}
    public void end (ActionData data) {}
    
    protected FieldMap getFieldMap () {
        return this.fieldMap;
    }
}