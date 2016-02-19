package com._604robotics.robotnik.data.sources;

import com._604robotics.robotnik.data.DataAccess;

public class ConstData implements DataAccess {
    private final double value;

    public ConstData (double value) {
        this.value = value;
    }
    
    public double get() {
        return value;
    }
}
