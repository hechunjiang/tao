package com.application.sven.huinews.main.login.model;

import com.application.sven.huinews.AppConfig;
import com.application.sven.huinews.base.BaseModel;
import com.application.sven.huinews.config.http.DataCallBack;
import com.application.sven.huinews.config.http.MyRetrofit;
import com.application.sven.huinews.entity.request.FindPassRequest;
import com.application.sven.huinews.entity.request.SmsCodeRequest;
import com.application.sven.huinews.entity.response.BaseResponse;
import com.application.sven.huinews.main.login.contract.FindPwdContract;

/**
 * auther: sunfuyi
 * data: 2018/6/8
 * effect:
 */
public class FindPwdModel extends FindPwdContract.Model {

    @Override
    public void findPwd(FindPassRequest request, final DataCallBack callback) {
        getRetrofit().onFindPass(request, new DataCallBack() {
            @Override
            public void onComplete() {
                callback.onComplete();
            }

            @Override
            public void onSucceed(String json) {
                callback.onSucceed(json);
            }

            @Override
            public void onFail(BaseResponse baseResponse) {
                callback.onFail(baseResponse);
            }
        });
    }

    @Override
    public void getRegCode(SmsCodeRequest request, final DataCallBack callBack) {
        getRetrofit().onSmsCode(request, new DataCallBack() {
            @Override
            public void onComplete() {
                callBack.onComplete();
            }

            @Override
            public void onSucceed(String json) {
                callBack.onSucceed(json);
            }

            @Override
            public void onFail(BaseResponse baseResponse) {
                callBack.onFail(baseResponse);
            }
        });
    }
}
