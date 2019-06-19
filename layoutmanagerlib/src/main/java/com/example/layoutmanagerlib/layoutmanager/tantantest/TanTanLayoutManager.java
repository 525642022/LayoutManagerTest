package com.example.layoutmanagerlib.layoutmanager.tantantest;


import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.example.layoutmanagerlib.layoutmanager.base.BaseLayoutManager;

import java.util.List;

public class TanTanLayoutManager extends BaseLayoutManager<TanTanBean> {
    private static final String TAG = "TanTanLayoutManager";
    TanTanControl tanTanControl;

    public TanTanLayoutManager(TanTanControl tanTanControl) {
        this.tanTanControl = tanTanControl;
    }

    @Override
    public void initItemView(RecyclerView.Recycler recycler) {

    }

    @Override
    public List<TanTanBean> createItemViewInfoList() {
        return tanTanControl.createItemViewInfoList(getItemCount());
    }

    @Override
    public void drawView(RecyclerView.Recycler recycler, RecyclerView.State state, List<TanTanBean> itemViewList) {
        int itemCount = getItemCount();
        if (itemCount < 1) {
            return;
        }
        for (int i = 0; i < itemViewList.size(); i++) {
            TanTanBean tanTanBean = itemViewList.get(i);
            View view = recycler.getViewForPosition(itemCount - tanTanBean.getLevel() - 1);
            addView(view);
            measureChildWithMargins(view, 0, 0);
            int widthSpace = getWidth() - getDecoratedMeasuredWidth(view);
            int heightSpace = getHeight() - getDecoratedMeasuredHeight(view);
            layoutDecoratedWithMargins(view, widthSpace / 2, heightSpace / 4,
                    widthSpace / 2 + getDecoratedMeasuredWidth(view),
                    heightSpace / 4 + getDecoratedMeasuredHeight(view));
            Log.e(TAG, tanTanBean.toString());
            if (tanTanBean.getLevel() > 0) {
                view.setScaleX(tanTanBean.getScaleX());
                view.setTranslationY(tanTanBean.getTranslateY());
                view.setScaleY(tanTanBean.getScaleY());
            }
        }
    }
}

