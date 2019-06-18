package com.example.layoutmanagertest.layoutmanager.tantantest;

import android.content.Context;
import android.util.TypedValue;


public class CardConfig {
    //屏幕上最多同时显示几个Item
    public static int DEFAULT_COUNT;

    //每一级的Scale
    public static float DEFAULT_SCALE;
    //每一级的translationY
    public static int DEFAULT_TRANS_Y;

    public static void initConfig(Context context) {
        DEFAULT_COUNT = 4;
        DEFAULT_SCALE = 0.05f;
        DEFAULT_TRANS_Y = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 15, context.getResources().getDisplayMetrics());
    }
}
