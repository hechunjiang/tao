package com.application.sven.huinews.utils.itemDecoration;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import com.application.sven.huinews.utils.CommonUtils;
import com.application.sven.huinews.utils.LogUtil;
import com.application.sven.huinews.utils.ScreensUitls;
import com.application.sven.huinews.view.newread.utils.ScreenUtils;
import com.scwang.smartrefresh.layout.util.DensityUtil;
import com.yanyusong.y_divideritemdecoration.Y_Divider;
import com.yanyusong.y_divideritemdecoration.Y_DividerBuilder;
import com.yanyusong.y_divideritemdecoration.Y_DividerItemDecoration;

/**
 * auther: sunfuyi
 * data: 2018/7/10
 * effect:
 */
public class BookItemDecoration extends RecyclerView.ItemDecoration {
    private int space; //位移间距
    int screenWidth;
    private Context mContext;

    public BookItemDecoration(Context mContext) {
        this.mContext = mContext;
        screenWidth = ScreensUitls.getScreenWidth(mContext);
        int itemWidth = CommonUtils.dip2px(mContext, 95);
        int margin = CommonUtils.dip2px(mContext, 20);
        this.space = (screenWidth - margin * 2 - itemWidth * 3) / 6;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        if (parent.getChildAdapterPosition(view) % 3 == 0) {
            outRect.left = 0; //第一列左边贴边
        } else {
            if (parent.getChildAdapterPosition(view) % 3 == 1) {
                outRect.left = space;//第二列移动一个位移间距
            } else {
                outRect.left = space * 2;//由于第二列已经移动了一个间距,所以第三列要移动两个位移间距就能右边贴边,且item间距相等
            }
        }
        if (parent.getChildAdapterPosition(view) >= 3) {
            outRect.top = CommonUtils.dip2px(mContext, 20);
        } else {
            outRect.top = 0;
        }
    }

}
