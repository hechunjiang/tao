package com.application.sven.huinews.entity.request;

import com.google.gson.annotations.SerializedName;

/**
 * Created by sfy. on 2018/4/10 0010.
 */

public class ShareVisitRequest extends BaseRequest {
    public static final String VIDEO_LIST = "videolist";
    public static final String VIDEO_DETAIL = "videodeail";
    public static final String MOVIE_DETAIL = "moviedeail";
    public static final String BOOK_DETAIL = "bookdetail";


    @SerializedName("activity_type")
    private String activityType;
    @SerializedName("code")
    private String code;
    @SerializedName("share_channel")
    private String shareChannel;

    public String getActivityType() {
        return activityType;
    }

    public void setActivityType(String activityType) {
        this.activityType = activityType;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getShareChannel() {
        return shareChannel;
    }

    public void setShareChannel(String shareChannel) {
        this.shareChannel = shareChannel;
    }
}
