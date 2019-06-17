package com.example.layoutmanagertest.layoutmanager.echelon;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class TrapezoidLayoutManager extends RecyclerView.LayoutManager {
    private int mItemViewWidth;
    private int mItemViewHeight;
    private int mItemCount;
    private float mProportion_h_w = 1.46f;
    private float mProportion_w = 0.9f;
    private float mScale = 0.95f;
    private float mTranslateY = 0.9f;
    private int mScrollOffset = Integer.MAX_VALUE;


    public TrapezoidLayoutManager() {
        mItemViewWidth = (int) (getHorizontalSpace() * mProportion_w);//item的宽
        mItemViewHeight = (int) (mItemViewWidth * mProportion_h_w);//item的高
    }

    @Override
    public RecyclerView.LayoutParams generateDefaultLayoutParams() {
        return new RecyclerView.LayoutParams(RecyclerView.LayoutParams.WRAP_CONTENT,
                RecyclerView.LayoutParams.WRAP_CONTENT);
    }

    @Override
    public boolean canScrollVertically() {
        return true;
    }

    @Override
    public void onLayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state) {
        //在这里重新绘制View
        if (state.getItemCount() == 0 || state.isPreLayout()) return;
        removeAndRecycleAllViews(recycler);
        initItemView();
        layoutChildView(recycler);
    }

    //初始化信息
    private void initItemView() {
        mItemViewWidth = (int) (getHorizontalSpace() * mProportion_w);//item的宽
        mItemViewHeight = (int) (mItemViewWidth * mProportion_h_w);//item的高
        mItemCount = getItemCount();
        mScrollOffset = Math.min(Math.max(mItemViewHeight, mScrollOffset), mItemCount * mItemViewHeight);
    }

    @Override
    public int scrollVerticallyBy(int dy, RecyclerView.Recycler recycler, RecyclerView.State state) {
        int pendingScrollOffset = mScrollOffset + dy;
        mScrollOffset = Math.min(Math.max(mItemViewHeight, mScrollOffset + dy), mItemCount * mItemViewHeight);
        layoutChildView(recycler);
        return mScrollOffset - pendingScrollOffset + dy;
    }

    private void layoutChildView(RecyclerView.Recycler recycler) {
        int bottomItemPosition = (int) Math.floor(mScrollOffset / mItemViewHeight);
        int bottomItemVisibleHeight = mScrollOffset % mItemViewHeight;
        ArrayList<ItemViewInfo> viewInfoArrayList = createItemViewInfoList(bottomItemPosition, bottomItemVisibleHeight);
        if (bottomItemPosition < mItemCount) {
            final int start = getVerticalSpace() - bottomItemVisibleHeight;
            ItemViewInfo itemViewInfo = new ItemViewInfo(start,
                    1.0f);
            viewInfoArrayList.add(itemViewInfo);
        } else {
            bottomItemPosition = bottomItemPosition - 1;
        }
        detachAndScrapAttachedViews(recycler);
        drawView(recycler, bottomItemPosition, viewInfoArrayList);

    }


    /***
     * 绘制View
     * @param recycler
     * @param bottomItemPosition
     * @param viewInfoArrayList
     */
    private void drawView(RecyclerView.Recycler recycler, int bottomItemPosition, List<ItemViewInfo> viewInfoArrayList) {
        int layoutCount = viewInfoArrayList.size();
        final int startPos = bottomItemPosition - (layoutCount - 1);
        for (int i = 0; i < layoutCount; i++) {
            View view = recycler.getViewForPosition(startPos + i);
            ItemViewInfo layoutInfo = viewInfoArrayList.get(i);
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

    /***
     * 创建bean
     * @param bottomItemPosition
     * @param bottomItemVisibleHeight
     * @return
     */
    private ArrayList<ItemViewInfo> createItemViewInfoList(int bottomItemPosition, float bottomItemVisibleHeight) {
        ArrayList<ItemViewInfo> viewInfoArrayList = new ArrayList<>();
        int remainSpace = getVerticalSpace() - mItemViewHeight;
        final float offsetPercentRelativeToItemView = bottomItemVisibleHeight * 1.0f / mItemViewHeight;
        for (int i = bottomItemPosition - 1, j = 1; i >= 0; i--, j++) {
            double maxOffset = (getVerticalSpace() - mItemViewHeight) / 2 * Math.pow(0.9, j);
            int top = (int) (remainSpace - offsetPercentRelativeToItemView * maxOffset);
            float scaleXY = (float) (Math.pow(mScale, j - 1) * (1 - offsetPercentRelativeToItemView * (1 - mScale)));
            ItemViewInfo info = new ItemViewInfo(top, scaleXY);
            viewInfoArrayList.add(0, info);
            remainSpace = (int) (remainSpace - maxOffset);
            if (remainSpace <= 0) {
                info.setTop((int) (remainSpace + maxOffset));
                break;
            }
        }
        return viewInfoArrayList;
    }

    /**
     * 获取RecyclerView的显示高度
     */
    public int getVerticalSpace() {
        return getHeight() - getPaddingTop() - getPaddingBottom();
    }

    /**
     * 获取RecyclerView的显示宽度
     */
    public int getHorizontalSpace() {
        return getWidth() - getPaddingLeft() - getPaddingRight();
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
