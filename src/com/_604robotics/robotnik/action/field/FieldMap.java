package com._604robotics.robotnik.action.field;

import java.util.Enumeration;
import java.util.Vector;

public class FieldMap {
    private final Vector fields = new Vector();
    
    public void define (String name, double value) {
        this.fields.addElement(new Field(name, value));
    }
    
    public void define (String name, boolean value) {
        this.fields.addElement(new Field(name, value ? 1D : 0D));
    }
    
    public Enumeration enumerate () {
        return this.fields.elements();
    }
}
