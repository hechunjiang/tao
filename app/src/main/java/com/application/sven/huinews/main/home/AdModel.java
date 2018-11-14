package com.application.sven.huinews.main.home;

import com.qq.e.ads.nativ.NativeExpressADView;

import java.io.Serializable;
import java.util.List;

/**
 * auther: sunfuyi
 * data: 2018/6/7
 * effect:
 */
public class AdModel implements Serializable {
    private NativeExpressADView mViews;
    private String type;

    public NativeExpressADView getmViews() {
        return mViews;
    }

    public void setmViews(NativeExpressADView mViews) {
        this.mViews = mViews;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
