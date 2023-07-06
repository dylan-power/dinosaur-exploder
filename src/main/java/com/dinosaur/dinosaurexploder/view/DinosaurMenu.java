package com.dinosaur.dinosaurexploder.view;

import com.almasb.fxgl.app.scene.FXGLMenu;
import com.almasb.fxgl.app.scene.MenuType;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.ui.FontType;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

public  class DinosaurMenu extends FXGLMenu {
    public static int play = 1;
    public int getPlay(){
        return play;
    }

    Properties properties = new Properties();

    public DinosaurMenu() {
        super(MenuType.MAIN_MENU);
        try {
            properties.setProperty("flag","1");
            FileOutputStream fileOut = new FileOutputStream("config.properties");
            properties.store(fileOut,"App Configuration");
            fileOut.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Media media = new Media(getClass().getResource("/assets/sounds/mainMenu.wav").toExternalForm());
        MediaPlayer mainMenuSound = new MediaPlayer(media);
        mainMenuSound.play();
        mainMenuSound.setCycleCount(MediaPlayer.INDEFINITE);

        var bg = new Rectangle(getAppWidth(), getAppHeight(), Color.BLACK);

        var title = FXGL.getUIFactoryService().newText("Dinosaur Exploder", Color.LIME, FontType.MONO, 35);
        var startButton = new Button("Start Game");
        var quitButton = new Button("Quit");
        var soundButton = new Button("Sound");

        startButton.setMinSize(200, 100);
        quitButton.setMinSize(200, 100);
        soundButton.setMinSize(50, 50);

        title.setTranslateY(100);
        title.setTranslateX(getAppWidth() / 2 - 175);

        startButton.setTranslateY(350);
        startButton.setTranslateX(getAppWidth() / 2 - 100);
        startButton.setStyle("-fx-font-size:20");

        quitButton.setTranslateY(490);
        quitButton.setTranslateX(getAppWidth() / 2 - 100);
        quitButton.setStyle("-fx-font-size:20");

        soundButton.setTranslateY(10);
        soundButton.setTranslateX(getAppWidth() / 2 + 150);
        soundButton.setStyle("-fx-font-size:20");

        startButton.setOnAction(event -> {
            fireNewGame();
            mainMenuSound.stop();
        });
        quitButton.setOnAction(event -> fireExit());

        soundButton.setOnAction(event -> {
            if (play == 1){
                try {
                    properties.setProperty("flag","0");
                    FileOutputStream fileOut = new FileOutputStream("config.properties");
                    properties.store(fileOut,"App Configuration");
                    fileOut.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                play = 0;
                mainMenuSound.stop();
            }
           else {
                try {
                    properties.setProperty("flag","1");
                    FileOutputStream fileOut = new FileOutputStream("config.properties");
                    properties.store(fileOut,"App Configuration");
                    fileOut.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                play = 1;
                mainMenuSound.play();
            }
        });
        getContentRoot().getChildren().addAll(
                bg, title, startButton, quitButton, soundButton
        );
    }

}
