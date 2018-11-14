package com.application.sven.huinews.entity.response;

import com.application.sven.huinews.utils.CommonUtils;

import java.io.Serializable;
import java.util.List;

/**
 * auther: sunfuyi
 * data: 2018/5/17
 * effect:
 */
public class MovieDetailResponse extends BaseResponse implements Serializable {


    private MovieDetailData data;

    public MovieDetailData getData() {
        return data;
    }

    public void setData(MovieDetailData data) {
        this.data = data;
    }

    public static class MovieDetailData implements Serializable {


        /**
         * id : 5
         * m_name : 红海行动
         * m_s_desc : 红海行动
         * m_desc : 索马里海域外，中国商船遭遇劫持，船员全数沦为阶下囚。蛟龙突击队沉着应对，潜入商船进行突袭，成功解救全部人质。  返航途中，非洲北部伊维亚共和国发生政变，恐怖组织连同叛军攻入首都，当地华侨面临危险，海军战舰接到上级命令改变航向，前往执行撤侨任务。蛟龙突击队八人，整装待发。  时间紧迫，在“撤侨遇袭可反击，相反则必须避免交火，以免引起外交冲突”的大原则下，海军战舰及蛟龙突击队深入伊维亚，在恶劣的环境之下，借助海陆等多种装备，成功转移等候在码头的中国侨民，并在激烈的遭遇战之后，营救了被恐怖分子追击的中国领事馆巴士。  然而事情尚未完结，就在掩护华侨撤离之际，蛟龙突击队收到中国人质被恐怖分子劫持的消息。众人深感责任重大，义无反顾地再度展开营救行动。前方路途险恶，蛟龙突击队即将遭遇的，远不止人质营救那么简单，恐怖分子的惊天阴谋即将浮出水面…..
         * a_star :  张译,黄景瑜,海清,杜江,蒋璐霞,尹昉,郭家豪,王雨甜,麦亨利
         * all_set_number : 1
         * now_set_number : 1
         * m_img : http://www.nr6h.cn/uploads/20180509/7cf13827a4cc2054ddad452385713c24.jpg
         * ext : [{"id":6,"now_set_number":"1","now_desc":""}]
         * analy : [{"id":3,"c_name":"618g"}]
         * recommen : [{"id":8,"m_name":"金刚：骷髅岛","m_s_desc":"金刚：骷髅岛","m_desc":"上世纪70年代，一支集结了科考队员、探险家、战地摄影记者、军人的探险队，冒险前往南太平洋上的神秘岛屿\u2014\u2014骷髅岛。他们的到来惊扰了岛上之神\u2014\u2014史上最大金刚。经过一番惨烈的激战之后，探险队员散落在了岛屿各处。此时，队员们才意识到这次探险并不是一次单纯的科考任务，而是去探索怪兽存在的证明。  在这片与世隔绝、危险密布的丛林，无数怪异的史前生物暗藏其中，时刻危及着他们的生命。队员们还遇到了神秘的原始部落，金刚的身世和其守护岛屿的原因也被逐渐揭开，原来，恐怖阴森的骷髅岛上还蛰伏着更凶狠残暴的怪兽\u2026\u2026\r\n","a_star":"汤姆·希德勒斯顿","all_set_number":1,"now_set_number":1,"m_img":"http://www.nr6h.cn/uploads/20180509/ab23c290dd609ff1c062763c9bca6106.jpg"},{"id":12,"m_name":"誓言","m_s_desc":"誓言","m_desc":"誓言","a_star":"贾乃亮,李晟,刘奕君,秦昊,刘威,李菁,王馨可,隆妮,邱士鉴,曹可凡","all_set_number":1,"now_set_number":3,"m_img":"http://www.nr6h.cn/uploads/20180509/5e0284dc0214631aae664cbeee1a334f.jpg"},{"id":9,"m_name":"真爱的谎言之破冰者","m_s_desc":"真爱的谎言之破冰者","m_desc":"真爱的谎言之破冰者","a_star":"罗晋,潘之琳,张晨光 , 曹征, 吕星辰,王砚辉 ,程煜","all_set_number":1,"now_set_number":5,"m_img":"http://www.nr6h.cn/uploads/20180509/d714c103610a8ece05b4d919ada75bbf.jpg"},{"id":6,"m_name":"唐人街探案2","m_s_desc":"唐人街探案2","m_desc":"秦风（刘昊然饰）接到唐仁（王宝强饰）的邀请来纽约参加其与阿香的婚礼。豪气逼人的唐仁迎接秦风，极尽招摇，岂料婚礼其实是唐仁为巨额奖金而参加的\u201c世界名侦探大赛\u201d，比赛的内容是寻找\u201c唐人街教父\u201d七叔的孙子。受骗的秦风怒极欲走，却被 FBI 探员陈英送来的讯息所吸引......","a_star":" 王宝强,刘昊然,肖央,刘承羽","all_set_number":1,"now_set_number":1,"m_img":"http://www.nr6h.cn/uploads/20180517/59e1a3f2647c8fd1330f9b249731e519.jpg"},{"id":10,"m_name":"冒险王卫斯理之蓝血人","m_s_desc":"冒险王卫斯理之蓝血人","m_desc":"冒险王卫斯理之蓝血人","a_star":"余文乐,文咏珊 ,胡然 ,徐冬冬,徐冬冬,黄浩然,叶项明","all_set_number":1,"now_set_number":5,"m_img":"http://www.nr6h.cn/uploads/20180509/ee01e76930198abfd2a00137b8ad4674.jpg"}]
         */

