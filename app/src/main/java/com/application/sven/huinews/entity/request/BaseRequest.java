package com.application.sven.huinews.entity.request;

/**
 * auther: sunfuyi
 * data: 2018/5/14
 * effect:
 */
public class BaseRequest {
    private String time;
    private String nonce_str;
    private String sign;
    private String json;
    private String debug = "2312";


   public String getDebug() {
        return debug;
    }

    public void setDebug(String debug) {
        this.debug = debug;
    }

    public String getJson() {
        return json;
    }

    public void setJson(String json) {
        this.json = json;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getNonce_str() {
        return nonce_str;
    }

    public void setNonce_str(String nonce_str) {
        this.nonce_str = nonce_str;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    @Override
    public String toString() {
        return "BaseRequest{" +
                "time='" + time + '\'' +
                ", nonce_str='" + nonce_str + '\'' +
                ", sign='" + sign + '\'' +
                '}';
    }
}
