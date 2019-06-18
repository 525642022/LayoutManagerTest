package com.example.layoutmanagertest.layoutmanager.arc;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.ArrayList;

/***
 * 弧形水平滑动的
 */
public class ArcLayoutManager extends RecyclerView.LayoutManager {
    private int mRadius = 400;
    private int startLeft;
    private int startBottom;
    private int mDecoratedChildWidth;
    private int mDecoratedChildHeight;

    //每个item之间的角度间隔
    private float intervalAngle = 30f;
    //初始角度
    private float mRotate = 0;

    /**
     * 当前旋转的角度
     **/
    private float offsetRotate;

    /**
     * 滑动距离和角度的一个比例
     **/
    private static float DISTANCE_RATIO = 10f;

    //最大和最小的移除角度
    private int minRemoveDegree = -120;
    private int maxRemoveDegree = 120;

    @Override
    public RecyclerView.LayoutParams generateDefaultLayoutParams() {
        return new RecyclerView.LayoutParams(RecyclerView.LayoutParams.WRAP_CONTENT,
                RecyclerView.LayoutParams.WRAP_CONTENT);
    }

    @Override
    public void onLayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state) {
        if (getItemCount() <= 0 || state.isPreLayout()) {
            offsetRotate = 0;
            return;
        }

        removeAndRecycleAllViews(recycler);
        initItemView(recycler);
        layoutChildView(recycler, state);

    }

    private void layoutChildView(RecyclerView.Recycler recycler, RecyclerView.State state) {
        ArrayList<ArcViewBean> arcViewBeans = createItemViewInfoList();
        detachAndScrapAttachedViews(recycler);
        drawView(recycler, state, arcViewBeans);
    }

    private void drawView(RecyclerView.Recycler recycler, RecyclerView.State state, ArrayList<ArcViewBean> arcViewBeans) {
        if (state.isPreLayout()) return;
        for (int i = 0; i < getItemCount(); i++) {
            ArcViewBean arcViewBean = arcViewBeans.get(i);
            if (arcViewBean.getRotate() <= maxRemoveDegree
                    && arcViewBean.getRotate() >= minRemoveDegree) {
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

    private ArrayList<ArcViewBean> createItemViewInfoList() {
        ArrayList<ArcViewBean> arcViewBeans = new ArrayList<>();
        for (int i = 0; i < getItemCount(); i++) {
            ArcViewBean arcViewBean = new ArcViewBean();
            float rotate = mRotate + i * intervalAngle - offsetRotate;
            arcViewBean.setRotate(rotate);
            arcViewBean.setLeft(startLeft + calLeftPosition(rotate));
            arcViewBean.setBottom(startBottom - calBottomPosition(rotate));
            arcViewBeans.add(arcViewBean);
        }
        return arcViewBeans;
    }


    /***
     *  初始化以西View信息
     * @param recycler
     */
    private void initItemView(RecyclerView.Recycler recycler) {
        //得到子view的宽和高，这边的item的宽高都是一样的，所以只需要进行一次测量
        View scrap = recycler.getViewForPosition(0);
        addView(scrap);
        measureChildWithMargins(scrap, 0, 0);
        //计算测量布局的宽高
        mDecoratedChildWidth = getDecoratedMeasuredWidth(scrap);
        mDecoratedChildHeight = getDecoratedMeasuredHeight(scrap);
        //确定起始位置，在最上方的中心处
        startLeft = (getHorizontalSpace() - mDecoratedChildWidth) / 2;
        startBottom = getVerticalSpace();
    }


    @Override
    public boolean canScrollHorizontally() {
        return true;
    }

    private int getHorizontalSpace() {
        return getWidth() - getPaddingRight() - getPaddingLeft();
    }

    /**
     * 当前item的x的坐标
     **/
    private int calLeftPosition(float rotate) {
        return (int) (mRadius * Math.cos(Math.toRadians(90 - rotate)));
    }

    /**
     * 当前item的y的坐标
     **/
    private int calBottomPosition(float rotate) {
        return (int) (mRadius * Math.sin(Math.toRadians(90 - rotate)));
    }

    private int getVerticalSpace() {
        return getHeight() - getPaddingBottom() - getPaddingTop();
    }

    @Override
    public int scrollHorizontallyBy(int dx, RecyclerView.Recycler recycler, RecyclerView.State state) {
        int willScroll = dx;

        //每个item x方向上的移动距离
        float theta = dx / DISTANCE_RATIO;
        float targetRotate = offsetRotate + theta;

        //目标角度
        if (targetRotate < 0) {
            willScroll = (int) (-offsetRotate * DISTANCE_RATIO);
        } else if (targetRotate > getMaxOffsetDegree()) {
            willScroll = (int) ((getMaxOffsetDegree() - offsetRotate) * DISTANCE_RATIO);
        }
        theta = willScroll / DISTANCE_RATIO;

        //当前移动的总角度
        offsetRotate += theta;
        layoutChildView(recycler, state);
        return willScroll;
    }

    /**
     * 最大的角度
     **/
    private float getMaxOffsetDegree() {
        return (getItemCount() - 1) * intervalAngle;
    }
}
