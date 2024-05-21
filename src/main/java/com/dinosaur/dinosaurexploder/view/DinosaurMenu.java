package com.dinosaur.dinosaurexploder.view;

import com.almasb.fxgl.app.scene.FXGLMenu;
import com.almasb.fxgl.app.scene.MenuType;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.ui.FontType;
import com.dinosaur.dinosaurexploder.model.GameConstants;
import javafx.collections.FXCollections;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Objects;


public class DinosaurMenu extends FXGLMenu {

    public DinosaurMenu() {
        super(MenuType.MAIN_MENU);

        Media media = new Media(Objects.requireNonNull(getClass().getResource(GameConstants.MAINMENU_SOUND)).toExternalForm());
        MediaPlayer mainMenuSound = new MediaPlayer(media);
        mainMenuSound.play();
        mainMenuSound.setCycleCount(MediaPlayer.INDEFINITE);
        var bg = new Rectangle(getAppWidth(), getAppHeight(), Color.BLACK);
        var title = FXGL.getUIFactoryService().newText(GameConstants.GAME_NAME, Color.LIME, FontType.MONO, 35);

        var startButton = new Button("Start Game");
        var quitButton = new Button("Quit");


        String languages[] = {"japanese", "french", "german", "russian", "english"};
        ChoiceBox choiceBox = new ChoiceBox(FXCollections.observableArrayList(languages));
        choiceBox.setValue("English");

        choiceBox.valueProperty().addListener((observableValue, o, t1) -> {
            if(t1.equals("german")) {
                try {
                    JSONParser parser = new JSONParser();
                    JSONObject data = (JSONObject) parser.parse(new FileReader("src/main/resources/assets/translation/german.json"));
                    startButton.setText(data.get("start").toString());
                    quitButton.setText(data.get("quit").toString());

                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else if (t1.equals("japanese")) {
                JSONParser parser = new JSONParser();
                JSONObject japan= null;
                try {
                    japan = (JSONObject) parser.parse(new FileReader("src/main/resources/assets/translation/japanese.json"));
                } catch (IOException e) {
                    throw new RuntimeException(e);
                } catch (ParseException e) {
                    throw new RuntimeException(e);
                }
                startButton.setText(japan.get("start").toString());
                quitButton.setText(japan.get("quit").toString());
            }

            else if (t1.equals("french")) {
                JSONParser parser = new JSONParser();
                JSONObject japan= null;
                try {
                    japan = (JSONObject) parser.parse(new FileReader("src/main/resources/assets/translation/french.json"));
                } catch (IOException e) {
                    throw new RuntimeException(e);
                } catch (ParseException e) {
                    throw new RuntimeException(e);
                }
                startButton.setText(japan.get("start").toString());
                quitButton.setText(japan.get("quit").toString());
            }

            else if (t1.equals("russian")) {
                JSONParser parser = new JSONParser();
                JSONObject japan= null;
                try {
                    japan = (JSONObject) parser.parse(new FileReader("src/main/resources/assets/translation/russian.json"));
                } catch (IOException e) {
                    throw new RuntimeException(e);
                } catch (ParseException e) {
                    throw new RuntimeException(e);
                }
                startButton.setText(japan.get("start").toString());
                quitButton.setText(japan.get("quit").toString());
            }
            else if (t1.equals("english")) {
                JSONParser parser = new JSONParser();
                JSONObject japan= null;
                try {
                    japan = (JSONObject) parser.parse(new FileReader("src/main/resources/assets/translation/en.json"));
                } catch (IOException e) {
                    throw new RuntimeException(e);
                } catch (ParseException e) {
                    throw new RuntimeException(e);
                }
                startButton.setText(japan.get("start").toString());
                quitButton.setText(japan.get("quit").toString());
            }



        });
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

            startButton.setMinSize(155, 50);
            quitButton.setMinSize(155, 50);

            title.setTranslateY(100);
            title.setTranslateX(getAppWidth() / 2 - 145);

            startButton.setTranslateY(400);
            startButton.setTranslateX(getAppWidth() / 2 - 50);
            startButton.setStyle("-fx-font-size:20");

            quitButton.setTranslateY(500);
            quitButton.setTranslateX(getAppWidth() / 2 - 50);
            quitButton.setStyle("-fx-font-size:20");

            choiceBox.setTranslateY(10);
            choiceBox.setTranslateX(10);

            startButton.setOnAction(event -> {
                fireNewGame();
                mainMenuSound.stop();
            });

            imageView_mute.setOnMouseClicked(mouseEvent -> {
                mainMenuSound.stop();
            });

            imageView_mute.setOnMousePressed(mouseEvent -> {
                mainMenuSound.stop();
            });
            quitButton.setOnAction(event -> fireExit());

            getContentRoot().getChildren().addAll(
                    bg, title, startButton, quitButton, imageView, imageView_mute, choiceBox
            );
        } catch (FileNotFoundException e) {
            System.out.println("File not found" + e.getMessage());
        }
    }

}
