package com.dinosaur.dinosaurexploder.model;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.EntityFactory;
import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.entity.Spawns;

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
                .from(data)
                .viewWithBBox("spaceship.png")
                .with(new PlayerComponent())
                .build();
    }

    @Spawns("basicProjectile")
    public Entity newBasicProjectile(SpawnData data)
    {
        return FXGL.entityBuilder()
                .from(data)
                .viewWithBBox("basicProjectile.png")
                .build();

    }
}
