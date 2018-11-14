package com.application.sven.huinews.entity.response;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.List;

/**
 * auther: sunfuyi
 * data: 2018/7/11
 * effect:
 */
public class BookStoreCate implements Serializable {

    /**
     * id : 1
     * name : 男生
     * searchMsg : [{"key":"type","data":[{"id":0,"name":"全部"},{"id":3,"name":"奇幻玄幻"},{"id":6,"name":"武侠仙侠"},{"id":9,"name":"都市异能"},{"id":10,"name":"历史穿越"}]},{"key":"is_end","data":[{"id":0,"name":"全部"},{"id":2,"name":"完结"},{"id":1,"name":"连载"}]},{"key":"sort","data":[{"id":0,"name":"排序"},{"id":1,"name":"热门"},{"id":2,"name":"人气"},{"id":3,"name":"新书"}]}]
     */

    private int id;
    private String name;
    private List<SearchMsgBean> searchMsg;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<SearchMsgBean> getSearchMsg() {
        return searchMsg;
    }

    public void setSearchMsg(List<SearchMsgBean> searchMsg) {
        this.searchMsg = searchMsg;
    }

    public static class SearchMsgBean implements Serializable {
        /**
         * key : type
         * data : [{"id":0,"name":"全部"},{"id":3,"name":"奇幻玄幻"},{"id":6,"name":"武侠仙侠"},{"id":9,"name":"都市异能"},{"id":10,"name":"历史穿越"}]
         */

        private String key;
        private List<DataBean> data;

        public static SearchMsgBean objectFromData(String str, String key) {

            try {
                JSONObject jsonObject = new JSONObject(str);

                return new Gson().fromJson(jsonObject.getString(str), SearchMsgBean.class);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return null;
        }

        public String getKey() {
            return key;
        }

        public void setKey(String key) {
            this.key = key;
        }

        public List<DataBean> getData() {
            return data;
        }

        public void setData(List<DataBean> data) {
            this.data = data;
        }

        public static class DataBean implements Serializable {
            /**
             * id : 0
             * name : 全部
             */

            private int id;
            private String name;

            public static DataBean objectFromData(String str, String key) {

                try {
                    JSONObject jsonObject = new JSONObject(str);

                    return new Gson().fromJson(jsonObject.getString(str), DataBean.class);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                return null;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }
        }
    }
}
