package com.dinosaur.dinosaurexploder;



import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.app.GameSettings;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.localization.Language;
import com.dinosaur.dinosaurexploder.controller.DinosaurController;
import com.dinosaur.dinosaurexploder.model.EntityType;
import com.dinosaur.dinosaurexploder.model.GameEntityFactory;
import com.dinosaur.dinosaurexploder.model.PlayerComponent;
import com.dinosaur.dinosaurexploder.view.DinosaurGUI;
import javafx.scene.input.KeyCode;

import static com.almasb.fxgl.dsl.FXGL.*;

import java.util.Locale;
import java.util.ResourceBundle;
/**
 * Summary :
 *      The Factory handles the DinosaurApp,Physics,Settings and Input of all entities in the game
 */
public class DinosaurApp extends GameApplication {
    DinosaurGUI gui = new DinosaurGUI();
    DinosaurController controller = new DinosaurController();
    /**
     * Summary :
     *      This method for the setting the Game GUI
     * Parameters :
     *      GameSettings
     */
    @Override
    protected void initSettings(GameSettings settings) {
        gui.initSettings(settings);
        settings.setAppIcon("icon.png");
        settings.setTitle("Dinosaur Exploder");
    }
    /**
     * Summary :
     *      This method is overriding the superclass method to EventHandling for the keyboard events
     */
    @Override
    protected void initInput(){
        //If the key pressed is the up arrow key, then call move up from the Player Component etc...
        controller.initInput();
    }
    /**
     * Summary :
     *      This method is overriding the superclass method to initialize the game
     */
    @Override
    protected void initGame()
    {
        controller.initDifficulty();
        controller.initGame();
    }
    /**
     * Summary :
     *      This method is overriding the superclass method to initialize the physics to the game
     */
    @Override
    protected void initPhysics() {
        controller.initPhysics();
    }
    /**
     * Summary :
     *      This method launches the game as it is the main method of the class
     * Parameters :
     *      Strings[]
     */


    public static void main(String[] args) {
        launch(args);
        
    }
    
	@Override
	protected void onPreInit() {
		initLanguages();
	}
	
	public static void initLanguages() {
		// Init languages
		getLocalizationService().addLanguageData(Language.ENGLISH, ResourceBundle.getBundle("assets.properties.texts", Locale.ENGLISH));
		getLocalizationService().addLanguageData(Language.GERMAN, ResourceBundle.getBundle("assets.properties.texts", Locale.GERMAN));
		
		// Set first entry as default
		getLocalizationService().selectedLanguageProperty().unbind();
		getLocalizationService().setSelectedLanguage(Language.ENGLISH);
	}
}
