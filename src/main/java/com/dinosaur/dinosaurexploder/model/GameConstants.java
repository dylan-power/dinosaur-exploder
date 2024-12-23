package com.dinosaur.dinosaurexploder.model;

import java.util.List;

import com.almasb.fxgl.localization.Language;

/**
 *
 * This holds every constant in the the PROJECT
 */
public class GameConstants {

    /*
     * CONSTANTS FOR IMAGES
     */
    public static final String BACKGROUND_IMAGEPATH = "/assets/textures/background.png";
    public static final String SPACESHIP_IMAGEPATH = "assets/textures/spaceship.png";
    public static final String SPACESHIP_IMAGEFILE = "spaceship.png";
    public static final String BASE_PROJECTILE_IMAGEPATH = "assets/textures/basicProjectile.png";
    public static final String BASE_PROJECTILE_IMAGEFILE = "basicProjectile.png";
    public static final String ENEMY_PROJECTILE_IMAGEFILE = "enemyProjectile.png";
    public static final String GREENDINO_IMAGEPATH = "assets/textures/greenDino.png";
    public static final String GREENDINO_IMAGEFILE = "greenDino.png";
    public static final String HEART_IMAGEPATH = "assets/textures/life.png";
    /*
     *CONSTANTS FOR FONTS
     */
    public static final String ARCADECLASSIC_FONTNAME = "ArcadeClassic";
    /*
     * SOUNDS
     */
    public static final String ENEMYSHOOT_SOUND = "/assets/sounds/enemyShoot.wav";
    public static final String SHOOT_SOUND = "/assets/sounds/shoot.wav";
    public static final String MAINMENU_SOUND = "/assets/sounds/mainMenu.wav";
    public static final String BACKGROUND_SOUND ="/assets/sounds/gameBackground.wav";
    public static final String ENEMY_EXPLODE_SOUND = "/assets/sounds/enemyExplode.wav";
    public static final String PLAYER_HIT_SOUND = "/assets/sounds/playerHit.wav";

    public static final String GAME_NAME = "Dinosaur Exploder";

    public static final List<Language> AVAILABLE_LANGUAGES  = List.of(Language.ENGLISH, Language.GERMAN );


}