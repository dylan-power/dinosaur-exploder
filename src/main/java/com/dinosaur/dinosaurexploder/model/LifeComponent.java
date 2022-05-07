package com.dinosaur.dinosaurexploder.model;

import com.almasb.fxgl.entity.component.Component;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class LifeComponent extends Component implements Life{
	Integer life = 3;
	
    @Override
    public void onUpdate(double ptf) {
        entity.getViewComponent().clearChildren();
        Text lifeText = new Text("Lives: "  + life.toString());
        lifeText.setFill(Color.RED);
        lifeText.setFont(Font.font("Arial", 20));
        entity.getViewComponent().addChild(lifeText);
    }

	@Override
	public int getLife() {
		return life;
	}

	@Override
	public void setLife(int i) {
		life = i;
	}

	@Override
	public int decreaseLife(int i) {
		life -= i;
		return life;
	}
}
