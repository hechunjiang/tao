package com.application.sven.huinews.entity.events;

/**
 * auther: sunfuyi
 * data: 2018/5/26
 * effect:
 */
public class SearchEvent {
    private String keyWord;

    public SearchEvent(String keyWord) {
        this.keyWord = keyWord;
    }

    public String getKeyWord() {
        return keyWord;
    }
}
