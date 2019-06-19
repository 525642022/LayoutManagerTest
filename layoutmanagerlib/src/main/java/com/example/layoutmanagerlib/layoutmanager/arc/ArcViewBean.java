package com.example.layoutmanagerlib.layoutmanager.arc;
//确定一个Item所需要的属性

public class ArcViewBean {
    //距离左边的距离
    private int left;
    //距离下面的距离
    private int bottom;
    //缩放大小
    private float scaleXY = 1.0f;
    //旋转角度
    private float rotate;

    public int getLeft() {
        return left;
    }

    public void setLeft(int left) {
        this.left = left;
    }

    public int getBottom() {
        return bottom;
    }

    public void setBottom(int bottom) {
        this.bottom = bottom;
    }

    public float getScaleXY() {
        if(rotate>90||rotate<-90){
            return 0.5f;
        }else{
            return 0.5f +(90-Math.abs(rotate))/90;
        }
    }

    public void setScaleXY(float scaleXY) {
        this.scaleXY = scaleXY;
    }

    public float getRotate() {
        return rotate;
    }

    public void setRotate(float rotate) {
        this.rotate = rotate;
    }


}
