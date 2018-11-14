package com.application.sven.huinews.main.login.contract;

import com.application.sven.huinews.base.BaseModel;
import com.application.sven.huinews.base.BasePresenter;
import com.application.sven.huinews.base.BaseView;
import com.application.sven.huinews.config.http.DataCallBack;
import com.application.sven.huinews.config.http.DataResponseCallback;
import com.application.sven.huinews.entity.request.BindWxRequest;
import com.application.sven.huinews.entity.request.FindPassRequest;
import com.application.sven.huinews.entity.request.LoginRequest;
import com.application.sven.huinews.entity.request.SmsCodeRequest;
import com.application.sven.huinews.mob.login.WechatLoginResponse;

/**
 * auther: sunfuyi
 * data: 2018/6/8
 * effect:
 */
public interface FindPwdContract {
    abstract class Model extends BaseModel {
        public abstract void findPwd(FindPassRequest request, DataCallBack callback);

        public abstract void getRegCode(SmsCodeRequest request, DataCallBack callBack);

    }

    interface View extends BaseView {
        FindPassRequest getFindPassRequest();

        SmsCodeRequest getSmsCodeRequest();

        void getSmsCodeSucceed();

        void onFindPwdSucceed();

    }

    abstract class Presenter extends BasePresenter<FindPwdContract.View, FindPwdContract.Model> {
        public abstract void onFindPwd();

        public abstract void onRegCode();

    }
}
