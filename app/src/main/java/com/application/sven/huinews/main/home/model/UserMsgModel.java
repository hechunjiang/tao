package com.application.sven.huinews.main.home.model;

import com.application.sven.huinews.AppConfig;
import com.application.sven.huinews.config.http.DataCallBack;
import com.application.sven.huinews.config.http.DataResponseCallback;
import com.application.sven.huinews.config.http.MyRetrofit;
import com.application.sven.huinews.entity.request.UserMsgRequest;
import com.application.sven.huinews.entity.request.UserVideoRequest;
import com.application.sven.huinews.entity.response.BaseResponse;
import com.application.sven.huinews.entity.response.UserInfoMsg;
import com.application.sven.huinews.entity.response.UserVideoResponse;
import com.application.sven.huinews.main.home.contract.UserMsgContract;
import com.application.sven.huinews.utils.LogUtil;
import com.google.gson.Gson;

/**
 * auther: sunfuyi
 * data: 2018/5/18
 * effect:
 */
public class UserMsgModel extends UserMsgContract.Model {

    @Override
    public void getUserMsg(UserMsgRequest request, final DataResponseCallback callBack) {
        getRetrofit().onUserMsg(request, new DataCallBack() {
            @Override
            public void onComplete() {
                callBack.onComplete();
            }

            @Override
            public void onSucceed(String json) {
                UserInfoMsg mUserInfo = new Gson().fromJson(json, UserInfoMsg.class);
                LogUtil.showJson("getUserMsg", json);
                callBack.onSucceed(mUserInfo.getData());
            }

            @Override
            public void onFail(BaseResponse baseResponse) {
                callBack.onFail(baseResponse);
            }
        });
    }

    @Override
    public void getUserVideo(UserVideoRequest request, final DataResponseCallback callBack) {
        getRetrofit().onUserVideo(request, new DataCallBack() {
            @Override
            public void onComplete() {
                callBack.onComplete();
            }

            @Override
            public void onSucceed(String json) {
                LogUtil.showJson("getUserVideo", json);
                UserVideoResponse response = new Gson().fromJson(json, UserVideoResponse.class);
                callBack.onSucceed(response.getData());
            }

            @Override
            public void onFail(BaseResponse baseResponse) {
                callBack.onFail(baseResponse);
            }
        });
    }

    @Override
    public void setFollowUser(UserMsgRequest request, final DataResponseCallback callback) {
        getRetrofit().onFollowUser(request, new DataCallBack() {
            @Override
            public void onComplete() {
                callback.onComplete();
            }

            @Override
            public void onSucceed(String json) {
                LogUtil.showJson("setFollowUser", json);
            }

            @Override
            public void onFail(BaseResponse baseResponse) {
                callback.onFail(baseResponse);
            }
        });
    }

    @Override
    public void setCancelFollowUser(UserMsgRequest request, final DataResponseCallback callback) {
        getRetrofit().onCancelFollowUser(request, new DataCallBack() {
            @Override
            public void onComplete() {
                callback.onComplete();
            }

            @Override
            public void onSucceed(String json) {
                LogUtil.showJson("setCancelFollowUser", json);
                callback.onSucceed(json);
            }

            @Override
            public void onFail(BaseResponse baseResponse) {
                callback.onFail(baseResponse);
            }
        });
    }
}
