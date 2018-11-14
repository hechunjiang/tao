package com.application.sven.huinews.entity.request;

/**
 * auther: sunfuyi
 * data: 2018/5/15
 * effect:
 */
public class VideoListRequest extends BaseRequest {
    private int typeId;
    private int limit;
    private int page;

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getTypeId() {
        return typeId;
    }

    public void setTypeId(int typeId) {
        this.typeId = typeId;
    }
}
