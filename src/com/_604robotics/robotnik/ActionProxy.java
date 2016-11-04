package com._604robotics.robotnik;

import com._604robotics.robotnik.action.ActionReference;
import com._604robotics.robotnik.logging.InternalLogger;

public class ActionProxy {
    private static boolean active = true;
    
    protected static void disable () { active = false; }
    
    public static void begin (String moduleName, String actionName, ActionReference action) {
        if (active) {
            try {
                action.begin();
            } catch (Exception ex) {
                InternalLogger.caught(moduleName, "Action", actionName, "begin", ex);
            }
        } else {
            action.begin();
        }
    }
    
    public static void run (String moduleName, String actionName, ActionReference action) {
        if (active) {
            try {
                action.run();
            } catch (Exception ex) {
                InternalLogger.caught(moduleName, "Action", actionName, "run", ex);
            }
        } else {
            action.run();
        }
    }
    
    public static void end (String moduleName, String actionName, ActionReference action) {
        if (active) {
            try {
                action.end();
            } catch (Exception ex) {
                InternalLogger.caught(moduleName, "Action", actionName, "end", ex);
            }
        } else {
            action.end();
        }
    }
}
