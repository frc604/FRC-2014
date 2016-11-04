package com._604robotics.robotnik.logging;

import java.io.PrintStream;
import javax.microedition.io.Connector;
import javax.microedition.io.ServerSocketConnection;
import javax.microedition.io.SocketConnection;

public class LogServer implements Runnable {
    private final Object mutex = new Object();
    private PrintStream out = null;
    
    public void println (String data) {
        synchronized (mutex) {
            if (out != null) {
                out.println(data);
            }
        }
    }
    
    public void run () {
        ServerSocketConnection server = null;
        SocketConnection conn;

        Logger.log("Launching log server...");
        while (server == null) {
            try {
                server = (ServerSocketConnection) Connector.open("socket://:3333");
            } catch (Exception e) {
                server = null;
                
                try {
                    Thread.sleep(1000);
                } catch (Exception e1) {}
            }
        }
        Logger.log("Launched log server.");
        
        while (true) {
            try {
                conn = (SocketConnection) server.acceptAndOpen();
                
                synchronized (mutex) {
                    out = new PrintStream(conn.openOutputStream());
                }
                
                Logger.log("Received log server connection.");
                conn.openInputStream().read();
                Logger.log("Lost log server connection.");
                
            } catch (Exception e) {
                synchronized (mutex) {
                    out = null;
                }
                
                Logger.error("Log server connection died.", e);
                
                try {
                    Thread.sleep(1000);
                } catch (Exception e1) {}
            }
        }
    }
}
