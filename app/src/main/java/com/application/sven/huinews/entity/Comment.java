package com.application.sven.huinews.entity;

import java.io.Serializable;

/**
 * auther: sunfuyi
 * data: 2018/6/1
 * effect:
 */
public class Comment implements Serializable {
    private String id;
    private String user_id;
    private String nickname;
    private String avatar;
    private long pub_detetime;
    private int like_count;
    private String content;
    private boolean is_up;
    private boolean is_sure;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public long getPub_detetime() {
        return pub_detetime;
    }

    public void setPub_detetime(long pub_detetime) {
        this.pub_detetime = pub_detetime;
    }

    public int getLike_count() {
        return like_count;
    }

    public void setLike_count(int like_count) {
        this.like_count = like_count;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public boolean isIs_up() {
        return is_up;
    }

    public void setIs_up(boolean is_up) {
        this.is_up = is_up;
    }

    public boolean isIs_sure() {
        return is_sure;
    }

    public void setIs_sure(boolean is_sure) {
        this.is_sure = is_sure;
    }
}

