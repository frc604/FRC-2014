package com._604robotics.robotnik.prefabs.devices;

import edu.wpi.first.wpilibj.AnalogChannel;
import edu.wpi.first.wpilibj.PIDSource;

public class MA3A10 implements PIDSource {
    private final AnalogChannel input;
    private double zero = 0D;
    
    public MA3A10 (int module, int port) {
        this.input = new AnalogChannel(module, port);
    }
    
    public MA3A10 (int port) {
        this(1, port);
    }
    
    public void setZero () {
        this.zero = this.getRaw();
    }
    
    public void setZero (double zero) {
        this.zero = zero;
    }
    
    public void setZeroAngle (double zeroAngle) {
        this.setZero(zeroAngle / 360 * 5);
    }
    
    public double getRaw () {
        return input.getVoltage() - zero;
    }
    
    public double getAngle () {
        return this.getRaw() / 5 * 360;
    }
    
    public double pidGet () {
        return this.getAngle();
    }
}
