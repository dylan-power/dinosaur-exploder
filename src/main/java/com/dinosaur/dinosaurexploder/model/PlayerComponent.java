package com.dinosaur.dinosaurexploder.model;

import static com.almasb.fxgl.dsl.FXGLForKtKt.spawn;

import com.almasb.fxgl.core.math.Vec2;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.dsl.components.ExpireCleanComponent;
import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.texture.Texture;
import com.dinosaur.dinosaurexploder.controller.SoundController;
import com.dinosaur.dinosaurexploder.view.DinosaurGUI;

import javafx.geometry.Point2D;
import javafx.scene.image.Image;
import javafx.util.Duration;

public class PlayerComponent extends Component implements Player{
    private Image spcshpImg = new Image(GameConstants.SPACESHIP_IMAGEPATH);

    int movementSpeed = 8;
    //entity is not initialized anywhere because it is linked in the factory
    /**
     * Summary :
     *      This method is overriding the superclass method to limit the upSide movement.
     */
    public void moveUp(){
        if(entity.getY() < 0) {
            System.out.println("Out of bounds");
            return;
        }
        entity.translateY(-movementSpeed);
        spawnMovementAnimation();
    }
    /**
     * Summary :
     *      This method is overriding the superclass method to limit the downSide movement.
     */
    public void moveDown(){
        if(!(entity.getY() < DinosaurGUI.HEIGHT - entity.getHeight())) {
            System.out.println("Out of bounds");
            return;
        }
        entity.translateY(movementSpeed);
        spawnMovementAnimation();
    }
    /**
     * Summary :
     *      This method is overriding the superclass method to limit the rightSide movement.
     */
    public void moveRight(){
        if(!(entity.getX() < DinosaurGUI.WIDTH - entity.getWidth())) {
            System.out.println("Out of bounds");
            return;
        }
        entity.translateX(movementSpeed);
        spawnMovementAnimation();
    }
    /**
     * Summary :
     *      This method is overriding the superclass method to limit the leftSide movement.
     */
    public void moveLeft(){
        if(entity.getX() < 0) {
            System.out.println("Out of bounds");
            return;
        }
        entity.translateX(-movementSpeed);
        spawnMovementAnimation();
    }
    /**
     * Summary :
     *      This method is overriding the superclass method to the shooting from the player and spawning of the new bullet
     */
    public void shoot(){
        SoundController.getInstance().playSoundEffect(GameConstants.SHOOT_SOUND);
        Point2D center = entity.getCenter();
        Vec2 direction = Vec2.fromAngle(entity.getRotation() -90);
        Image projImg = new Image(GameConstants.BASE_PROJECTILE_IMAGEPATH);

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