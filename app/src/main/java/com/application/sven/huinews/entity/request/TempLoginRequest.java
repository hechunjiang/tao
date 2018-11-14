package com.application.sven.huinews.entity.request;

import com.google.gson.annotations.SerializedName;

/**
 * auther: sunfuyi
 * data: 2018/5/14
 * effect:
 */
public class TempLoginRequest extends BaseRequest {
    @SerializedName("mobile_brand")
    private String mobileBrand;

    public String getMobileBrand() {
        return mobileBrand;
    }

    public void setMobileBrand(String mobileBrand) {
        this.mobileBrand = mobileBrand;
    }

}
