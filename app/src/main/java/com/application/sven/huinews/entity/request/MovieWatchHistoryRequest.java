package com.application.sven.huinews.entity.request;

/**
 * auther: sunfuyi
 * data: 2018/5/25
 * effect:
 */
public class MovieWatchHistoryRequest extends BaseRequest {
    private String type; //last-->最后一次    list-->观看记录
    private int limit;
    private int page;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

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
}
