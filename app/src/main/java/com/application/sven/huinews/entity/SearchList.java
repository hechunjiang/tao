package com.application.sven.huinews.entity;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

/**
 * auther: sunfuyi
 * data: 2018/5/28
 * effect:
 */
public class SearchList implements Serializable {

    /**
     * id : 129093
     * title : 三分钟告诉你正在热映的战争史诗大片《红海行动》到底有多好看
     * video_cover : http://static2.quduopai.cn/videosnapshot/7682f33b45e809c2efe486c2256215de58e9276d/00002.jpg
     * create_time : 2018-05-26
     * play_count : 0
     * video_duration : 163
     * a_star :
     * m_desc :
     */

    private int id;
    private String title;
    private String video_cover;
    private String create_time;
    private String play_count;
    private int video_duration;
    private String a_star;
    private String m_desc;

    public static SearchList objectFromData(String str, String key) {

        try {
            JSONObject jsonObject = new JSONObject(str);

            return new Gson().fromJson(jsonObject.getString(str), SearchList.class);
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

    public String getVideo_cover() {
        return video_cover;
    }

    public void setVideo_cover(String video_cover) {
        this.video_cover = video_cover;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    public String getPlay_count() {
        return play_count;
    }

    public void setPlay_count(String play_count) {
        this.play_count = play_count;
    }

    public int getVideo_duration() {
        return video_duration;
    }

    public void setVideo_duration(int video_duration) {
        this.video_duration = video_duration;
    }

    public String getA_star() {
        return a_star;
    }

    public void setA_star(String a_star) {
        this.a_star = a_star;
    }

    public String getM_desc() {
        return m_desc;
    }

    public void setM_desc(String m_desc) {
        this.m_desc = m_desc;
    }
}
