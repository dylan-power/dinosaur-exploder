package com.dinosaur.dinosaurexploder.model;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.component.Component;

import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class LifeComponent extends Component implements Life {
	// Created two methods for shorter and cleaner code
	public void setEntity(Node j) {
		entity.getViewComponent().addChild(j);
	}

	public void clearEntity() {
		entity.getViewComponent().clearChildren();
	}

	Integer life = 3;
	private Image heart = new Image("assets/textures/life.png");
	// Declaring Lives Text
	Text lifeText = new Text("Lives");
	// Declaring 3 Hearts
	ImageView test1 = new ImageView(heart);
	ImageView test2 = new ImageView(heart);
	ImageView test3 = new ImageView(heart);

	@Override
	public void onUpdate(double ptf) {
		clearEntity();
		lifeText.setFill(Color.RED);
		lifeText.setFont(Font.font("ArcadeClassic", 20));
		// Adjusting Hearts with respect to text and eachother
		test1.setLayoutY(10);
		test2.setLayoutY(10);
		test3.setLayoutY(10);
		test2.setLayoutX(test1.getLayoutX() + 30);
		test3.setLayoutX(test2.getLayoutX() + 30);
		// setting them for display
		if (life == 3) {
			setEntity(test1);
			setEntity(test2);
			setEntity(test3);
			setEntity(lifeText);
		} else if (life == 2) {
			clearEntity();
			setEntity(test1);
			setEntity(test2);
			setEntity(lifeText);
		} else if (life == 1) {
			clearEntity();
			setEntity(test1);
			setEntity(lifeText);
		} else {
			clearEntity();
			setEntity(lifeText);
		}
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
