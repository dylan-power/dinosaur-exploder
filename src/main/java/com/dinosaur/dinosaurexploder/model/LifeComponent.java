package com.dinosaur.dinosaurexploder.model;

import com.almasb.fxgl.entity.component.Component;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
/**
 * Summary :
 *      This handles the life component of the Player implements the life interface and extends the Component
 */
public class LifeComponent extends Component implements Life{
	Integer life = 3;
	/**
	 * Summary :
	 *      This method is overriding the superclass method to run for every frame like a continues flow , without any stop until we put stop to it.
	 * Parameters :
	 *      double ptf
	 */
    @Override
    public void onUpdate(double ptf) {
        entity.getViewComponent().clearChildren();
        Text lifeText = new Text("Lives: "  + life.toString());
        lifeText.setFill(Color.RED);
        lifeText.setFont(Font.font("Arial", 20));
        entity.getViewComponent().addChild(lifeText);
    }
	/**
	 * Summary :
	 *      This method is overriding the superclass method to return the life the current life
	 */
	@Override
	public int getLife() {
		return life;
	}
	/**
	 * Summary :
	 *      This method is overriding the superclass method to sets the life the current life
	 */
	@Override
	public void setLife(int i) {
		life = i;
	}
	/**
	 * Summary :
	 *      This method is overriding the superclass method to decrease the life to the current life
	 */
	@Override
	public int decreaseLife(int i) {
		life -= i;
		return life;
	}
}
