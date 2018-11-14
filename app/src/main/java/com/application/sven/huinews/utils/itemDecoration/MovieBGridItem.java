package com.application.sven.huinews.utils.itemDecoration;

import android.content.Context;
import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.application.sven.huinews.utils.CommonUtils;
import com.yanyusong.y_divideritemdecoration.Y_Divider;
import com.yanyusong.y_divideritemdecoration.Y_DividerBuilder;
import com.yanyusong.y_divideritemdecoration.Y_DividerItemDecoration;

/**
 * Created by sfy. on 2018/5/8 0008.
 */

public class MovieBGridItem extends Y_DividerItemDecoration {

    public MovieBGridItem(Context context) {
        super(context);
    }

    @Override
    public Y_Divider getDivider(int itemPosition) {

        Y_Divider divider = null;

        switch (itemPosition % 2) {
            case 0:
                //每一行第一个显示rignt和bottom
                divider = new Y_DividerBuilder()
                        .setRightSideLine(true, 0xffffffff, 2.5f, 0, 0)
                        .setBottomSideLine(true, 0xffffffff, 5, 0, 0)
                        .create();
                break;
            case 1:
                //第二个显示Left和bottom
                divider = new Y_DividerBuilder()
                        .setLeftSideLine(true, 0xffffffff, 2.5f, 0, 0)
                        .setBottomSideLine(true, 0xffffffff, 5, 0, 0)
                        .create();
                break;
            default:
                break;
        }
        return divider;
    }
}
