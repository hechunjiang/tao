package com.application.sven.huinews.main.login.presenter;

import com.application.sven.huinews.config.http.DataCallBack;
import com.application.sven.huinews.config.http.DataResponseCallback;
import com.application.sven.huinews.db.UserSpCache;
import com.application.sven.huinews.entity.User;
import com.application.sven.huinews.entity.request.BindWxRequest;
import com.application.sven.huinews.entity.request.LoginRequest;
import com.application.sven.huinews.entity.response.BaseResponse;
import com.application.sven.huinews.entity.response.LoginResponse;
import com.application.sven.huinews.main.login.contract.LoginContract;
import com.application.sven.huinews.mob.login.WechatLogin;
import com.application.sven.huinews.mob.login.WechatLoginCallBack;
import com.application.sven.huinews.mob.login.WechatLoginResponse;
import com.application.sven.huinews.utils.LogUtil;
import com.application.sven.huinews.utils.ToastUtils;
import com.google.gson.Gson;

/**
 * auther: sunfuyi
 * data: 2018/5/17
 * effect:
 */
public class LoginPresenter extends LoginContract.Presenter {
    @Override
    public void onLogin() {
        final LoginRequest request = mView.getLoginRequest();
        mModel.onLogin(request, new DataResponseCallback<LoginResponse.Data>() {

            @Override
            public void onSucceed(LoginResponse.Data data) {
                UserSpCache.getInstance(mContext).putString(UserSpCache.KEY_TICKET, data.getLoginTicket());
                UserSpCache.getInstance(mContext).putString(UserSpCache.KEY_PHONE, request.getAccount());
                mView.onLoginSucceed();
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

    @Override
    public void onWechatLogin() {
        new WechatLogin(mContext).wechatLogin(new WechatLoginCallBack() {
            @Override
            public void onResponse(final WechatLoginResponse response) {
                LogUtil.showJson("onWechatLogin", response.toString());
                if (response.getCode() != 200) {
                    //  ToastUtils.showLong(mContext, "微信授权失败，请用手机号登录");
                    mView.showErrorTip(response.getCode(), "微信授权失败，请用手机号登录");
                    return;
                }

                BindWxRequest request = new BindWxRequest();
                request.setHeadImg(response.getUserIcon());
                request.setOpenId(response.getOpenId());
                request.setSex(response.getSex().equals("0") ? "1" : "2");
                request.setUserName(response.getUserName());
                request.setUnionid(response.getUnionid());
                UserSpCache.getInstance(mContext).putString(UserSpCache.WX_UNIONID, response.getUnionid());

                mModel.onCheckIsBindWx(request, new DataResponseCallback<LoginResponse>() {
                    @Override
                    public void onComplete() {

                    }

                    @Override
                    public void onSucceed(LoginResponse loginResponse) {
                        UserSpCache.getInstance(mContext).putString(UserSpCache.KEY_TICKET, loginResponse.getData().getLoginTicket());
                        UserSpCache.getInstance(mContext).putString(UserSpCache.KEY_PHONE, loginResponse.getData().getUser().getName());
                        mView.onLoginSucceed();
                    }

                    @Override
                    public void onFail(BaseResponse baseResponse) {
                        if (baseResponse.getCode() == DataCallBack.WECHAT_BIND_PHONE) {
                            mView.onWechatLoginSucceed(response);
                        }
                        mView.showErrorTip(baseResponse.getCode(), baseResponse.getMsg());
                    }
                });
            }
        });
    }
}
