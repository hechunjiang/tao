package com.application.sven.huinews.entity.response;

import java.io.Serializable;

/**
 * auther: sunfuyi
 * data: 2018/6/15
 * effect:
 */
public class VideoCountResponse extends BaseResponse {
    private Data data;

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public static class Data implements Serializable {
        private int count;

        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
        }
    }
}
