package com._604robotics.robotnik.prefabs.devices;

import com.sun.squawk.util.MathUtils;
import edu.wpi.first.wpilibj.AnalogChannel;
import edu.wpi.first.wpilibj.PIDSource;

// TODO: Document wiring configuration of this sensor.
public class AS5145B implements PIDSource {
    private final AnalogChannel channel;
    private double zero = 0D;
    
    public AS5145B (int module, int port) {
        this.channel = new AnalogChannel(module, port);
        this.channel.setAverageBits(this.getAverageBits());
    }
    
    public AS5145B (int port) {
        this(1, port);
    }
    
    public double getAngle () {
        return (this.channel.getAverageVoltage() * 72) - this.zero;
    }
    
    public double pidGet () {
        return this.getAngle();
    }
    
    private int getAverageBits () {
        return (int) Math.ceil(MathUtils.log(4096 * channel.getModule().getSampleRate() / 1000000) / MathUtils.log(2));
    }
    
    public void setZero () {
        this.zero = this.getAngle();
    }
    
    public void setZero (double zero) {
        this.zero = zero;
    }
}
