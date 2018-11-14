package com.application.sven.huinews.entity.request;

/**
 * auther: sunfuyi
 * data: 2018/7/11
 * effect:
 */
public class BookChapterRequest extends BaseRequest {
    private int id;
    private String sort;
    private int limit;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }
}
