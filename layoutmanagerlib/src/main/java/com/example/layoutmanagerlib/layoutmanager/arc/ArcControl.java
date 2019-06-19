package com.example.layoutmanagerlib.layoutmanager.arc;

import java.util.ArrayList;

public class ArcControl {
    private int mRadius = 400;
    private int startLeft;
    private int startBottom;
    //每个item之间的角度间隔
    private float intervalAngle = 30f;
    //初始角度
    private float mRotate = 0;
    //滑动距离和角度的一个比例
    private float diatance_ratio = 10f;
    //最大和最小的移除角度
    private int minRemoveDegree = -120;
    private int maxRemoveDegree = 120;

    public ArcControl(int mRadius, float intervalAngle, float mRotate, float diatance_ratio, int minRemoveDegree, int maxRemoveDegree) {
        this.mRadius = mRadius;
        this.intervalAngle = intervalAngle;
        this.mRotate = mRotate;
        this.diatance_ratio = diatance_ratio;
        this.minRemoveDegree = minRemoveDegree;
        this.maxRemoveDegree = maxRemoveDegree;
    }


    public int getStartLeft() {
        return startLeft;
    }

    public void setStartLeft(int startLeft) {
        this.startLeft = startLeft;
    }

    public int getStartBottom() {
        return startBottom;
    }

    public void setStartBottom(int startBottom) {
        this.startBottom = startBottom;
    }

    public float getIntervalAngle() {
        return intervalAngle;
    }


    public float getmRotate() {
        return mRotate;
    }


    public float getDiatance_ratio() {
        return diatance_ratio;
    }

    public int getMinRemoveDegree() {
        return minRemoveDegree;
    }


    public int getMaxRemoveDegree() {
        return maxRemoveDegree;
    }


    /**
     * 当前item的x的坐标
     **/
    public int calLeftPosition(float rotate) {
        return (int) (mRadius * Math.cos(Math.toRadians(90 - rotate)));
    }

    /**
     * 当前item的y的坐标
     **/
    public int calBottomPosition(float rotate) {
        return (int) (mRadius * Math.sin(Math.toRadians(90 - rotate)));
    }

    /**
     * 最大的角度
     **/
    public float getMaxOffsetDegree(int count) {
        return (count - 1) * intervalAngle;
    }

    public ArrayList<ArcViewBean> createItemViewInfoList(int count,float offsetRotate) {
        ArrayList<ArcViewBean> arcViewBeans = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            ArcViewBean arcViewBean = new ArcViewBean();
            float rotate = getmRotate() + i * getIntervalAngle() - offsetRotate;
            arcViewBean.setRotate(rotate);
            arcViewBean.setLeft(getStartLeft() + calLeftPosition(rotate));
            arcViewBean.setBottom(getStartBottom() - calBottomPosition(rotate));
            arcViewBeans.add(arcViewBean);
        }
        return arcViewBeans;
    }
}
