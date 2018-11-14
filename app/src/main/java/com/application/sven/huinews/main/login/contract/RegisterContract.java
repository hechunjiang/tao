package com.application.sven.huinews.main.login.contract;

import com.application.sven.huinews.base.BaseModel;
import com.application.sven.huinews.base.BasePresenter;
import com.application.sven.huinews.base.BaseView;
import com.application.sven.huinews.config.http.DataCallBack;
import com.application.sven.huinews.config.http.DataResponseCallback;
import com.application.sven.huinews.entity.request.RegisterRequest;
import com.application.sven.huinews.entity.request.SmsCodeRequest;
import com.application.sven.huinews.mob.login.WechatLoginResponse;

/**
 * auther: sunfuyi
 * data: 2018/5/23
 * effect:
 */
public interface RegisterContract {
    abstract class Model extends BaseModel {
        public abstract void getRegCode(SmsCodeRequest request, DataCallBack callBack);

        public abstract void getReg(RegisterRequest request, boolean isWechat, DataResponseCallback callback);

    }

    interface View extends BaseView {
        SmsCodeRequest getSmsCodeRequest();

        RegisterRequest getRegisterRequest();

        void getSmsCodeSucceed();

        void onRegisterSucceed();

    }

    abstract class Presenter extends BasePresenter<RegisterContract.View, RegisterContract.Model> {
        public abstract void onRegCode();

        public abstract void onReg(boolean isWechat);
    }
}
