package com.application.sven.huinews.utils;

import android.view.View;

/**
 * auther: sunfuyi
 * data: 2018/5/10
 * effect:
 */
public class MeasureUtlis {

    public static int getViewHeight(View v) {
        int width = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        int height = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        v.measure(width, height);
        return v.getHeight();
    }


}
