package com.application.sven.huinews.main.preemption.model;

import com.application.sven.huinews.AppConfig;
import com.application.sven.huinews.config.http.DataCallBack;
import com.application.sven.huinews.config.http.DataResponseCallback;
import com.application.sven.huinews.config.http.MyRetrofit;
import com.application.sven.huinews.entity.request.MovieMoreListRequest;
import com.application.sven.huinews.entity.response.BaseResponse;
import com.application.sven.huinews.entity.response.MoreMovieListResponse;
import com.application.sven.huinews.entity.response.MovieListResponse;
import com.application.sven.huinews.main.preemption.contract.MoreMovieContract;
import com.application.sven.huinews.utils.LogUtil;
import com.google.gson.Gson;

/**
 * auther: sunfuyi
 * data: 2018/5/25
 * effect:
 */
public class MoreMovieModel extends MoreMovieContract.Model {
    @Override
    public void getMoreMovie(MovieMoreListRequest request, final DataResponseCallback callback) {
        getRetrofit().onMovieMoreList(request, new DataCallBack() {
            @Override
            public void onComplete() {
                callback.onComplete();
            }

            @Override
            public void onSucceed(String json) {
                LogUtil.showJson("getMoreMovie", json);
                MoreMovieListResponse response = new Gson().fromJson(json,MoreMovieListResponse.class);
                callback.onSucceed(response.getData());
            }

            @Override
            public void onFail(BaseResponse baseResponse) {
                callback.onFail(baseResponse);
            }
        });
    }
}
