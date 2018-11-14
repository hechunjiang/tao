package com.application.sven.huinews.entity.response;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

/**
 * auther: sunfuyi
 * data: 2018/5/14
 * effect:
 */
public class DataConfig extends BaseResponse implements Serializable {
    private Data data;

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public static class Data implements Serializable {
        /**
         * URL : {"user_center":"http://www.baidu.com","an_apprentice":"http://www.baidu.com"}
         * NEEDCOUNT : 5
         */

        private URLBean URL;
        private int NEEDCOUNT;
        private SHAREBean SHARE;
        private long time;
        private GOLDBean GOlD;
        private int QQ;
        private ADTYPEBean ADTYPE;
        private int WATCHTIME = 20;
        private boolean REDOPEN;

        public boolean isREDOPEN() {
            return REDOPEN;
        }

        public void setREDOPEN(boolean REDOPEN) {
            this.REDOPEN = REDOPEN;
        }

        public int getWATCHTIME() {
            return WATCHTIME;
        }

        public void setWATCHTIME(int WATCHTIME) {
            this.WATCHTIME = WATCHTIME;
        }

        public ADTYPEBean getADTYPE() {
            return ADTYPE;
        }

        public void setADTYPE(ADTYPEBean ADTYPE) {
            this.ADTYPE = ADTYPE;
        }

        public int getQQ() {
            return QQ;
        }

        public void setQQ(int QQ) {
            this.QQ = QQ;
        }

        public GOLDBean getGOlD() {
            return GOlD;
        }

        public void setGOlD(GOLDBean GOlD) {
            this.GOlD = GOlD;
        }

        public URLBean getURL() {
            return URL;
        }

        public void setURL(URLBean URL) {
            this.URL = URL;
        }

        public int getNEEDCOUNT() {
            return NEEDCOUNT;
        }

        public void setNEEDCOUNT(int NEEDCOUNT) {
            this.NEEDCOUNT = NEEDCOUNT;
        }

        public SHAREBean getSHARE() {
            return SHARE;
        }

        public void setSHARE(SHAREBean SHARE) {
            this.SHARE = SHARE;
        }


        public long getTime() {
            return time;
        }

        public void setTime(long time) {
            this.time = time;
        }

        public static class URLBean implements Serializable {
            /**
             * user_center : http://www.baidu.com
             * an_apprentice : http://www.baidu.com
             */

            private String user_center;
            private String an_apprentice;

            public String getUser_center() {
                return user_center;
            }

            public void setUser_center(String user_center) {
                this.user_center = user_center;
            }

            public String getAn_apprentice() {
                return an_apprentice;
            }

            public void setAn_apprentice(String an_apprentice) {
                this.an_apprentice = an_apprentice;
            }
        }

        public static class SHAREBean implements Serializable {
            private String title;
            private String content;

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getContent() {
                return content;
            }

            public void setContent(String content) {
                this.content = content;
            }
        }

        public static class GOLDBean implements Serializable {
            private int v_at_count;
            private int v_at_red;

            public int getV_at_count() {
                return v_at_count;
            }

            public void setV_at_count(int v_at_count) {
                this.v_at_count = v_at_count;
            }

            public int getV_at_red() {
                return v_at_red;
            }

            public void setV_at_red(int v_at_red) {
                this.v_at_red = v_at_red;
            }
        }

        @Override
        public String toString() {
            return "AppConfig{" +
                    "URL=" + URL +
                    ", NEEDCOUNT='" + NEEDCOUNT + '\'' +
                    '}';
        }


        public static class ADTYPEBean implements Serializable {

            /**
             * open_screen : open
             * v_transverse_screen : vt
             * v_vertical_screen : vv
             * v_detail : vd
             * f_broadcast : fb
             * f_c_list : fcl
             * f_list : flist
             * f_detail : fd
             * other_broadcast : ob
             * small_ad : sa
             * transverse_ad : ta
             * multi_ad : ma
             * big_ad : ba
             */

            private String open_screen;  // 开屏广告
            private String v_transverse_screen; //视频横屏布局
            private String v_vertical_screen; //视频竖向布局
            private String v_detail; //视频详情
            private String f_broadcast; //影视轮播
            private String f_c_list; //影视分类列表
            private String f_list; //影视列表
            private String f_detail;//影视详情
            private String other_broadcast; //其他地方轮播
            private String small_ad; //小图
            private String transverse_ad;  //横幅广告
            private String multi_ad; //主图广告（三图）
            private String big_ad; //大图


            public String getOpen_screen() {
                return open_screen;
            }

            public void setOpen_screen(String open_screen) {
                this.open_screen = open_screen;
            }

            public String getV_transverse_screen() {
                return v_transverse_screen;
            }

            public void setV_transverse_screen(String v_transverse_screen) {
                this.v_transverse_screen = v_transverse_screen;
            }

            public String getV_vertical_screen() {
                return v_vertical_screen;
            }

            public void setV_vertical_screen(String v_vertical_screen) {
                this.v_vertical_screen = v_vertical_screen;
            }

            public String getV_detail() {
                return v_detail;
            }

            public void setV_detail(String v_detail) {
                this.v_detail = v_detail;
            }

            public String getF_broadcast() {
                return f_broadcast;
            }

            public void setF_broadcast(String f_broadcast) {
                this.f_broadcast = f_broadcast;
            }

            public String getF_c_list() {
                return f_c_list;
            }

            public void setF_c_list(String f_c_list) {
                this.f_c_list = f_c_list;
            }

            public String getF_list() {
                return f_list;
            }

            public void setF_list(String f_list) {
                this.f_list = f_list;
            }

            public String getF_detail() {
                return f_detail;
            }

            public void setF_detail(String f_detail) {
                this.f_detail = f_detail;
            }

            public String getOther_broadcast() {
                return other_broadcast;
            }

            public void setOther_broadcast(String other_broadcast) {
                this.other_broadcast = other_broadcast;
            }

            public String getSmall_ad() {
                return small_ad;
            }

            public void setSmall_ad(String small_ad) {
                this.small_ad = small_ad;
            }

            public String getTransverse_ad() {
                return transverse_ad;
            }

            public void setTransverse_ad(String transverse_ad) {
                this.transverse_ad = transverse_ad;
            }

            public String getMulti_ad() {
                return multi_ad;
            }

            public void setMulti_ad(String multi_ad) {
                this.multi_ad = multi_ad;
            }

            public String getBig_ad() {
                return big_ad;
            }

            public void setBig_ad(String big_ad) {
                this.big_ad = big_ad;
            }
        }
    }

    @Override
    public String toString() {
        return "AppConfig{" +
                "data=" + data +
                '}';
    }
}
