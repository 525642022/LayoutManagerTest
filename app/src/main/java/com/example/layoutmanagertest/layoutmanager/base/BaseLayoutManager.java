package com.example.layoutmanagertest.layoutmanager.base;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import java.util.List;

public abstract class BaseLayoutManager<T> extends RecyclerView.LayoutManager implements  LayoutManagerInterface<T> {
    @Override
    public RecyclerView.LayoutParams generateDefaultLayoutParams() {
        return new RecyclerView.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
    }

    @Override
    public void onLayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state) {
        if (getItemCount() <= 0 || state.isPreLayout()) {
            return;
        }
        //移除所有View
        removeAndRecycleAllViews(recycler);
        initItemView(recycler);
        List<T> itemViewList =  createItemViewInfoList();
        // 在布局之前，将所有的子 View 先 Detach 掉，放入到 Scrap 缓存中
        detachAndScrapAttachedViews(recycler);
        drawView(recycler, state, itemViewList);
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
}
