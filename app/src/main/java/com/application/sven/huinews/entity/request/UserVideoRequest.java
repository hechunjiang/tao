package com.application.sven.huinews.entity.request;

/**
 * auther: sunfuyi
 * data: 2018/5/18
 * effect:
 */
public class UserVideoRequest extends BaseRequest {
    private int user_id;
    private int limit;
    private int page;

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
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
