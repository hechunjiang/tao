package com.application.sven.huinews.entity.response;

import com.application.sven.huinews.entity.request.BaseRequest;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.List;

/**
 * auther: sunfuyi
 * data: 2018/7/11
 * effect:
 */
public class BookStoreCategroyResponse extends BaseResponse {

    private List<BookStoreCate> data;

    public List<BookStoreCate> getData() {
        return data;
    }

    public void setData(List<BookStoreCate> data) {
        this.data = data;
    }

}
