package com.application.sven.huinews.config;

import com.application.sven.huinews.main.preemption.adapter.PreeAdapter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;



/**
 * Created by sfy. on 2018/5/8 0008.
 */

public class PreeBean  implements Serializable {
    private int viewType;
    private String viewTitle;
    private int iconRes;


    public PreeBean(int viewType, String viewTitle, int iconRes) {
        this.viewType = viewType;
        this.viewTitle = viewTitle;
        this.iconRes = iconRes;
    }

    public String getViewTitle() {
        return viewTitle;
    }

    public void setViewTitle(String viewTitle) {
        this.viewTitle = viewTitle;
    }

    public int getViewType() {
        return viewType;
    }

    public void setViewType(int viewType) {
        this.viewType = viewType;
    }


}
