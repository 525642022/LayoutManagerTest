package com.example.layoutmanagerlib.layoutmanager.trapezoid;


public class TrapezoidViewBean {
    private float scaleXY;
    private int top;

    public TrapezoidViewBean(int top, float scaleXY) {
        this.top = top;
        this.scaleXY = scaleXY;

    }


    public float getScaleXY() {
        return scaleXY;
    }

    public void setScaleXY(float mScaleXY) {
        this.scaleXY = mScaleXY;
    }


    public int getTop() {
        return top;
    }

    public void setTop(int mTop) {
        this.top = mTop;
    }

}
