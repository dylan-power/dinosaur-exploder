package com.dinosaur.dinosaurexploder.controller;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public class SoundController {
    private static SoundController instance;
    private MediaPlayer inGameSound;
    private BorderPane root;
    private static double volume = 1.0;
    private static double prevVolume = 1.0;
    private static double sfxVolume = 1.0;
    private static double prevSfxVolume = 1.0;
    private static Image viewImage;
    private static Image viewSfxImage;
    private SoundController() {}

    public static SoundController getInstance() {
        if (instance == null) {
            instance = new SoundController();
        }
        return instance;
    }

    public void playInGameSound(String musicResource, double volumeValue) {
        if(inGameSound != null)
        {
            inGameSound.stop();
        }
            Media media = new Media(getClass().getResource(musicResource).toExternalForm());
            inGameSound = new MediaPlayer(media);
            inGameSound.setCycleCount(MediaPlayer.INDEFINITE);
            inGameSound.setVolume(volume);
            inGameSound.play();
    }

    public void stopInGameSound() {
        if (inGameSound != null) {
            inGameSound.stop();
        }
    }

    public void muteInGameSound() {
        if (inGameSound != null) {
            inGameSound.setMute(true);
        }
    }

    public void unmuteInGameSound() {
        if (inGameSound != null) {
            inGameSound.setMute(false);
        }
    }

    public boolean isMuted() {
        return inGameSound != null && inGameSound.isMute();
    }
    public double getVolume() {
        return volume;
    }
    public double getSfxVolume() {
        return sfxVolume;
    }
    public Image getViewSfxImage() {
        return viewSfxImage;
    }
    public Image getViewImage() {
        return viewImage;
    }
    public void playSoundEffect(String soundResource) {
        // Use the controlled volume
        Media media = new Media(getClass().getResource(soundResource).toExternalForm());
        MediaPlayer sfxPlayer = new MediaPlayer(media);
        sfxPlayer.setVolume(sfxVolume);
        sfxPlayer.play();
    }
    public double adjustInGameSFX(Slider sfxVolumeSlider, Label sfxVolumeLabel, Image sfxMute, Image sfxAudioOn, ImageView sfxImageViewPlaying) {
        sfxVolumeSlider.setValue(sfxVolume);
        sfxVolumeSlider.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                Platform.runLater(() -> {
                        sfxVolume = newValue.doubleValue();
                        sfxVolumeLabel.setText(String.format("%.0f%%", sfxVolume * 100));
                        System.out.println("VFX Volume label updated to: " + sfxVolumeLabel.getText());
                    if(sfxVolume == 0.00)
                    {
                        sfxImageViewPlaying.setImage(sfxMute);
                        viewSfxImage = sfxMute;
                    }
                    else
                    {
                        sfxImageViewPlaying.setImage(sfxAudioOn);
                        viewSfxImage = sfxAudioOn;
                    }
                });
            }

        });

        sfxImageViewPlaying.setOnMouseClicked(mouseEvent -> {
            if (sfxVolume == 0.00){
                sfxImageViewPlaying.setImage(sfxAudioOn);
                sfxVolume = prevSfxVolume;
                sfxVolumeSlider.setValue(sfxVolume);
                viewSfxImage = sfxAudioOn;
            } else {
                sfxImageViewPlaying.setImage(sfxMute);
                prevSfxVolume = sfxVolume;
                sfxVolume = 0.00;
                sfxVolumeSlider.setValue(sfxVolume);
                viewSfxImage = sfxMute;
            }
        });
        return sfxVolume;
    }
    public double adjustVolume(Slider volumeSlider, Label volumeLabel, Image mute, Image audioOn, ImageView imageViewPlaying) {
        volumeSlider.setValue(volume);
        volumeSlider.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                Platform.runLater(() -> {

                    volume = newValue.doubleValue();
                    inGameSound.setVolume(volume);
                    volumeLabel.setText(String.format("%.0f%%", volume * 100));
                    System.out.println("Volume label updated to: " + volumeLabel.getText());
                    if(volume == 0.00)
                    {
                        imageViewPlaying.setImage(mute);
                        viewImage = mute;
                    }
                    else
                    {
                        imageViewPlaying.setImage(audioOn);
                        viewImage = audioOn;
                    }
                });
            }
        });


        imageViewPlaying.setOnMouseClicked(mouseEvent -> {
            if (volume == 0.00){
                imageViewPlaying.setImage(audioOn);
                volume = prevVolume;
                volumeSlider.setValue(volume);
                viewImage = audioOn;
            } else {
                imageViewPlaying.setImage(mute);
                prevVolume = volume;
                volume = 0.00;
                volumeSlider.setValue(volume);
                viewImage = mute;
            }
        });

        return volume;
    }
}
