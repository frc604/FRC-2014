package com._604robotics.robotnik.coordinator;

import com._604robotics.robotnik.module.ModuleManager;
import java.util.Enumeration;
import java.util.Vector;

public class CoordinatorList {
    private final Vector coordinators = new Vector();
    
    protected void add (Coordinator coordinator) {
        this.coordinators.addElement(coordinator);
    }
    
    public void attach (ModuleManager modules) {
        final Enumeration i = this.coordinators.elements();
        while (i.hasMoreElements()) ((Coordinator) i.nextElement()).attach(modules);
    }
    
    public void update () {
        final Enumeration i = this.coordinators.elements();
        while (i.hasMoreElements()) ((Coordinator) i.nextElement()).update();
    }
}