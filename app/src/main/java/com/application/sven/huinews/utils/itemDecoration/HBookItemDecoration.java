package com.application.sven.huinews.utils.itemDecoration;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

/**
 * auther: sunfuyi
 * data: 2018/7/12
 * effect:
 */
public class HBookItemDecoration extends RecyclerView.ItemDecoration {

    @Override
    public void getItemOffsets(Rect outRect, View child, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, child, parent, state);

        outRect.left = 20;
        outRect.right = 20;

    }
}
