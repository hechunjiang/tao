package com.application.sven.huinews.entity.response;

import android.text.Html;

import com.application.sven.huinews.utils.CommonUtils;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.Serializable;

/**
 * auther: sunfuyi
 * data: 2018/7/14
 * effect:
 */
public class BookChapterInfoResponse extends BaseResponse {
    private BookChapterItem data;

    public BookChapterItem getData() {
        return data;
    }

    public void setData(BookChapterItem data) {
        this.data = data;
    }


}
