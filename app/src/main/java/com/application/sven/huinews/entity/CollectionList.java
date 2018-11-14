package com.application.sven.huinews.entity;

import java.io.Serializable;
import java.util.List;

/**
 * auther: sunfuyi
 * data: 2018/5/24
 * effect:
 */
public class CollectionList implements Serializable {

    /**
     * is_more : false
     * lists : [{"id":107249,"video_cover":"http://static2.quduopai.cn/videosnapshot/56fb9a71543e271192b5cac118567775b35de170/00006.jpg","title":"浅浅自我安慰夜华炼丹无大碍，夜华假装无事淡定喝茶","play_count":0,"create_time":"2018-05-24","a_star":""}]
     */

    private boolean is_more;
    private List<ListsBean> lists;

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
         * id : 107249
         * video_cover : http://static2.quduopai.cn/videosnapshot/56fb9a71543e271192b5cac118567775b35de170/00006.jpg
         * title : 浅浅自我安慰夜华炼丹无大碍，夜华假装无事淡定喝茶
         * play_count : 0
         * create_time : 2018-05-24
         * a_star :
         */

        private int id;
        private String video_cover;
        private String m_img;
        private String title;
        private Object play_count;
        private String create_time;
        private String a_star;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getVideo_cover() {
            return video_cover;
        }

        public void setVideo_cover(String video_cover) {
            this.video_cover = video_cover;
        }

        public String getM_img() {
            return m_img;
        }

        public void setM_img(String m_img) {
            this.m_img = m_img;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public Object getPlay_count() {
            return play_count;
        }

        public void setPlay_count(Object play_count) {
            this.play_count = play_count;
        }

        public String getCreate_time() {
            return create_time;
        }

        public void setCreate_time(String create_time) {
            this.create_time = create_time;
        }

        public String getA_star() {
            return a_star;
        }

        public void setA_star(String a_star) {
            this.a_star = a_star;
        }
    }
}
