package com.application.sven.huinews.main.preemption.model;

import com.application.sven.huinews.AppConfig;
import com.application.sven.huinews.config.http.DataCallBack;
import com.application.sven.huinews.config.http.DataResponseCallback;
import com.application.sven.huinews.config.http.MyRetrofit;
import com.application.sven.huinews.entity.request.AdsRequest;
import com.application.sven.huinews.entity.request.MovieListRequest;
import com.application.sven.huinews.entity.request.MovieWatchHistoryRequest;
import com.application.sven.huinews.entity.request.VideoListRequest;
import com.application.sven.huinews.entity.response.AdsReponse;
import com.application.sven.huinews.entity.response.BaseResponse;
import com.application.sven.huinews.entity.response.MovieContinuedResponse;
import com.application.sven.huinews.entity.response.MovieHistoryResponse;
import com.application.sven.huinews.entity.response.MovieListResponse;
import com.application.sven.huinews.main.preemption.contract.PreeTabContract;
import com.application.sven.huinews.utils.LogUtil;
import com.google.gson.Gson;

/**
 * auther: sunfuyi
 * data: 2018/5/15
 * effect:
 */
public class PreeTabModel extends PreeTabContract.Model {

    @Override
    public void getMovieList(MovieListRequest request, final DataResponseCallback callBack) {
        getRetrofit().onMovieList(request, new DataCallBack() {
            @Override
            public void onComplete() {
                callBack.onComplete();
            }

            @Override
            public void onSucceed(String json) {
                LogUtil.showJson("getMovieList", json);
                MovieListResponse response = new Gson().fromJson(json, MovieListResponse.class);
                callBack.onSucceed(response.getData());
            }

            @Override
            public void onFail(BaseResponse baseResponse) {
                callBack.onFail(baseResponse);
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
                MovieContinuedResponse response = new Gson().fromJson(json, MovieContinuedResponse.class);
                callback.onSucceed(response);
            }

            @Override
            public void onFail(BaseResponse baseResponse) {
                callback.onFail(baseResponse);
            }
        });
    }

    @Override
    public void getPreeAds(AdsRequest request, final DataResponseCallback callback) {
        getRetrofit().onAds(request, new DataCallBack() {
            @Override
            public void onComplete() {
                callback.onComplete();
            }

            @Override
            public void onSucceed(String json) {
                LogUtil.showJson("getPreeAds", json);
                AdsReponse reponse = new Gson().fromJson(json, AdsReponse.class);
                callback.onSucceed(reponse);
            }

            @Override
            public void onFail(BaseResponse baseResponse) {
                callback.onFail(baseResponse);
            }
        });
    }
}
