package com.application.sven.huinews.entity;

import java.io.Serializable;
import java.util.List;

/**
 * auther: sunfuyi
 * data: 2018/5/25
 * effect:
 */
public class MovieHistory implements Serializable {

    /**
     * is_more : false
     * lists : [{"id":5,"m_name":"红海行动","m_img":"http://www.nr6h.cn/uploads/20180509/7cf13827a4cc2054ddad452385713c24.jpg","play_id":6,"play_time":0}]
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
         * id : 5
         * m_name : 红海行动
         * m_img : http://www.nr6h.cn/uploads/20180509/7cf13827a4cc2054ddad452385713c24.jpg
         * play_id : 6
         * play_time : 0
         */

        private int id;
        private String m_name;
        private String m_img;
        private int play_id;
        private int play_time;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getM_name() {
            return m_name;
        }

        public void setM_name(String m_name) {
            this.m_name = m_name;
        }

        public String getM_img() {
            return m_img;
        }

        public void setM_img(String m_img) {
            this.m_img = m_img;
        }

        public int getPlay_id() {
            return play_id;
        }

        public void setPlay_id(int play_id) {
            this.play_id = play_id;
        }

        public int getPlay_time() {
            return play_time;
        }

        public void setPlay_time(int play_time) {
            this.play_time = play_time;
        }
    }
}
