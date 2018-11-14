package com.application.sven.huinews.entity.response;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.List;

/**
 * auther: sunfuyi
 * data: 2018/7/12
 * effect:
 */
public class BookStoreListResponse extends BaseResponse {

    private BookStoreList data;

    public BookStoreList getData() {
        return data;
    }

    public void setData(BookStoreList data) {
        this.data = data;
    }
}
