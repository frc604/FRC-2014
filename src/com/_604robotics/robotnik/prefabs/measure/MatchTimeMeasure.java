package com._604robotics.robotnik.prefabs.measure;

import com._604robotics.robotnik.procedure.Measure;
import edu.wpi.first.wpilibj.DriverStation;

public class MatchTimeMeasure extends Measure {
    private final double seconds;

    public MatchTimeMeasure (double seconds) {
        this.seconds = seconds;
    }

    public boolean complete () {
        return DriverStation.getInstance().getMatchTime() >= seconds;
    }
}
