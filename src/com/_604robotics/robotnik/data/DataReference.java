package com._604robotics.robotnik.data;

import com._604robotics.robotnik.memory.IndexedTable.Slice;

public class DataReference implements DataAccess {
    private final Data data;
    private final Slice value;
    
    public DataReference (Data data, Slice value) {
        this.data = data;
        this.value = value;
    }
    
    public double get () {
        return this.value.getNumber(0D);
    }
    
    public void update () {
        this.value.putNumber(this.data.run());
    }
}
