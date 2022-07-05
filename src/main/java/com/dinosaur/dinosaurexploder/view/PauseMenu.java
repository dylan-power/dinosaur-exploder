package com.dinosaur.dinosaurexploder.view;

import com.almasb.fxgl.app.scene.FXGLMenu;
import com.almasb.fxgl.app.scene.MenuType;
import javafx.beans.binding.Bindings;
import javafx.geometry.Pos;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

import static com.almasb.fxgl.dsl.FXGLForKtKt.getUIFactoryService;

public class PauseMenu extends FXGLMenu {
    public PauseMenu() {
        super(MenuType.GAME_MENU);

        PauseButton btnBack = new PauseButton("Back",() -> fireResume());

        PauseButton btnQuitGame = new PauseButton("Quit Game",() -> fireExit());

        ControlButton btnControls = new ControlButton("Controls");

        btnControls.setControlAction(() -> {

            var bg = new Rectangle(getAppWidth(), getAppHeight(), Color.color(0,0,0,0.5));

            var controlsBox = new VBox(15);

            controlsBox.getChildren().addAll(
                    new PauseButton("Back", () -> {
                        controlsBox.getChildren().removeAll(controlsBox.getChildren());
                        removeChild(bg);
                        btnBack.enable();
                        btnQuitGame.enable();
                        btnControls.enable();
                    }),
                    new OptionsButton("↑: Move spaceship up"),
                    new OptionsButton("↓: Move spaceship down"),
                    new OptionsButton("→: Move spaceship right"),
                    new OptionsButton("←: Move spaceship left"),
                    new OptionsButton("ESC: Pause the game"),
                    new OptionsButton("SPACE: Shoot"));

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
        var box = new VBox(15,
                btnBack,
                btnControls,
                btnQuitGame);

        box.setTranslateX(100);
        box.setTranslateY(getAppWidth() / 2);


        getContentRoot().getChildren().addAll(
                bg, box);
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

        private  Text text;
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

            selector = new Rectangle(5, 22, Color.WHITE);
            selector.setTranslateX(-20);
            selector.visibleProperty().bind(focusedProperty());

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

            setOnMouseDragOver(event -> text.setStrokeWidth(1.6));

            getChildren().addAll(selector, text);

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

            selector = new Rectangle(5, 22, Color.WHITE);
            selector.setTranslateX(-20);
            selector.visibleProperty().bind(focusedProperty());

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

            getChildren().addAll(selector, text);
        }
    }
}