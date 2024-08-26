package com.dinosaur.dinosaurexploder.view;

import com.almasb.fxgl.app.scene.FXGLMenu;
import com.almasb.fxgl.app.scene.MenuType;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.scene.Scene;
import com.almasb.fxgl.ui.FontType;
import com.dinosaur.dinosaurexploder.model.GameConstants;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.BorderPane;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class DinosaurMenu extends FXGLMenu {
    private MediaPlayer mainMenuSound;

    public DinosaurMenu() {
        super(MenuType.MAIN_MENU);

        Media media = new Media(getClass().getResource(GameConstants.MAINMENU_SOUND).toExternalForm());
        mainMenuSound = new MediaPlayer(media);
        mainMenuSound.play();
        mainMenuSound.setCycleCount(MediaPlayer.INDEFINITE);

        var bg = new Rectangle(getAppWidth(), getAppHeight(), Color.BLACK);

        var title = FXGL.getUIFactoryService().newText(GameConstants.GAME_NAME, Color.LIME, FontType.MONO, 35);
        var startButton = new Button("Start Game");
        var quitButton = new Button("Quit");

        Slider volumeSlider = new Slider(0, 1, 1);
        volumeSlider.setShowTickLabels(true);
        volumeSlider.setShowTickMarks(true);
        volumeSlider.setBlockIncrement(0.01);

        Label volumeLabel = new Label("Volume: 100%");

        volumeSlider.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                mainMenuSound.setVolume(newValue.doubleValue());
                volumeLabel.setText(String.format("Volume: %.0f%%", newValue.doubleValue() * 100));
            }
        });


        try {


            FileInputStream fileInputStream = new FileInputStream("../dinosaur-exploder/src/main/resources/assets/textures/dinomenu.png");
            FileInputStream mutemusic_button = new FileInputStream("../dinosaur-exploder/src/main/resources/assets/textures/silent.png");
            FileInputStream audioOnButton = new FileInputStream("../dinosaur-exploder/src/main/resources/assets/textures/playing.png");

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


            Image audioOn = new Image(audioOnButton);
            ImageView imageViewPlaying = new ImageView(audioOn);
            imageViewPlaying.setFitHeight(40);
            imageViewPlaying.setFitWidth(50);
            imageViewPlaying.setX(490);
            imageViewPlaying.setY(20);
            imageViewPlaying.setPreserveRatio(true);


            startButton.setMinSize(50, 50);
            startButton.setPrefSize(140,60);

            quitButton.setMinSize(140, 50);

            title.setTranslateY(100);
            title.setTranslateX(getAppWidth() / 2 - 145);

            startButton.setTranslateY(400);
            startButton.setTranslateX(getAppWidth() / 2 - 50);
            startButton.setStyle("-fx-font-size:20");

            quitButton.setTranslateY(500);
            quitButton.setTranslateX(getAppWidth() / 2 - 50);
            quitButton.setStyle("-fx-font-size:20");

            BorderPane root = new BorderPane();
            root.setTop(title);
            BorderPane.setAlignment(title, Pos.CENTER);

            BorderPane volumePane = new BorderPane();
            volumePane.setLeft(volumeLabel);
            volumePane.setCenter(volumeSlider);
            volumePane.setStyle("-fx-padding: 10;");

            root.setCenter(volumePane);
            root.setBottom(new BorderPane(startButton, null, quitButton, null, null));
            BorderPane.setAlignment(startButton, Pos.CENTER);
            BorderPane.setAlignment(quitButton, Pos.BOTTOM_CENTER);

            startButton.setOnAction(event -> {
                fireNewGame();
                mainMenuSound.stop();
            });

            imageViewPlaying.setOnMouseClicked(mouseEvent -> {
                if (mainMenuSound.isMute()){
                    mainMenuSound.setMute(false);
                    imageViewPlaying.setImage(audioOn);
                } else {
                    mainMenuSound.setMute(true);
                    imageViewPlaying.setImage(mute);
                }
            });

            quitButton.setOnAction(event -> fireExit());

            getContentRoot().getChildren().addAll(
                    bg, title, startButton, quitButton, imageView, imageViewPlaying, volumeSlider, volumeLabel
            );
        }
        catch (FileNotFoundException e){
            System.out.println("File not found" + e.getMessage());
        }
    }
    @Override
    public void onEnteredFrom(Scene prevState) {
        super.onEnteredFrom(prevState);
        FXGL.getAudioPlayer().stopAllSounds();
        mainMenuSound.play();
    }

}