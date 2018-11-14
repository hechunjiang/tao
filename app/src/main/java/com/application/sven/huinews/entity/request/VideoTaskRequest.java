package com.application.sven.huinews.entity.request;

import com.google.gson.annotations.SerializedName;

/**
 * auther: sunfuyi
 * data: 2018/6/1
 * effect:
 */
public class VideoTaskRequest extends BaseRequest {
    public static final int TASK_ID_READ_NEWS = 2;  //阅读金币视频
    public static final int TASK_ID_READ_RED_NEWS = 3;  //阅读红包视频
    public static final int TASK_ID_READ_PUSH_NEWS = 1;  //阅读推送消息
    public static final int TASK_ID_READ_AD = 1004;  //阅读广告
    public static final String TASK_CODE_READ_NEWS = "key_code=usual_read";


    @SerializedName("id")
    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
