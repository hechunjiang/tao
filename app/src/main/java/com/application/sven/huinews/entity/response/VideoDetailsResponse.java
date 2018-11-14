package com.application.sven.huinews.entity.response;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.List;

/**
 * auther: sunfuyi
 * data: 2018/6/7
 * effect:
 */
public class VideoDetailsResponse extends BaseResponse {

    private VideoInfo data;

    public VideoInfo getData() {
        return data;
    }

    public void setData(VideoInfo data) {
        this.data = data;
    }

    public static class VideoInfo implements Serializable {

        /**
         * id : 54435
         * title : 最美爱徒演唱《采花》，歌声仿佛一股河水流下来，超好听
         * category :
         * video_url : http://v2.quduopai.cn/ActLogo-ss-mp4-ld/ee9956b8bb6af6a970468d1ffa59014bf9aa0a37/ld.mp4
         * video_duration : 168
         * video_cover : http://static2.quduopai.cn/videosnapshot/ee9956b8bb6af6a970468d1ffa59014bf9aa0a37/00014.jpg
         * video_height : 360
         * video_width : 640
         * like_count : 0
         * dislike_count : 0
         * comment_count : 0
         * share_count : 0
         * play_count : 0
         * group_id : 0
         * user_id : 542
         * user_nickname : 今生无悔
         * user_avatar : http://wx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTLbUy58dwpLeG4GNJHEzc5cVujgBA59as5U3QLT4pkWUBhZcTSjibPyAPDc1xhpKbfibSoFZPsoyGXQ/0
         * create_time : 1526536096
         * collect_count : 0
         * order_time : 1526536096
         * is_collected : true
         * is_up : true
         * is_follow : true
         * recommen : [{"id":223452,"title":"王者：对面四个人竟看到貂蝉掉头就跑，这下好了吧，让人拿下五杀","video_duration":54,"video_cover":"http://static2.quduopai.cn/videosnapshot/da11cbf2b9b81c2a7bf9ddadbe775eedee7d9562/00026.jpg","play_count":"0","group_id":0,"user_id":1232,"user_nickname":"开心就好！","user_avatar":"http://wx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTLCuE3s45as7O1V7QlAf9Vb6FR0FujeXOXmJMl7WljVdK78Hpnic0nt2w81v0NibxLQEdF2OGSAKumw/0"},{"id":204040,"title":"??求小心心","video_duration":6,"video_cover":"http://p9.pstatp.com/large/89be0001e77f67f30149.jpeg","play_count":"0","group_id":0,"user_id":307,"user_nickname":"海洋","user 06-07 19:19:57.022 8807-8807/com.application.sven.huinews E/getVideoInfo___1: _avatar":"http://wx.qlogo.cn/mmopen/vi_32/DYAIOgq83eomfJrLJnibagffuClP54IEjEbBG0FS7dibBvicEEiaCtbKEfkzTiakjCs9TTQxrxS5PCqISwOJa6vxZ6A/0"},{"id":131770,"title":"太多人了","video_duration":7,"video_cover":"http://p3.pstatp.com/large/879200150f19635f8e13.jpeg","play_count":"0","group_id":0,"user_id":47,"user_nickname":"深夜演绎执着","user_avatar":"http://wx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTIVibVqfusUeORkgiapsyK7bBIJ5tyic0Mt9jUHBicicTLkl0phr0bicywOm6B21IjhIf3w1PJ79jzq9b0w/0"},{"id":68943,"title":"王者荣耀：玄策暴力拿一血教程！","video_duration":44,"video_cover":"http://static2.quduopai.cn/image/sp/2018/05/21/5b01ad8ad92b5832141.jpeg","play_count":"0","group_id":0,"user_id":957,"user_nickname":"大心","user_avatar":"http://wx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTKV61IUtHReOFfPsdd3e7WI4s88wsCcfImsTsoanydnuuibxRfoRSHHeSMKeywNDVtCyoyRgP3yr2w/0"},{"id":68450,"title":"她背的都是钱","video_duration":14,"video_cover":"http://p1.pstatp.com/large/85680012d107eae0f7ce.jpeg","play_count":"2146","group_id":0,"user_id":126,"user_nickname":"Timesir","user_avatar":"http://wx.qlogo.cn/mmhead/thfLhcllFYqYHjXzmyFXx3W1VTd9IsxSuE3nLRPH0FkPwV0LEcDSJg/0"}]
         */

