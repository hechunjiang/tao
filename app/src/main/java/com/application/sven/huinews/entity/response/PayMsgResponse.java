package com.application.sven.huinews.entity.response;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.List;

/**
 * auther: sunfuyi
 * data: 2018/7/13
 * effect:
 */
public class PayMsgResponse extends BaseResponse {
    private PayMsg data;

    public PayMsg getData() {
        return data;
    }

    public void setData(PayMsg data) {
        this.data = data;
    }

    public static class PayMsg implements Serializable {

        /**
         * diamond : 0
         * paymsg : [{"id":9,"name":"10元","description":"100砖石"},{"id":10,"name":"30元","description":"500砖石"},{"id":11,"name":"50元","description":"1000砖石"},{"id":12,"name":"100元","description":"1800砖石"}]
         * payprompt : 1.淘视界充值的砖石只能在淘视界使用
         * 2.充值后砖石余额长时间无变化，请联系客服人员
         */

        private int diamond;
        private String payprompt;
        private List<PaymsgBean> paymsg;

        public static PayMsg objectFromData(String str, String key) {

            try {
                JSONObject jsonObject = new JSONObject(str);

                return new Gson().fromJson(jsonObject.getString(str), PayMsg.class);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return null;
        }

        public int getDiamond() {
            return diamond;
        }

        public void setDiamond(int diamond) {
            this.diamond = diamond;
        }

        public String getPayprompt() {
            return payprompt;
        }

        public void setPayprompt(String payprompt) {
            this.payprompt = payprompt;
        }

        public List<PaymsgBean> getPaymsg() {
            return paymsg;
        }

        public void setPaymsg(List<PaymsgBean> paymsg) {
            this.paymsg = paymsg;
        }

        public static class PaymsgBean {
            /**
             * id : 9
             * name : 10元
             * description : 100砖石
             */

            private int id;
            private String name;
            private String description;
            private int base_diamond;
            private int add_diamond;


            public static PaymsgBean objectFromData(String str, String key) {

                try {
                    JSONObject jsonObject = new JSONObject(str);

                    return new Gson().fromJson(jsonObject.getString(str), PaymsgBean.class);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                return null;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getDescription() {
                return description;
            }

            public void setDescription(String description) {
                this.description = description;
            }

            public int getBase_diamond() {
                return base_diamond;
            }

            public void setBase_diamond(int base_diamond) {
                this.base_diamond = base_diamond;
            }

            public int getAdd_diamond() {
                return add_diamond;
            }

            public void setAdd_diamond(int add_diamond) {
                this.add_diamond = add_diamond;
            }
        }
    }
}
