package com.dinosaur.dinosaurexploder.controller;

import com.almasb.fxgl.entity.Entity;
import com.dinosaur.dinosaurexploder.model.EntityType;
import com.dinosaur.dinosaurexploder.model.GameEntityFactory;
import com.dinosaur.dinosaurexploder.model.PlayerComponent;
import javafx.scene.input.KeyCode;

import static com.almasb.fxgl.dsl.FXGL.*;
import static com.almasb.fxgl.dsl.FXGL.getAppCenter;

public class DinosaurController {
    private Entity player;

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
        spawn("greenDino", getAppCenter().getX() - 45, 200);
    }

    public void initPhysics() {
        onCollisionBegin(EntityType.PROJECTILE, EntityType.GREENDINO, (projectile, greendino) -> {
            projectile.removeFromWorld();
            greendino.removeFromWorld();
        });
    }
}
