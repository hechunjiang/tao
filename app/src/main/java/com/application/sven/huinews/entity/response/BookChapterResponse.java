package com.application.sven.huinews.entity.response;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

/**
 * auther: sunfuyi
 * data: 2018/7/11
 * effect:
 */
public class BookChapterResponse extends BaseResponse {


    private BookChapters data;

    public BookChapters getData() {
        return data;
    }

    public void setData(BookChapters data) {
        this.data = data;
    }
}
