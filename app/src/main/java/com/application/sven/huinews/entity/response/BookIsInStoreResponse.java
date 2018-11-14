package com.application.sven.huinews.entity.response;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * auther: sunfuyi
 * data: 2018/7/13
 * effect:
 */
public class BookIsInStoreResponse extends BaseResponse {

    private BookIsInStore data;

    public BookIsInStore getData() {
        return data;
    }

    public void setData(BookIsInStore data) {
        this.data = data;
    }

    public static class BookIsInStore {
        /**
         * status : false
         */

        private boolean status;


        public boolean isStatus() {
            return status;
        }

        public void setStatus(boolean status) {
            this.status = status;
        }
    }


}
