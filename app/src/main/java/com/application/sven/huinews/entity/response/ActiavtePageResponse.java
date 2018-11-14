package com.application.sven.huinews.entity.response;

import java.util.List;

/**
 * Created by sfy. on 2018/4/11 0011.
 */

public class ActiavtePageResponse extends BaseResponse {

    private List<Data> data;

    public List<Data> getData() {
        return data;
    }

    public void setData(List<Data> data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "ActiavtePageResponse{" +
                "data=" + data +
                '}';
    }

    public static class Data {

        /**
         * images : http://android.news.88acca.com/static/img/banner/bannerb.png
         * jump_url : http://www.aizhuifan.cn/tqsf/index.html
         */

        private String images;
        private String jump_url;

        public String getImages() {
            return images;
        }

        public void setImages(String images) {
            this.images = images;
        }

        public String getJump_url() {
            return jump_url;
        }

        public void setJump_url(String jump_url) {
            this.jump_url = jump_url;
        }
    }
}
