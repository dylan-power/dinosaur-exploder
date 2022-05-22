package com.dinosaur.dinosaurexploder.model;

import com.almasb.fxgl.core.math.Vec2;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.entity.component.Component;
import com.dinosaur.dinosaurexploder.view.DinosaurGUI;
import javafx.geometry.Point2D;
import javafx.scene.image.Image;

import static com.almasb.fxgl.dsl.FXGLForKtKt.*;

public class PlayerComponent extends Component implements Player{
    int movementSpeed = 8;
    //entity is not initialized anywhere because it is linked in the factory
    public void moveUp(){
        if(entity.getY() < 0) {
            System.out.println("Out of bounds");
            return;
        }
        entity.translateY(-movementSpeed);
    }
    public void moveDown(){
        if(!(entity.getY() < DinosaurGUI.HEIGHT - entity.getHeight())) {
            System.out.println("Out of bounds");
            return;
        }
        entity.translateY(movementSpeed);
    }
    public void moveRight(){
        if(!(entity.getX() < DinosaurGUI.WIDTH - entity.getWidth())) {
            System.out.println("Out of bounds");
            return;
        }
        entity.translateX(movementSpeed);
    }
    public void moveLeft(){
        if(entity.getX() < 0) {
            System.out.println("Out of bounds");
            return;
        }
        entity.translateX(-movementSpeed);
    }

    public void shoot(){
        FXGL.play("shoot.wav");
        Point2D center = entity.getCenter();
        Vec2 direction = Vec2.fromAngle(entity.getRotation() -90);
        Image projImg = new Image("assets/textures/basicProjectile.png");
        Image spcshpImg = new Image("assets/textures/spaceship.png");
        spawn("basicProjectile",
                new SpawnData(center.getX() - (projImg.getWidth()/2) +3, center.getY() - spcshpImg.getHeight()/2)
                        .put("direction", direction.toPoint2D() )
        );
    }
}
