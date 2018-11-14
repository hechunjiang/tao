package com.application.sven.huinews.db.Dao;

import java.io.Serializable;

/**
 * auther: sunfuyi
 * data: 2018/7/20
 * effect:
 */
public class Book implements Serializable {
    private int bookId;
    private String bookName;
    private int latestReadChapter;//最后一次阅读的章节
    private int latestReadChapterId;//最后一次阅读的章节的id
    private int lastestReadPage;//最后一次阅读章节的页码

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public int getLatestReadChapter() {
        return latestReadChapter;
    }

    public void setLatestReadChapter(int latestReadChapter) {
        this.latestReadChapter = latestReadChapter;
    }

    public int getLatestReadChapterId() {
        return latestReadChapterId;
    }

    public void setLatestReadChapterId(int latestReadChapterId) {
        this.latestReadChapterId = latestReadChapterId;
    }

    public int getLastestReadPage() {
        return lastestReadPage;
    }

    public void setLastestReadPage(int lastestReadPage) {
        this.lastestReadPage = lastestReadPage;
    }

    @Override
    public String toString() {
        return "Book{" +
                "bookId=" + bookId +
                ", bookName='" + bookName + '\'' +
                ", latestReadChapter=" + latestReadChapter +
                ", latestReadChapterId=" + latestReadChapterId +
                ", lastestReadPage=" + lastestReadPage +
                '}';
    }
}
