package com.application.sven.huinews.utils.umeng.push;

import java.io.Serializable;

/**
 * Created by Administrator on 2018/3/7 0007.
 */

public class PushClickUrl implements Serializable {
    private String url;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return "PushClickUrl{" +
                "url='" + url + '\'' +
                '}';
    }
}
