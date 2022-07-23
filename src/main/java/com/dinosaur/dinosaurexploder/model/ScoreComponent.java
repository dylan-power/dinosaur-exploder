package com.dinosaur.dinosaurexploder.model;

import com.almasb.fxgl.entity.component.Component;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class ScoreComponent extends Component  implements Score{
    Integer score = 0;


    @Override
    public void onUpdate(double ptf) {
        entity.getViewComponent().clearChildren();
        GridPane gridPane = new GridPane();
        gridPane.setHgap(10);
        Text scoreText = new Text("Score: "  + score.toString());
        Image image = new Image("assets/textures/greenDino.png",25,20,false, false);
        ImageView imageView = new ImageView();
        imageView.setImage(image);
        scoreText.setFill(Color.GREEN);
        scoreText.setFont(Font.font("ArcadeClassic", 20));
        gridPane.add(scoreText,1, 0);
        gridPane.add(imageView,2, 0);

        entity.getViewComponent().addChild(gridPane);

    }

    @Override
    public int getScore() {
        return score;
    }

    @Override
    public void setScore(int i) {
        score = i;
    }

    @Override
    public void incrementScore(int i) {
        score += i;
    }


}
