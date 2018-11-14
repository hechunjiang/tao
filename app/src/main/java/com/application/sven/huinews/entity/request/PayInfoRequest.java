package com.application.sven.huinews.entity.request;

/**
 * auther: sunfuyi
 * data: 2018/7/14
 * effect:
 */
public class PayInfoRequest extends BaseRequest {
    private String payModel;
    private int id;
    private String payFrom;
    private String channel;

    public String getPayModel() {
        return payModel;
    }

    public void setPayModel(String payModel) {
        this.payModel = payModel;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPayFrom() {
        return payFrom;
    }

    public void setPayFrom(String payFrom) {
        this.payFrom = payFrom;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }
}

