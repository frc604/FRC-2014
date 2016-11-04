package com._604robotics.robotnik.action.field;

public class Field {
    private final String name;
    private final double defaultValue;
    
    public Field (String name, double defaultValue) {
        this.name = name;
        this.defaultValue = defaultValue;
    }

    public String getName() {
        return this.name;
    }

    public double getDefaultValue() {
        return this.defaultValue;
    }
}
