package com.application.sven.huinews.main.home.adapter.viewholder;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.application.sven.huinews.R;
import com.application.sven.huinews.utils.CommonUtils;
import com.application.sven.huinews.utils.LogUtil;
import com.qq.e.ads.nativ.NativeExpressADView;
import com.qq.e.comm.pi.AdData;

/**
 * auther: sunfuyi
 * data: 2018/6/4
 * effect:
 */
public class HomeAdOneViewHolder extends RecyclerView.ViewHolder {
    private ViewGroup container;
    private TextView tv_content;
    private Context mContext;

    public HomeAdOneViewHolder(Context context, View v) {
        super(v);
        this.mContext = context;
        container = v.findViewById(R.id.express_ad_container);
        tv_content = v.findViewById(R.id.tv_content);
    }


    public void setTencentAds(NativeExpressADView adView) {
        // mAdViewPositionMap.put(adView, position); // 广告在列表中的位置是可以被更新的
        if (container.getChildCount() > 0 && container.getChildAt(0) == adView) {
            return;
        }
        if (container.getChildCount() > 0) {
            container.removeAllViews();
        }

        if (adView.getParent() != null) {
            ((ViewGroup) adView.getParent()).removeView(adView);
        }
        tv_content.setText(adView.getBoundData().getTitle());

        container.addView(adView);
        adView.render(); // 调用render方法后sdk才会开始展示广告*/
    }

    public void setTencentRightImg(NativeExpressADView adView) {
        if (container.getChildCount() > 0 && container.getChildAt(0) == adView) {
            return;
        }
        if (container.getChildCount() > 0) {
            container.removeAllViews();
        }

        if (adView.getParent() != null) {
            ((ViewGroup) adView.getParent()).removeView(adView);
        }
        tv_content.setText(adView.getBoundData().getTitle());

        container.addView(adView);
        adView.render(); // 调用render方法后sdk才会开始展示广告*/
    }

}
