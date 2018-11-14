package com.application.sven.huinews.entity.response;

import java.io.Serializable;
import java.util.List;

/**
 * auther: sunfuyi
 * data: 2018/5/15
 * effect:
 */
public class VideoListResponse implements Serializable {
    /**
     * is_has_more : true
     * list : [{"id":6534165972566150413,"title":"原因是跟朋友家的小妹妹玩了一下午，各种宠妹各种摸头杀完了还非得晚上带回家，结果分开后上车就声泪俱下，我该怎么办","category":"douyin","video_url":"https://aweme.snssdk.com/aweme/v1/play/?video_id=f022de2f6e2d461183ec0d7eb19c8771&line=0&ratio=720p&media_type=4&vr_type=0&test_cdn=None&improve_bitrate=0","video_duration":1511,"video_cover":"http://p3.pstatp.com/large/6fd3000bd2c6f17fef1d.jpeg","video_height":960,"video_width":540,"like_count":2599507,"dislike_count":0,"comment_count":41216,"share_count":386802,"play_count":79635082,"group_id":6534165972566150413,"user_id":23604,"user_nickname":"美子","user_avatar":"https://p1.pstatp.com/aweme/1080x1080/3b5b002573d59cdaec7a.jpeg","create_time":1521354072,"collect_count":670,"is_gold":0,"is_redpack":1,"is_collected":false,"is_up":false}]
     */

    private VideoList data;

    public VideoList getData() {
        return data;
    }

    public void setData(VideoList data) {
        this.data = data;
    }

    public static class VideoList implements Serializable {
        private boolean is_has_more;
        private List<ListBean> lists;
        private String icon;
        private String layout;

        public boolean isIs_has_more() {
            return is_has_more;
        }

        public void setIs_has_more(boolean is_has_more) {
            this.is_has_more = is_has_more;
        }

        public List<ListBean> getList() {
            return lists;
        }

        public void setList(List<ListBean> list) {
            this.lists = list;
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

        public static class ListBean implements Serializable {
            /**
             * id : 6534165972566150413
             * title : 原因是跟朋友家的小妹妹玩了一下午，各种宠妹各种摸头杀完了还非得晚上带回家，结果分开后上车就声泪俱下，我该怎么办
             * category : douyin
             * video_url : https://aweme.snssdk.com/aweme/v1/play/?video_id=f022de2f6e2d461183ec0d7eb19c8771&line=0&ratio=720p&media_type=4&vr_type=0&test_cdn=None&improve_bitrate=0
             * video_duration : 1511
             * video_cover : http://p3.pstatp.com/large/6fd3000bd2c6f17fef1d.jpeg
             * video_height : 960
             * video_width : 540
             * like_count : 2599507
             * dislike_count : 0
             * comment_count : 41216
             * share_count : 386802
             * play_count : 79635082
             * group_id : 6534165972566150413
             * user_id : 23604
             * user_nickname : 美子
             * user_avatar : https://p1.pstatp.com/aweme/1080x1080/3b5b002573d59cdaec7a.jpeg
             * create_time : 1521354072
             * collect_count : 670
             * is_gold : 0
             * is_redpack : 1
             * is_collected : false
             * is_up : false
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
            private long group_id;
            private int user_id;
            private String user_nickname;
            private String user_avatar;
            private int create_time;
            private int collect_count;
            private int is_gold;
            private int is_redpack;
            private boolean is_collected;
            private boolean is_up;
            private boolean is_follow;
            private int is_ad;

            private boolean isSaveRedVideo;

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

            public long getGroup_id() {
                return group_id;
            }

            public void setGroup_id(long group_id) {
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

            public int getIs_gold() {
                return is_gold;
            }

            public void setIs_gold(int is_gold) {
                this.is_gold = is_gold;
            }

            public int getIs_redpack() {
                return is_redpack;
            }

            public void setIs_redpack(int is_redpack) {
                this.is_redpack = is_redpack;
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

            public int isIs_ad() {
                return is_ad;
            }

            public void setIs_ad(int is_ad) {
                this.is_ad = is_ad;
            }


            public boolean isSaveRedVideo() {
                return isSaveRedVideo;
            }

            public void setSaveRedVideo(boolean saveRedVideo) {
                isSaveRedVideo = saveRedVideo;
            }

            @Override
            public boolean equals(Object obj) {
                ListBean data = null;
                if (obj instanceof ListBean) {
                    data = (ListBean) obj;
                    return this.id == data.getId();
                }
                return false;
            }


        }
    }
}
