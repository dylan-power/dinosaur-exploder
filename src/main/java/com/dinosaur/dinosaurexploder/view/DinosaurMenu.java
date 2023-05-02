package com.dinosaur.dinosaurexploder.view;

import com.almasb.fxgl.app.scene.FXGLMenu;
import com.almasb.fxgl.app.scene.MenuType;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.ui.FontType;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class DinosaurMenu extends FXGLMenu {

    private boolean soundOn = true;
    private final MediaPlayer mainMenuSound;

    public DinosaurMenu() {
        super(MenuType.MAIN_MENU);

        Media media = new Media(getClass().getResource("/assets/sounds/mainMenu.wav").toExternalForm());
        mainMenuSound = new MediaPlayer(media);
        mainMenuSound.setCycleCount(MediaPlayer.INDEFINITE);
        if (soundOn) {
            mainMenuSound.play();
        }

        var bg = new Rectangle(getAppWidth(), getAppHeight(), Color.BLACK);
        var title = FXGL.getUIFactoryService().newText("Dinosaur Exploder", Color.LIME, FontType.MONO, 35);
        var startButton = new Button("Start Game");
        var quitButton = new Button("Quit");
        var soundToggleButton = new Button("Sound: ON");

        startButton.setMinSize(200, 100);
        quitButton.setMinSize(200, 100);
        soundToggleButton.setMinSize(200, 100);

        title.setTranslateY(100);
        title.setTranslateX(getAppWidth() / 2 - 175);

        startButton.setTranslateY(350);
        startButton.setTranslateX(getAppWidth() / 2 - 100);
        startButton.setStyle("-fx-font-size:20");

        quitButton.setTranslateY(490);
        quitButton.setTranslateX(getAppWidth() / 2 - 100);
        quitButton.setStyle("-fx-font-size:20");

        soundToggleButton.setTranslateY(630);
        soundToggleButton.setTranslateX(getAppWidth() / 2 - 100);
        soundToggleButton.setStyle("-fx-font-size:20");

        startButton.setOnAction(event -> {
            fireNewGame();
            stopMainMenuSound();
        });

        quitButton.setOnAction(event -> fireExit());

        soundToggleButton.setOnAction(event -> {
            soundOn = !soundOn;
            if (soundOn) {
                mainMenuSound.play();
                soundToggleButton.setText("Sound: ON");
            } else {
                mainMenuSound.stop();
                soundToggleButton.setText("Sound: OFF");
            }
        });

        getContentRoot().getChildren().addAll(
                bg, title, startButton, quitButton, soundToggleButton
        );
    }

    private void stopMainMenuSound() {
        if (soundOn) {
            mainMenuSound.stop();
        }
    }
}