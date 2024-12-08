package com.dinosaur.dinosaurexploder.view;

import com.almasb.fxgl.app.scene.FXGLMenu;
import com.almasb.fxgl.app.scene.MenuType;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.scene.Scene;
import com.dinosaur.dinosaurexploder.model.GameConstants;
import com.dinosaur.dinosaurexploder.model.GameSettings;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

public class DinosaurMenu extends FXGLMenu {
    private final MediaPlayer mainMenuSound;

    public DinosaurMenu() {
        super(MenuType.MAIN_MENU);

        // Initialize background music
        Media media = new Media(getClass().getResource(GameConstants.MAINMENU_SOUND).toExternalForm());
        mainMenuSound = new MediaPlayer(media);
        mainMenuSound.play();
        mainMenuSound.setCycleCount(MediaPlayer.INDEFINITE);

        // Create the background
        var bg = new Rectangle(getAppWidth(), getAppHeight(), Color.BLACK);

        // Create buttons
        var startButton = new Button("Start Game");
        var quitButton = new Button("Quit");

        // Create volume slider and label
        Slider volumeSlider = new Slider(0, 1, 1);
        volumeSlider.setBlockIncrement(0.01);
        Label volumeLabel = new Label("100%");
        volumeSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
            mainMenuSound.setVolume(newValue.doubleValue());
            volumeLabel.setText(String.format("%.0f%%", newValue.doubleValue() * 100));
        });

        ComboBox<String> difficultyBox = new ComboBox<>();
        difficultyBox.getItems().addAll("1", "2", "3", "4", "5");
        difficultyBox.setValue("1");

        difficultyBox.valueProperty().addListener((observable, oldValue, newValue) -> {
            System.out.println("Selected Difficulty: " + newValue);
            GameSettings.getInstance().setDifficultyLevel(Integer.parseInt(newValue));
        });

        // Set uniform size for buttons and dropdowns
        startButton.setPrefSize(200, 50);
        quitButton.setPrefSize(200, 50);

        difficultyBox.setPrefSize(200, 50);

        startButton.setStyle("-fx-font-size: 18px;");
        quitButton.setStyle("-fx-font-size: 18px;");
        difficultyBox.setStyle("-fx-font-size: 18px;");

        // Set volume slider width
        volumeSlider.setPrefWidth(150);

        // Create root layout container
        BorderPane root = new BorderPane();
        root.setPrefSize(getAppWidth(), getAppHeight());

        // Top layout for title, difficulty, and volume
        VBox settingsBox = new VBox(10, new Label("Difficulty:"), difficultyBox);
        settingsBox.setAlignment(Pos.TOP_LEFT);
        settingsBox.setPadding(new Insets(10));

        HBox volumeBox = new HBox(10, volumeLabel, volumeSlider);
        volumeBox.setAlignment(Pos.TOP_RIGHT);
        volumeBox.setPadding(new Insets(10));

        BorderPane topPane = new BorderPane();
        topPane.setLeft(settingsBox);
        topPane.setRight(volumeBox);

        root.setTop(topPane);

        // Center layout for dinosaur image
        ImageView imageView = createImageView("assets/textures/dinomenu.png", 300, 400);
        StackPane centerPane = new StackPane(imageView);
        centerPane.setAlignment(Pos.CENTER);
        root.setCenter(centerPane);

        // Bottom layout for buttons
        VBox buttonBox = new VBox(20, startButton, quitButton);
        buttonBox.setAlignment(Pos.CENTER);
        buttonBox.setPadding(new Insets(10));
        root.setBottom(buttonBox);

        // Set button actions
        startButton.setOnAction(event -> {
            fireNewGame();
            mainMenuSound.stop();
        });

        quitButton.setOnAction(event -> fireExit());

        // Add everything to the scene
        getContentRoot().getChildren().addAll(bg, root);
    }

    /**
     * Helper method to create an ImageView with specific width and height.
     */
    private ImageView createImageView(String path, double width, double height) {
        try (InputStream stream = getClass().getClassLoader().getResourceAsStream(path)) {
            if (stream == null) {
                throw new FileNotFoundException("Resource not found: " + path);
            }
            Image image = new Image(stream);
            ImageView imageView = new ImageView(image);
            imageView.setFitWidth(width);
            imageView.setFitHeight(height);
            imageView.setPreserveRatio(true);
            return imageView;
        } catch (IOException e) {
            e.printStackTrace();
            return new ImageView();
        }
    }


    @Override
    public void onEnteredFrom(Scene prevState) {
        super.onEnteredFrom(prevState);
        FXGL.getAudioPlayer().stopAllSounds();
        mainMenuSound.play();
    }

}