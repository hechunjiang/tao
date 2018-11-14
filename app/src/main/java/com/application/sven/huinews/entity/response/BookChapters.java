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
public class BookChapters implements Serializable {


    /**
     * lists : [{"id":8,"chapter_name":"第一章","is_gold":1},{"id":17,"chapter_name":" 第二章 白狐儿脸","is_gold":1},{"id":20,"chapter_name":"第三章 两个酒窝","is_gold":2},{"id":21,"chapter_name":"第四章 去那座山摘山楂","is_gold":2},{"id":22,"chapter_name":"第五章 天下第一美人","is_gold":2},{"id":23,"chapter_name":"第六章 走一个","is_gold":2}]
     * is_more : false
     */

    private boolean is_more;
    private int chapter_number;
    private List<ListsBean> lists;


    public boolean isIs_more() {
        return is_more;
    }

    public void setIs_more(boolean is_more) {
        this.is_more = is_more;
    }

    public int getChapter_number() {
        return chapter_number;
    }

    public void setChapter_number(int chapter_number) {
        this.chapter_number = chapter_number;
    }

    public List<ListsBean> getLists() {
        return lists;
    }

    public void setLists(List<ListsBean> lists) {
        this.lists = lists;
    }

    public static class ListsBean implements Serializable{
        /**
         * id : 8
         * chapter_name : 第一章
         * is_gold : 1
         */

        private int id;
        private String chapter_name;
        private int is_gold;


        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getChapter_name() {
            return chapter_name;
        }

        public void setChapter_name(String chapter_name) {
            this.chapter_name = chapter_name;
        }

        public int getIs_gold() {
            return is_gold;
        }

        public void setIs_gold(int is_gold) {
            this.is_gold = is_gold;
        }

        @Override
        public String toString() {
            return "ListsBean{" +
                    "id=" + id +
                    ", chapter_name='" + chapter_name + '\'' +
                    ", is_gold=" + is_gold +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "BookChapters{" +
                "is_more=" + is_more +
                ", chapter_number=" + chapter_number +
                ", lists=" + lists +
                '}';
    }
}
