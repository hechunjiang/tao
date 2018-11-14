package com.application.sven.huinews.entity.request;

/**
 * auther: sunfuyi
 * data: 2018/7/12
 * effect:
 */
public class BookShelfAddRequest extends BaseRequest {
    private int bookId;
    private int chapterId;

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public int getChapterId() {
        return chapterId;
    }

    public void setChapterId(int chapterId) {
        this.chapterId = chapterId;
    }
}
