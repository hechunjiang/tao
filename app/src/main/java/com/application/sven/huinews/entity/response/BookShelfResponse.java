package com.application.sven.huinews.entity.response;

/**
 * auther: sunfuyi
 * data: 2018/7/13
 * effect:
 */
public class BookShelfResponse extends BaseResponse {
    private BookShelfList data;

    public BookShelfList getData() {
        return data;
    }

    public void setData(BookShelfList data) {
        this.data = data;
    }
}
