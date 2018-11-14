package com.application.sven.huinews.entity.response;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

/**
 * auther: sunfuyi
 * data: 2018/7/20
 * effect:
 */
public class BookShareResponse extends BaseResponse {
    private BookShare data;

    public BookShare getData() {
        return data;
    }

    public void setData(BookShare data) {
        this.data = data;
    }

    public static class BookShare implements Serializable {

        /**
         * title : 雪中悍刀行
         * content : 江湖是一张珠帘。 大人物小人物，是珠子，大故事小故事，是串线。 情义二字，则是那些珠子的精气神。 ———— 开始收官中。 最终章将以那一声“小二上酒”结尾
         * img : http://www.nr6h.cn/uploads/20180613/f8bbdbd796bb4201b5925a687b89c872.png
         * url : http://www.baidu.com
         */

        private String title;
        private String content;
        private String img;
        private String url;


        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getImg() {
            return img;
        }

        public void setImg(String img) {
            this.img = img;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }
}
