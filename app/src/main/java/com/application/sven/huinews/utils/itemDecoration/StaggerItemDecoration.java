package com.application.sven.huinews.utils.itemDecoration;

import android.content.Context;
import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.application.sven.huinews.utils.CommonUtils;

/**
 * Created by sfy. on 2018/5/3 0003.
 */

public class StaggerItemDecoration extends RecyclerView.ItemDecoration {
    private Context mContext;

    public StaggerItemDecoration(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        outRect.left = CommonUtils.dip2px(mContext,5);
        outRect.right = CommonUtils.dip2px(mContext,5);
        if (parent.getChildLayoutPosition(view) % 2 == 0 ) {
            outRect.right = 0;
            outRect.left = 0;
        }
    }
}
