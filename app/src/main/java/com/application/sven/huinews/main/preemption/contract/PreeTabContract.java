package com.application.sven.huinews.main.preemption.contract;

import com.application.sven.huinews.base.BaseModel;
import com.application.sven.huinews.base.BasePresenter;
import com.application.sven.huinews.base.BaseView;
import com.application.sven.huinews.config.http.DataResponseCallback;
import com.application.sven.huinews.entity.AdsList;
import com.application.sven.huinews.entity.MovieHistory;
import com.application.sven.huinews.entity.request.AdsRequest;
import com.application.sven.huinews.entity.request.MovieListRequest;
import com.application.sven.huinews.entity.request.MovieWatchHistoryRequest;
import com.application.sven.huinews.entity.request.VideoListRequest;
import com.application.sven.huinews.entity.response.MovieContinuedResponse;
import com.application.sven.huinews.entity.response.MovieListResponse;
import com.application.sven.huinews.entity.response.VideoListResponse;
import com.application.sven.huinews.main.home.contract.HomeTabContract;
import com.application.sven.huinews.utils.ads.AdsConfig;
import com.qq.e.ads.nativ.NativeExpressADView;

import java.util.List;

/**
 * auther: sunfuyi
 * data: 2018/5/15
 * effect:
 */
public interface PreeTabContract {
    abstract class Model extends BaseModel {
        /**
         * 视频列表
         *
         * @param request
         * @param callBack
         */
        public abstract void getMovieList(MovieListRequest request, DataResponseCallback callBack);


        public abstract void getMovieHistory(MovieWatchHistoryRequest request, DataResponseCallback callback);

        public abstract void getPreeAds(AdsRequest request, DataResponseCallback callback);

    }

    interface View extends BaseView {
        MovieListRequest movieListRequest();

        void setMovieList(List<MovieListResponse.MovieData> movieList);

        MovieWatchHistoryRequest getMovieWatchHistoryRequest();

        void setMovieHistory(MovieContinuedResponse.Data response);


        void setAdsLit(List<AdsList> ads);

        void setTencentAds(List<NativeExpressADView> list);
    }

    abstract class Presenter extends BasePresenter<PreeTabContract.View, PreeTabContract.Model> {
        //视频列表
        public abstract void onMovieList();

        public abstract void onMovieHistory();

        //广告获取
        public abstract void onPreeAds(int page);

        public abstract void onTencentAds(AdsConfig mAdsConfig, int count);


    }

}
