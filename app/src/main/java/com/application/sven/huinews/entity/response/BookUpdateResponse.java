package com.application.sven.huinews.entity.response;

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
public class BookUpdateResponse {

    private BookUpdate data;

    public BookUpdate getData() {
        return data;
    }

    public void setData(BookUpdate data) {
        this.data = data;
    }

    public static class BookUpdate implements Serializable {
        private List<BookList> data;
        private List<BookUpdateWeek> week;

        public List<BookList> getData() {
            return data;
        }

        public void setData(List<BookList> data) {
            this.data = data;
        }

        public List<BookUpdateWeek> getWeek() {
            return week;
        }

        public void setWeek(List<BookUpdateWeek> week) {
            this.week = week;
        }
    }


}
