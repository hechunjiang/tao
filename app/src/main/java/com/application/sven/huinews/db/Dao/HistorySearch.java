package com.application.sven.huinews.db.Dao;

import java.io.Serializable;

import io.realm.RealmObject;
import io.realm.annotations.Required;

/**
 * auther: sunfuyi
 * data: 2018/5/28
 * effect:
 */
public class HistorySearch extends RealmObject implements Serializable {

    @Required
    private String keyWords;

    public String getKeyWords() {
        return keyWords;
    }

    public void setKeyWords(String keyWords) {
        this.keyWords = keyWords;
    }
}
