package com.application.sven.huinews.config;

import java.io.Serializable;

/**
 * auther: sunfuyi
 * data: 2018/5/11
 * effect:
 */
public class VideoData implements Serializable {
    private String videoUrl;
    private String coverUrl;

    public VideoData(String videoUrl, String coverUrl) {
        this.videoUrl = videoUrl;
        this.coverUrl = coverUrl;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }

    public String getCoverUrl() {
        return coverUrl;
    }

    public void setCoverUrl(String coverUrl) {
        this.coverUrl = coverUrl;
    }

    @Override
    public String toString() {
        return "VideoData{" +
                "videoUrl='" + videoUrl + '\'' +
                ", coverUrl='" + coverUrl + '\'' +
                '}';
    }
}
