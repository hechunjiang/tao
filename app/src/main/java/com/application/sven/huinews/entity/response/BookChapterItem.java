package com.application.sven.huinews.entity.response;

import android.text.Html;


import java.io.Serializable;

/**
 * auther: sunfuyi
 * data: 2018/7/16
 * effect:
 */
public class BookChapterItem implements Serializable {

    /**
     * id : 24
     * book_id : 6
     * chapter_name : 序章 大荒
     * is_gold : 1
     * golds : 0
     * status : 2
     * content : content
     * pre : {"status":false,"chapter_id":0}
     * next : {"status":false,"chapter_id":0}
     */

    private int id;
    private int book_id;
    private String chapter_name;
    private int is_gold;
    private int golds;
    private int status;
    private String content;
    private PreBean pre;
    private NextBean next;
    private int show;
    private int left_diamond;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getBook_id() {
        return book_id;
    }

    public void setBook_id(int book_id) {
        this.book_id = book_id;
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

    public int getGolds() {
        return golds;
    }

    public void setGolds(int golds) {
        this.golds = golds;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getContent() {
        return Html.fromHtml(content).toString();
    }


    public void setContent(String content) {
        this.content = content;
    }

    public PreBean getPre() {
        return pre;
    }

    public void setPre(PreBean pre) {
        this.pre = pre;
    }

    public NextBean getNext() {
        return next;
    }

    public void setNext(NextBean next) {
        this.next = next;
    }

    public static class PreBean implements Serializable {
        /**
         * status : false
         * chapter_id : 0
         */

        private boolean status;
        private int chapter_id;


        public boolean isStatus() {
            return status;
        }

        public void setStatus(boolean status) {
            this.status = status;
        }

        public int getChapter_id() {
            return chapter_id;
        }

        public void setChapter_id(int chapter_id) {
            this.chapter_id = chapter_id;
        }
    }

    public static class NextBean implements Serializable {
        /**
         * status : false
         * chapter_id : 0
         */

        private boolean status;
        private int chapter_id;


        public boolean isStatus() {
            return status;
        }

        public void setStatus(boolean status) {
            this.status = status;
        }

        public int getChapter_id() {
            return chapter_id;
        }

        public void setChapter_id(int chapter_id) {
            this.chapter_id = chapter_id;
        }
    }

    public int getShow() {
        return show;
    }

    public void setShow(int show) {
        this.show = show;
    }

    public int getLeft_diamond() {
        return left_diamond;
    }

    public void setLeft_diamond(int left_diamond) {
        this.left_diamond = left_diamond;
    }
}
