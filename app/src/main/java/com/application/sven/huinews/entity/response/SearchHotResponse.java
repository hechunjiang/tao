package com.application.sven.huinews.entity.response;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

/**
 * auther: sunfuyi
 * data: 2018/5/26
 * effect:
 */
public class SearchHotResponse extends BaseResponse {

    private List<SearchList> data;

    public List<SearchList> getData() {
        return data;
    }

    public void setData(List<SearchList> data) {
        this.data = data;
    }

    public static class SearchList {


        /**
         * keyword : 人才
         */

        private String keyword;


        public String getKeyword() {
            return keyword;
        }

        public void setKeyword(String keyword) {
            this.keyword = keyword;
        }
    }
}
