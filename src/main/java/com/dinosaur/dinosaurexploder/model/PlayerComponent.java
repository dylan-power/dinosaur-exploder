package com.dinosaur.dinosaurexploder.model;

import com.almasb.fxgl.core.math.Vec2;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.entity.component.Component;
import javafx.geometry.Point2D;
import javafx.scene.image.Image;

import static com.almasb.fxgl.dsl.FXGLForKtKt.spawn;

public class PlayerComponent extends Component {
    int movementSpeed = 8;
    //entity is not initialized anywhere because it is linked in the factory
    public void moveUp(){
        entity.translateY(-movementSpeed);
    }
    public void moveDown(){
        entity.translateY(movementSpeed);
    }
    public void moveRight(){
        entity.translateX(movementSpeed);
    }
    public void moveLeft(){
        entity.translateX(-movementSpeed);
    }

    public void shoot(){
        Point2D center = entity.getCenter();
        Vec2 direction = Vec2.fromAngle(entity.getRotation() -90);
        Image projImg = new Image("assets/textures/basicProjectile.png");
        Image spcshpImg = new Image("assets/textures/spaceship.png");
        spawn("basicProjectile",
                new SpawnData(center.getX() - (projImg.getWidth()/2), center.getY() - spcshpImg.getHeight() -20)
                .put("direction", direction.toPoint2D() )
        );
    }
}
