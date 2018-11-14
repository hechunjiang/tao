package com.application.sven.huinews.entity.request;

import com.google.gson.annotations.SerializedName;

/**
 * auther: sunfuyi
 * data: 2018/6/1
 * effect:
 */
public class AdCommentRequest extends BaseRequest {
    @SerializedName("content")
    private String commentContent;
    @SerializedName("video_id")
    private String videoId;

    public String getCommentContent() {
        return commentContent;
    }

    public void setCommentContent(String commentContent) {
        this.commentContent = commentContent;
    }

    public String getVideoId() {
        return videoId;
    }

    public void setVideoId(int videoId) {
        this.videoId = videoId+"";
    }
}
