package com.dinosaur.dinosaurexploder.view;

import static com.almasb.fxgl.dsl.FXGL.getLocalizationService;
import static com.almasb.fxgl.dsl.FXGL.getSettings;

import java.util.Arrays;
import java.util.Locale;
import com.almasb.fxgl.app.scene.FXGLMenu;
import com.almasb.fxgl.app.scene.MenuType;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.localization.Language;
import com.almasb.fxgl.ui.FXGLChoiceBox;
import com.almasb.fxgl.ui.FontType;
import com.dinosaur.dinosaurexploder.DinosaurApp;
import com.dinosaur.dinosaurexploder.model.GameConstants;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.util.StringConverter;

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
        FileInputStream mutemusic_button=new FileInputStream("../dinosaur-exploder/src/main/resources/assets/textures/silent.png");

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
        ImageView imageView_mute=new ImageView(mute);
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

        imageView_mute.setOnMouseClicked(mouseEvent -> {
            mainMenuSound.stop();
        });

        imageView_mute.setOnMousePressed(mouseEvent -> {
            mainMenuSound.stop();
        });
        quitButton.setOnAction(event -> fireExit());

        getContentRoot().getChildren().addAll(
                bg, title, startButton, quitButton,imageView,imageView_mute
        );
    }

		var title = FXGL.getUIFactoryService().newText(GameConstants.GAME_NAME, Color.LIME, FontType.MONO, 35);
		title.setTranslateY(100);
		title.setTranslateX(getAppWidth() / 2 - 175);
		
		DinosaurApp.initLanguages();
		drawControls(mainMenuSound, bg, title);
	}

	private void drawControls(MediaPlayer mainMenuSound, Rectangle bg, Text title) {
		getContentRoot().getChildren().removeAll(getContentRoot().getChildren());
		
		var startButton = new Button(getLocalizationService().getLocalizedString("Menu.1"));
		var quitButton = new Button(getLocalizationService().getLocalizedString("Menu.2"));
		var chooseLanguage = new FXGLChoiceBox<Language>(FXCollections.observableArrayList(GameConstants.AVAILABLE_LANGUAGES));
		

		startButton.setMinSize(200, 100);
		quitButton.setMinSize(200, 100);

		startButton.setTranslateY(350);
		startButton.setTranslateX(getAppWidth() / 2 - 100);
		startButton.setStyle("-fx-font-size:20");


		quitButton.setTranslateY(490);
		quitButton.setTranslateX(getAppWidth() / 2 - 100);
		quitButton.setStyle("-fx-font-size:20");				
		
		chooseLanguage.setTranslateY(700);
		chooseLanguage.setTranslateX(getAppWidth() / 2 - 100);
		chooseLanguage.setValue(getLocalizationService().getSelectedLanguage());
		quitButton.setStyle("-fx-font-size:20");
		chooseLanguage.setConverter(new StringConverter<Language>() {
			
			@Override
			public String toString(Language language) {
					return getLocalizationService().getLocalizedString(language.getName());
			}
			
			@Override
			public Language fromString(String text) {
				throw new UnsupportedOperationException();
			}
		});
		
		startButton.setOnAction(event -> {
			fireNewGame();
			mainMenuSound.stop();
		});
		
		quitButton.setOnAction(event -> fireExit());
		
		chooseLanguage.setOnAction(event -> {
			getLocalizationService().setSelectedLanguage(chooseLanguage.getValue());
			drawControls(mainMenuSound, bg, title);			
		});
		
		getContentRoot().getChildren().addAll(bg, title, startButton, quitButton, chooseLanguage);
	}
}