package com.application.sven.huinews.entity.request;

/**
 * auther: sunfuyi
 * data: 2018/5/22
 * effect:
 */
public class VideoCommentRequest extends BaseRequest {
    private String page;
    private String video_id;
    private String order;

    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
    }

    public String getVideo_id() {
        return video_id;
    }

    public void setVideo_id(String video_id) {
        this.video_id = video_id;
    }

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }
}
