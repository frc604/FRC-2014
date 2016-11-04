package com._604robotics.robotnik;

import com._604robotics.robotnik.coordinator.connectors.Binding;
import com._604robotics.robotnik.coordinator.connectors.DataWire;
import com._604robotics.robotnik.logging.Logger;

public class ConnectorProxy {
    private static boolean active = true;
    
    protected static void disable () { active = false; }
    
    public static void pipe (Binding binding) {
        if (active) {
            try {
                conduct(binding);
            } catch (Exception ex) {
                Logger.error("Caught error while piping a Binding", ex);
            }
        } else {
            conduct(binding);
        }
    }
    
    public static void pipe (DataWire wire) {
        if (active) {
            try {
                conduct(wire);
            } catch (Exception ex) {
                Logger.error("Caught error while piping a DataWire", ex);
            }
        } else {
            conduct(wire);
        }
    }
    
    private static void conduct(Binding binding) {
        if (binding.getTrigger().get()) {
            binding.getRecipient().sendTrigger(binding.isSafety() ? 2D : 1D);
        }
    }
    
    private static void conduct (DataWire wire) {
        if (wire.isActive())
            wire.getRecipient().sendData(wire.getFieldName(), wire.getData().get());
    }
}
