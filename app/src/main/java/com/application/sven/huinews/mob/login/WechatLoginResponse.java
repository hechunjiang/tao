package com.application.sven.huinews.mob.login;

import android.os.Parcel;
import android.os.Parcelable;

import com.application.sven.huinews.config.Constant;
import com.google.gson.annotations.SerializedName;

/**
 * auther: sunfuyi
 * data: 2018/5/14
 * effect:
 */
public class WechatLoginResponse implements Parcelable {
    @SerializedName(Constant.WX_LOGIN_KEY_OPEN_ID)
    private String openId;
    @SerializedName(Constant.WX_LOGIN_KEY_SEX)
    private String sex;
    @SerializedName(Constant.WX_LOGIN_KEY_USER_ICON)
    private String userIcon;
    @SerializedName(Constant.WX_LOGIN_KEY_USER_NAME)
    private String userName;
    @SerializedName(Constant.WX_LOGIN_KEY_CODE)
    private int code;
    @SerializedName(Constant.WX_LOGIN_KEY_UNIONID)
    private String unionid;


    public String getUnionid() {
        return unionid;
    }

    public void setUnionid(String unionid) {
        this.unionid = unionid;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getUserIcon() {
        return userIcon;
    }

    public void setUserIcon(String userIcon) {
        this.userIcon = userIcon;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.openId);
        dest.writeString(this.sex);
        dest.writeString(this.userIcon);
        dest.writeString(this.userName);
        dest.writeInt(this.code);
    }

    public WechatLoginResponse() {
    }

    protected WechatLoginResponse(Parcel in) {
        this.openId = in.readString();
        this.sex = in.readString();
        this.userIcon = in.readString();
        this.userName = in.readString();
        this.code = in.readInt();
    }

    public static final Parcelable.Creator<WechatLoginResponse> CREATOR = new Parcelable.Creator<WechatLoginResponse>() {
        @Override
        public WechatLoginResponse createFromParcel(Parcel source) {
            return new WechatLoginResponse(source);
        }

        @Override
        public WechatLoginResponse[] newArray(int size) {
            return new WechatLoginResponse[size];
        }
    };


    @Override
    public String toString() {
        return "WechatLoginResponse{" +
                "openId='" + openId + '\'' +
                ", sex='" + sex + '\'' +
                ", userIcon='" + userIcon + '\'' +
                ", userName='" + userName + '\'' +
                ", code=" + code +
                '}';
    }
}
