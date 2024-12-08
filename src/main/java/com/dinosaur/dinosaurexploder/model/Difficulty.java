package com.dinosaur.dinosaurexploder.model;

import java.util.Random;

public class Difficulty {
    private final double speed;
    private final double minAngleOffset;
    private final double maxAngleOffset;
    private final Random random = new Random();

    public Difficulty(double speed, double min, double max) {
        assert max > min;
        this.speed = speed;
        this.minAngleOffset = min;
        this.maxAngleOffset = max;
    }
    public double getSpeed() {
        return speed;
    }

    public double getAngleOffset() {
        if (minAngleOffset == maxAngleOffset) {
            return minAngleOffset;
        }
        return minAngleOffset + (maxAngleOffset - minAngleOffset) * random.nextDouble();
    }
}