        private int id;
        private String m_name;
        private String m_s_desc;
        private String m_desc;
        private String a_star;
        private int all_set_number;
        private int now_set_number;
        private String m_img;
        private String play_count;
        private List<ExtBean> ext;
        private boolean is_collected;
        private List<AnalyBean> analy;
        private List<RecommenBean> recommen;

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

        public String getPlay_count() {
            return play_count;
        }

        public void setPlay_count(String play_count) {
            this.play_count = play_count;
        }

        public String getM_img() {
            return m_img;
        }

        public void setM_img(String m_img) {
            this.m_img = m_img;
        }

        public List<ExtBean> getExt() {
            return ext;
        }

        public void setExt(List<ExtBean> ext) {
            this.ext = ext;
        }

        public boolean isIs_collected() {
            return is_collected;
        }

        public void setIs_collected(boolean is_collected) {
            this.is_collected = is_collected;
        }

        public List<AnalyBean> getAnaly() {
            return analy;
        }

        public void setAnaly(List<AnalyBean> analy) {
            this.analy = analy;
        }

        public List<RecommenBean> getRecommen() {
            return recommen;
        }

        public void setRecommen(List<RecommenBean> recommen) {
            this.recommen = recommen;
        }

        public static class ExtBean implements Serializable {
            /**
             * id : 6
             * now_set_number : 1
             * now_desc :
             */

            private int id;
            private int[] url_resourece;
            private String now_set_number;
            private String now_desc;
            private boolean isSelected;

            public boolean isSelected() {
                return isSelected;
            }

            public void setSelected(boolean selected) {
                isSelected = selected;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getNow_set_number() {

                return CommonUtils.numToString(now_set_number);
            }

            public void setNow_set_number(String now_set_number) {
                this.now_set_number = now_set_number;
            }

            public String getNow_desc() {
                return now_desc;
            }

            public void setNow_desc(String now_desc) {
                this.now_desc = now_desc;
            }

            public int[] getUrl_resourece() {
                return url_resourece;
            }

            public void setUrl_resourece(int[] url_resourece) {
                this.url_resourece = url_resourece;
            }
        }

        public static class AnalyBean implements Serializable {
            /**
             * id : 3
             * c_name : 618g
             */

            private int id;
            private String c_name;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getC_name() {
                return c_name;
            }

            public void setC_name(String c_name) {
                this.c_name = c_name;
            }
        }

        public static class RecommenBean implements Serializable {
            /**
             * id : 8
             * m_name : 金刚：骷髅岛
             * m_s_desc : 金刚：骷髅岛
             * m_desc : 上世纪70年代，一支集结了科考队员、探险家、战地摄影记者、军人的探险队，冒险前往南太平洋上的神秘岛屿——骷髅岛。他们的到来惊扰了岛上之神——史上最大金刚。经过一番惨烈的激战之后，探险队员散落在了岛屿各处。此时，队员们才意识到这次探险并不是一次单纯的科考任务，而是去探索怪兽存在的证明。  在这片与世隔绝、危险密布的丛林，无数怪异的史前生物暗藏其中，时刻危及着他们的生命。队员们还遇到了神秘的原始部落，金刚的身世和其守护岛屿的原因也被逐渐揭开，原来，恐怖阴森的骷髅岛上还蛰伏着更凶狠残暴的怪兽……
             * <p>
             * a_star : 汤姆·希德勒斯顿
             * all_set_number : 1
             * now_set_number : 1
             * m_img : http://www.nr6h.cn/uploads/20180509/ab23c290dd609ff1c062763c9bca6106.jpg
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
