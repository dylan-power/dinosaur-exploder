package com.dinosaur.dinosaurexploder.view;

import com.almasb.fxgl.app.GameSettings;
import com.almasb.fxgl.app.scene.FXGLMenu;
import com.almasb.fxgl.app.scene.MenuType;
import com.almasb.fxgl.app.scene.SceneFactory;
import javafx.scene.Scene;

public class DinosaurGUI {
    public static final int WIDTH = 550;
    public static final int HEIGHT = 750;

    public void initSettings(GameSettings settings) {
        settings.setWidth(WIDTH);
        settings.setHeight(HEIGHT);
        settings.setMainMenuEnabled(true);

        // Custom main menu
        settings.setSceneFactory(new SceneFactory() {
            @Override
            public FXGLMenu newMainMenu() {
                return new DinosaurMenu();
            }

            @Override
            public FXGLMenu newGameMenu() {
                return new PauseMenu();
            }
        });


        settings.setVersion("1.0");
        settings.setTicksPerSecond(60); // check info : settings.setProfilingEnabled(true);
    }
}
