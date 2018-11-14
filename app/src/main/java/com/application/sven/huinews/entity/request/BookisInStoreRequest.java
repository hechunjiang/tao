package com.application.sven.huinews.entity.request;

/**
 * auther: sunfuyi
 * data: 2018/7/13
 * effect:
 */
public class BookisInStoreRequest extends BaseRequest {
    private int bookId;

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }
}
