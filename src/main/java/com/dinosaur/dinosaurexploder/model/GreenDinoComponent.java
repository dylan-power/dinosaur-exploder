package com.dinosaur.dinosaurexploder.model;

import com.almasb.fxgl.core.math.Vec2;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.time.LocalTimer;
import javafx.geometry.Point2D;
import javafx.util.Duration;

import static com.almasb.fxgl.dsl.FXGLForKtKt.spawn;

public class GreenDinoComponent extends Component implements Dinosaur{
    double verticalSpeed = 1;

    private LocalTimer timer = FXGL.newLocalTimer();

    @Override
    public void onUpdate(double ptf) {
        entity.translateY(verticalSpeed);

        //The dinosaur shoots every 2 seconds
        if (timer.elapsed(Duration.seconds(2)))
        {
            shoot();
            timer.capture();
        }
    }



    @Override
    public void shoot() {
        Point2D center = entity.getCenter();
        Vec2 direction = Vec2.fromAngle(entity.getRotation() +90);
        spawn("basicEnemyProjectile",
                new SpawnData(center.getX() + 50 +3, center.getY())
                        .put("direction", direction.toPoint2D() )
        );
    }
}
