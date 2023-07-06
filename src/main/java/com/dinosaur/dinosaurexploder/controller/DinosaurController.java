package com.dinosaur.dinosaurexploder.controller;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.dinosaur.dinosaurexploder.DinosaurApp;
import com.dinosaur.dinosaurexploder.model.*;
import com.dinosaur.dinosaurexploder.view.DinosaurGUI;
import com.dinosaur.dinosaurexploder.view.DinosaurMenu;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

import static com.almasb.fxgl.dsl.FXGL.*;
import static com.almasb.fxgl.dsl.FXGLForKtKt.spawn;
import static javafx.util.Duration.seconds;

/**
 * Summary :
 *      The Factory handles the Dinosaur , player controls and collision detection of all entities in the game
 */
public class DinosaurController {
    private Entity player;
    private Entity score;
    private Entity life;
    private int lives = 3;
    private int play;
    Properties properties = new Properties();
    FileInputStream fileIn;

    public void init(){
        try {
            fileIn = new FileInputStream("config.properties");
            properties.load(fileIn);
            fileIn.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        String flag = properties.getProperty("flag");
        play = Integer.valueOf(flag);
    };

    /**
     * Summary :
     *      Detecting the player damage to decrease the lives and checking if the game is over
     */
    public void damagePlayer() {
        init();
        lives = life.getComponent(LifeComponent.class).decreaseLife(1);
        var flash = new Rectangle(DinosaurGUI.WIDTH, DinosaurGUI.HEIGHT, Color.rgb(190, 10, 15, 0.5));
        getGameScene().addUINode(flash);
        runOnce(() -> getGameScene().removeUINode(flash), seconds(0.5));

        if (lives <= 0) {
            // Added extra line of code to sync the lives counter after death
            // All hearts disappear after death
            life.getComponent(LifeComponent.class).onUpdate(lives);
            System.out.println("Game Over!");
            gameOver();
        }
        else{
            System.out.printf("%d lives remaining ! ", lives);
        }
    }
    /**
     * Summary :
     *      To move the space shuttle in forward , backward , right , left directions
     */
    public void initInput() {
        init();
        onKey(KeyCode.UP, () -> player.getComponent(PlayerComponent.class).moveUp());
        onKey(KeyCode.DOWN, () -> player.getComponent(PlayerComponent.class).moveDown());
        onKey(KeyCode.LEFT, () -> player.getComponent(PlayerComponent.class).moveLeft());
        onKey(KeyCode.RIGHT, () -> player.getComponent(PlayerComponent.class).moveRight());

        onKeyDown(KeyCode.SPACE,() -> player.getComponent(PlayerComponent.class).shoot(play));
    }
    /**
     * Summary :
     *      Game Background , Spawning Dinos , Limiting Player movements are Described in the below Method
     */
    public void initGame() {
        init();
        getGameWorld().addEntityFactory(new GameEntityFactory());

        spawn("background", 0, 0);

        player = spawn("player", getAppCenter().getX() - 45, getAppHeight()-200);
        if (play == 1) {
            FXGL.play("gameBackground.wav");
        }

        /*
         * At each second that passes, we have 2 out of 3 chances of spawning a green
         * dinosaur
         * This spawns dinosaurs randomly
         */
        run(() -> {
            if (random(0, 2) < 2)
                spawn("greenDino", random(0, getAppWidth() - 80), -50);
        }, seconds(0.75));

       score = spawn("Score", getAppCenter().getX() -270, getAppCenter().getY() - 320);
       life = spawn("Life", getAppCenter().getX() +175, getAppCenter().getY() - 300);
    }
    /**
     * Summary :
     *      Detect the collision between the game elements.
     */
    public void initPhysics() {
        init();
        onCollisionBegin(EntityType.PROJECTILE, EntityType.GREENDINO, (projectile, greendino) -> {
            if (play == 1) {
                FXGL.play("enemyExplode.wav");
            }
            projectile.removeFromWorld();
            greendino.removeFromWorld();
            score.getComponent(ScoreComponent.class).incrementScore(1);
        });
        
        onCollisionBegin(EntityType.ENEMYPROJECTILE, EntityType.PLAYER, (projectile, player) -> {
            if (play == 1) {
                FXGL.play("playerHit.wav");
            }
            projectile.removeFromWorld();
            System.out.println("You got hit !\n");
            damagePlayer();
        });
        
        onCollisionBegin(EntityType.PLAYER, EntityType.GREENDINO, (player, greendino) -> {
            if (play == 1) {
                FXGL.play("playerHit.wav");
            }
            greendino.removeFromWorld();
            System.out.println("You touched a dino !");
            damagePlayer();
        });
    }
    /**
     * Summary :
     *      To detect whether the player lives are empty or not
     */
    public void gameOver(){
        getDialogService().showConfirmationBox("Game Over. Play Again?", yes ->{
            if (yes){
                getGameController().startNewGame();
            } else {
                getGameController().gotoMainMenu();
            }
        });
    }
}
