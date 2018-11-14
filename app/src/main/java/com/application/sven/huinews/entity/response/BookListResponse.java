package com.application.sven.huinews.entity.response;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

/**
 * auther: sunfuyi
 * data: 2018/7/10
 * effect:
 */
public class BookListResponse extends BaseResponse {

    private List<BookList> data;

    public List<BookList> getData() {
        return data;
    }

    public void setData(List<BookList> data) {
        this.data = data;
    }
}
