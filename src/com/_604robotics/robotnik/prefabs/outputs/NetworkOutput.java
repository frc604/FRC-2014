package com._604robotics.robotnik.prefabs.outputs;

import com._604robotics.robotnik.data.DataRecipient;
import com.sun.squawk.util.StringTokenizer;
import edu.wpi.first.wpilibj.networktables.NetworkTable;
import edu.wpi.first.wpilibj.tables.ITable;

public class NetworkOutput {
    private final DataRecipient doubleRecipient;
    private final DataRecipient booleanRecipient;
    
    public NetworkOutput (String namespace) {
        final StringTokenizer tokens = new StringTokenizer(namespace, ".");
        
        ITable table = NetworkTable.getTable(tokens.nextToken());
        
        while (tokens.hasMoreTokens()) {
            table = table.getSubTable(tokens.nextToken());
        }
        
        this.doubleRecipient  = new NetworkOutputDataRecipient(table, false);
        this.booleanRecipient = new NetworkOutputDataRecipient(table, true);
    }
    
    public DataRecipient asDouble (String name) {
        return this.doubleRecipient;
    }
    
    public DataRecipient asBoolean (String name) {
        return this.booleanRecipient;
    }
    
    private class NetworkOutputDataRecipient implements DataRecipient {
        private final ITable table;
        private final boolean asBoolean;
        
        public NetworkOutputDataRecipient (ITable table, boolean asBoolean) {
            this.table = table;
            this.asBoolean = asBoolean;
        }

        public void sendData (String fieldName, double dataValue) {
            if (this.asBoolean) {
                this.table.putBoolean(fieldName, dataValue > 0);
            } else {
                this.table.putNumber(fieldName, dataValue);
            }
        }
    }
}