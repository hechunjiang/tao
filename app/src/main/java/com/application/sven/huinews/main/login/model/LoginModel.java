package com.application.sven.huinews.main.login.model;

import com.application.sven.huinews.AppConfig;
import com.application.sven.huinews.config.http.DataCallBack;
import com.application.sven.huinews.config.http.DataResponseCallback;
import com.application.sven.huinews.config.http.MyRetrofit;
import com.application.sven.huinews.db.UserSpCache;
import com.application.sven.huinews.entity.request.BindWxRequest;
import com.application.sven.huinews.entity.request.LoginRequest;
import com.application.sven.huinews.entity.response.BaseResponse;
import com.application.sven.huinews.entity.response.LoginResponse;
import com.application.sven.huinews.main.login.contract.LoginContract;
import com.application.sven.huinews.utils.LogUtil;
import com.google.gson.Gson;

/**
 * auther: sunfuyi
 * data: 2018/5/17
 * effect:
 */
public class LoginModel extends LoginContract.Model {
    @Override
    public void onLogin(LoginRequest request, final DataResponseCallback callback) {
        getRetrofit().onLogin(request, new DataCallBack() {
            @Override
            public void onComplete() {
                callback.onComplete();
            }

            @Override
            public void onSucceed(String json) {
                LoginResponse response = new Gson().fromJson(json, LoginResponse.class);
                callback.onSucceed(response.getData());
            }

            @Override
            public void onFail(BaseResponse baseResponse) {
                callback.onFail(baseResponse);
            }
        });
    }

    @Override
    public void onCheckIsBindWx(BindWxRequest request, final DataResponseCallback callBack) {
        getRetrofit().onCheckIsBindWx(request, new DataCallBack() {
            @Override
            public void onComplete() {
                callBack.onComplete();
            }

            @Override
            public void onSucceed(String json) {
                LoginResponse response = new Gson().fromJson(json, LoginResponse.class);
                callBack.onSucceed(response);
            }

            @Override
            public void onFail(BaseResponse baseResponse) {
                callBack.onFail(baseResponse);
            }
        });
    }
}
