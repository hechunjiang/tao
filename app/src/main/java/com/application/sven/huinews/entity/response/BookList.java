package com.application.sven.huinews.entity.response;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.List;

/**
 * auther: sunfuyi
 * data: 2018/7/10
 * effect:
 */
public class BookList implements Serializable {
    /**
     * id : 36
     * sub_name : 顶部轮播
     * icon : http://www.nr6h.cn/uploads/20180508/500bb9322f638ce367e68b932b632272.png
     * layout : x_top_banner
     * show_more_type : none
     * lists : [{"id":1,"title":"雪中悍刀行","author":"烽火戏诸侯","description":"江湖是一张珠帘。 大人物小人物，是珠子，大故事小故事，是串线。 情义二字，则是那些珠子的精气神。 \u2014\u2014\u2014\u2014 开始收官中。 最终章将以那一声\u201c小二上酒\u201d结尾","pic":"http://www.nr6h.cn/uploads/20180629/1adc2154520b9a154c253edbf7c17e09.png","book_status":1,"chapter_number":"第2章","cay_name":"武侠仙侠"},{"id":2,"title":"圣墟 ","author":"辰东","description":"在破败中崛起，在寂灭中复苏。\r\n　　沧海成尘，雷电枯竭，那一缕幽雾又一次临近大地，世间的枷锁被打开了，一个全新的世界就此揭开神秘的一角\u2026\u2026","pic":"http://www.nr6h.cn/uploads/20180629/971235e9b0b5edc8c4bb02ac4d26befe.png","book_status":1,"chapter_number":"第1章","cay_name":"奇幻玄幻"},{"id":5,"title":"重生完美时代","author":"公子不歌","description":"命运把李牧一脚踹回了2001年。置身于这个缔造了无数巨子的完美时代，他欣喜的拍拍屁股，起身踏上了一条自己从未走过的人生之路。这个时代给了他无数通往成功的阳关道，他却只想走出过程最精彩、最肆意、同样也是最彪悍的那一条。\u201c他不是巨子，他缔造巨子\u201d","pic":"http://www.nr6h.cn/uploads/20180629/08cb753c7e65b4656306399afc293f4c.png","book_status":1,"chapter_number":"第1章","cay_name":"都市异能"}]
     */

    private int id;
    private String sub_name;
    private String icon;
    private String layout;
    private String show_more_type;
    private long now_time;
    private List<ListsBean> lists;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSub_name() {
        return sub_name;
    }

    public void setSub_name(String sub_name) {
        this.sub_name = sub_name;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getLayout() {
        return layout;
    }

    public void setLayout(String layout) {
        this.layout = layout;
    }

    public String getShow_more_type() {
        return show_more_type;
    }

    public void setShow_more_type(String show_more_type) {
        this.show_more_type = show_more_type;
    }

    public long getNow_time() {
        return now_time;
    }

    public void setNow_time(long now_time) {
        this.now_time = now_time;
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
         * pic : http://www.nr6h.cn/uploads/20180629/1adc2154520b9a154c253edbf7c17e09.png
         * book_status : 1
         * chapter_number : 第2章
         * cay_name : 武侠仙侠
         */

        private int id;
        private String title;
        private String author;
        private String description;
        private String pic;
        private int book_status;
        private String chapter_number;
        private String cay_name;

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

        public String getChapter_number() {
            return chapter_number;
        }

        public void setChapter_number(String chapter_number) {
            this.chapter_number = chapter_number;
        }

        public String getCay_name() {
            return cay_name;
        }

        public void setCay_name(String cay_name) {
            this.cay_name = cay_name;
        }
    }
}
