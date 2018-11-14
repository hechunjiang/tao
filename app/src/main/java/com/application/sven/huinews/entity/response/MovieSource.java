package com.application.sven.huinews.entity.response;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * auther: sunfuyi
 * data: 2018/5/18
 * effect:
 */
public class MovieSource extends BaseResponse {


    /**
     * source : http://pcvideogs.titan.mgtv.com/c1/2018/01/26_0/A875B409BA0659A981F9DC5AC5DC04FC_20180126_1_1_1256.mp4?arange=0&pm=2RDUO_sn_wWNHOXVs9HfP0MjI~9Qy_WWwYqMbfzewOM6xJTzYIV6JfAT69jpjRyFFZ0suCckAg28TtvO5784vQcIMKbNZ_2BmDRZmIgwTayIBY~4Mzf72_rz4wLLnzusQvcMjP_lQcwRD~kbFWAf3GYUyKdU~V1jbvbPH2G8zQXs~Qf1ysKrknHoQsxwbv~GFoeXbBAvnvvjedhOfrSz0iHEQIRybcZBAPrPeiyesLwBWeMZFjmU2MidlyEfCyOW~GKdrgyRkKU3QKKbVIJHgl~5qgKQBzlSf~ok3HIv9HGahyzPGwxVwmeZem3TO7YpB3VNE857Kn_DhJQQ~Pe~Nm~TiBkON_LHWn~CWR_dZsW0RWAGtCqgsOwPvocTEyEyBXEYl_3ejn0RJgzX05wLmA--
     * status : 1
     * pay_type : share
     * pay_gold : 0
     * pay_msg : 分享后继续观看
     * free_time : 600
     */

    private String source;
    private int status;
    private String pay_type;
    private int pay_gold;
    private String pay_msg;
    private int free_time;



    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getPay_type() {
        return pay_type;
    }

    public void setPay_type(String pay_type) {
        this.pay_type = pay_type;
    }

    public int getPay_gold() {
        return pay_gold;
    }

    public void setPay_gold(int pay_gold) {
        this.pay_gold = pay_gold;
    }

    public String getPay_msg() {
        return pay_msg;
    }

    public void setPay_msg(String pay_msg) {
        this.pay_msg = pay_msg;
    }

    public int getFree_time() {
        return free_time;
    }

    public void setFree_time(int free_time) {
        this.free_time = free_time;
    }

    @Override
    public String toString() {
        return "MovieSource{" +
                "source='" + source + '\'' +
                ", status=" + status +
                ", pay_type='" + pay_type + '\'' +
                ", pay_gold=" + pay_gold +
                ", pay_msg='" + pay_msg + '\'' +
                ", free_time=" + free_time +
                '}';
    }
}
