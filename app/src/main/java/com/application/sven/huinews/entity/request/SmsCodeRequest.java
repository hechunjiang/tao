package com.application.sven.huinews.entity.request;

/**
 * Created by Sven on 2018/1/30.
 */

public class SmsCodeRequest extends BaseRequest {
    private String type;
    private String phone;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
