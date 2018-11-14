package com.application.sven.huinews.entity.response;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * auther: sunfuyi
 * data: 2018/8/2
 * effect:
 */
public class GiveGoldResponse extends BaseResponse {

    private GoldData data;

    public GoldData getData() {
        return data;
    }

    public void setData(GoldData data) {
        this.data = data;
    }

    public static class GoldData {
        /**
         * status : true
         * gold : 200
         */

        private boolean status;
        private int gold;


        public boolean isStatus() {
            return status;
        }

        public void setStatus(boolean status) {
            this.status = status;
        }

        public int getGold() {
            return gold;
        }

        public void setGold(int gold) {
            this.gold = gold;
        }
    }


}
