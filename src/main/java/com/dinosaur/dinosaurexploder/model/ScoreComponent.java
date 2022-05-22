package com.dinosaur.dinosaurexploder.model;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.component.Component;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class ScoreComponent extends Component  implements Score{
    Integer score = 0;


    @Override
    public void onUpdate(double ptf) {
        entity.getViewComponent().clearChildren();
        Text scoreText = new Text("Score: "  + score.toString());
        scoreText.setFill(Color.GREEN);
        scoreText.setFont(Font.font("Arial", 20));
        entity.getViewComponent().addChild(scoreText);

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
