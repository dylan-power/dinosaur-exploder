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
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.util.StringConverter;

public class DinosaurMenu extends FXGLMenu {

    public DinosaurMenu() {
        super(MenuType.MAIN_MENU);

        Media media = new Media(getClass().getResource(GameConstants.MAINMENU_SOUND).toExternalForm());
        MediaPlayer mainMenuSound = new MediaPlayer(media);
        mainMenuSound.play();
        mainMenuSound.setCycleCount(MediaPlayer.INDEFINITE);

        var bg = new Rectangle(getAppWidth(), getAppHeight(), Color.BLACK);

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