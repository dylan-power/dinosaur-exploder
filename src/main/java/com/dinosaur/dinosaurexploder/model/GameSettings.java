package com.dinosaur.dinosaurexploder.model;

public class GameSettings {
    // Declare private static instance of the class
    private static GameSettings instance;

    // Global variables
    private int difficultyLevel;
    private Difficulty difficulty;

    // Private constructor to prevent instantiation from other classes
    private GameSettings() {
        difficultyLevel = 1;
        difficulty = createDifficulty();
    }

    private Difficulty createDifficulty() {
        double speed, min, max;
        switch (difficultyLevel) {
            case 1:
                speed = 1.0;
                min = 90;
                max = 90;
                break;
            case 2:
                speed = 2.0;
                min = 90;
                max = 90;
                break;
            case 3:
                speed = 2.5;
                min = 90;
                max = 90;
                break;
            case 4:
                speed = 2.5;
                min = 22.5;
                max = 112.5;
                break;
            case 5:
                speed = 3.0;
                min = 45;
                max = 135;
                break;
            default:
                throw new IllegalArgumentException("Unknown difficulty level!");
        }
        return new Difficulty(speed, min, max);
    }

    // Public static method to provide access to the instance
    public static GameSettings getInstance() {
        if (instance == null) {
            instance = new GameSettings();
        }
        return instance;
    }

    // Getters and setters for the global variables

    public Difficulty getDifficulty() {
        return difficulty;
    }

    public void setDifficultyLevel(int level) {
        this.difficultyLevel = level;
        difficulty = createDifficulty();
    }
}
