package com.application.sven.huinews.entity.response;

import com.google.gson.annotations.SerializedName;

/**
 * auther: sunfuyi
 * data: 2018/6/1
 * effect:
 */
public class VideoTaskResponse extends BaseResponse {
    private Data data;

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public static class Data {
        @SerializedName("gold_flag")
        private int count;
        @SerializedName("count")
        private int mCount;

        public int getmCount() {
            return mCount;
        }

        public void setmCount(int mCount) {
            this.mCount = mCount;
        }

        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
        }
    }

}
