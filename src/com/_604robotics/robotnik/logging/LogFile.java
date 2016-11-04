package com._604robotics.robotnik.logging;

import com.sun.squawk.microedition.io.FileConnection;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Random;
import javax.microedition.io.Connector;

public class LogFile {
    private final PrintStream file;
    
    public LogFile () {
        final String filename = System.currentTimeMillis() + "-" + new Random().nextInt() + ".log";

        PrintStream result = null;
        Exception error = null;
        
        try {
            final FileConnection file = (FileConnection) Connector.open("file:///" + filename, Connector.READ_WRITE);
            if (!file.exists())
                file.create();
            
            result = new PrintStream(file.openOutputStream());
        } catch (IOException ex) {
            error = ex;
        }
         
        file = result;
         
        if (error != null) {
            System.err.println("Could not open log file:");
            error.printStackTrace();
        } else {
            System.out.println("Recording to log file \"" + filename + "\". To pull it off, connect to your cRIO via FTP.");
        }
    }
    
    public synchronized void println (String msg) {
        if (file != null) file.println(msg);
    }
}
