package com.application.sven.huinews.config;

import java.io.Serializable;

/**
 * Created by sfy. on 2018/5/9 0009.
 */

public class HomeBean implements Serializable {
    private int viewType;

    public HomeBean(int viewType) {
        this.viewType = viewType;
    }

    public int getViewType() {
        return viewType;
    }

    public void setViewType(int viewType) {
        this.viewType = viewType;
    }
}
