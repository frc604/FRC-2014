package com._604robotics.robotnik.module;

import com._604robotics.robotnik.meta.Iterator;
import java.util.Hashtable;

public class ModuleMap {
    private final Hashtable moduleTable = new Hashtable();
    
    protected void add (String name, Module module) {
        this.moduleTable.put(name, module);
    }
    
    protected Module getModule (String name) {
        return (Module) this.moduleTable.get(name);
    }
    
    protected Iterator iterate () {
        return new Iterator(this.moduleTable);
    }
}
