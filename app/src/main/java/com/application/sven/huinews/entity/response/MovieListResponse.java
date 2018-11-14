package com.application.sven.huinews.entity.response;

import java.io.Serializable;
import java.util.List;

/**
 * auther: sunfuyi
 * data: 2018/5/17
 * effect:
 */
public class MovieListResponse extends BaseResponse implements Serializable {

    private List<MovieData> data;

    public List<MovieData> getData() {
        return data;
    }

    public void setData(List<MovieData> data) {
        this.data = data;
    }

    public static class MovieData implements Serializable {
        /**
         * icon : http://www.nr6h.cn/uploads/20180508/500bb9322f638ce367e68b932b632272.png
         * layout : top_banner
         * lists : [{"id":5,"m_name":"红海行动","m_s_desc":"红海行动","m_desc":"索马里海域外，中国商船遭遇劫持，船员全数沦为阶下囚。蛟龙突击队沉着应对，潜入商船进行突袭，成功解救全部人质。  返航途中，非洲北部伊维亚共和国发生政变，恐怖组织连同叛军攻入首都，当地华侨面临危险，海军战舰接到上级命令改变航向，前往执行撤侨任务。蛟龙突击队八人，整装待发。  时间紧迫，在\u201c撤侨遇袭可反击，相反则必须避免交火，以免引起外交冲突\u201d的大原则下，海军战舰及蛟龙突击队深入伊维亚，在恶劣的环境之下，借助海陆等多种装备，成功转移等候在码头的中国侨民，并在激烈的遭遇战之后，营救了被恐怖分子追击的中国领事馆巴士。  然而事情尚未完结，就在掩护华侨撤离之际，蛟龙突击队收到中国人质被恐怖分子劫持的消息。众人深感责任重大，义无反顾地再度展开营救行动。前方路途险恶，蛟龙突击队即将遭遇的，远不止人质营救那么简单，恐怖分子的惊天阴谋即将浮出水面\u2026..","a_star":" 张译,黄景瑜,海清,杜江,蒋璐霞,尹昉,郭家豪,王雨甜,麦亨利","all_set_number":1,"now_set_number":1,"m_img":"http://www.nr6h.cn/uploads/20180509/cc978a8d849fccf08e972d74b5d3bb41.jpg"}]
         */

        private int id;
        private String sub_name;
        private String icon;
        private String layout;
        private String show_more_type;
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

        public List<ListsBean> getLists() {
            return lists;
        }

        public void setLists(List<ListsBean> lists) {
            this.lists = lists;
        }

        public static class ListsBean implements Serializable {
            /**
             * id : 5
             * m_name : 红海行动
             * m_s_desc : 红海行动
             * m_desc : 索马里海域外，中国商船遭遇劫持，船员全数沦为阶下囚。蛟龙突击队沉着应对，潜入商船进行突袭，成功解救全部人质。  返航途中，非洲北部伊维亚共和国发生政变，恐怖组织连同叛军攻入首都，当地华侨面临危险，海军战舰接到上级命令改变航向，前往执行撤侨任务。蛟龙突击队八人，整装待发。  时间紧迫，在“撤侨遇袭可反击，相反则必须避免交火，以免引起外交冲突”的大原则下，海军战舰及蛟龙突击队深入伊维亚，在恶劣的环境之下，借助海陆等多种装备，成功转移等候在码头的中国侨民，并在激烈的遭遇战之后，营救了被恐怖分子追击的中国领事馆巴士。  然而事情尚未完结，就在掩护华侨撤离之际，蛟龙突击队收到中国人质被恐怖分子劫持的消息。众人深感责任重大，义无反顾地再度展开营救行动。前方路途险恶，蛟龙突击队即将遭遇的，远不止人质营救那么简单，恐怖分子的惊天阴谋即将浮出水面…..
             * a_star :  张译,黄景瑜,海清,杜江,蒋璐霞,尹昉,郭家豪,王雨甜,麦亨利
             * all_set_number : 1
             * now_set_number : 1
             * m_img : http://www.nr6h.cn/uploads/20180509/cc978a8d849fccf08e972d74b5d3bb41.jpg
             */

            private int id;
            private String m_name;
            private String m_s_desc;
            private String m_desc;
            private String a_star;
            private int all_set_number;
            private int now_set_number;
            private String m_img;
            private int play_id;
            private int play_time;

            public int getPlay_time() {
                return play_time;
            }

            public void setPlay_time(int play_time) {
                this.play_time = play_time;
            }

            public int getPlay_id() {
                return play_id;
            }

            public void setPlay_id(int play_id) {
                this.play_id = play_id;
            }

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
