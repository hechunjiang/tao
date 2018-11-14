package com.application.sven.huinews.utils.itemDecoration;

import android.content.Context;
import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.application.sven.huinews.utils.CommonUtils;

/**
 * Created by sfy. on 2018/5/9 0009.
 */

public class MovieCountGridItem extends RecyclerView.ItemDecoration {
    private Context mContext;

    public MovieCountGridItem(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        int dp = CommonUtils.dip2px(mContext, 20);
        outRect.left = dp;
        outRect.bottom = dp;
        if (parent.getChildLayoutPosition(view) % 5 == 0) {
            outRect.left = dp;
        }

    }
}
