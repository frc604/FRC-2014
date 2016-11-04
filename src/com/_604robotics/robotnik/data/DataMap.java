package com._604robotics.robotnik.data;

import com._604robotics.robotnik.meta.Iterator;
import java.util.Hashtable;

public class DataMap {
    private final Hashtable dataTable = new Hashtable();
    
    protected void add (String name, Data data) {
        this.dataTable.put(name, data);
    }
    
    protected Data getData (String name) {
        return (Data) this.dataTable.get(name);
    }
    
    protected Iterator iterate () {
        return new Iterator(this.dataTable);
    }
}
