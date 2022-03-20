package com.dinosaur.dinosaurexploder.model;

public interface Score {
    public void onUpdate(double tpf);
    public int getScore();
    public void setScore(int i);
    public void incrementScore(int i);

}