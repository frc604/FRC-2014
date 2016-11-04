package com._604robotics.robotnik.coordinator;

import com._604robotics.robotnik.ConnectorProxy;
import com._604robotics.robotnik.coordinator.connectors.Binding;
import com._604robotics.robotnik.coordinator.connectors.DataWire;
import com._604robotics.robotnik.coordinator.connectors.Group;
import com._604robotics.robotnik.module.ModuleManager;
import java.util.Enumeration;
import java.util.Vector;

public class Coordinator {
    private final Vector triggerBindings = new Vector();
    private final Vector dataWires = new Vector();
    private final Vector subGroups = new Vector();
    
    protected void apply (ModuleManager modules) {}
    
    public void attach (ModuleManager modules) {
        this.triggerBindings.removeAllElements();
        this.dataWires.removeAllElements();
        this.subGroups.removeAllElements();
        
        this.apply(modules);
        
        final Enumeration i = this.subGroups.elements();
        while (i.hasMoreElements()) ((Group) i.nextElement()).attach(modules);
    }
    
    protected void bind (Binding binding) {
        this.triggerBindings.addElement(binding);
    }
    
    protected void fill (DataWire dataWire) {
        this.dataWires.addElement(dataWire);
    }
    
    protected void group (Group group) {
        this.subGroups.addElement(group);
    }
    
    public void update () {
        final Enumeration wires = this.dataWires.elements();
        while (wires.hasMoreElements()) ConnectorProxy.pipe((DataWire) wires.nextElement());
        
        final Enumeration bindings = this.triggerBindings.elements();
        while (bindings.hasMoreElements()) ConnectorProxy.pipe((Binding) bindings.nextElement());
        
        final Enumeration groups = this.subGroups.elements();
        while (groups.hasMoreElements()) ((Group) groups.nextElement()).update();
    }
}