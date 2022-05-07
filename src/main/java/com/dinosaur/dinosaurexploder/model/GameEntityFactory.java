package com.dinosaur.dinosaurexploder.model;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.dsl.components.OffscreenCleanComponent;
import com.almasb.fxgl.dsl.components.ProjectileComponent;
import com.almasb.fxgl.dsl.views.SelfScrollingBackgroundView;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.EntityFactory;
import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.entity.Spawns;
import com.almasb.fxgl.physics.BoundingShape;
import com.almasb.fxgl.physics.HitBox;
import javafx.geometry.Orientation;
import javafx.geometry.Point2D;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.util.Objects;

import static com.almasb.fxgl.dsl.FXGLForKtKt.texture;

/**
 * The Factory handles the creation (and spawning) of all entities in the game
 *
 */

public class GameEntityFactory implements EntityFactory {

    @Spawns("background")
    public Entity newBackground(SpawnData data)
    {
        Image img = new Image(Objects.requireNonNull(Objects.requireNonNull(getClass().getResource("/assets/textures/background.png")).toString()));

        return FXGL.entityBuilder()
                .view(new SelfScrollingBackgroundView(img, 3000, 1500, Orientation.VERTICAL, -50))
                .zIndex(-1)
                .buildAndAttach();
    }

    @Spawns("player")
    public Entity newPlayer(SpawnData data)
    {
        return FXGL.entityBuilder()
                .type(EntityType.PLAYER)
                .from(data)
                //The view with BBox creates a hitbox from the image
                .viewWithBBox("spaceship.png")
                .collidable()
                .with(new PlayerComponent())
                .build();
    }

    @Spawns("basicProjectile")
    public Entity newBasicProjectile(SpawnData data)
    {
        Point2D direction = data.get("direction");
        return FXGL.entityBuilder()
                .type(EntityType.PROJECTILE)
                .from(data)
                //The OffscreenCleanComponent is used because when the projectiles move, if they
                //move outside the screen we want them deleted.
                .with(new OffscreenCleanComponent())
                .view("basicProjectile.png")
                .bbox(new HitBox(BoundingShape.box(50,50)))
                .collidable()
                .with(new ProjectileComponent(direction, 600))
                .build();

    }

    @Spawns("basicEnemyProjectile")
    public Entity newBasicEnemyProjectile(SpawnData data)
    {
        Point2D direction = data.get("direction");
        return FXGL.entityBuilder()
                .type(EntityType.ENEMYPROJECTILE)
                .from(data)
                .with(new OffscreenCleanComponent())
                .view(texture("enemyProjectile.png", 100 , 100))
                .bbox(new HitBox(BoundingShape.box(20,20)))
                .collidable()
                .with(new ProjectileComponent(direction, 300))
                .build();

    }


    @Spawns("greenDino")
    public Entity newGreenDino(SpawnData data)
    {
        return FXGL.entityBuilder()
                .type(EntityType.GREENDINO)
                .from(data)
                .with(new OffscreenCleanComponent())
                .view(texture("greenDino.png", 80 , 60))
                .bbox(new HitBox(BoundingShape.box(65,55)))
                .collidable()
                .with(new GreenDinoComponent())
                .build();
    }

    @Spawns("Score")
    public Entity newScore(SpawnData data) {
        Text scoreText = new Text("Score: 0");
        scoreText.setFill(Color.GREEN);
        scoreText.setFont(Font.font("Arial", 20));
        return FXGL.entityBuilder().type(EntityType.SCORE).from(data).view(scoreText).with(new ScoreComponent()).with(new OffscreenCleanComponent()).build();
    }

    @Spawns("Life")
    public Entity newLife(SpawnData data) 
    {
        Text lifeText = new Text("Lives: 3");
        return FXGL.entityBuilder().type(EntityType.LIFE).from(data).view(lifeText).with(new LifeComponent()).with(new OffscreenCleanComponent()).build();
    }
}
