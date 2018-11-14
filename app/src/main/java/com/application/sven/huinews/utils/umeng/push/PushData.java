package com.application.sven.huinews.utils.umeng.push;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * auther: sunfuyi
 * data: 2018/8/2
 * effect:
 */
public class PushData {

    /**
     * rid : 40270
     * pid : 0
     * url :
     * title :
     */

    private int rid;
    private int pid;
    private String url;
    private String title;



    public int getRid() {
        return rid;
    }

    public void setRid(int rid) {
        this.rid = rid;
    }

    public int getPid() {
        return pid;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
