package com.application.sven.huinews.main.read.adapter;

import android.content.Context;
import android.util.SparseArray;

import com.application.sven.huinews.R;
import com.application.sven.huinews.entity.response.BookChapterInfoResponse;
import com.application.sven.huinews.entity.response.BookChapterItem;
import com.application.sven.huinews.entity.response.BookChapters;
import com.application.sven.huinews.utils.LogUtil;
import com.application.sven.huinews.view.newread.StringAdapter;

import java.util.List;

/**
 * auther: sunfuyi
 * data: 2018/7/16
 * effect:
 */
public class BookReadAdapter extends StringAdapter {
    private SparseArray<BookChapterItem> bookArray;
    // private List<BookChapters.ListsBean> mBookChapterItem;
    boolean isUpDate;

    public BookReadAdapter() {
        bookArray = new SparseArray<>();
    }

    @Override
    protected String getPageSource(int section) {
        BookChapterItem bookChapterItem = bookArray.get(section);
        return bookChapterItem != null ? bookChapterItem.getContent() : null;
    }


    @Override
    public int getSectionCount() {
        return bookArray.size();
    }

    @Override
    public int getChapterMoney(int section) {
        return bookArray.get(section).getGolds();
    }

    @Override
    public int getUserBanlce(int section) {
        return bookArray.get(section).getLeft_diamond();
    }


    @Override
    public boolean isShowPay(int section) {
        if (bookArray.get(section).getShow() == 2 || bookArray.get(section).getShow() == 3) {
            return true;
        }
        return false;
    }

    @Override
    public int payStatus(int section) {
        return bookArray.get(section).getShow();
    }

    @Override
    public int getChapterId(int section) {
        return bookArray.get(section).getId();
    }


    @Override
    public String getPayInfo(Context mContext, int section) {
        BookChapterItem bookChapterItem = bookArray.get(section);
        String payInfo = null;
        if (bookChapterItem.getShow() == 2) {
            payInfo = mContext.getString(R.string.read_pay_buttom_text);
        } else if (bookChapterItem.getShow() == 3) {
            payInfo = mContext.getString(R.string.read_pay_current);
        }
        return payInfo;
    }

    @Override
    public String getSectionName(int section) {
        BookChapterItem bookChapterItem = bookArray.get(section);
        return bookChapterItem != null ? bookArray.get(section).getChapter_name() : null;

    }

    @Override
    public boolean hasNextSection(int currentSection) {
        return bookArray.get(currentSection + 1) != null;
    }

    @Override
    public boolean hasPreviousSection(int currentSection) {
        return bookArray.get(currentSection - 1) != null;
    }

    public void addData(int section, BookChapterItem content) {
        bookArray.put(section, content);
    }

    public boolean hasSection(int section) {
        return bookArray.get(section) != null;
    }

    public String upDate(int section) {
        BookChapterItem bookChapterItem = bookArray.get(section);
        return bookChapterItem != null ? bookArray.get(section).getContent() : null;
    }


}
