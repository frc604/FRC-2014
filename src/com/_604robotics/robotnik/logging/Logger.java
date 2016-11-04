package com._604robotics.robotnik.logging;

import java.io.PrintStream;

public class Logger {
    public static void log (String message) {
        record(System.out, "[INFO] " + message);
    }
    
    public static void warn (String message) {
        record(System.err, "[WARN] " + message);
        trace(new Exception());
    }
    
    public static void error (String message, Exception ex) {
        record(System.err, "[ERROR] " + message + ": (" + ex.getClass().getName() + ") " + ex.getMessage());
        trace(ex);
    }
    
    private static void record (PrintStream std, String message) {
        final String line = "(" + System.currentTimeMillis() + " ms) " + message;
        
        std.println(line);
        file.println(line);
        server.println(line);
    }
    
    private static void trace (Exception ex) {
        ex.printStackTrace();
        file.println(ex.toString());
        server.println(ex.toString());
    }
    
    private static final LogFile file = new LogFile();
    private static final LogServer server = new LogServer();
    
    static {
        new Thread(server).start();
    }
}