        private int id;
        private String title;
        private String category;
        private String video_url;
        private int video_duration;
        private String video_cover;
        private int video_height;
        private int video_width;
        private int like_count;
        private int dislike_count;
        private int comment_count;
        private int share_count;
        private String play_count;
        private int group_id;
        private int user_id;
        private String user_nickname;
        private String user_avatar;
        private int create_time;
        private int collect_count;
        private int order_time;
        private boolean is_collected;
        private boolean is_up;
        private boolean is_follow;
        private List<RecommenBean> recommen;

        public static VideoInfo objectFromData(String str, String key) {

            try {
                JSONObject jsonObject = new JSONObject(str);

                return new Gson().fromJson(jsonObject.getString(str), VideoInfo.class);
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

        public String getCategory() {
            return category;
        }

        public void setCategory(String category) {
            this.category = category;
        }

        public String getVideo_url() {
            return video_url;
        }

        public void setVideo_url(String video_url) {
            this.video_url = video_url;
        }

        public int getVideo_duration() {
            return video_duration;
        }

        public void setVideo_duration(int video_duration) {
            this.video_duration = video_duration;
        }

        public String getVideo_cover() {
            return video_cover;
        }

        public void setVideo_cover(String video_cover) {
            this.video_cover = video_cover;
        }

        public int getVideo_height() {
            return video_height;
        }

        public void setVideo_height(int video_height) {
            this.video_height = video_height;
        }

        public int getVideo_width() {
            return video_width;
        }

        public void setVideo_width(int video_width) {
            this.video_width = video_width;
        }

        public int getLike_count() {
            return like_count;
        }

        public void setLike_count(int like_count) {
            this.like_count = like_count;
        }

        public int getDislike_count() {
            return dislike_count;
        }

        public void setDislike_count(int dislike_count) {
            this.dislike_count = dislike_count;
        }

        public int getComment_count() {
            return comment_count;
        }

        public void setComment_count(int comment_count) {
            this.comment_count = comment_count;
        }

        public int getShare_count() {
            return share_count;
        }

        public void setShare_count(int share_count) {
            this.share_count = share_count;
        }

        public String getPlay_count() {
            return play_count;
        }

        public void setPlay_count(String play_count) {
            this.play_count = play_count;
        }

        public int getGroup_id() {
            return group_id;
        }

        public void setGroup_id(int group_id) {
            this.group_id = group_id;
        }

        public int getUser_id() {
            return user_id;
        }

        public void setUser_id(int user_id) {
            this.user_id = user_id;
        }

        public String getUser_nickname() {
            return user_nickname;
        }

        public void setUser_nickname(String user_nickname) {
            this.user_nickname = user_nickname;
        }

        public String getUser_avatar() {
            return user_avatar;
        }

        public void setUser_avatar(String user_avatar) {
            this.user_avatar = user_avatar;
        }

        public int getCreate_time() {
            return create_time;
        }

        public void setCreate_time(int create_time) {
            this.create_time = create_time;
        }

        public int getCollect_count() {
            return collect_count;
        }

        public void setCollect_count(int collect_count) {
            this.collect_count = collect_count;
        }

        public int getOrder_time() {
            return order_time;
        }

        public void setOrder_time(int order_time) {
            this.order_time = order_time;
        }

        public boolean isIs_collected() {
            return is_collected;
        }

        public void setIs_collected(boolean is_collected) {
            this.is_collected = is_collected;
        }

        public boolean isIs_up() {
            return is_up;
        }

        public void setIs_up(boolean is_up) {
            this.is_up = is_up;
        }

        public boolean isIs_follow() {
            return is_follow;
        }

        public void setIs_follow(boolean is_follow) {
            this.is_follow = is_follow;
        }

        public List<RecommenBean> getRecommen() {
            return recommen;
        }

        public void setRecommen(List<RecommenBean> recommen) {
            this.recommen = recommen;
        }

        public static class RecommenBean {
            /**
             * id : 223452
             * title : 王者：对面四个人竟看到貂蝉掉头就跑，这下好了吧，让人拿下五杀
             * video_duration : 54
             * video_cover : http://static2.quduopai.cn/videosnapshot/da11cbf2b9b81c2a7bf9ddadbe775eedee7d9562/00026.jpg
             * play_count : 0
             * group_id : 0
             * user_id : 1232
             * user_nickname : 开心就好！
             * user_avatar : http://wx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTLCuE3s45as7O1V7QlAf9Vb6FR0FujeXOXmJMl7WljVdK78Hpnic0nt2w81v0NibxLQEdF2OGSAKumw/0
             * user 06-07 19:19:57.022 8807-8807/com.application.sven.huinews E/getVideoInfo___1: _avatar : http://wx.qlogo.cn/mmopen/vi_32/DYAIOgq83eomfJrLJnibagffuClP54IEjEbBG0FS7dibBvicEEiaCtbKEfkzTiakjCs9TTQxrxS5PCqISwOJa6vxZ6A/0
             */

            private int id;
            private String title;
            private int video_duration;
            private String video_cover;
            private String play_count;
            private int group_id;
            private int user_id;
            private String user_nickname;
            private String user_avatar;
            @SerializedName("user 06-07 19:19:57.022 8807-8807/com.application.sven.huinews E/getVideoInfo___1: _avatar")
            private String _$User060719195702288078807ComApplicationSvenHuinewsEGetVideoInfo___1_avatar315; // FIXME check this code

            public static RecommenBean objectFromData(String str, String key) {

                try {
                    JSONObject jsonObject = new JSONObject(str);

                    return new Gson().fromJson(jsonObject.getString(str), RecommenBean.class);
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

            public int getVideo_duration() {
                return video_duration;
            }

            public void setVideo_duration(int video_duration) {
                this.video_duration = video_duration;
            }

            public String getVideo_cover() {
                return video_cover;
            }

            public void setVideo_cover(String video_cover) {
                this.video_cover = video_cover;
            }

            public String getPlay_count() {
                return play_count;
            }

            public void setPlay_count(String play_count) {
                this.play_count = play_count;
            }

            public int getGroup_id() {
                return group_id;
            }

            public void setGroup_id(int group_id) {
                this.group_id = group_id;
            }

            public int getUser_id() {
                return user_id;
            }

            public void setUser_id(int user_id) {
                this.user_id = user_id;
            }

            public String getUser_nickname() {
                return user_nickname;
            }

            public void setUser_nickname(String user_nickname) {
                this.user_nickname = user_nickname;
            }

            public String getUser_avatar() {
                return user_avatar;
            }

            public void setUser_avatar(String user_avatar) {
                this.user_avatar = user_avatar;
            }

            public String get_$User060719195702288078807ComApplicationSvenHuinewsEGetVideoInfo___1_avatar315() {
                return _$User060719195702288078807ComApplicationSvenHuinewsEGetVideoInfo___1_avatar315;
            }

            public void set_$User060719195702288078807ComApplicationSvenHuinewsEGetVideoInfo___1_avatar315(String _$User060719195702288078807ComApplicationSvenHuinewsEGetVideoInfo___1_avatar315) {
                this._$User060719195702288078807ComApplicationSvenHuinewsEGetVideoInfo___1_avatar315 = _$User060719195702288078807ComApplicationSvenHuinewsEGetVideoInfo___1_avatar315;
            }
        }
    }
}
