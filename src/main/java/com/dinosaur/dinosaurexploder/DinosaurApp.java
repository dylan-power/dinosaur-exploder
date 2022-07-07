package com.dinosaur.dinosaurexploder;

import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.app.GameSettings;
import com.almasb.fxgl.audio.Music;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.dinosaur.dinosaurexploder.controller.DinosaurController;
import com.dinosaur.dinosaurexploder.model.EntityType;
import com.dinosaur.dinosaurexploder.model.GameEntityFactory;
import com.dinosaur.dinosaurexploder.model.PlayerComponent;
import com.dinosaur.dinosaurexploder.view.DinosaurGUI;
import javafx.scene.input.KeyCode;

import static com.almasb.fxgl.dsl.FXGL.*;

public class DinosaurApp extends GameApplication {
    DinosaurGUI gui = new DinosaurGUI();
    DinosaurController controller = new DinosaurController();

    @Override
    protected void initSettings(GameSettings settings) {
        gui.initSettings(settings);
        settings.setAppIcon("icon.png");
    }

    /*
    * EventHandling for the keyboard events
     */
    @Override
    protected void initInput(){
        //If the key pressed is the up arrow key, then call move up from the Player Component etc...
        controller.initInput();
    }

    @Override
    protected void initGame()
    {
        controller.initGame();
    }

    @Override
    protected void initPhysics() {
        controller.initPhysics();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
