package com._604robotics.robotnik.logging;

public class InternalLogger {
    public static void missing (String type, String name) {
        Logger.warn("Missing " + type + " - " + name);
    }
    
    public static void caught (String moduleName, String type, String name, String step, Exception ex) {
        Logger.error("Caught error while in step " + step + " of " + type + " " + name + " of module " + moduleName, ex);
    }
}
