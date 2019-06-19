package com.example.layoutmanagerlib.layoutmanager.trapezoid;

import java.util.ArrayList;

public class TrapezoidControl {
    private float mScale = 0.95f;
    private float mTranslateY = 0.9f;
    private int bottomItemPosition;
    public TrapezoidControl(float mScale, float mTranslateY) {
        this.mScale = mScale;
        this.mTranslateY = mTranslateY;
    }

    public float getmScale() {
        return mScale;
    }

    public int getBottomItemPosition() {
        return bottomItemPosition;
    }

    public void setmScale(float mScale) {
        this.mScale = mScale;
    }

    public float getmTranslateY() {
        return mTranslateY;
    }

    public void setmTranslateY(float mTranslateY) {
        this.mTranslateY = mTranslateY;
    }

    /***
     * 创建bean
     * @param bottomItemPosition
     * @param bottomItemVisibleHeight
     * @return
     */
    public ArrayList<TrapezoidViewBean> createItemViewInfoList(int bottomItemPosition, float bottomItemVisibleHeight, int verticalSpace, int itemViewHeight, int itemCount ) {
        this.bottomItemPosition = bottomItemPosition;
        ArrayList<TrapezoidViewBean> viewInfoArrayList = new ArrayList<>();
        int remainSpace = verticalSpace - itemViewHeight;
        final float offsetPercentRelativeToItemView = bottomItemVisibleHeight * getmTranslateY()/ itemViewHeight;
        for (int i = bottomItemPosition - 1, j = 1; i >= 0; i--, j++) {
            double maxOffset = (verticalSpace - itemViewHeight) / 2 * Math.pow(0.9, j);
            int top = (int) (remainSpace - offsetPercentRelativeToItemView * maxOffset);
            float scaleXY = (float) (Math.pow(mScale, j - 1) * (1 - offsetPercentRelativeToItemView * (1 - mScale)));
            TrapezoidViewBean info = new TrapezoidViewBean(top, scaleXY);
            viewInfoArrayList.add(0, info);
            remainSpace = (int) (remainSpace - maxOffset);
            if (remainSpace <= 0) {
                info.setTop((int) (remainSpace + maxOffset));
                break;
            }
        }
        if (bottomItemPosition < itemCount) {
            final int start = (int)(verticalSpace- bottomItemVisibleHeight);
            TrapezoidViewBean itemViewInfo = new TrapezoidViewBean(start,
                    1.0f);
            viewInfoArrayList.add(itemViewInfo);
        }else{
            this.bottomItemPosition = this.bottomItemPosition - 1;
        }
        return viewInfoArrayList;
    }
}
