package com.dinosaur.dinosaurexploder.model;

import com.almasb.fxgl.core.math.Vec2;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.time.LocalTimer;
import javafx.geometry.Point2D;
import javafx.util.Duration;

import static com.almasb.fxgl.dsl.FXGLForKtKt.spawn;
/**
 * Summary :
 *      This class extends Component and Implements the Dinosaur Classes and Handles the Shooting and Updating the Dino
 */
public class GreenDinoComponent extends Component implements Dinosaur{
    public Difficulty difficulty = GameSettings.getInstance().getDifficulty();
    private final LocalTimer timer = FXGL.newLocalTimer();
    /**
     * Summary :
     *      This method runs for every frame like a continues flow , without any stop until we put stop to it.
     * Parameters :
     *      double ptf
     */
    @Override
    public void onUpdate(double ptf) {
        entity.translateY(difficulty.getSpeed());

        //The dinosaur shoots every 2 seconds
        if (timer.elapsed(Duration.seconds(1.5)) && entity.getPosition().getY() > 0)
        {
            shoot();
            timer.capture();
        }
    }
    /**
     * Summary :
     *      This handles with the shooting of the dinosaur and spawning of the new bullet
     */
    @Override
    public void shoot() {
        FXGL.play(GameConstants.ENEMYSHOOT_SOUND);
        Point2D center = entity.getCenter();
        Vec2 direction = Vec2.fromAngle(entity.getRotation() + difficulty.getAngleOffset());
        spawn("basicEnemyProjectile",
                new SpawnData(center.getX(), center.getY())
                        .put("direction", direction.toPoint2D() )
        );
    }
}
