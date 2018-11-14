package com.application.sven.huinews.entity.response;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

/**
 * auther: sunfuyi
 * data: 2018/7/14
 * effect:
 */
public class PayInfoResponse extends BaseResponse {

    private PayInfo data;

    public PayInfo getData() {
        return data;
    }

    public void setData(PayInfo data) {
        this.data = data;
    }

    public static class PayInfo implements Serializable {
        /**
         * appid : wxa23177a56a15d5cb
         * partnerid : 1508366811
         * prepayid : wx20180714135422216436
         * timestamp : 1531547662
         * noncestr : aHG9sX0JLMoeoCJr
         * package : Sign=WXPay
         * sign : 0014ACA6E49B2F53FBE45AAAC060E2B6
         */

        private String appid;
        private String partnerid;
        private String prepayid;
        private String timestamp;
        private String noncestr;
        @SerializedName("package")
        private String packageX;
        private String sign;


        public String getAppid() {
            return appid;
        }

        public void setAppid(String appid) {
            this.appid = appid;
        }

        public String getPartnerid() {
            return partnerid;
        }

        public void setPartnerid(String partnerid) {
            this.partnerid = partnerid;
        }

        public String getPrepayid() {
            return prepayid;
        }

        public void setPrepayid(String prepayid) {
            this.prepayid = prepayid;
        }

        public String getTimestamp() {
            return timestamp;
        }

        public void setTimestamp(String timestamp) {
            this.timestamp = timestamp;
        }

        public String getNoncestr() {
            return noncestr;
        }

        public void setNoncestr(String noncestr) {
            this.noncestr = noncestr;
        }

        public String getPackageX() {
            return packageX;
        }

        public void setPackageX(String packageX) {
            this.packageX = packageX;
        }

        public String getSign() {
            return sign;
        }

        public void setSign(String sign) {
            this.sign = sign;
        }

        @Override
        public String toString() {
            return "PayInfo{" +
                    "appid='" + appid + '\'' +
                    ", partnerid='" + partnerid + '\'' +
                    ", prepayid='" + prepayid + '\'' +
                    ", timestamp='" + timestamp + '\'' +
                    ", noncestr='" + noncestr + '\'' +
                    ", packageX='" + packageX + '\'' +
                    ", sign='" + sign + '\'' +
                    '}';
        }
    }


    @Override
    public String toString() {
        return "PayInfoResponse{" +
                "data=" + data +
                '}';
    }
}
