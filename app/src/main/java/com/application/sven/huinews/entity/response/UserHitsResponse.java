package com.application.sven.huinews.entity.response;

import java.io.Serializable;

/**
 * auther: sunfuyi
 * data: 2018/6/2
 * effect:
 */
public class UserHitsResponse extends BaseResponse {
    private Data data;

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "UserHitsResponse{" +
                "data=" + data +
                '}';
    }

    public static class Data implements Serializable {

        /**
         * readType : oneRed
         * redMsg : 1
         * newNews : false
         * unTisk : true
         */

        private String readType;
        private int redMsg;
        private boolean newNews;
        private boolean unTisk;

        public String getReadType() {
            return readType;
        }

        public void setReadType(String readType) {
            this.readType = readType;
        }

        public int getRedMsg() {
            return redMsg;
        }

        public void setRedMsg(int redMsg) {
            this.redMsg = redMsg;
        }

        public boolean isNewNews() {
            return newNews;
        }

        public void setNewNews(boolean newNews) {
            this.newNews = newNews;
        }

        public boolean isUnTisk() {
            return unTisk;
        }

        public void setUnTisk(boolean unTisk) {
            this.unTisk = unTisk;
        }

        @Override
        public String toString() {
            return "Data{" +
                    "readType='" + readType + '\'' +
                    ", redMsg=" + redMsg +
                    ", newNews=" + newNews +
                    ", unTisk=" + unTisk +
                    '}';
        }
    }
}
