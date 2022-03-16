package com.dinosaur.dinosaurexploder.model;

import com.almasb.fxgl.core.math.Vec2;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.physics.PhysicsComponent;
import com.almasb.fxgl.time.LocalTimer;
import javafx.geometry.Point2D;
import javafx.util.Duration;

public class GreenDinoComponent extends Component implements Dinosaur{
    double verticalSpeed = 1;

    @Override
    public void onUpdate(double ptf) {
        entity.translateY(verticalSpeed);
    }

    @Override
    public void shoot() {

    }
}
