package com.application.sven.huinews.entity.request;

import com.google.gson.annotations.SerializedName;

/**
 * auther: sunfuyi
 * data: 2018/5/23
 * effect:
 */
public class LoginRequest extends BaseRequest {
    @SerializedName("phone")
    private String account;
    @SerializedName("pass")
    private String password;

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
