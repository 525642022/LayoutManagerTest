package com.example.layoutmanagerlib.layoutmanager.arc;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.layoutmanagerlib.layoutmanager.base.BaseLayoutManager;


import java.util.List;

/***
 * 弧形水平滑动的
 */
public class ArcLayoutManager extends BaseLayoutManager<ArcViewBean> {
    private int mDecoratedChildWidth;
    private int mDecoratedChildHeight;
    //当前旋转的角度
    private float offsetRotate;
    private ArcControl arcControl;

    public ArcLayoutManager(ArcControl arcControl) {

        this.arcControl = arcControl;
    }

    @Override
    public void drawView(RecyclerView.Recycler recycler, RecyclerView.State state, List<ArcViewBean> itemViewList) {
        if (state.isPreLayout()) return;
        for (int i = 0; i < getItemCount(); i++) {
            ArcViewBean arcViewBean = itemViewList.get(i);
            if (arcViewBean.getRotate() <= arcControl.getMaxRemoveDegree()
                    && arcViewBean.getRotate() >= arcControl.getMinRemoveDegree()) {
                View scrap = recycler.getViewForPosition(i);
                measureChildWithMargins(scrap, 0, 0);
                addView(scrap);
                int left = arcViewBean.getLeft();
                int bottom = arcViewBean.getBottom();

                scrap.setRotation(arcViewBean.getRotate());
                layoutDecorated(scrap, left, bottom,
                        left + mDecoratedChildWidth, bottom + mDecoratedChildHeight);
                scrap.setScaleX(arcViewBean.getScaleXY());
                scrap.setScaleY(arcViewBean.getScaleXY());
            }
        }
    }


    @Override
    public void initItemView(RecyclerView.Recycler recycler) {
        //得到子view的宽和高，这边的item的宽高都是一样的，所以只需要进行一次测量
        View scrap = recycler.getViewForPosition(0);
        addView(scrap);
        measureChildWithMargins(scrap, 0, 0);
        //计算测量布局的宽高
        mDecoratedChildWidth = getDecoratedMeasuredWidth(scrap);
        mDecoratedChildHeight = getDecoratedMeasuredHeight(scrap);
        int startLeft = (getHorizontalSpace() - mDecoratedChildWidth) / 2;
        int startBottom = getVerticalSpace();
        arcControl.setStartLeft(startLeft);
        arcControl.setStartBottom(startBottom);
    }

    @Override
    public List<ArcViewBean> createItemViewInfoList() {
        return arcControl.createItemViewInfoList(getItemCount(), offsetRotate);
    }


    @Override
    public boolean canScrollHorizontally() {
        return true;
    }


    @Override
    public int scrollHorizontallyBy(int dx, RecyclerView.Recycler recycler, RecyclerView.State state) {
        int willScroll = dx;

        //每个item x方向上的移动距离
        float theta = dx / arcControl.getDiatance_ratio();
        float targetRotate = offsetRotate + theta;

        //目标角度
        if (targetRotate < 0) {
            willScroll = (int) (-offsetRotate * arcControl.getDiatance_ratio());
        } else if (targetRotate > arcControl.getMaxOffsetDegree(getItemCount())) {
            willScroll = (int) ((arcControl.getMaxOffsetDegree(getItemCount()) - offsetRotate) * arcControl.getDiatance_ratio());
        }
        theta = willScroll / arcControl.getDiatance_ratio();
        //当前移动的总角度
        offsetRotate += theta;
        onLayoutChildren(recycler, state);
        return willScroll;
    }


}
