package com.dinosaur.dinosaurexploder.model;

import com.almasb.fxgl.dsl.EntityBuilder;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.dsl.components.ExpireCleanComponent;
import com.almasb.fxgl.dsl.components.OffscreenCleanComponent;
import com.almasb.fxgl.dsl.components.ProjectileComponent;
import com.almasb.fxgl.dsl.views.SelfScrollingBackgroundView;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.EntityFactory;
import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.entity.Spawns;
import com.almasb.fxgl.physics.BoundingShape;
import com.almasb.fxgl.physics.HitBox;
import com.almasb.fxgl.texture.AnimatedTexture;
import com.almasb.fxgl.texture.AnimationChannel;
import javafx.geometry.Orientation;
import javafx.geometry.Point2D;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.util.Duration;

import java.util.Objects;

import static com.almasb.fxgl.dsl.FXGLForKtKt.texture;

/**
 * Summary :
 *      The Factory handles the creation of Background , Player , Score , Life , Dino, Explosion
 */
public class GameEntityFactory implements EntityFactory {
    /**
     * Summary :
     *      New Background creation will be handled in below Entity
     */
    @Spawns("background")
    public Entity newBackground(SpawnData data){
        Image img = new Image(Objects.requireNonNull(Objects.requireNonNull(getClass().getResource(GameConstants.BACKGROUND_IMAGEPATH)).toString()));

        return FXGL.entityBuilder()
                .view(new SelfScrollingBackgroundView(img, 3000, 1500, Orientation.VERTICAL, -50))
                .zIndex(-1)
                .buildAndAttach();
    }
    /**
     * Summary :
     *      New Players creation will be handled in below Entity
     */
    @Spawns("player")
    public Entity newPlayer(SpawnData data)
    {
        return entityBuilderBase(data, EntityType.PLAYER)
                .view(GameConstants.SPACESHIP_IMAGEFILE)
                // Center body hitbox
                .bbox(new HitBox(new Point2D(20, 0), BoundingShape.box(35, 100)))
                // Wings hitbox
                .bbox(new HitBox(new Point2D(-5, 45), BoundingShape.box(90, 30)))
                .collidable()
                .with(new PlayerComponent())
                .build();
    }
    /**
     * Summary :
     *      New BasicProjectile creation will be handled in below Entity
     */
    @Spawns("basicProjectile")
    public Entity newBasicProjectile(SpawnData data) {
        Point2D direction = data.get("direction");
        return entityBuilderBase(data, EntityType.PROJECTILE)
                //The OffscreenCleanComponent is used because when the projectiles move, if they
                //move outside the screen we want them deleted.
                .with(new OffscreenCleanComponent())
                .view(GameConstants.BASE_PROJECTILE_IMAGEFILE)
                .bbox(new HitBox(BoundingShape.box(50, 50)))
                .collidable()
                .with(new ProjectileComponent(direction, 600))
                .build();

    }
    /**
     * Summary :
     *      New Enemy BasicProjectile creation will be handled in below Entity
     */
    @Spawns("basicEnemyProjectile")
    public Entity newBasicEnemyProjectile(SpawnData data) {
        Point2D direction = data.get("direction");
        return entityBuilderBase(data, EntityType.GREENDINO)
                .with(new OffscreenCleanComponent())
                .view(texture(GameConstants.ENEMY_PROJECTILE_IMAGEFILE, 30, 17))
                .bbox(new HitBox(BoundingShape.box(20, 20)))
                .collidable()
                .with(new ProjectileComponent(direction, 300))
                .build();

    }
    /**
     * Summary :
     *      New Green Dino creation will be handled in below Entity
     */
    @Spawns("greenDino")
    public Entity newGreenDino(SpawnData data) {
        return entityBuilderBase(data, EntityType.GREENDINO)
                .with(new OffscreenCleanComponent())
                .view(texture(GameConstants.GREENDINO_IMAGEFILE, 80 , 60))
                .bbox(new HitBox(BoundingShape.box(65,55)))
                .collidable()
                .with(new GreenDinoComponent())
                .build();
    }
    /**
     * Summary :
     *      Setting up the Score will be handled in below Entity
     */
    @Spawns("Score")
    public Entity newScore(SpawnData data) {
        Text scoreText = new Text("Score: 0");
        scoreText.setFill(Color.GREEN);
        scoreText.setFont(Font.font(GameConstants.ARCADECLASSIC_FONTNAME, 20));
        return entityBuilderBase(data,EntityType.SCORE)
                .view(scoreText)
                .with(new ScoreComponent())
                .with(new OffscreenCleanComponent()).build();
    }
    /**
     * Summary :
     *      Life text will be handled in below Entity
     */
    @Spawns("Life")
    public Entity newLife(SpawnData data) 
    {
        Text lifeText = new Text("Lives: 3");
        return entityBuilderBase(data,EntityType.LIFE)
                .from(data)
                .view(lifeText)
                .with(new LifeComponent())
                .with(new OffscreenCleanComponent()).build();
    }

    /**
     * Summary :
     *      Animation of an explosion will be handled in below Entity
     */
    @Spawns("explosion")
    public Entity newExplosion(SpawnData data)
    {
        Duration seconds = Duration.seconds(0.4);
        AnimationChannel ac = new AnimationChannel(
                FXGL.image("explosion.png"),
                seconds, 16);

        AnimatedTexture at = new AnimatedTexture(ac);
        at.play();
        return FXGL.entityBuilder(data)
                .view(at)
                .with(new ExpireCleanComponent(seconds))
                .build();
    }
  
    /**
     * Summary :
     *      Reusable part of every entity
     */
    private EntityBuilder entityBuilderBase(SpawnData data, EntityType type)
    {
        return FXGL.entityBuilder()
                .type(type)
                .from(data);
    }
}
