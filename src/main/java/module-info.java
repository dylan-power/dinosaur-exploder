module com.dinosaur.dinosaurexploder {
    requires javafx.controls;
    requires com.almasb.fxgl.all;
    exports com.dinosaur.dinosaurexploder;

    opens assets.textures;

    opens com.dinosaur.dinosaurexploder.model to com.almasb.fxgl.core;
    //opens com.dinosaur.dinosaurexploder.controller to com.almasb.fxgl.core;
}