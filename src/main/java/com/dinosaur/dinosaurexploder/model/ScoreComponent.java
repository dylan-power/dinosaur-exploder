package com.dinosaur.dinosaurexploder.model;

import com.almasb.fxgl.entity.component.Component;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
/**
 * Summary :
 *      This handles the Score component of the Player implements the Score interface and extends the Component
 */
public class ScoreComponent extends Component  implements Score{
    Integer score = 0;
    /**
     * Summary :
     *      This method runs for every frame like a continues flow , without any stop until we put stop to it.
     * Parameters :
     *      double ptf
     */
    @Override
    public void onUpdate(double ptf) {
        entity.getViewComponent().clearChildren();
        Text scoreText = new Text("Score: "  + score.toString());
        scoreText.setFill(Color.GREEN);
        scoreText.setFont(Font.font("Arial", 20));
        entity.getViewComponent().addChild(scoreText);

    }
    /**
     * Summary :
     *      This method is overriding the superclass method to return the Score to the current Score
     */
    @Override
    public int getScore() {
        return score;
    }
    /**
     * Summary :
     *      This method is overriding the superclass method to set the Score to the current Score
     */
    @Override
    public void setScore(int i) {
        score = i;
    }
    /**
     * Summary :
     *      This method is overriding the superclass method to increment the Score to the current Score
     */
    @Override
    public void incrementScore(int i) {
        score += i;
    }


}
