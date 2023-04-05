package com.dinosaur.dinosaurexploder.model;

/**
 * Summary :
 *      This interface is to implement in  new DinosaurClass for clean Understanding
 */
public interface Dinosaur {
    /**
     * Summary :
     *      This method runs for every frame like a continues flow , without any stop until we put stop to it.
     * Parameters :
     *      double ptf
     */
    public void onUpdate(double ptf);
    /**
     * Summary :
     *      This handles with the shooting from the dinosaur and spawning of the new bullet
     */
    public void shoot();
}
