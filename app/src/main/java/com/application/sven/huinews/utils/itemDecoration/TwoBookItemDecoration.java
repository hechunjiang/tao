package com.application.sven.huinews.utils.itemDecoration;

import android.content.Context;

import com.application.sven.huinews.utils.CommonUtils;
import com.yanyusong.y_divideritemdecoration.Y_Divider;
import com.yanyusong.y_divideritemdecoration.Y_DividerBuilder;
import com.yanyusong.y_divideritemdecoration.Y_DividerItemDecoration;

/**
 * auther: sunfuyi
 * data: 2018/7/10
 * effect:
 */

public class TwoBookItemDecoration extends Y_DividerItemDecoration {

    private Context mContext;

    public TwoBookItemDecoration(Context context) {
        super(context);
        this.mContext = context;
    }


    @Override
    public Y_Divider getDivider(int itemPosition) {
        Y_Divider divider = null;
        switch (itemPosition % 2) {
            case 0:
                //每一行第一个和第二个显示rignt和bottom
                divider = new Y_DividerBuilder().setRightSideLine(true, 0xffffffff, 20, 0, 0).setBottomSideLine(true, 0xffffffff, 20, 0, 0).create();
                break;
            case 1:
                //最后一个只显示bottom
                divider = new Y_DividerBuilder()
                        .setBottomSideLine(true, 0xffffffff, 20, 0, 0)
                        .create();
                break;
            default:
                break;
        }
        return divider;
    }
}
