package com._604robotics.robotnik.trigger.sources;

import com._604robotics.robotnik.trigger.Trigger;
import com.sun.squawk.util.StringTokenizer;
import edu.wpi.first.wpilibj.networktables.NetworkTable;
import edu.wpi.first.wpilibj.tables.ITable;

public class NetworkTrigger extends Trigger {
    private final ITable table;
    
    private final String key;
    private final boolean defaultValue;
    
    public NetworkTrigger (String namespace, String key, boolean defaultValue) {
        this.key = key;
        this.defaultValue = defaultValue;

        final StringTokenizer tokens = new StringTokenizer(namespace, ".");
        
        ITable workingTable = NetworkTable.getTable(tokens.nextToken());
        
        while (tokens.hasMoreTokens()) {
            workingTable = workingTable.getSubTable(tokens.nextToken());
        }
        
        this.table = workingTable;
    }

    public boolean run () {
        return this.table.getBoolean(this.key, this.defaultValue);
    }
}