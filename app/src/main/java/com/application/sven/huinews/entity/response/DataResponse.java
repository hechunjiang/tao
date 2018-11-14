package com.application.sven.huinews.entity.response;

/**
 * auther: sunfuyi
 * data: 2018/5/15
 * effect:
 */
public class DataResponse extends BaseResponse {
    private String data;

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "DataResponse{" +
                "data='" + data + '\'' +
                '}';
    }
}
