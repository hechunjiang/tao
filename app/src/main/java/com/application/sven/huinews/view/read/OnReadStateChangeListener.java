package com.application.sven.huinews.view.read;

/**
 * auther: sunfuyi
 * data: 2018/7/6
 * effect:
 */
public interface OnReadStateChangeListener {

    void onChapterChanged(int chapter);

    void onPageChanged(int chapter, int page);

    void onLoadChapterFailure(int chapter);

    void onCenterClick();

    void onFlip();
}
