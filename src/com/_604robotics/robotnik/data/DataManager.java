package com._604robotics.robotnik.data;

import com._604robotics.robotnik.DataProxy;
import com._604robotics.robotnik.meta.Iterator;
import com._604robotics.robotnik.meta.Repackager;
import com._604robotics.robotnik.memory.IndexedTable;
import com._604robotics.robotnik.logging.InternalLogger;
import java.util.Hashtable;

public class DataManager {
    private final String moduleName;
    
    private final Hashtable dataTable;
    
    public DataManager (String moduleName, DataMap dataMap, final IndexedTable table) {
        this.moduleName = moduleName;
        
        this.dataTable = Repackager.repackage(dataMap.iterate(), new Repackager() {
           public Object wrap (Object key, Object value) {
               return new DataReference((Data) value, table.getSlice((String) key));
           }
        });
    }
    
    public DataReference getData (String name) {
        final DataReference ref = (DataReference) this.dataTable.get(name);
        if (ref == null) InternalLogger.missing("DataReference", name);
        return ref;
    }
    
    public void update () {
        final Iterator i = new Iterator(this.dataTable);
        while (i.next()) DataProxy.update(moduleName, (String) i.key, (DataReference) i.value);
    }
}
