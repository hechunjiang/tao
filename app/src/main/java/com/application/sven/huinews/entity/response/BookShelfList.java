package com.application.sven.huinews.entity.response;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.List;

/**
 * auther: sunfuyi
 * data: 2018/7/13
 * effect:
 */
public class BookShelfList implements Serializable {

    /**
     * lists : [{"id":1,"title":"雪中悍刀行","author":"烽火戏诸侯","description":"江湖是一张珠帘。 大人物小人物，是珠子，大故事小故事，是串线。 情义二字，则是那些珠子的精气神。 \u2014\u2014\u2014\u2014 开始收官中。 最终章将以那一声\u201c小二上酒\u201d结尾","pic":"http://www.nr6h.cn/uploads/20180613/f8bbdbd796bb4201b5925a687b89c872.png","book_status":1,"chapter_number":6}]
     * is_more : false
     */

    private boolean is_more;
    private List<ListsBean> lists;

    public static BookShelfList objectFromData(String str, String key) {

        try {
            JSONObject jsonObject = new JSONObject(str);

            return new Gson().fromJson(jsonObject.getString(str), BookShelfList.class);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }

    public boolean isIs_more() {
        return is_more;
    }

    public void setIs_more(boolean is_more) {
        this.is_more = is_more;
    }

    public List<ListsBean> getLists() {
        return lists;
    }

    public void setLists(List<ListsBean> lists) {
        this.lists = lists;
    }

    public static class ListsBean {
        /**
         * id : 1
         * title : 雪中悍刀行
         * author : 烽火戏诸侯
         * description : 江湖是一张珠帘。 大人物小人物，是珠子，大故事小故事，是串线。 情义二字，则是那些珠子的精气神。 ———— 开始收官中。 最终章将以那一声“小二上酒”结尾
         * pic : http://www.nr6h.cn/uploads/20180613/f8bbdbd796bb4201b5925a687b89c872.png
         * book_status : 1
         * chapter_number : 6
         */

        private int id;
        private String title;
        private String author;
        private String description;
        private String pic;
        private int book_status;
        private int chapter_number;

        public static ListsBean objectFromData(String str, String key) {

            try {
                JSONObject jsonObject = new JSONObject(str);

                return new Gson().fromJson(jsonObject.getString(str), ListsBean.class);
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

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getAuthor() {
            return author;
        }

        public void setAuthor(String author) {
            this.author = author;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getPic() {
            return pic;
        }

        public void setPic(String pic) {
            this.pic = pic;
        }

        public int getBook_status() {
            return book_status;
        }

        public void setBook_status(int book_status) {
            this.book_status = book_status;
        }

        public int getChapter_number() {
            return chapter_number;
        }

        public void setChapter_number(int chapter_number) {
            this.chapter_number = chapter_number;
        }
    }
}
