package com.dinosaur.dinosaurexploder.view;

import com.almasb.fxgl.app.scene.FXGLMenu;
import com.almasb.fxgl.app.scene.MenuType;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.ui.FontType;
import com.dinosaur.dinosaurexploder.model.GameConstants;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class DinosaurMenu extends FXGLMenu {

    public DinosaurMenu() throws FileNotFoundException {
        super(MenuType.MAIN_MENU);

        Media media = new Media(getClass().getResource(GameConstants.MAINMENU_SOUND).toExternalForm());
        MediaPlayer mainMenuSound = new MediaPlayer(media);
        mainMenuSound.play();
        mainMenuSound.setCycleCount(MediaPlayer.INDEFINITE);

        var bg = new Rectangle(getAppWidth(), getAppHeight(), Color.BLACK);

        var title = FXGL.getUIFactoryService().newText(GameConstants.GAME_NAME, Color.LIME, FontType.MONO, 35);
        var startButton = new Button("Start Game");
        var quitButton = new Button("Quit");
        FileInputStream fileInputStream= new FileInputStream("../dinosaur-exploder/src/main/resources/assets/textures/dinomenu.png");
        Image image = new Image(fileInputStream);

        ImageView imageView = new ImageView(image);

        imageView.setFitHeight(150);
        imageView.setFitWidth(120);
        imageView.setX(250);
        imageView.setY(200);

        imageView.setPreserveRatio(true);

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
        quitButton.setOnAction(event -> fireExit());

        getContentRoot().getChildren().addAll(
                bg, title, startButton, quitButton,imageView
        );
    }

}
