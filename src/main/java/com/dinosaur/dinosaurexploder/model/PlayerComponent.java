package com.dinosaur.dinosaurexploder.model;

import static com.almasb.fxgl.dsl.FXGL.image;
import static com.almasb.fxgl.dsl.FXGLForKtKt.spawn;

import com.almasb.fxgl.core.math.Vec2;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.dsl.components.ExpireCleanComponent;
import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.texture.Texture;
import com.dinosaur.dinosaurexploder.view.DinosaurGUI;

import javafx.geometry.Point2D;
import javafx.scene.image.Image;
import javafx.util.Duration;

public class PlayerComponent extends Component implements Player{
	private Image spcshpImg = new Image("assets/textures/spaceship.png");
	
    int movementSpeed = 8;
    //entity is not initialized anywhere because it is linked in the factory
    public void moveUp(){
        if(entity.getY() < 0) {
            System.out.println("Out of bounds");
            return;
        }
        entity.translateY(-movementSpeed);
        spawnMovementAnimation();
    }
    public void moveDown(){
        if(!(entity.getY() < DinosaurGUI.HEIGHT - entity.getHeight())) {
            System.out.println("Out of bounds");
            return;
        }
        entity.translateY(movementSpeed);
        spawnMovementAnimation();
    }
    public void moveRight(){
        if(!(entity.getX() < DinosaurGUI.WIDTH - entity.getWidth())) {
            System.out.println("Out of bounds");
            return;
        }
        entity.translateX(movementSpeed);
        spawnMovementAnimation();
    }
    public void moveLeft(){
        if(entity.getX() < 0) {
            System.out.println("Out of bounds");
            return;
        }
        entity.translateX(-movementSpeed);
        spawnMovementAnimation();
    }

    public void shoot(){
        FXGL.play("shoot.wav");
        Point2D center = entity.getCenter();
        Vec2 direction = Vec2.fromAngle(entity.getRotation() -90);
        Image projImg = new Image("assets/textures/basicProjectile.png");
      
        spawn("basicProjectile",
                new SpawnData(center.getX() - (projImg.getWidth()/2) +3, center.getY() - spcshpImg.getHeight()/2)
                        .put("direction", direction.toPoint2D() )
        );
    }
    
   
    
    private void spawnMovementAnimation() {
    	
        FXGL.entityBuilder()
                .at(getEntity().getCenter().subtract(spcshpImg.getWidth() / 2, spcshpImg.getHeight() / 2))
                .view(new Texture(spcshpImg))
                .with(new ExpireCleanComponent(Duration.seconds(0.15)).animateOpacity())
                .buildAndAttach();
    }
    
	
	
}
