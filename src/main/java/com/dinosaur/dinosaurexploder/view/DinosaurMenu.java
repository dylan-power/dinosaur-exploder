package com.dinosaur.dinosaurexploder.view;

import com.almasb.fxgl.app.scene.FXGLMenu;
import com.almasb.fxgl.app.scene.MenuType;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.ui.FontType;
import com.dinosaur.dinosaurexploder.model.GameConstants;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class DinosaurMenu extends FXGLMenu {

    public DinosaurMenu() {
        super(MenuType.MAIN_MENU);

        Media media = new Media(getClass().getResource(GameConstants.MAINMENU_SOUND).toExternalForm());
        MediaPlayer mainMenuSound = new MediaPlayer(media);
        mainMenuSound.play();
        mainMenuSound.setCycleCount(MediaPlayer.INDEFINITE);

        var bg = new Rectangle(getAppWidth(), getAppHeight(), Color.BLACK);

        var title = FXGL.getUIFactoryService().newText(GameConstants.GAME_NAME, Color.LIME, FontType.MONO, 35);
        var startButton = new Button("Start Game");
        var quitButton = new Button("Quit");
        try {


            FileInputStream fileInputStream = new FileInputStream("../dinosaur-exploder/src/main/resources/assets/textures/dinomenu.png");
            FileInputStream mutemusic_button = new FileInputStream("../dinosaur-exploder/src/main/resources/assets/textures/silent.png");

            // image for dino in main menu
            Image image = new Image(fileInputStream);
            ImageView imageView = new ImageView(image);
            imageView.setFitHeight(250);
            imageView.setFitWidth(200);
            imageView.setX(200);
            imageView.setY(190);
            imageView.setPreserveRatio(true);

            //adding image to manually mute music

            Image mute = new Image(mutemusic_button);
            ImageView imageView_mute = new ImageView(mute);
            imageView_mute.setFitHeight(40);
            imageView_mute.setFitWidth(50);
            imageView_mute.setX(490);
            imageView_mute.setY(20);
            imageView_mute.setPreserveRatio(true);

            startButton.setMinSize(50, 50);
            quitButton.setMinSize(140, 50);

            title.setTranslateY(100);
            title.setTranslateX(getAppWidth() / 2 - 145);

            startButton.setTranslateY(400);
            startButton.setTranslateX(getAppWidth() / 2 - 50);
            startButton.setStyle("-fx-font-size:20");

            quitButton.setTranslateY(500);
            quitButton.setTranslateX(getAppWidth() / 2 - 50);
            quitButton.setStyle("-fx-font-size:20");

            startButton.setOnAction(event -> {
                fireNewGame();
                mainMenuSound.stop();
            });

            imageView_mute.setOnMouseClicked(event -> {
            if (mainMenuSound.getStatus() == MediaPlayer.Status.PLAYING) {
                mainMenuSound.pause(); // Pausa la música si está reproduciendo
            } else {
                mainMenuSound.play(); // Reproduce la música si está pausada
            }
            });
            quitButton.setOnAction(event -> fireExit());

            getContentRoot().getChildren().addAll(
                    bg, title, startButton, quitButton, imageView, imageView_mute
            );
        }
        catch (FileNotFoundException e){
           System.out.println("File not found" + e.getMessage());
        }
    }

}
