package com.application.sven.huinews.entity.response;

/**
 * Created by Administrator on 2018/3/8 0008.
 */

public class VideoShareUrlResponse extends BaseResponse {
    private Data data;

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public static class Data{
        private String url;

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }
}
