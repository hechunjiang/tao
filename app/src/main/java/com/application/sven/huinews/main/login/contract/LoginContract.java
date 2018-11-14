package com.application.sven.huinews.main.login.contract;

import com.application.sven.huinews.base.BaseModel;
import com.application.sven.huinews.base.BasePresenter;
import com.application.sven.huinews.base.BaseView;
import com.application.sven.huinews.config.http.DataCallBack;
import com.application.sven.huinews.config.http.DataResponseCallback;
import com.application.sven.huinews.entity.request.BindWxRequest;
import com.application.sven.huinews.entity.request.LoginRequest;
import com.application.sven.huinews.mob.login.WechatLoginResponse;

/**
 * auther: sunfuyi
 * data: 2018/5/17
 * effect:
 */
public interface LoginContract {

    abstract class Model extends BaseModel {
        public abstract void onLogin(LoginRequest request, DataResponseCallback callback);

        public abstract void onCheckIsBindWx(BindWxRequest request, DataResponseCallback callBack);
    }

    interface View extends BaseView {
        LoginRequest getLoginRequest();

        void onWechatLoginSucceed(WechatLoginResponse response);

        void onLoginSucceed();


    }

    abstract class Presenter extends BasePresenter<LoginContract.View, LoginContract.Model> {
        public abstract void onLogin();

        public abstract void onWechatLogin();
    }
}
