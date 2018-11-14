package com.application.sven.huinews.main.my.model;

import com.application.sven.huinews.AppConfig;
import com.application.sven.huinews.config.http.DataCallBack;
import com.application.sven.huinews.config.http.DataResponseCallback;
import com.application.sven.huinews.config.http.MyRetrofit;
import com.application.sven.huinews.entity.UserInfo;
import com.application.sven.huinews.entity.request.ActiavtePageRequest;
import com.application.sven.huinews.entity.request.MovieWatchHistoryRequest;
import com.application.sven.huinews.entity.response.ActiavtePageResponse;
import com.application.sven.huinews.entity.response.BaseResponse;
import com.application.sven.huinews.entity.response.MovieHistoryResponse;
import com.application.sven.huinews.entity.response.UserFlagResponse;
import com.application.sven.huinews.entity.response.UserHitsResponse;
import com.application.sven.huinews.entity.response.UserInfoResponse;
import com.application.sven.huinews.main.my.contract.UserIndexContract;
import com.application.sven.huinews.utils.LogUtil;
import com.google.gson.Gson;

/**
 * auther: sunfuyi
 * data: 2018/5/23
 * effect:
 */
public class UserIndexModel extends UserIndexContract.Model {
    @Override
    public void getUserInfo(final DataResponseCallback callback) {
        getRetrofit().onUserIndex(new DataCallBack() {
            @Override
            public void onComplete() {
                callback.onComplete();
            }

            @Override
            public void onSucceed(String json) {
                UserInfoResponse response = new Gson().fromJson(json, UserInfoResponse.class);
                callback.onSucceed(response.getData());
            }

            @Override
            public void onFail(BaseResponse baseResponse) {
                callback.onFail(baseResponse);
            }
        });
    }

    @Override
    public void getUserFlag(final DataResponseCallback callback) {
        getRetrofit().onUserFlag(new DataCallBack() {
            @Override
            public void onComplete() {
                callback.onComplete();
            }

            @Override
            public void onSucceed(String json) {
                UserFlagResponse response = new Gson().fromJson(json, UserFlagResponse.class);
                callback.onSucceed(response.getData());
            }

            @Override
            public void onFail(BaseResponse baseResponse) {
                callback.onFail(baseResponse);
            }
        });
    }

    @Override
    public void getMovieHistory(MovieWatchHistoryRequest request, final DataResponseCallback callback) {
        getRetrofit().onMovieHistory(request, new DataCallBack() {
            @Override
            public void onComplete() {
                callback.onComplete();
            }

            @Override
            public void onSucceed(String json) {
                MovieHistoryResponse response = new Gson().fromJson(json, MovieHistoryResponse.class);
                callback.onSucceed(response.getData());
            }

            @Override
            public void onFail(BaseResponse baseResponse) {
                callback.onFail(baseResponse);
            }
        });
    }

    @Override
    public void getActivePage(ActiavtePageRequest request, final DataResponseCallback callback) {
        getRetrofit().onActiavtePage(request, new DataCallBack() {
            @Override
            public void onComplete() {
                callback.onComplete();
            }

            @Override
            public void onSucceed(String json) {
                ActiavtePageResponse response = new Gson().fromJson(json, ActiavtePageResponse.class);
                callback.onSucceed(response.getData());

            }

            @Override
            public void onFail(BaseResponse baseResponse) {
                callback.onFail(baseResponse);
            }
        });
    }

    @Override
    public void getDotInfo(final DataResponseCallback callback) {
        getRetrofit().onHitsInfo(new DataCallBack() {
            @Override
            public void onComplete() {
                callback.onComplete();
            }

            @Override
            public void onSucceed(String json) {
                LogUtil.showJson("getDotInfo", json);
                UserHitsResponse response = new Gson().fromJson(json, UserHitsResponse.class);
                callback.onSucceed(response);
            }

            @Override
            public void onFail(BaseResponse baseResponse) {
                callback.onFail(baseResponse);
            }
        });
    }
}
