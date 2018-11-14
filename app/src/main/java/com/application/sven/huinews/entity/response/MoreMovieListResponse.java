package com.application.sven.huinews.entity.response;

import java.io.Serializable;
import java.util.List;

/**
 * auther: sunfuyi
 * data: 2018/5/25
 * effect:
 */
public class MoreMovieListResponse extends BaseResponse {

    private MoreMovieList data;

    public MoreMovieList getData() {
        return data;
    }

    public void setData(MoreMovieList data) {
        this.data = data;
    }

    public static class MoreMovieList implements Serializable {
        private List<ListsBean> lists;

        public List<ListsBean> getLists() {
            return lists;
        }

        public void setLists(List<ListsBean> lists) {
            this.lists = lists;
        }

        public static class ListsBean {
            /**
             * id : 32
             * m_name : 高能少年团 第2季
             * m_s_desc : 高能少年团 第2季
             * m_desc : 高能少年团2》由王俊凯、董子健、杨紫、张一山、王大陆组成的嘉宾阵容。以“成长十二课”为主题，通过一次次贴近生活的课程，让少年团在学习、历练的过程中，得到德智体美劳全方位的锻炼。
             * a_star : 王俊凯,董子健,杨紫,张一山,王大陆
             * all_set_number : 12
             * now_set_number : 4
             * m_img : http://www.nr6h.cn/uploads/20180523/11e90ab05ae6c3118e465f1cd08250e1.png
             */

            private int id;
            private String m_name;
            private String m_s_desc;
            private String m_desc;
            private String a_star;
            private int all_set_number;
            private int now_set_number;
            private String m_img;

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

            public String getM_s_desc() {
                return m_s_desc;
            }

            public void setM_s_desc(String m_s_desc) {
                this.m_s_desc = m_s_desc;
            }

            public String getM_desc() {
                return m_desc;
            }

            public void setM_desc(String m_desc) {
                this.m_desc = m_desc;
            }

            public String getA_star() {
                return a_star;
            }

            public void setA_star(String a_star) {
                this.a_star = a_star;
            }

            public int getAll_set_number() {
                return all_set_number;
            }

            public void setAll_set_number(int all_set_number) {
                this.all_set_number = all_set_number;
            }

            public int getNow_set_number() {
                return now_set_number;
            }

            public void setNow_set_number(int now_set_number) {
                this.now_set_number = now_set_number;
            }

            public String getM_img() {
                return m_img;
            }

            public void setM_img(String m_img) {
                this.m_img = m_img;
            }
        }
    }

}
