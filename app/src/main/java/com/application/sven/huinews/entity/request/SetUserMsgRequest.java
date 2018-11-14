package com.application.sven.huinews.entity.request;

/**
 * auther: sunfuyi
 * data: 2018/7/20
 * effect:
 */
public class SetUserMsgRequest extends BaseRequest {
    private String lat;
    private String lng;
    private String device_tokens;
    private int is_deduction; //是否设置自动扣除钻石 1 ->否 2->是

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLng() {
        return lng;
    }

    public void setLng(String lng) {
        this.lng = lng;
    }

    public String getDevice_tokens() {
        return device_tokens;
    }

    public void setDevice_tokens(String device_tokens) {
        this.device_tokens = device_tokens;
    }

    public int getIs_deduction() {
        return is_deduction;
    }

    public void setIs_deduction(int is_deduction) {
        this.is_deduction = is_deduction;
    }
}
