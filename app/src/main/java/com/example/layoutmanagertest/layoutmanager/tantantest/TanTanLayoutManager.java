package com.example.layoutmanagertest.layoutmanager.tantantest;


import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

public class TanTanLayoutManager extends RecyclerView.LayoutManager {

    @Override
    public RecyclerView.LayoutParams generateDefaultLayoutParams() {
        return new RecyclerView.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
    }

    @Override
    public void onLayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state) {
        // 先移除所有view
        removeAllViews();
        // 在布局之前，将所有的子 View 先 Detach 掉，放入到 Scrap 缓存中
        detachAndScrapAttachedViews(recycler);
        //获取条目数量
        int itemCount = getItemCount();
        if (itemCount < 1) {
            return;
        }
        //定义边界 防止数组越界
        int lastPosition;
        if (itemCount < CardConfig.DEFAULT_COUNT) {
            lastPosition = 0;
        } else {
            lastPosition = itemCount - CardConfig.DEFAULT_COUNT;
        }
        //从看见层的底层一次网上添加
        for (int position = lastPosition; position < itemCount; position++) {
            View view = recycler.getViewForPosition(position);
            addView(view);
            measureChildWithMargins(view, 0, 0);
            int widthSpace = getWidth() - getDecoratedMeasuredWidth(view);
            int heightSpace = getHeight() - getDecoratedMeasuredHeight(view);
            //将childView设置水平居中
            layoutDecoratedWithMargins(view, widthSpace / 2, heightSpace / 4,
                    widthSpace / 2 + getDecoratedMeasuredWidth(view),
                    heightSpace / 4+ getDecoratedMeasuredHeight(view));
            //获取当前的层数
            int level = itemCount - position - 1;
            //设置每层的Scale和translationY
            if(level>0){
                //不是第一层
                //设置每一层X方向的缩小
                view.setScaleX(1-CardConfig.DEFAULT_SCALE*level);
                if(level<CardConfig.DEFAULT_COUNT-1){
                    //Y需要缩小的和位移
                    view.setTranslationY(CardConfig.DEFAULT_TRANS_Y * level);
                    view.setScaleY(1 - CardConfig.DEFAULT_SCALE * level);
                }else{
                    //不需要缩小和位移只需要和前一层保持一致
                    view.setTranslationY(CardConfig.DEFAULT_TRANS_Y * (level - 1));
                    view.setScaleY(1 - CardConfig.DEFAULT_SCALE * (level - 1));
                }
            }
        }
    }
}
