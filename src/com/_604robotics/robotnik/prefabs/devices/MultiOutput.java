package com._604robotics.robotnik.prefabs.devices;

import edu.wpi.first.wpilibj.PIDOutput;

public class MultiOutput implements PIDOutput {
    private final PIDOutput[] outputs;
    
    public MultiOutput (PIDOutput[] outputs) {
        this.outputs = outputs;
    }
    
    public void set (double value) {
        for (int i = 0; i < this.outputs.length; i++) {
            this.outputs[i].pidWrite(value);
        }
    }

    public void pidWrite (double value) {
        this.set(value);
    }
    
    public void stopMotor () {
        this.set(0D);
    }
}
