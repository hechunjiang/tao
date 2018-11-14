package com.application.sven.huinews.entity.request;

/**
 * auther: sunfuyi
 * data: 2018/7/18
 * effect:
 */
public class BookChapterPayRequest extends BaseRequest {
    private int type;
    private int bookId;
    private int chapterId;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

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

    @Override
    public String toString() {
        return "BookChapterPayRequest{" +
                "type=" + type +
                ", bookId=" + bookId +
                ", chapterId=" + chapterId +
                '}';
    }
}
