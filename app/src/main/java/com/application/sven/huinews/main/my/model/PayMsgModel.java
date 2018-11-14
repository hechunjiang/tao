package com.application.sven.huinews.main.my.model;

import com.application.sven.huinews.config.api.Api;
import com.application.sven.huinews.config.http.DataCallBack;
import com.application.sven.huinews.config.http.DataResponseCallback;
import com.application.sven.huinews.entity.request.PayInfoRequest;
import com.application.sven.huinews.entity.response.BaseResponse;
import com.application.sven.huinews.entity.response.PayInfoResponse;
import com.application.sven.huinews.entity.response.PayMsgResponse;
import com.application.sven.huinews.main.my.contract.PayMsgContract;
import com.application.sven.huinews.utils.LogUtil;
import com.google.gson.Gson;

/**
 * auther: sunfuyi
 * data: 2018/7/13
 * effect:
 */
public class PayMsgModel extends PayMsgContract.Model {
    @Override
    public void onPayMsg(final DataResponseCallback callback) {
        getRetrofit().onPayMsg(new DataCallBack() {
            @Override
            public void onComplete() {
                callback.onComplete();
            }

            @Override
            public void onSucceed(String json) {
                PayMsgResponse response = new Gson().fromJson(json, PayMsgResponse.class);
                callback.onSucceed(response.getData());
            }

            @Override
            public void onFail(BaseResponse baseResponse) {
                callback.onFail(baseResponse);
            }
        });
    }

    @Override
    public void onPayInfo(PayInfoRequest request, final DataResponseCallback callback) {
        getRetrofit().onPayInfo(request, new DataCallBack() {
            @Override
            public void onComplete() {
                callback.onComplete();
            }

            @Override
            public void onSucceed(String json) {
                PayInfoResponse response = new Gson().fromJson(json, PayInfoResponse.class);
                callback.onSucceed(response.getData());
                LogUtil.showLog("onPayInfo" + json);
                LogUtil.showLog("onPayInfo  response:" + response.toString());
            }

            @Override
            public void onFail(BaseResponse baseResponse) {
                callback.onFail(baseResponse);
            }
        });
    }
}
