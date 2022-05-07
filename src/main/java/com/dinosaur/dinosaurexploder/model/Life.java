package com.dinosaur.dinosaurexploder.model;

public interface Life {
    public void onUpdate(double tpf);
    public int getLife();
    public void setLife(int i);
    public int decreaseLife(int i);
}