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
import java.io.InputStream;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.BorderPane;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.Region;
import java.io.FileNotFoundException;
import java.util.Objects;
import javafx.scene.layout.StackPane;

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
        volumeSlider.setBlockIncrement(0.01);

        volumeSlider.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/styles/styles.css")).toExternalForm());

        //Sets the volume label
        Label volumeLabel = new Label("100%");
        volumeSlider.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                mainMenuSound.setVolume(newValue.doubleValue());
                volumeLabel.setText(String.format("%.0f%%", newValue.doubleValue() * 100));
            }
        });


        try {

            //Using InputStream for efficient fetching of images
            InputStream menuImage = getClass().getClassLoader().getResourceAsStream("assets/textures/dinomenu.png");
            if (menuImage == null) {
                throw new FileNotFoundException("Resource not found: assets/textures/dinomenu.png");
            }
            InputStream muteButton = getClass().getClassLoader().getResourceAsStream("assets/textures/silent.png");
            if (muteButton == null) {
                throw new FileNotFoundException("Resource not found: assets/textures/silent.png");
            }
            InputStream soundButton = getClass().getClassLoader().getResourceAsStream("assets/textures/playing.png");
            if (soundButton == null) {
                throw new FileNotFoundException("Resource not found: assets/textures/playing.png");
            }

            // image for dino in main menu
            Image image = new Image(menuImage);
            ImageView imageView = new ImageView(image);
            imageView.setFitHeight(250);
            imageView.setFitWidth(200);
            imageView.setX(200);
            imageView.setY(190);
            imageView.setPreserveRatio(true);

            //adding image to manually mute music
            Image mute = new Image(muteButton);


            Image audioOn = new Image(soundButton);
            ImageView imageViewPlaying = new ImageView(audioOn);
            imageViewPlaying.setFitHeight(50);
            imageViewPlaying.setFitWidth(60);
            imageViewPlaying.setX(470);
            imageViewPlaying.setY(20);
            imageViewPlaying.setPreserveRatio(true);


            startButton.setMinSize(50, 50);
            startButton.setPrefSize(140,60);

            quitButton.setMinSize(140, 60);

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
            BorderPane.setAlignment(volumeLabel, Pos.CENTER);
            volumePane.setCenter(volumeSlider);
            volumeSlider.setStyle("-fx-padding: 10px;");
            volumeSlider.setTranslateY(25);
            volumeSlider.setTranslateX(10);
            volumeLabel.setTranslateX(20);
            volumeLabel.setTranslateY(20);
            volumeLabel.setStyle("-fx-text-fill: #61C181;");



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
                    mainMenuSound.setMute(false); //False later
                    imageViewPlaying.setImage(audioOn);
                } else {
                    mainMenuSound.setMute(true);
                    imageViewPlaying.setImage(mute);
                }
            });

            quitButton.setOnAction(event -> fireExit());

            ComboBox<String> difficultyBox = new ComboBox<>(); // Dropdown menu for difficulty
            difficultyBox.getItems().addAll("Easy", "Medium", "Hard"); // Added the difficulty levels
            difficultyBox.setValue("Medium"); // Default value
            difficultyBox.setTranslateY(580);
            difficultyBox.setTranslateX(getAppWidth() / 2 - 50);

            startButton.setOnAction(event -> {
                fireNewGame();
                mainMenuSound.stop();
                // Pass the selected difficulty to the game settings
                FXGL.getWorldProperties().setValue("difficulty", difficultyBox.getValue());
                System.out.println("Difficulty set to: " + difficultyBox.getValue()); // Print the selected difficulty

            });
            getContentRoot().getChildren().addAll(
                    bg, title, startButton, quitButton, imageView, imageViewPlaying, volumeLabel, volumeSlider, difficultyBox
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