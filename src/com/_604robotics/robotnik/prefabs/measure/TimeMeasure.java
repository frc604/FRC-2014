package com._604robotics.robotnik.prefabs.measure;

import com._604robotics.robotnik.procedure.Measure;
import edu.wpi.first.wpilibj.Timer;

public class TimeMeasure extends Measure {
    private final Timer timer = new Timer();
    private final double seconds;

    public TimeMeasure (double seconds) {
        this.seconds = seconds;
    }

    public void initialize() {
        timer.reset();
        timer.start();
    }

    public boolean complete () {
        final boolean complete = timer.get() > seconds;
        if (complete)
            timer.stop();
        return complete;
    }
}
