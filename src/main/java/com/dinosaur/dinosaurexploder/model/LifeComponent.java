package com.dinosaur.dinosaurexploder.model;

import static com.almasb.fxgl.dsl.FXGL.getLocalizationService;

import com.almasb.fxgl.entity.component.Component;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

/**
 * Summary :
 * This handles the life component of the Player implements the life interface and extends the Component
 */
public class LifeComponent extends Component implements Life {

    final private Image heart = new Image(GameConstants.HEART_IMAGEPATH);
    Integer life = 3;
    // Declaring Lives Text
    Text lifeText = new Text(getLocalizationService().getLocalizedString("Game.5"));

    // Declaring 3 Hearts
    ImageView test1 = new ImageView(heart);
    ImageView test2 = new ImageView(heart);
    ImageView test3 = new ImageView(heart);

    /**
     * Summary :
     * This method is overriding the superclass method to run for every frame like a continues flow , without any stop until we put stop to it.
     * Parameters :
     * double ptf
     */
    @Override
    public void onUpdate(double ptf) 
    {
        clearEntity();
        lifeText.setFill(Color.RED);
        lifeText.setFont(Font.font(GameConstants.ARCADECLASSIC_FONTNAME, 20));

        // Adjusting Hearts with respect to text and each other
        test1.setLayoutY(10);
        test2.setLayoutY(10);
        test3.setLayoutY(10);
        test2.setLayoutX(test1.getLayoutX() + 30);
        test3.setLayoutX(test2.getLayoutX() + 30);

        // Setting them for display
        if (life == 3) {
            setEntity(test1);
            setEntity(test2);
            setEntity(test3);

        } else if (life == 2) {
            clearEntity();
            setEntity(test1);
            setEntity(test2);

        } else if (life == 1) {
            clearEntity();
            setEntity(test1);

        } else {
            clearEntity();

        }
      

        // TODO : Refactoring: Consolidate Duplicate Conditional Fragments
        setEntity(lifeText);
    }

    // Created two methods for shorter and cleaner code
    public void setEntity(Node j) {
        entity.getViewComponent().addChild(j);
    }

    public void clearEntity() {
        entity.getViewComponent().clearChildren();
    }

    @Override
    public int getLife() {
        return life;
    }

    /**
     * Summary :
     * This method is overriding the superclass method to sets the life the current life
     */
    @Override
    public void setLife(int i) {
        life = i;
    }

    /**
     * Summary :
     * This method is overriding the superclass method to decrease the life to the current life
     */
    @Override
    public int decreaseLife(int i) {
        life -= i;
        return life;
    }
}