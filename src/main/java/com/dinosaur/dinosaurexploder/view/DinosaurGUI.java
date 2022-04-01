package com.dinosaur.dinosaurexploder.view;

import com.almasb.fxgl.app.GameSettings;

public class DinosaurGUI {
    public void initSettings(GameSettings settings) {
        settings.setWidth(550);
        settings.setHeight(750);
        settings.setTitle("Dinosaur Exploder");
        settings.setVersion("1.0");
        settings.setTicksPerSecond(60); // check info : settings.setProfilingEnabled(true);
    }
}
