package com._604robotics.robotnik.meta;

import java.util.Hashtable;

public abstract class Repackager {
    protected abstract Object wrap (Object key, Object value);

    private Hashtable compute (Iterator i) {
        final Hashtable table = new Hashtable();
        while (i.next()) {
            table.put(i.key, this.wrap(i.key, i.value));
        }
        return table;
    }

    public static Hashtable repackage (Iterator i, Repackager r) {
        return r.compute(i);
    }
}
