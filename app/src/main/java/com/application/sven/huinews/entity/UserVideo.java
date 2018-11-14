package com.application.sven.huinews.entity;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

/**
 * auther: sunfuyi
 * data: 2018/6/5
 * effect:
 */
public class UserVideo implements Serializable {


    /**
     * id : 60800
     * title : 搞笑段子剧 农村小姐姐买老鼠药 真是好笑 今天你笑了吗
     * video_cover : http://static2.quduopai.cn/videosnapshot/238644199589a8b6a159f905b9d2f574b9a477af/00013.jpg
     * video_duration : 98
     * create_time : 2018-05-18
     * play_count : 0
     */

    private int id;
    private String title;
    private String video_cover;
    private int video_duration;
    private String create_time;
    private String play_count;

    public static UserVideo objectFromData(String str, String key) {

        try {
            JSONObject jsonObject = new JSONObject(str);

            return new Gson().fromJson(jsonObject.getString(str), UserVideo.class);
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

    public int getVideo_duration() {
        return video_duration;
    }

    public void setVideo_duration(int video_duration) {
        this.video_duration = video_duration;
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
}
