package com._604robotics.robotnik.module;

import com._604robotics.robotnik.action.ActionManager;
import com._604robotics.robotnik.action.ActionReference;
import com._604robotics.robotnik.data.DataManager;
import com._604robotics.robotnik.data.DataReference;
import com._604robotics.robotnik.memory.IndexedTable;
import com._604robotics.robotnik.trigger.TriggerManager;
import com._604robotics.robotnik.trigger.TriggerReference;

public class ModuleReference {
    private final Module module;
    
    private final DataManager dataManager;
    private final TriggerManager triggerManager;
    
    private final ActionManager actionManager;
    
    public ModuleReference (String name, Module module, IndexedTable table) {
        this.dataManager = new DataManager(name, module.getDataMap(), table.getSubTable("data"));
        this.triggerManager = new TriggerManager(name, module.getTriggerMap(), table.getSubTable("triggers"));
        
        this.actionManager = new ActionManager(this, name, module.getActionController(), table.getSubTable("actions"));
        
        this.module = module;
    }
    
    public DataReference getData (String name) {
        return this.dataManager.getData(name);
    }
    
    public TriggerReference getTrigger (String name) {
        return this.triggerManager.getTrigger(name);
    }
    
    public ActionReference getAction (String name) {
        return this.actionManager.getAction(name);
    }
    
    public void start () {
        this.module.start();
    }
    
    public void update () {
        this.dataManager.update();
        this.triggerManager.update();
        
        this.actionManager.reset();
    }
    
    public void execute () {
        this.actionManager.update();
        this.actionManager.execute();
    }
    
    public void end () {
        this.actionManager.end();
        this.module.end();
    }
}