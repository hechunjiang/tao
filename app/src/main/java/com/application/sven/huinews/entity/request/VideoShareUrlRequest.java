package com.application.sven.huinews.entity.request;

import com.google.gson.annotations.SerializedName;

/**
 * auther: sunfuyi
 * data: 2018/5/29
 * effect:
 */
public class VideoShareUrlRequest extends BaseRequest {
    @SerializedName("video_id")
    private String videoId;


    public String getVideoId() {
        return videoId;
    }

    public void setVideoId(String videoId) {
        this.videoId = videoId;
    }
}
