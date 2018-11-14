package com.application.sven.huinews.main.my.model;

import com.application.sven.huinews.AppConfig;
import com.application.sven.huinews.config.http.DataCallBack;
import com.application.sven.huinews.config.http.DataResponseCallback;
import com.application.sven.huinews.config.http.MyRetrofit;
import com.application.sven.huinews.entity.request.FollowListRequest;
import com.application.sven.huinews.entity.request.UserMsgRequest;
import com.application.sven.huinews.entity.response.BaseResponse;
import com.application.sven.huinews.entity.response.FollowListResponse;
import com.application.sven.huinews.main.my.contract.FollowContract;
import com.application.sven.huinews.utils.LogUtil;
import com.google.gson.Gson;

/**
 * auther: sunfuyi
 * data: 2018/6/1
 * effect:
 */
public class FollowListModel extends FollowContract.Model {
    @Override
    public void getFollowList(FollowListRequest request, final DataResponseCallback callback) {
        getRetrofit().onFollowList(request, new DataCallBack() {
            @Override
            public void onComplete() {
                callback.onComplete();
            }

            @Override
            public void onSucceed(String json) {
                LogUtil.showJson("getFollowList", json);
                FollowListResponse response = new Gson().fromJson(json, FollowListResponse.class);
                callback.onSucceed(response.getData());
            }

            @Override
            public void onFail(BaseResponse baseResponse) {
                callback.onFail(baseResponse);
            }
        });
    }

    @Override
    public void setFollowUser(UserMsgRequest request, DataResponseCallback callback) {

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
                callback.onSucceed(json);
            }

            @Override
            public void onFail(BaseResponse baseResponse) {
                callback.onFail(baseResponse);
            }
        });
    }
}
