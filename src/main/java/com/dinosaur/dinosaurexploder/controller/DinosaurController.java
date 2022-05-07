package com.dinosaur.dinosaurexploder.controller;

import com.almasb.fxgl.entity.Entity;
import com.dinosaur.dinosaurexploder.model.*;
import javafx.scene.input.KeyCode;

import static com.almasb.fxgl.dsl.FXGL.*;
import static com.almasb.fxgl.dsl.FXGLForKtKt.spawn;
import static javafx.util.Duration.seconds;

public class DinosaurController {
    private Entity player;
    private Entity score;
    private Entity life;
    private int lives = 3;
    
    public void damagePlayer() {
        lives = life.getComponent(LifeComponent.class).decreaseLife(1);

        if (lives < 0) {
            System.out.println("Game Over!");
            System.exit(1);
        }
        else{
            System.out.printf("%d lives remaining ! ", lives);
        }
    }

    public void initInput() {
        onKey(KeyCode.UP, () -> player.getComponent(PlayerComponent.class).moveUp());
        onKey(KeyCode.DOWN, () -> player.getComponent(PlayerComponent.class).moveDown());
        onKey(KeyCode.LEFT, () -> player.getComponent(PlayerComponent.class).moveLeft());
        onKey(KeyCode.RIGHT, () -> player.getComponent(PlayerComponent.class).moveRight());

        onKeyDown(KeyCode.SPACE,() -> player.getComponent(PlayerComponent.class).shoot());
    }

    public void initGame() {
        getGameWorld().addEntityFactory(new GameEntityFactory());

        spawn("background", 0, 0);

        player = spawn("player", getAppCenter().getX() - 45, getAppHeight()-200);

        /* At each second that passes, we have 2 out of 3 chances of spawning a green dinosaur
        *  This spawns dinosaurs randomly
         */
        run(() -> {
            if (random(0,2) < 2)
                spawn("greenDino", random(0, getAppWidth() - 80), -50);
        }, seconds(0.75));

       score = spawn("Score", getAppCenter().getX() -250, getAppCenter().getY() - 300);
       life = spawn("Life", getAppCenter().getX() +175, getAppCenter().getY() - 300);
    }

    public void initPhysics() {
        onCollisionBegin(EntityType.PROJECTILE, EntityType.GREENDINO, (projectile, greendino) -> {
            projectile.removeFromWorld();
            greendino.removeFromWorld();
            score.getComponent(ScoreComponent.class).incrementScore(1);
        });
        
        onCollisionBegin(EntityType.ENEMYPROJECTILE, EntityType.PLAYER, (projectile, player) -> {
            projectile.removeFromWorld();
            System.out.println("You got hit !\n");
            damagePlayer();
        });
        
        onCollisionBegin(EntityType.PLAYER, EntityType.GREENDINO, (player, greendino) -> {
            greendino.removeFromWorld();
            System.out.println("You touched a dino !");
            damagePlayer();
        });
    }
}
