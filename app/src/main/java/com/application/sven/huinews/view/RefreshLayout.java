package com.application.sven.huinews.view;

import android.content.Context;
import android.util.AttributeSet;

import com.application.sven.huinews.R;
import com.scwang.smartrefresh.header.MaterialHeader;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.footer.BallPulseFooter;

/**
 * auther: sunfuyi
 * data: 2018/5/15
 * effect:
 */
public class RefreshLayout extends SmartRefreshLayout {
    private MaterialHeader mMaterialHeader;
    private BallPulseFooter mBallPulseFooter;

    public RefreshLayout(Context context) {
        super(context);
        init(context);
    }

    public RefreshLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public RefreshLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }
    private void init(Context context){
        setEnableRefresh(true);
        setEnableLoadmore(true);
        mMaterialHeader = new MaterialHeader(context);
        mMaterialHeader.setColorSchemeColors(context.getResources().getColor(R.color.color_def));
        setRefreshHeader(mMaterialHeader);
        mBallPulseFooter = new BallPulseFooter(context);
        mBallPulseFooter.setIndicatorColor(context.getResources().getColor(R.color.color_def));
        mBallPulseFooter.setAnimatingColor(context.getResources().getColor(R.color.color_def));
        mBallPulseFooter.setNormalColor(context.getResources().getColor(R.color.color_def));
        setRefreshFooter(mBallPulseFooter);
    }
}
