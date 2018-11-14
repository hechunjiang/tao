package com.application.sven.huinews.entity.request;

/**
 * auther: sunfuyi
 * data: 2018/5/21
 * effect:
 */
public class VideoLikeRequest extends BaseRequest {
    private String id;
    private String type;

    public String getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id+"";
    }

    public String getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type+"";
    }
}
