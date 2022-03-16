package com.dinosaur.dinosaurexploder.model;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.dsl.components.OffscreenCleanComponent;
import com.almasb.fxgl.dsl.components.ProjectileComponent;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.EntityFactory;
import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.entity.Spawns;
import com.almasb.fxgl.physics.BoundingShape;
import com.almasb.fxgl.physics.HitBox;
import javafx.geometry.Point2D;

import static com.almasb.fxgl.dsl.FXGLForKtKt.texture;

/**
 * The Factory handles the creation (and spawning) of all entities in the game
 *
 */

public class GameEntityFactory implements EntityFactory {

    @Spawns("background")
    public Entity newBackground(SpawnData data)
    {
        return FXGL.entityBuilder()
                .from(data)
                .view("background.png")
                .build();
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
                .bbox(new HitBox(BoundingShape.box(30,10)))
                .collidable()
                .with(new ProjectileComponent(direction, 600))
                .build();

    }

    @Spawns("basicEnemyProjectile")
    public Entity newBasicEnemyProjectile(SpawnData data)
    {
        Point2D direction = data.get("direction");
        return FXGL.entityBuilder()
                .from(data)
                .with(new OffscreenCleanComponent())
                .view("basicProjectile.png")
                .collidable()
                .with(new ProjectileComponent(direction, 600))
                .build();

    }


    @Spawns("greenDino")
    public Entity newGreenDino(SpawnData data)
    {
        return FXGL.entityBuilder()
                .type(EntityType.GREENDINO)
                .from(data)
                .with(new OffscreenCleanComponent())
                .view(texture("greenDino.png", 83 , 59))
                .bbox(new HitBox(BoundingShape.box(65,55)))
                .collidable()
                .build();
    }

}
