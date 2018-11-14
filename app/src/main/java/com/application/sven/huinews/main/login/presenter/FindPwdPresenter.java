package com.application.sven.huinews.main.login.presenter;

import com.application.sven.huinews.config.http.DataCallBack;
import com.application.sven.huinews.entity.request.FindPassRequest;
import com.application.sven.huinews.entity.request.SmsCodeRequest;
import com.application.sven.huinews.entity.response.BaseResponse;
import com.application.sven.huinews.main.login.contract.FindPwdContract;

/**
 * auther: sunfuyi
 * data: 2018/6/8
 * effect:
 */
public class FindPwdPresenter extends FindPwdContract.Presenter {
    @Override
    public void onFindPwd() {
        FindPassRequest request = mView.getFindPassRequest();
        mModel.findPwd(request, new DataCallBack() {
            @Override
            public void onComplete() {

            }

            @Override
            public void onSucceed(String json) {
                mView.onFindPwdSucceed();
            }

            @Override
            public void onFail(BaseResponse baseResponse) {
                mView.showErrorTip(baseResponse.getCode(), baseResponse.getMsg());
            }
        });
    }

    @Override
    public void onRegCode() {
        SmsCodeRequest request = mView.getSmsCodeRequest();
        mModel.getRegCode(request, new DataCallBack() {
            @Override
            public void onComplete() {

            }

            @Override
            public void onSucceed(String json) {
                mView.getSmsCodeSucceed();
            }

            @Override
            public void onFail(BaseResponse baseResponse) {
                mView.showErrorTip(baseResponse.getCode(), baseResponse.getMsg());
            }
        });
    }
}
