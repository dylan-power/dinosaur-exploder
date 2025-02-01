package com.dinosaur.dinosaurexploder.model;

import com.almasb.fxgl.core.math.Vec2;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.entity.component.Component;
import com.dinosaur.dinosaurexploder.utils.GameData;

import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.util.List;

import static com.almasb.fxgl.dsl.FXGL.getLocalizationService;
import static com.almasb.fxgl.dsl.FXGLForKtKt.spawn;

public class BombComponent extends Component implements Bomb {
    private int bombCount = 3;
    private Image spcshpImg;
   
    public BombComponent() {
        // Selected spaceship from GameData
        int selectedShip = GameData.getSelectedShip();

        //  Set the image of SelectedShip using the spaceship number
        if (selectedShip != 0) {
            String shipImagePath = "/assets/textures/spaceship" + selectedShip + ".png";
            System.out.println("Selected spaceship: " + selectedShip);
            this.spcshpImg = new Image(shipImagePath);
        } 
    }

    
    // Declaring Bomb Text
    private Image bomb = new Image(GameConstants.BOMB_IMAGEPATH);
    // Declaring 3 Bomb
    ImageView bomb1 = new ImageView(bomb);
    ImageView bomb2 = new ImageView(bomb);
    ImageView bomb3 = new ImageView(bomb);
    private Text bombText = new Text("Bombs Left: " + bombCount);
    private Node bombUI;
    /**
     * Summary :
     *      This method is overriding the superclass method to run for every frame like a continuous flow, without any stop until we put stop to it.
     * Parameters :
     *      double tpf - Time per frame
     */
    @Override
    public void onUpdate(double tpf){
        if(bombUI==null){
            bombUI = CreateBombUI();
            entity.getViewComponent().addChild(bombUI);
        }
        updateBombUI();
    }
    /**
     * Summary :
     *      This method creates the UI for displaying the bomb count and bomb images.
     * Returns :
     *      Node - The created bomb UI node
     */
    private Node CreateBombUI(){
        var container = new Pane();
        bomb1.setLayoutY(10);
        bomb2.setLayoutY(10);
        bomb3.setLayoutY(10);
        bomb2.setLayoutX(30);
        bomb3.setLayoutX(60);
        container.getChildren().addAll(bomb1, bomb2, bomb3);

        bombText.setFill(Color.BLUE);
        bombText.setFont(Font.font(GameConstants.ARCADECLASSIC_FONTNAME, 20));
        bombText.setLayoutX(0);
        bombText.setLayoutY(0);

        container.getChildren().add(bombText);

        return container;
    }

    /**
     * Summary :
     *      This method updates the bomb UI based on the current bomb count.
     */
    private void updateBombUI() {
        bomb1.setVisible(bombCount >= 1);
        bomb2.setVisible(bombCount >= 2);
        bomb3.setVisible(bombCount >= 3);
        bombText.setText("Bombs Left: " + bombCount);
    }
    /**
     * Summary:
     *      This method returns the current number of bombs.
     */
    @Override
    public int getBombCount() {
        return bombCount;
    }

    /**
     * Summary :
     *      This method is used to launch a row of bullets as a bomb.
     * Parameters :
     *      Entity player - The player entity using the bomb
     */
    @Override
    public void useBomb(Entity player) {
        if (getBombCount() > 0) {
            bombCount--;
            updateBombUI();
            spawnBombBullets(player);
        } else {
            System.out.println("No bombs left!");
        }
    }
    /**
     * Summary :
     *      This method spawns a row of bullets from the player's position.
     * Parameters :
     *      Entity player - The player entity from which to spawn the bullets
     */
    private void spawnBombBullets(Entity player) {
        Point2D center = player.getCenter();
        Image projImg = new Image(GameConstants.BASE_PROJECTILE_IMAGEPATH);
        for (int i = -5; i <= 5; i++) {
            double angle = entity.getRotation() - 90 + i * 10;
            Vec2 direction = Vec2.fromAngle(angle);
            spawn("basicProjectile", new SpawnData(center.getX() - (projImg.getWidth() / 2) + 3, center.getY() - spcshpImg.getHeight() / 2)
                    .put("direction", direction.toPoint2D()));
        }
        System.out.println("Bomb used! " + getBombCount() + " bombs left!");
    }
}