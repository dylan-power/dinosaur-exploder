package com.dinosaur.dinosaurexploder.controller;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.dinosaur.dinosaurexploder.model.*;
import com.dinosaur.dinosaurexploder.view.DinosaurGUI;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import static com.almasb.fxgl.dsl.FXGL.*;
import static com.almasb.fxgl.dsl.FXGLForKtKt.spawn;
import static javafx.util.Duration.seconds;

public class DinosaurController {
    private Entity player;
    private Entity score;
    public static Entity life;

    public static Entity secondLife;

    public static Entity thirdLife;
    
    public void damagePlayer() throws IllegalArgumentException {
        var life1 = life.getComponent(LifeComponent.class);;
        var life2 = secondLife.getComponent(LifeComponent.class);
        var life3 = thirdLife.getComponent(LifeComponent.class);

        if (life1.getValue() == 1) {
            life1.setValue(0);
            life.removeFromWorld();
            System.out.println("2 lives remaining");
            }
        else if (life2.getValue() == 1) {
            life2.setValue(0);
            secondLife.removeFromWorld();
            System.out.println("1 life remaining");
            }
        else {
            life3.setValue(0);
            thirdLife.removeFromWorld();
            System.out.println("Game Over!");
            System.exit(1);
        }
        var flash = new Rectangle(DinosaurGUI.WIDTH, DinosaurGUI.HEIGHT, Color.rgb(190, 10, 15, 0.5));
        getGameScene().addUINode(flash);
        runOnce(() -> getGameScene().removeUINode(flash), seconds(0.5));

    }

    public void initInput() {
        onKey(KeyCode.UP, () -> player.getComponent(PlayerComponent.class).moveUp());
        onKey(KeyCode.DOWN, () -> player.getComponent(PlayerComponent.class).moveDown());
        onKey(KeyCode.LEFT, () -> player.getComponent(PlayerComponent.class).moveLeft());
        onKey(KeyCode.RIGHT, () -> player.getComponent(PlayerComponent.class).moveRight());

        onKeyDown(KeyCode.SPACE,() -> player.getComponent(PlayerComponent.class).shoot());
    }

    public void initGame() throws IllegalArgumentException {
        getGameWorld().addEntityFactory(new GameEntityFactory());

        spawn("background", 0, 0);

        player = spawn("player", getAppCenter().getX() - 45, getAppHeight()-200);

        FXGL.play("engine.wav");

        /* At each second that passes, we have 2 out of 3 chances of spawning a green dinosaur
        *  This spawns dinosaurs randomly
         */
        run(() -> {
            if (random(0,2) < 2)
                spawn("greenDino", random(0, getAppWidth() - 80), -50);
        }, seconds(0.75));

       score = spawn("Score", getAppCenter().getX() -250, getAppCenter().getY() - 300);
       life = spawn("Life", getAppCenter().getX() +175, getAppCenter().getY() - 300);
       secondLife = spawn ("Second Life", getAppCenter().getX() + 125, getAppCenter().getY() -300);
       thirdLife = spawn ("Third Life", getAppCenter().getX() + 75, getAppCenter().getY() -300);
    }

    public void initPhysics() {
        onCollisionBegin(EntityType.PROJECTILE, EntityType.GREENDINO, (projectile, greendino) -> {
            FXGL.play("enemyExplode.wav");
            projectile.removeFromWorld();
            greendino.removeFromWorld();
            score.getComponent(ScoreComponent.class).incrementScore(1);
        });
        
        onCollisionBegin(EntityType.ENEMYPROJECTILE, EntityType.PLAYER, (projectile, player) -> {
            FXGL.play("playerHit.wav");
            projectile.removeFromWorld();
            System.out.println("You got hit !\n");
            damagePlayer();
        });
        
        onCollisionBegin(EntityType.PLAYER, EntityType.GREENDINO, (player, greendino) -> {
            FXGL.play("playerHit.wav");
            greendino.removeFromWorld();
            System.out.println("You touched a dino !");
            damagePlayer();
        });
    }
}
