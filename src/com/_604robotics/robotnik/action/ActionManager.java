package com._604robotics.robotnik.action;

import com._604robotics.robotnik.ActionProxy;
import com._604robotics.robotnik.meta.Iterator;
import com._604robotics.robotnik.meta.Repackager;
import com._604robotics.robotnik.meta.Scorekeeper;
import com._604robotics.robotnik.memory.IndexedTable;
import com._604robotics.robotnik.logging.InternalLogger;
import com._604robotics.robotnik.module.ModuleReference;
import java.util.Hashtable;

public class ActionManager {
    private final String moduleName;
    
    private final ActionController controller;
    
    private final IndexedTable triggerTable;
    private final IndexedTable statusTable;
    private final Hashtable actionTable;
    
    public ActionManager (final ModuleReference module, String moduleName, ActionController controller, final IndexedTable table) {
        this.moduleName = moduleName;
        
        this.controller = controller;
        
        this.triggerTable = table.getSubTable("triggers");
        
        this.statusTable = table.getSubTable("status");
        this.statusTable.putString("triggeredAction", "");
        this.statusTable.putString("lastAction", "");
        
        final IndexedTable dataTable = table.getSubTable("data");
        this.actionTable = Repackager.repackage(controller.iterate(), new Repackager() {
           public Object wrap (Object key, Object value) {
               return new ActionReference(module, (Action) value, triggerTable.getSlice((String) key), dataTable.getSubTable((String) key));
           }
        });
    }
    
    public ActionReference getAction (String name) {
        ActionReference ref = (ActionReference) this.actionTable.get(name);
        if (ref == null) InternalLogger.missing("ActionReference", name);
        return ref;
    }
    
    public void reset () {
        final Iterator i = new Iterator(this.actionTable);
        while (i.next()) ((ActionReference) i.value).reset();
    }
    
    public void update () {
        final Scorekeeper r = new Scorekeeper(0D);
        final Iterator i = controller.iterate();
        
        while (i.next()) r.consider(i.key, this.triggerTable.getNumber((String) i.key, 0D));
        
        this.statusTable.putString("triggeredAction", r.score > 0 ? (String) r.victor : "");
    }
    
    public void execute () {
        final String triggeredAction = this.statusTable.getString("triggeredAction", "");
        final String lastAction = this.statusTable.getString("lastAction", "");
        
        final String selectedAction = this.controller.pickAction(lastAction, triggeredAction);
        
        if (!lastAction.equals("") && !lastAction.equals(selectedAction)) {
            ActionProxy.end(moduleName, lastAction, this.getAction(lastAction));
        }

        if (!selectedAction.equals("")) {
            final ActionReference action = this.getAction(selectedAction);
            
            if (lastAction.equals("") || !lastAction.equals(selectedAction)) {
                ActionProxy.begin(moduleName, selectedAction, action);
            }
            
            ActionProxy.run(moduleName, selectedAction, action);
        }
        
        this.statusTable.putString("lastAction", selectedAction);
    }
    
    public void end () {
        final String lastAction = this.statusTable.getString("lastAction", "");
        
        if (!lastAction.equals("")) {
            ActionProxy.end(moduleName, lastAction, this.getAction(lastAction));
        }
        
        this.statusTable.putString("lastAction", "");
    }
}