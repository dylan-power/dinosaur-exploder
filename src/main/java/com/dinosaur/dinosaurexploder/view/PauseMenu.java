package com.dinosaur.dinosaurexploder.view;

import com.almasb.fxgl.app.scene.FXGLMenu;
import com.almasb.fxgl.app.scene.MenuType;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.ui.FontType;
import javafx.beans.binding.Bindings;
import javafx.geometry.Pos;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import static com.almasb.fxgl.dsl.FXGL.*;

import static com.almasb.fxgl.dsl.FXGL.getLocalizationService;
import static com.almasb.fxgl.dsl.FXGLForKtKt.getUIFactoryService;

import com.dinosaur.dinosaurexploder.DinosaurApp;
import com.dinosaur.dinosaurexploder.model.GameConstants;

public class PauseMenu extends FXGLMenu {
    public PauseMenu() {
        super(MenuType.GAME_MENU);
		DinosaurApp.initLanguages();
        

        PauseButton btnBack = new PauseButton(getLocalizationService().getLocalizedString("Pause.1"),() -> fireResume());

        PauseButton btnQuitGame = new PauseButton(getLocalizationService().getLocalizedString("Pause.2"),() -> fireExitToMainMenu());

        ControlButton btnControls = new ControlButton(getLocalizationService().getLocalizedString("Pause.3"));

        btnControls.setControlAction(() -> {

            var bg = new Rectangle(getAppWidth(), getAppHeight(), Color.color(0,0,0,0.5));

            var controlsBox = new VBox(15);

            controlsBox.getChildren().addAll(
                    new PauseButton(getLocalizationService().getLocalizedString("Pause.4"), () -> {
                        controlsBox.getChildren().removeAll(controlsBox.getChildren());
                        removeChild(bg);
                        btnBack.enable();
                        btnQuitGame.enable();
                        btnControls.enable();
                    }),
                    new OptionsButton(getLocalizationService().getLocalizedString("Pause.5")),
                    new OptionsButton(getLocalizationService().getLocalizedString("Pause.6")),
                    new OptionsButton(getLocalizationService().getLocalizedString("Pause.7")),
                    new OptionsButton(getLocalizationService().getLocalizedString("Pause.8")),
                    new OptionsButton(getLocalizationService().getLocalizedString("Pause.9")),
                    new OptionsButton(getLocalizationService().getLocalizedString("Pause.10")));

            controlsBox.setTranslateX(300);
            controlsBox.setTranslateY(getAppWidth() / 2);

            btnBack.disable();
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

    private static class OptionsButton extends StackPane {

        private String description;
        private  Text text;

        public OptionsButton(String description) {
            this.description = description;

            text = getUIFactoryService().newText(description, Color.WHITE, 14.0);
            setAlignment(Pos.CENTER_LEFT);
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