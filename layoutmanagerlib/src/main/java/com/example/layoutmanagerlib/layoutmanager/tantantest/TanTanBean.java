package com.example.layoutmanagerlib.layoutmanager.tantantest;

public class TanTanBean {
    private int translateY;
    private  float scaleX;
    private  float scaleY;
    private int level;

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getTranslateY() {
        return translateY;
    }

    public void setTranslateY(int translateY) {
        this.translateY = translateY;
    }

    public float getScaleX() {
        return scaleX;
    }

    public void setScaleX(float scaleX) {
        this.scaleX = scaleX;
    }

    public float getScaleY() {
        return scaleY;
    }

    public void setScaleY(float scaleY) {
        this.scaleY = scaleY;
    }

    @Override
    public String toString() {
        return "TanTanBean{" +
                "translateY=" + translateY +
                ", scaleX=" + scaleX +
                ", scaleY=" + scaleY +
                ", level=" + level +
                '}';
    }
}
