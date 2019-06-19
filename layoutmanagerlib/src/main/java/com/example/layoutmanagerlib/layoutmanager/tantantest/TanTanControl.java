package com.example.layoutmanagerlib.layoutmanager.tantantest;

import java.util.ArrayList;
import java.util.List;

public class TanTanControl {

    private int count = 4;
    private float scale = 0.05f;
    private int translateY = 100;

    public TanTanControl(int count, float scale, int translateY) {
        this.count = count;
        this.scale = scale;
        this.translateY = translateY;
    }

    public int getCount() {
        return count;
    }

    public float getScale() {
        return scale;
    }

    public int getTranslateY() {
        return translateY;
    }

    public List<TanTanBean> createItemViewInfoList(int itemCount) {
        int lastPosition;
        if (itemCount < count) {
            lastPosition = 0;
        } else {
            lastPosition = itemCount - count;
        }
        List<TanTanBean> tanTanBeanList = new ArrayList<>();
        for (int position = lastPosition; position < itemCount; position++) {
            TanTanBean tanTanBean = new TanTanBean();
            int level = itemCount - position - 1;
            tanTanBean.setLevel(level);
            //设置每层的Scale和translationY
            if (level > 0) {
                //设置每一层X方向的缩小
                tanTanBean.setScaleX(1 - scale * level);
                if (level < count - 1) {
                    //Y需要缩小的和位移
                    tanTanBean.setTranslateY(translateY * level);
                    tanTanBean.setScaleY(1 - scale * level);
                } else {
                    //不需要缩小和位移只需要和前一层保持一致
                    tanTanBean.setTranslateY(translateY * (level - 1));
                    tanTanBean.setScaleY(1 - scale * (level - 1));
                }
            }
            tanTanBeanList.add(tanTanBean);
        }

        return tanTanBeanList;
    }

}
