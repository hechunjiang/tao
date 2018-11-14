package com.application.sven.huinews.entity.request;

/**
 * auther: sunfuyi
 * data: 2018/7/13
 * effect:
 */
public class BookShelfDelRequest extends BaseRequest {
    private int type;
    private String bookId;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getBookId() {
        return bookId;
    }

    public void setBookId(String bookId) {
        this.bookId = bookId;
    }
}
