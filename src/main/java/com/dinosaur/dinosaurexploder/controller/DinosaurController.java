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
    private Entity life;
    private int lives = 3;
    
    public void damagePlayer() {
        lives = life.getComponent(LifeComponent.class).decreaseLife(1);
        var flash = new Rectangle(DinosaurGUI.WIDTH, DinosaurGUI.HEIGHT, Color.rgb(190, 10, 15, 0.5));
        getGameScene().addUINode(flash);
        runOnce(() -> getGameScene().removeUINode(flash), seconds(0.5));

        if (lives <= 0) {
            System.out.println("Game Over!");
            gameOver();
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

        FXGL.play("gameBackground.wav");

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
    public void gameOver(){
        getDialogService().showConfirmationBox("Game Over. Play Again?", yes ->{
            if (yes){
                getGameController().startNewGame();
            }else{
                getGameController().gotoMainMenu();
            }
        });
    }
}
