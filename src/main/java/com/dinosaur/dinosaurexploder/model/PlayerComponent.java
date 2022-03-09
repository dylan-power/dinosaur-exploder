package com.dinosaur.dinosaurexploder.model;

import com.almasb.fxgl.entity.component.Component;

public class PlayerComponent extends Component {
    //entity is not initialized anywhere because it is linked in the factory
    public void moveUp(){
        entity.translateY(-5);
    }
    public void moveDown(){
        entity.translateY(5);

    }
    public void moveRight(){
        entity.translateX(5);
    }
    public void moveLeft(){
        entity.translateX(-5);
    }
}
