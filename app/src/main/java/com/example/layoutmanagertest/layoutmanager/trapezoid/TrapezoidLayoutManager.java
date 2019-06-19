package com.example.layoutmanagertest.layoutmanager.trapezoid;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.layoutmanagertest.layoutmanager.base.BaseLayoutManager;

import java.util.ArrayList;
import java.util.List;

public class TrapezoidLayoutManager extends BaseLayoutManager<TrapezoidViewBean> {
    private TrapezoidControl trapezoidControl;
    private int mItemViewWidth;
    private int mItemViewHeight;
    private int mItemCount;
    private float mProportion_h_w = 1.46f;
    private float mProportion_w = 0.9f;

    private int mScrollOffset = Integer.MAX_VALUE;


    public TrapezoidLayoutManager(TrapezoidControl trapezoidControl) {
      this.trapezoidControl = trapezoidControl;
    }



    @Override
    public boolean canScrollVertically() {
        return true;
    }




    @Override
    public int scrollVerticallyBy(int dy, RecyclerView.Recycler recycler, RecyclerView.State state) {
        int pendingScrollOffset = mScrollOffset + dy;
        mScrollOffset = Math.min(Math.max(mItemViewHeight, mScrollOffset + dy), mItemCount * mItemViewHeight);
        onLayoutChildren(recycler,state);
        return mScrollOffset - pendingScrollOffset + dy;
    }



    @Override
    public void initItemView(RecyclerView.Recycler recycler) {
        mItemViewWidth = (int) (getHorizontalSpace() * mProportion_w);//item的宽
        mItemViewHeight = (int) (mItemViewWidth * mProportion_h_w);//item的高
        mItemCount = getItemCount();
        mScrollOffset = Math.min(Math.max(mItemViewHeight, mScrollOffset), mItemCount * mItemViewHeight);
    }

    @Override
    public List createItemViewInfoList() {
        int bottomItemPosition = (int) Math.floor(mScrollOffset / mItemViewHeight);
        int bottomItemVisibleHeight = mScrollOffset % mItemViewHeight;
        return trapezoidControl.createItemViewInfoList(bottomItemPosition,bottomItemVisibleHeight,getVerticalSpace(),mItemViewHeight,getItemCount());
    }

    @Override
    public void drawView(RecyclerView.Recycler recycler, RecyclerView.State state, List<TrapezoidViewBean> itemViewList) {


        int layoutCount = itemViewList.size();
        final int startPos = trapezoidControl.getBottomItemPosition() - (layoutCount - 1);
        for (int i = 0; i < layoutCount; i++) {
            View view = recycler.getViewForPosition(startPos + i);
            TrapezoidViewBean layoutInfo = itemViewList.get(i);
            addView(view);
            measureChildWithExactlySize(view);
            int left = (getHorizontalSpace() - mItemViewWidth) / 2;
            layoutDecoratedWithMargins(view, left, layoutInfo.getTop(), left + mItemViewWidth, layoutInfo.getTop() + mItemViewHeight);
            view.setPivotX(view.getWidth() / 2);
            view.setPivotY(0);
            view.setScaleX(layoutInfo.getScaleXY());
            view.setScaleY(layoutInfo.getScaleXY());
        }
    }
    /**
     * 测量itemview的确切大小
     */
    private void measureChildWithExactlySize(View child) {
        final int widthSpec = View.MeasureSpec.makeMeasureSpec(mItemViewWidth, View.MeasureSpec.EXACTLY);
        final int heightSpec = View.MeasureSpec.makeMeasureSpec(mItemViewHeight, View.MeasureSpec.EXACTLY);
        child.measure(widthSpec, heightSpec);
    }
}
