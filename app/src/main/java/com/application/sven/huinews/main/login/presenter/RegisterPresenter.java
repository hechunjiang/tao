package com.application.sven.huinews.main.login.presenter;

import com.application.sven.huinews.R;
import com.application.sven.huinews.config.http.DataCallBack;
import com.application.sven.huinews.config.http.DataResponseCallback;
import com.application.sven.huinews.db.UserSpCache;
import com.application.sven.huinews.entity.request.RegisterRequest;
import com.application.sven.huinews.entity.request.SmsCodeRequest;
import com.application.sven.huinews.entity.response.BaseResponse;
import com.application.sven.huinews.entity.response.RegisterResponse;
import com.application.sven.huinews.main.login.contract.RegisterContract;
import com.application.sven.huinews.utils.ToastUtils;

/**
 * auther: sunfuyi
 * data: 2018/5/23
 * effect:
 */
public class RegisterPresenter extends RegisterContract.Presenter {
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

    @Override
    public void onReg(boolean isWeChat) {
        final RegisterRequest request = mView.getRegisterRequest();
        mModel.getReg(request, isWeChat, new DataResponseCallback<RegisterResponse.Data>() {

            @Override
            public void onSucceed(RegisterResponse.Data data) {
                ToastUtils.showLong(mContext, mContext.getString(R.string.register_ok));
                UserSpCache.getInstance(mContext).putString(UserSpCache.KEY_TICKET, data.getLoginTicket());
                UserSpCache.getInstance(mContext).putString(UserSpCache.KEY_PHONE, request.getPhone());
                mView.onRegisterSucceed();
            }

            @Override
            public void onFail(BaseResponse response) {
                mView.showErrorTip(response.getCode(), response.getMsg());
            }

            @Override
            public void onComplete() {

            }
        });
    }
}
