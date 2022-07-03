package com.dinosaur.dinosaurexploder.view;

import com.almasb.fxgl.app.scene.FXGLMenu;
import com.almasb.fxgl.app.scene.MenuType;
import javafx.beans.binding.Bindings;
import javafx.geometry.Pos;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
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
        PauseButton btnControls = new PauseButton("Controls", () -> {});
        PauseButton btnQuitGame = new PauseButton("Quit Game",() -> fireExit());

        var bg = new Rectangle(getAppWidth(), getAppHeight(), Color.color(0,0,0,0.5));
        var box = new VBox(15,
                btnBack,
                btnControls,
                new PauseButton("Placeholder 1", () -> {}),
                new PauseButton("Placeholder 2",() -> {}),
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
                if (e.getCode() == KeyCode.ENTER) {
                    action.run();
                }
            });

            setOnMouseClicked(event->{
                action.run();
            });


            getChildren().addAll(selector, text);

        }
    }

}