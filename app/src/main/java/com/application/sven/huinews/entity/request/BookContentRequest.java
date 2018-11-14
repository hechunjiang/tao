package com.application.sven.huinews.entity.request;

import java.io.Serializable;

/**
 * auther: sunfuyi
 * data: 2018/7/13
 * effect:
 */
public class BookContentRequest extends BaseRequest implements Serializable {
    private int bookId;
    private int chapterId;
    private int type;

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

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "BookContentRequest{" +
                "bookId=" + bookId +
                ", chapterId=" + chapterId +
                ", type=" + type +
                '}';
    }
}
