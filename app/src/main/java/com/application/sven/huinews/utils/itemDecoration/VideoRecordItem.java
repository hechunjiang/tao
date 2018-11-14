package com.application.sven.huinews.utils.itemDecoration;

import android.content.Context;
import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.application.sven.huinews.utils.CommonUtils;

/**
 * Created by sfy. on 2018/5/8 0008.
 */

public class VideoRecordItem extends RecyclerView.ItemDecoration {
    private Context context;

    public VideoRecordItem(Context context) {
        this.context = context;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        int childAdapterPosition = parent.getChildAdapterPosition(view);
        if (childAdapterPosition == 0) {
            outRect.right = CommonUtils.dip2px(context, 5);
        } else {
            outRect.right = CommonUtils.dip2px(context, 5);
        }
    }
}
