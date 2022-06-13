package com.dinosaur.dinosaurexploder.model;

import com.almasb.fxgl.dsl.components.HealthIntComponent;
import com.almasb.fxgl.entity.component.Component;

public class LifeComponent extends Component {

    private int healthIntComponent = 1;


    public LifeComponent(int healthIntComponent) {
         new HealthIntComponent(healthIntComponent);
    }

    public int getValue() {
		return healthIntComponent;
	}

	public void setValue(int i) {
		this.healthIntComponent = i;
	}

}
