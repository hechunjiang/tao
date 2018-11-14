package com.application.sven.huinews.entity;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

/**
 * auther: sunfuyi
 * data: 2018/6/1
 * effect:
 */
public class FollowList implements Serializable {

    /**
     * user_id : 2132
     * headimg : http://wx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTLLZ4cK3hXg1txuQ5oNk7Uich3ibiaoMJ8N2R1loKPdS7yMYbcPB5UbvqLDDLs17mEXRklkCicrvhjicuw/132
     * nickname : 水电维修厨卫灯具安装砸墙管道疏通
     * create_time : 2018-06-01
     */

    private int user_id;
    private String headimg;
    private String nickname;
    private String create_time;


    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getHeadimg() {
        return headimg;
    }

    public void setHeadimg(String headimg) {
        this.headimg = headimg;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }
}
