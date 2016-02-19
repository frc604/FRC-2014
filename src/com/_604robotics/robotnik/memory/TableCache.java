package com._604robotics.robotnik.memory;

import edu.wpi.first.wpilibj.networktables.NetworkTable;
import edu.wpi.first.wpilibj.tables.ITable;
import java.util.Hashtable;

public class TableCache {
    private static final Hashtable cache = new Hashtable();
    
    protected static IndexedTable getTable (String key) {
        return getTable(NetworkTable.getTable(key));
    }
    
    protected static IndexedTable getSubTable(ITable parent, String key) {
        return getTable(parent.getSubTable(key));
    }
    
    private static IndexedTable getTable (ITable table) {
        if (cache.containsKey(table)) {
            return (IndexedTable) cache.get(table);
        } else {
            final IndexedTable result = new IndexedTable(table);
            cache.put(table, result);
            
            return result;
        }
    }
}
