package com.application.sven.huinews.entity.request;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Sven on 2018/1/30.
 */

public class RegisterRequest extends BaseRequest {
    private String phone;
    @SerializedName("verify")
    private String registerCode;
    private String pass;
    @SerializedName("mobile_brand")
    private String mobileBrand;
    @SerializedName("nickname")
    private String nickName;
    @SerializedName("headimg")
    private String headIcon;
    @SerializedName("openid")
    private String openId; //wx
    private String sex;

    private String unionid;

    @SerializedName("f_invit_code")
    private String invitCode;

    public String getUnionid() {
        return unionid;
    }

    public void setUnionid(String unionid) {
        this.unionid = unionid;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getRegisterCode() {
        return registerCode;
    }

    public void setRegisterCode(String registerCode) {
        this.registerCode = registerCode;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getMobileBrand() {
        return mobileBrand;
    }

    public void setMobileBrand(String mobileBrand) {
        this.mobileBrand = mobileBrand;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getHeadIcon() {
        return headIcon;
    }

    public void setHeadIcon(String headIcon) {
        this.headIcon = headIcon;
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

    public String getInvitCode() {
        return invitCode;
    }

    public void setInvitCode(String invitCode) {
        this.invitCode = invitCode;
    }
}
