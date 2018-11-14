package com.application.sven.huinews.entity.request;

/**
 * auther: sunfuyi
 * data: 2018/7/13
 * effect:
 */
public class BookShelfListRequest extends BaseRequest {
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
}
