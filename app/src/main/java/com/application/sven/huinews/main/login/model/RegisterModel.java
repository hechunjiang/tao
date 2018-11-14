package com.application.sven.huinews.main.login.model;

import com.application.sven.huinews.AppConfig;
import com.application.sven.huinews.config.http.DataCallBack;
import com.application.sven.huinews.config.http.DataResponseCallback;
import com.application.sven.huinews.config.http.MyRetrofit;
import com.application.sven.huinews.entity.request.RegisterRequest;
import com.application.sven.huinews.entity.request.SmsCodeRequest;
import com.application.sven.huinews.entity.response.BaseResponse;
import com.application.sven.huinews.entity.response.LoginResponse;
import com.application.sven.huinews.entity.response.RegisterResponse;
import com.application.sven.huinews.main.login.contract.RegisterContract;
import com.application.sven.huinews.utils.LogUtil;
import com.google.gson.Gson;

/**
 * auther: sunfuyi
 * data: 2018/5/23
 * effect:
 */
public class RegisterModel extends RegisterContract.Model {


    @Override
    public void getRegCode(SmsCodeRequest request, final DataCallBack callBack) {
        getRetrofit().onSmsCode(request, new DataCallBack() {
            @Override
            public void onComplete() {
                callBack.onComplete();
            }

            @Override
            public void onSucceed(String json) {
                LogUtil.showJson("getRegCode", json);
                callBack.onSucceed(json);
            }

            @Override
            public void onFail(BaseResponse baseResponse) {
                callBack.onFail(baseResponse);
            }
        });
    }

    @Override
    public void getReg(RegisterRequest request, boolean isWeChat, final DataResponseCallback callback) {
        if (isWeChat) {
            //微信注册
            getRetrofit().onWxRegister(request, new DataCallBack() {
                @Override
                public void onComplete() {
                    callback.onComplete();
                }

                @Override
                public void onSucceed(String json) {
                    RegisterResponse response = new Gson().fromJson(json, RegisterResponse.class);
                    callback.onSucceed(response.getData());
                }

                @Override
                public void onFail(BaseResponse baseResponse) {
                    callback.onFail(baseResponse);
                }
            });
        } else {
            //手机注册
            getRetrofit().onRegister(request, new DataCallBack() {
                @Override
                public void onComplete() {
                    callback.onComplete();
                }

                @Override
                public void onSucceed(String json) {
                    RegisterResponse response = new Gson().fromJson(json, RegisterResponse.class);
                    callback.onSucceed(response.getData());
                }

                @Override
                public void onFail(BaseResponse baseResponse) {
                    callback.onFail(baseResponse);
                }
            });
        }
    }
}
