module com.dinosaur.dinosaurexploder {
    requires javafx.controls;
    requires com.almasb.fxgl.all;
    exports com.dinosaur.dinosaurexploder;
    requires javafx.media;

    opens assets.textures;
    opens assets.sounds;
    opens assets.ui.fonts;

    opens com.dinosaur.dinosaurexploder.model to com.almasb.fxgl.core;
    //opens com.dinosaur.dinosaurexploder.controller to com.almasb.fxgl.core;
}