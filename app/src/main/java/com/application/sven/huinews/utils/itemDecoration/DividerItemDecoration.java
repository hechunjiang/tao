package com.application.sven.huinews.utils.itemDecoration;

import android.content.Context;

import com.yanyusong.y_divideritemdecoration.Y_Divider;
import com.yanyusong.y_divideritemdecoration.Y_DividerBuilder;
import com.yanyusong.y_divideritemdecoration.Y_DividerItemDecoration;

/**
 * auther: sunfuyi
 * data: 2018/5/18
 * effect:
 */
public class DividerItemDecoration extends Y_DividerItemDecoration {


    public DividerItemDecoration(Context context) {
        super(context);
    }

    @Override
    public Y_Divider getDivider(int itemPosition) {
        Y_Divider divider = null;
        switch (itemPosition % 3) {
            case 0:
            case 1:
                //每一行第一个和第二个显示rignt和bottom
                divider = new Y_DividerBuilder().setRightSideLine(true, 0xffffffff, 5, 0, 0).setBottomSideLine(true, 0xffffffff, 5, 0, 0).create();
                break;
            case 2:
                //最后一个只显示bottom
                divider = new Y_DividerBuilder().setBottomSideLine(true, 0xffffffff, 5, 0, 0).create();
                break;
            default:
                break;
        }
        return divider;
    }
}