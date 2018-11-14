package com.application.sven.huinews.entity.request;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Sven on 2018/2/1.
 */

public class BindWxRequest extends BaseRequest {
    @SerializedName("nickname")
    private Object userName;
    @SerializedName("headimg")
    private Object headImg;
    @SerializedName("openid")
    private Object openId;
    private Object sex;
    private String unionid;

    public String getUnionid() {
        return unionid;
    }

    public void setUnionid(String unionid) {
        this.unionid = unionid;
    }

    public Object getUserName() {
        return userName;
    }

    public void setUserName(Object userName) {
        this.userName = userName;
    }

    public Object getHeadImg() {
        return headImg;
    }

    public void setHeadImg(Object headImg) {
        this.headImg = headImg;
    }

    public Object getOpenId() {
        return openId;
    }

    public void setOpenId(Object openId) {
        this.openId = openId;
    }

    public Object getSex() {
        return sex;
    }

    public void setSex(Object sex) {
        this.sex = sex;
    }


    @Override
    public String toString() {
        return "BindWxRequest{" +
                "userName=" + userName +
                ", headImg=" + headImg +
                ", openId=" + openId +
                ", sex=" + sex +
                ", unionid='" + unionid + '\'' +
                '}';
    }
}
