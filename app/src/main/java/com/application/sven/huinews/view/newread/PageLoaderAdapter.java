package com.application.sven.huinews.view.newread;

import android.content.Context;

import java.util.List;

/**
 * Created by Chu on 2017/8/12.
 */

public interface PageLoaderAdapter {

    int getPageCount(int section, PageProperty property);

    List<String> getPageLines(int section, int page, PageProperty property);

    int getSectionCount();

    int getChapterMoney(int section);

    int getUserBanlce(int section);

    boolean isShowPay(int section);

    int payStatus(int section);

    int getChapterId(int section);


    String getPayInfo(Context mContext, int section);

    String getSectionName(int section);

    boolean hasNextSection(int currentSection);

    boolean hasPreviousSection(int currentSection);


}
