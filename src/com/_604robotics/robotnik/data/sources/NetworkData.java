package com._604robotics.robotnik.data.sources;

import com._604robotics.robotnik.data.Data;
import com.sun.squawk.util.StringTokenizer;
import edu.wpi.first.wpilibj.networktables.NetworkTable;
import edu.wpi.first.wpilibj.tables.ITable;

public class NetworkData extends Data {
    private final ITable table;
    
    private final String key;
    private final double defaultValue;
    
    public NetworkData (String namespace, String key, double defaultValue) {
        this.key = key;
        this.defaultValue = defaultValue;

        final StringTokenizer tokens = new StringTokenizer(namespace, ".");
        
        ITable workingTable = NetworkTable.getTable(tokens.nextToken());
        while (tokens.hasMoreTokens()) workingTable = workingTable.getSubTable(tokens.nextToken());
        
        this.table = workingTable;
    }

    public double run () {
        return this.table.getNumber(this.key, this.defaultValue);
    }
}