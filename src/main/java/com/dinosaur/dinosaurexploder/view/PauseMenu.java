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

        PauseButton btnControls = new PauseButton("Controls", () -> {

            var bg = new Rectangle(getAppWidth(), getAppHeight(), Color.color(0,0,0,0.5));

            var controlsBox = new VBox(15);

            controlsBox.getChildren().addAll(
                    new PauseButton("Back", () -> {
                        controlsBox.getChildren().removeAll(controlsBox.getChildren());
                        removeChild(bg);
                        btnBack.enable();
                        btnQuitGame.enable();
                    }),
                    new PauseButton("Placeholder 2",() -> {
                    }));

            controlsBox.setTranslateX(300);
            controlsBox.setTranslateY(getAppWidth() / 2);

            btnBack.disable();
            btnQuitGame.disable();


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
                bg,
                box
        );

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
                if (e.getCode() == KeyCode.ENTER && !disable && this.getChildren().size()<1) {
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