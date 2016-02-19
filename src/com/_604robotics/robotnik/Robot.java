package com._604robotics.robotnik;

import com._604robotics.robotnik.coordinator.CoordinatorList;
import com._604robotics.robotnik.procedure.ModeMap;
import com._604robotics.robotnik.module.ModuleManager;
import com._604robotics.robotnik.module.ModuleMap;
import com._604robotics.robotnik.memory.IndexedTable;
import com._604robotics.robotnik.logging.Logger;
import com._604robotics.robotnik.logging.TimeSampler;
import com._604robotics.robotnik.procedure.Procedure;
import edu.wpi.first.wpilibj.SimpleRobot;

public class Robot extends SimpleRobot {
    public static interface Safety {
        public static boolean ENABLED  = true;
        public static boolean DISABLED = false;
    }
    
    private final IndexedTable table = IndexedTable.getTable("robotnik");
    private final TimeSampler loopTime = new TimeSampler("Loop", 1D);
    
    private ModuleManager moduleManager = new ModuleManager(new ModuleMap(), this.table.getSubTable("modules"));
    private CoordinatorList coordinatorList = new CoordinatorList();
    private ModeMap modeMap = new ModeMap();
    
    public Robot () {}
    public Robot (boolean safetyEnabled) {
        if (!safetyEnabled) {
            RobotProxy.disable();
            
            DataProxy.disable();
            TriggerProxy.disable();
            ActionProxy.disable();

            ConnectorProxy.disable();

            Logger.warn("Exception protection has been disabled. Make sure you know what you're doing!");
        }
    }
    
    protected void set (ModuleMap moduleMap) {
        this.moduleManager = new ModuleManager(moduleMap, this.table.getSubTable("modules"));
    }
    
    protected void set (CoordinatorList coordinatorList) {
        this.coordinatorList = coordinatorList;
    }
    
    protected void set (ModeMap modeMap) {
        this.modeMap = modeMap;
    }
    
    public void robotInit () {
        this.coordinatorList.attach(this.moduleManager);
        this.modeMap.attach(this.moduleManager);
    }
    
    public void autonomous () {
        Logger.log(" -- Autonomous mode begin.");
        
        this.loopTime.start();
        RobotProxy.start(moduleManager);
        
        final Procedure mode = this.modeMap.getAutonomousMode();
        while (this.isEnabled() && this.isAutonomous()) {
            RobotProxy.tick(mode, moduleManager, coordinatorList);
            sleep();
            this.loopTime.sample();
        }
        
        RobotProxy.end(mode, moduleManager);
        this.loopTime.stop();

        Logger.log(" -- Autonomous mode end.");
    }
    
    public void operatorControl () {
        Logger.log(" -- Teleop mode begin.");
        
        this.loopTime.start();
        RobotProxy.start(moduleManager);
        
        final Procedure mode = this.modeMap.getTeleopMode();
        while (this.isEnabled() && this.isOperatorControl()) {
            RobotProxy.tick(mode, moduleManager, coordinatorList);
            sleep();
            this.loopTime.sample();
        }
        
        RobotProxy.end(mode, moduleManager);
        this.loopTime.stop();
        
        Logger.log(" -- Teleop mode end.");
    }
    
    public void disabled () {
        Logger.log(" -- Disabled mode begin.");
        
        while (!this.isEnabled()) {
            RobotProxy.update(moduleManager);
            sleep();
        }
        
        Logger.log(" -- Disabled mode end.");
    }
    
    private void sleep () {
        try {
            Thread.sleep(19);
        } catch (Exception e) {}
    }
}