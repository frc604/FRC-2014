package com._604robotics.robotnik;

import com._604robotics.robotnik.logging.InternalLogger;
import com._604robotics.robotnik.trigger.TriggerReference;

public class TriggerProxy {
    private static boolean active = true;
    
    protected static void disable () { active = false; }
    
    public static void update (String moduleName, String triggerName, TriggerReference trigger) {
        if (active) {
            try {
                trigger.update();
            } catch (Exception ex) {
                InternalLogger.caught(moduleName, "Trigger", triggerName, "run", ex);
            }
        } else {
            trigger.update();
        }
    }
}
