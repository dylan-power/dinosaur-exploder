package com.dinosaur.dinosaurexploder.view;

import com.almasb.fxgl.app.scene.FXGLMenu;
import com.almasb.fxgl.app.scene.MenuType;
import com.almasb.fxgl.audio.Sound;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.ui.FontType;
import com.dinosaur.dinosaurexploder.controller.SoundController;
import javafx.beans.binding.Bindings;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

import static com.almasb.fxgl.dsl.FXGL.getLocalizationService;
import static com.almasb.fxgl.dsl.FXGLForKtKt.getUIFactoryService;

import com.dinosaur.dinosaurexploder.DinosaurApp;
import com.dinosaur.dinosaurexploder.model.GameConstants;
import org.jetbrains.annotations.NotNull;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class PauseMenu extends FXGLMenu {

    //These are for tracking how many times the pauseMenu has been opened. Helps in getting the mute/unmute image from SoundController
    //For first time, this helps loading the unmuted icon and if count increases more than 1, it changes the icon depending on the controller
    //At this point of time, I really don't have other proper method, It works so why not, But if you know any other solution please let me know and contribute
    private int count = 0;
    private int sfxCount = 0;
    public PauseMenu() {
        super(MenuType.GAME_MENU);
        DinosaurApp.initLanguages();

        PauseButton btnBack = new PauseButton(getLocalizationService().getLocalizedString("Pause.1"),() -> fireResume());

        SoundButton btnMusic = new SoundButton(getLocalizationService().getLocalizedString("Pause.2")); //need2ComeBack

        PauseButton btnQuitGame = new PauseButton(getLocalizationService().getLocalizedString("Pause.3"),() -> fireExitToMainMenu());

        ControlButton btnControls = new ControlButton(getLocalizationService().getLocalizedString("Pause.4"));

        btnMusic.setVolume(() -> {
            var bg = new Rectangle(getAppWidth(), getAppHeight(), Color.color(0, 0, 0, 0.5));
            count++;
            sfxCount++;
            var settingsBox = new VBox(15);
            ImageView imageViewPlaying;
            Slider volumeSlider;
            Label volumeLabel;
            ImageView sfxImageViewPlaying;
            Slider sfxVolumeSlider;
            Label sfxVolumeLabel;
            InputStream muteButton = null;
            InputStream soundButton = null;
            InputStream sfxMuteButton = null;
            InputStream sfxSoundButton = null;
            Image mute = null;
            Image audioOn = null;
            Image sfxMute = null;
            Image sfxAudioOn = null;
            double sfxVolume = SoundController.getInstance().getSfxVolume();
            double volume = SoundController.getInstance().getVolume();
            Image sfxImage = null;
            Image image = null;

            //for main background
            volumeSlider = new Slider(0, 1, volume);
            volumeLabel = new Label("100%");
            volumeLabel.setText(String.format("%.0f%%", volume * 100));
            imageViewPlaying = new ImageView();

            //for sfx
            sfxVolumeSlider = new Slider(0, 1, sfxVolume);
            sfxVolumeLabel = new Label("100%");
            sfxVolumeLabel.setText(String.format("%.0f%%", sfxVolume * 100));
            sfxImageViewPlaying = new ImageView();

            PauseButton backButton = new PauseButton(getLocalizationService().getLocalizedString("Pause.5"), () -> {
                settingsBox.getChildren().removeAll(settingsBox.getChildren());
                removeChild(bg);
                btnBack.enable();
                btnMusic.enable();
                btnQuitGame.enable();
                btnControls.enable();
            });

            VBox volumeBox = new VBox(1);
            volumeBox.getChildren().addAll(volumeLabel, volumeSlider);

            HBox audioBox = new HBox(15);
            audioBox.getChildren().addAll(imageViewPlaying, volumeBox);

            VBox sfxVolumeBox = new VBox(1);
            sfxVolumeBox.getChildren().addAll(sfxVolumeLabel, sfxVolumeSlider);
            HBox sfxAudioBox = new HBox(15);
            sfxAudioBox.getChildren().addAll(sfxImageViewPlaying, sfxVolumeBox);

            settingsBox.getChildren().addAll(
                    backButton, // Back button
                    new OptionsButton(getLocalizationService().getLocalizedString("Pause.12")),
                    audioBox,
                    new OptionsButton(getLocalizationService().getLocalizedString("Pause.13")),
                    sfxAudioBox
            );


            settingsBox.setTranslateX(300);
            settingsBox.setTranslateY(getAppWidth() / 2);
            volumeSlider.setBlockIncrement(0.01);
            volumeSlider.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/styles/styles.css")).toExternalForm());
            sfxVolumeSlider.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/styles/styles.css")).toExternalForm());



            try {
                muteButton = getClass().getClassLoader().getResourceAsStream("assets/textures/silent.png");
                soundButton = getClass().getClassLoader().getResourceAsStream("assets/textures/playing.png");
                sfxMuteButton = getClass().getClassLoader().getResourceAsStream("assets/textures/silent.png");
                sfxSoundButton = getClass().getClassLoader().getResourceAsStream("assets/textures/playing.png");
                if (muteButton == null || soundButton == null || sfxMuteButton == null || sfxSoundButton == null) {
                    throw new FileNotFoundException("Resource not found: mute or play icon");
                }
            } catch (FileNotFoundException e) {
                System.out.println("File not found: " + e.getMessage());
            }

            // Initialize images for mute and play
            mute = new Image(muteButton);
            audioOn = new Image(soundButton);
            if(count <= 1)
                imageViewPlaying.setImage(audioOn);
            imageViewPlaying.setFitHeight(35);
            imageViewPlaying.setFitWidth(45);
            imageViewPlaying.setPreserveRatio(true);

            // Initialize images for mute and play for SFX
            sfxMute = new Image(sfxMuteButton);
            sfxAudioOn = new Image(sfxSoundButton);
            if(sfxCount <= 1)
                sfxImageViewPlaying.setImage(sfxAudioOn);
            sfxImageViewPlaying.setFitHeight(35);
            sfxImageViewPlaying.setFitWidth(45);
            sfxImageViewPlaying.setPreserveRatio(true);

            image = SoundController.getInstance().getViewImage();
            if(count > 1)
                imageViewPlaying.setImage(image);
            volume = SoundController.getInstance().adjustVolume(volumeSlider, volumeLabel, mute, audioOn, imageViewPlaying);
            sfxImage = SoundController.getInstance().getViewSfxImage();
            if(sfxCount > 1)
                sfxImageViewPlaying.setImage(sfxImage);
            sfxVolume = SoundController.getInstance().adjustInGameSFX(sfxVolumeSlider, sfxVolumeLabel, sfxMute, sfxAudioOn, sfxImageViewPlaying);
            volumeLabel.setStyle("-fx-text-fill: #61C181;");
            sfxVolumeLabel.setStyle("-fx-text-fill: #61C181;");

            btnBack.disable();
            btnMusic.disable();
            btnQuitGame.disable();
            btnControls.disable();

            getContentRoot().getChildren().addAll(bg, settingsBox);
        });

        btnControls.setControlAction(() -> {

            var bg = new Rectangle(getAppWidth(), getAppHeight(), Color.color(0,0,0,0.5));

            var controlsBox = new VBox(15);

            controlsBox.getChildren().addAll(
                    new PauseButton(getLocalizationService().getLocalizedString("Pause.5"), () -> {
                        controlsBox.getChildren().removeAll(controlsBox.getChildren());
                        removeChild(bg);
                        btnBack.enable();
                        btnMusic.enable();
                        btnQuitGame.enable();
                        btnControls.enable();
                    }),
                    new OptionsButton("↑ / W : Move spaceship up"),
                    new OptionsButton("↓ / S : Move spaceship down"),
                    new OptionsButton("→ / D : Move spaceship right"),
                    new OptionsButton("← / A : Move spaceship left"),
                    new OptionsButton("ESC: Pause the game"),
                    new OptionsButton("SPACE: Shoot"));
            new OptionsButton(getLocalizationService().getLocalizedString("Pause.6"));
            new OptionsButton(getLocalizationService().getLocalizedString("Pause.7"));
            new OptionsButton(getLocalizationService().getLocalizedString("Pause.8"));
            new OptionsButton(getLocalizationService().getLocalizedString("Pause.9"));
            new OptionsButton(getLocalizationService().getLocalizedString("Pause.10"));
            new OptionsButton(getLocalizationService().getLocalizedString("Pause.11"));

            controlsBox.setTranslateX(300);
            controlsBox.setTranslateY(getAppWidth() / 2);

            btnBack.disable();
            btnMusic.disable();
            btnQuitGame.disable();
            btnControls.disable();


            getContentRoot().getChildren().addAll(
                    bg,
                    controlsBox
            );

        });

        var bg = new Rectangle(getAppWidth(), getAppHeight(), Color.color(0,0,0,0.5));
        var title = FXGL.getUIFactoryService().newText(GameConstants.GAME_NAME, Color.WHITE, FontType.MONO, 35);
        var box = new VBox(15,
                btnBack,
                btnMusic,
                btnControls,
                btnQuitGame);
        var version = FXGL.getUIFactoryService().newText("v1.0-Developer", Color.WHITE, FontType.MONO, 20);

        title.setTranslateX(getAppWidth() / 2 - 175);
        title.setTranslateY(150);

        box.setTranslateX(100);
        box.setTranslateY(getAppWidth() / 2 + 100);

        version.setTranslateX(10);
        version.setTranslateY(getAppHeight() - 10);

        getContentRoot().getChildren().addAll(
                bg,title,version, box);
    }

    @NotNull
    private static String getKey() {
        return "Pause.1";
    }

    private static class OptionsButton extends StackPane {

        private String description;
        private Text text;

        public OptionsButton(String description) {
            this.description = description;

            text = getUIFactoryService().newText(description, Color.WHITE, 14.0);
            setAlignment(Pos.CENTER_LEFT);
            getChildren().addAll(text);

        }
    }

    private static class VolumeOptions extends StackPane {

        private String description;
        private Text text;

        public VolumeOptions(String description) {
            this.description = description;

            text = getUIFactoryService().newText(description, Color.WHITE, 14.0);
            setAlignment(Pos.CENTER_LEFT);
            getChildren().addAll(text);

        }
    }

    private static class SoundButton extends StackPane {

        private static final Color SELECTED_COLOR = Color.WHITE;
        private static final Color NOT_SELECTED_COLOR = Color.GRAY;
        private String name;
        private Runnable action;

        private Text text;
        private boolean disable = false;

        public void disable(){
            disable = true;
        }

        public void enable(){
            disable = false;
        }
        public SoundButton(String name) {
            this.name = name;
        }
        public void setVolume(Runnable action)
        {
            this.action = action;
            text = getUIFactoryService().newText(name, Color.WHITE, 24.0);
            text.strokeProperty().bind(
                    Bindings.when(focusedProperty()).then(SELECTED_COLOR).otherwise(NOT_SELECTED_COLOR)
            );
            text.setStrokeWidth(0.5);
            setAlignment(Pos.CENTER_LEFT);
            setFocusTraversable(true);
            setOnKeyPressed(e -> {
                if (e.getCode() == KeyCode.ENTER & !disable) {
                    action.run();
                }
            });

            setOnMouseClicked(event->{
                if(!disable){
                    action.run();
                }
            });
            setOnMouseEntered(event -> text.setFill(Color.RED));
            setOnMouseExited(event -> text.setFill(SELECTED_COLOR));
            getChildren().addAll(text);
        }
    }

    private static class PauseButton extends StackPane {

        private static final Color SELECTED_COLOR = Color.WHITE;
        private static final Color NOT_SELECTED_COLOR = Color.GRAY;
        private String name;
        private Runnable action;

        private Text text;
        private Rectangle selector;

        private boolean disable = false;

        public void disable(){
            disable = true;
        }

        public void enable(){
            disable = false;
        }
        public PauseButton(String name, Runnable action) {
            this.name = name;
            this.action = action;

            text = getUIFactoryService().newText(name, Color.WHITE, 24.0);

            text.strokeProperty().bind(
                    Bindings.when(focusedProperty()).then(SELECTED_COLOR).otherwise(NOT_SELECTED_COLOR)
            );
            text.setStrokeWidth(0.5);


            setAlignment(Pos.CENTER_LEFT);
            setFocusTraversable(true);

            setOnKeyPressed(e -> {
                if (e.getCode() == KeyCode.ENTER && !disable) {
                    action.run();
                }
            });

            setOnMouseClicked(event->{
                if(!disable){
                    action.run();
                }
            });

            setOnMouseEntered(event -> text.setFill(Color.RED));
            setOnMouseExited(event -> text.setFill(SELECTED_COLOR));

            getChildren().addAll(text);

        }
    }

    private static class ControlButton extends StackPane {

        private static final Color SELECTED_COLOR = Color.WHITE;
        private static final Color NOT_SELECTED_COLOR = Color.GRAY;
        private String name;
        private Runnable action;

        private  Text text;
        private Rectangle selector;

        private boolean disable = false;

        public void disable(){
            disable = true;
        }

        public void enable(){
            disable = false;
        }
        public ControlButton(String name) {
            this.name = name;
        }

        public void setControlAction(Runnable action){
            this.action = action;

            text = getUIFactoryService().newText(name, Color.WHITE, 24.0);

            text.strokeProperty().bind(
                    Bindings.when(focusedProperty()).then(SELECTED_COLOR).otherwise(NOT_SELECTED_COLOR)
            );
            text.setStrokeWidth(0.5);

            setAlignment(Pos.CENTER_LEFT);
            setFocusTraversable(true);

            setOnKeyPressed(e -> {
                if (e.getCode() == KeyCode.ENTER & !disable) {
                    action.run();
                }
            });

            setOnMouseClicked(event->{
                if(!disable){
                    action.run();
                }
            });

            setOnMouseEntered(event -> text.setFill(Color.RED));
            setOnMouseExited(event -> text.setFill(SELECTED_COLOR));

            getChildren().addAll(text);
        }
    }
}