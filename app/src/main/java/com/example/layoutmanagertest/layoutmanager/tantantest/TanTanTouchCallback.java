package com.example.layoutmanagertest.layoutmanager.tantantest;

import android.graphics.Canvas;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;

import com.example.layoutmanagertest.R;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.List;

public class TanTanTouchCallback extends ItemTouchHelper.SimpleCallback {
    protected RecyclerView mRv;
    protected List mDatas;
    protected RecyclerView.Adapter mAdapter;

    public TanTanTouchCallback(RecyclerView rv, RecyclerView.Adapter adapter, List datas) {
        super(0, ItemTouchHelper.DOWN | ItemTouchHelper.UP | ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT);
        mRv = rv;
        mAdapter = adapter;
        mDatas = datas;
    }

    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
        return false;
    }

    //设置移除阈值
    public float getThreshold(RecyclerView.ViewHolder viewHolder) {
        return mRv.getWidth() * 0.5f;
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
        //实现条目的循环
        Object remove = mDatas.remove(viewHolder.getLayoutPosition());
        mDatas.add(0, remove);
        mAdapter.notifyDataSetChanged();
        //探探只是第一层加了rotate & alpha的操作
        //对rotate进行复位
        viewHolder.itemView.setRotation(0);
        if (viewHolder instanceof ViewHolder) {
            ViewHolder holder = (ViewHolder) viewHolder;
            holder.setAlpha(R.id.iv_love, 0);
            holder.setAlpha(R.id.iv_del, 0);
        }
    }

    @Override
    public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
        //先根据滑动的dxdy 算出现在动画的比例系数fraction
        double swipValue = Math.sqrt(dX * dX + dY * dY);
        double fraction = swipValue / getThreshold(viewHolder);
        //边界修正
        if (fraction > 1) {
            fraction = 1;
        }
        int childCount = recyclerView.getChildCount();
        //对每个ChildView 进行位移
        for (int i = 0; i < childCount; i++) {
            View child = recyclerView.getChildAt(i);
            //获取层数
            int level = childCount - i - 1;
            if (level > 0) {
                //说有层数都进行X方形的变形
                child.setScaleX((float) (1 - CardConfig.DEFAULT_SCALE * level + fraction * CardConfig.DEFAULT_SCALE));
                if (level < CardConfig.DEFAULT_COUNT - 1) {
                    child.setScaleY((float) (1 - CardConfig.DEFAULT_SCALE * level + fraction * CardConfig.DEFAULT_SCALE));
                    child.setTranslationY((float) (CardConfig.DEFAULT_TRANS_Y * level - fraction * CardConfig.DEFAULT_TRANS_Y));
                } else {
                    //不进行动画
                }
            } else {
                //第一层添加的动画
                float xFraction = dX / getThreshold(viewHolder);
                //边界修正 最大为1
                if (xFraction > 1) {
                    xFraction = 1;
                } else if (xFraction < -1) {
                    xFraction = -1;
                }
                if (viewHolder instanceof ViewHolder) {
                    ViewHolder holder = (ViewHolder) viewHolder;
                    if (dX > 0) {
                        //露出左边，比心
                        holder.setAlpha(R.id.iv_love, xFraction);
                    } else if (dX < 0) {
                        //露出右边，滚犊子
                        holder.setAlpha(R.id.iv_del, -xFraction);
                    } else {
                        holder.setAlpha(R.id.iv_love, 0);
                        holder.setAlpha(R.id.iv_del, 0);
                    }
                }
            }
        }
    }
}


