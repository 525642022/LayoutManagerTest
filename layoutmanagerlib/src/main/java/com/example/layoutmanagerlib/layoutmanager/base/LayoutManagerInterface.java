package com.example.layoutmanagerlib.layoutmanager.base;

import android.support.v7.widget.RecyclerView;

import java.util.List;

public interface LayoutManagerInterface<T> {
    /***
     * 初始化绘制参数
     * @param recycler
     */
    abstract  void initItemView(RecyclerView.Recycler recycler);

    /***
     * 创建绘制界面所需要的bean
     * @return
     */
    abstract List<T> createItemViewInfoList();

    /***
     * 实际绘制
     * @param recycler
     * @param state
     * @param itemViewList
     */
    abstract void drawView(RecyclerView.Recycler recycler, RecyclerView.State state, List<T> itemViewList);


}
