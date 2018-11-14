package com.application.sven.huinews.entity.response;

import com.application.sven.huinews.entity.ActiveIcon;
import com.application.sven.huinews.entity.PushTask;
import com.google.gson.annotations.SerializedName;

/**
 * auther: sunfuyi
 * data: 2018/6/2
 * effect:
 */
public class PushTaskResponse extends BaseResponse {
    private Data data;

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public static class Data {
        @SerializedName("active_push")
        private PushTask data;
        private ActiveIcon active_icon;

        public PushTask getData() {
            return data;
        }


        public void setData(PushTask data) {
            this.data = data;
        }


        public ActiveIcon getActive_icon() {
            return active_icon;
        }

        public void setActive_icon(ActiveIcon active_icon) {
            this.active_icon = active_icon;
        }
    }
}
