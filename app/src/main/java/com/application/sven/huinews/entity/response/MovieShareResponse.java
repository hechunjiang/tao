package com.application.sven.huinews.entity.response;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

/**
 * auther: sunfuyi
 * data: 2018/6/13
 * effect:
 */
public class MovieShareResponse extends BaseResponse {


    private Data data;

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public static class Data implements Serializable {
        /**
         * title : 奔跑吧兄弟第6季
         * content :  兄弟团再度集结跑进联合国！邓超自创“邓氏英文”洗脑力max，化身按摩技师尴尬圆场；“拍腿起舞”考验极限反应力，99连击鹿晗精神崩溃；陈赫打嗝唱歌介绍大红袍，耿直放话不认识杨颖；郑恺十级英文获专业人士点赞，Baby清唱英文歌为唱功正名；“晨赫”母子翻脸，大黑牛怒怼赤赤；王祖蓝颜值PK碾压鹿晗可还行？
         * img : http://www.nr6h.cn/uploads/20180523/500cd67dbe95f749d3028a047133e2ef.png
         * url : http://t.cn/RBJ6OsY
         */

        private String title;
        private String content;
        private String img;
        private String url;

        public static Data objectFromData(String str, String key) {

            try {
                JSONObject jsonObject = new JSONObject(str);

                return new Gson().fromJson(jsonObject.getString(str), Data.class);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return null;
        }

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
