package com.application.sven.huinews.utils;

import android.support.annotation.NonNull;
import android.support.v4.view.ViewPager;
import android.view.View;

/**
 * Created by sfy. on 2018/5/8 0008.
 */

public class CardTransformer implements ViewPager.PageTransformer {
    private static final float MAX_SCALE = 1.1f;
    private static final float MIN_SCALE = 1.0f;//0.85f

    @Override
    public void transformPage(View page, float position) {
        if (position <= 1) {
            float scaleFactor = MIN_SCALE + (1 - Math.abs(position)) * (MAX_SCALE - MIN_SCALE);
            page.setScaleX(scaleFactor);  //缩放效果
            if (position > 0) {
                page.setTranslationX(-scaleFactor * 2);
            } else if (position < 0) {
                page.setTranslationX(scaleFactor * 2);
            }
            page.setScaleY(scaleFactor);
        } else {
            page.setScaleX(MIN_SCALE);
            page.setScaleY(MIN_SCALE);
        }
    }
}
