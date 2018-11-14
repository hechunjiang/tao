package com.application.sven.huinews.entity.request;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Sven on 2018/2/3.
 */

public class FindPassRequest extends BaseRequest {
    private String phone;
    private String pass;
    @SerializedName("verify")
    private String code;

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
