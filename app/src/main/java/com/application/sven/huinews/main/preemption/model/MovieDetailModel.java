package com.application.sven.huinews.main.preemption.model;

import com.application.sven.huinews.AppConfig;
import com.application.sven.huinews.config.http.DataCallBack;
import com.application.sven.huinews.config.http.DataResponseCallback;
import com.application.sven.huinews.config.http.MyRetrofit;
import com.application.sven.huinews.entity.request.AdsRequest;
import com.application.sven.huinews.entity.request.MovieDetailRequest;
import com.application.sven.huinews.entity.request.MoviePayRequest;
import com.application.sven.huinews.entity.request.MovieResPlayRequest;
import com.application.sven.huinews.entity.request.MovieShareRequest;
import com.application.sven.huinews.entity.request.MovieVisitRequest;
import com.application.sven.huinews.entity.request.ShareVisitRequest;
import com.application.sven.huinews.entity.request.VideoCollectionCancelRequest;
import com.application.sven.huinews.entity.request.VideoCollectionRequest;
import com.application.sven.huinews.entity.request.VideoReportRequest;
import com.application.sven.huinews.entity.request.VideoShareUrlRequest;
import com.application.sven.huinews.entity.response.AdsReponse;
import com.application.sven.huinews.entity.response.BaseResponse;
import com.application.sven.huinews.entity.response.DataResponse;
import com.application.sven.huinews.entity.response.MovieDetailResponse;
import com.application.sven.huinews.entity.response.MovieShareResponse;
import com.application.sven.huinews.entity.response.MovieSource;
import com.application.sven.huinews.entity.response.VideoShareUrlResponse;
import com.application.sven.huinews.main.preemption.contract.MovieDetailContract;
import com.application.sven.huinews.utils.AesUtil;
import com.application.sven.huinews.utils.LogUtil;
import com.google.gson.Gson;

/**
 * auther: sunfuyi
 * data: 2018/5/17
 * effect:
 */
public class MovieDetailModel extends MovieDetailContract.Model {
    @Override
    public void getMovieDetail(MovieDetailRequest request, final DataResponseCallback callBack) {
        getRetrofit().onMovieDetail(request, new DataCallBack() {
            @Override
            public void onComplete() {
                callBack.onComplete();
            }

            @Override
            public void onSucceed(String json) {
                LogUtil.showJson("MovieDetailModel", json);
                MovieDetailResponse response = new Gson().fromJson(json, MovieDetailResponse.class);
                callBack.onSucceed(response.getData());
            }

            @Override
            public void onFail(BaseResponse baseResponse) {
                callBack.onFail(baseResponse);
            }
        });
    }

    @Override
    public void getMovieResPlay(final MovieResPlayRequest request, final DataResponseCallback callBack) {
        getRetrofit().onMovieResPlay(request, new DataCallBack() {
            @Override
            public void onComplete() {
                callBack.onComplete();
            }

            @Override
            public void onSucceed(String json) {
                String source = "";
                try {
                    DataResponse response = new Gson().fromJson(json, DataResponse.class);
                    source = AesUtil.desEncrypt(response.getData());


                } catch (Exception e) {
                    e.printStackTrace();
                }

                MovieSource mMovieSource = new Gson().fromJson(source, MovieSource.class);
                LogUtil.showLog("msg---解析："+mMovieSource.toString());
                callBack.onSucceed(mMovieSource);
            }

            @Override
            public void onFail(BaseResponse baseResponse) {
                callBack.onFail(baseResponse);
            }
        });
    }

    @Override
    public void getMovieCollection(VideoCollectionRequest request, final DataCallBack callBack) {
        getRetrofit().onVideoCollection(request, new DataCallBack() {
            @Override
            public void onComplete() {
                callBack.onComplete();
            }

            @Override
            public void onSucceed(String json) {
                LogUtil.showJson("getMovieCollection", json);
            }

            @Override
            public void onFail(BaseResponse baseResponse) {
                callBack.onFail(baseResponse);
            }
        });
    }

    @Override
    public void setCancelMovieCollection(VideoCollectionCancelRequest request, final DataCallBack callBack) {
        getRetrofit().onCollectionCancel(request, new DataCallBack() {
            @Override
            public void onComplete() {
                callBack.onComplete();
            }

            @Override
            public void onSucceed(String json) {
                LogUtil.showJson("setCancelMovieCollection", json);
            }

            @Override
            public void onFail(BaseResponse baseResponse) {
                callBack.onFail(baseResponse);
            }
        });
    }

    @Override
    public void setMovieVisit(MovieVisitRequest request, final DataCallBack callBack) {
        getRetrofit().onMovieVisit(request, new DataCallBack() {
            @Override
            public void onComplete() {
                callBack.onComplete();
            }

            @Override
            public void onSucceed(String json) {
                callBack.onSucceed(json);
            }

            @Override
            public void onFail(BaseResponse baseResponse) {
                callBack.onFail(baseResponse);
            }
        });
    }

    @Override
    public void shareVideo(VideoShareUrlRequest request, final DataResponseCallback callback) {
        getRetrofit().onVideoShare(request, new DataCallBack() {
            @Override
            public void onComplete() {
                callback.onComplete();
            }

            @Override
            public void onSucceed(String json) {
                VideoShareUrlResponse response = new Gson().fromJson(json, VideoShareUrlResponse.class);
                callback.onSucceed(response);
            }

            @Override
            public void onFail(BaseResponse baseResponse) {
                callback.onFail(baseResponse);
            }
        });
    }

    @Override
    public void shareMovie(final MovieShareRequest request, final DataResponseCallback callback) {
        getRetrofit().onMovieShare(request, new DataCallBack() {
            @Override
            public void onComplete() {
                callback.onComplete();
            }

            @Override
            public void onSucceed(String json) {
                MovieShareResponse response = new Gson().fromJson(json, MovieShareResponse.class);
                callback.onSucceed(response);
                LogUtil.showJson("shareMovie", json);

            }

            @Override
            public void onFail(BaseResponse baseResponse) {
                callback.onFail(baseResponse);
            }
        });
    }

    @Override
    public void shareVisit(ShareVisitRequest request, final DataCallBack callBack) {
        getRetrofit().onShareVisit(request, new DataCallBack() {
            @Override
            public void onComplete() {
                callBack.onComplete();
            }

            @Override
            public void onSucceed(String json) {
                callBack.onSucceed(json);
            }

            @Override
            public void onFail(BaseResponse baseResponse) {
                callBack.onFail(baseResponse);
            }
        });
    }

    @Override
    public void getAdList(AdsRequest request, final DataResponseCallback callBack) {
        getRetrofit().onAds(request, new DataCallBack() {
            @Override
            public void onComplete() {
                callBack.onComplete();
            }

            @Override
            public void onSucceed(String json) {
                LogUtil.showJson("getAdList", json);
                AdsReponse reponse = new Gson().fromJson(json, AdsReponse.class);
                callBack.onSucceed(reponse);
            }

            @Override
            public void onFail(BaseResponse baseResponse) {
                callBack.onFail(baseResponse);
            }
        });
    }

    @Override
    public void setMoviePay(MoviePayRequest request,final DataResponseCallback callback) {
        getRetrofit().onMoviePay(request, new DataCallBack() {
            @Override
            public void onComplete() {
                callback.onComplete();
            }

            @Override
            public void onSucceed(String json) {
                LogUtil.showJson("setMoviePay", json);
                callback.onSucceed(json);

            }

            @Override
            public void onFail(BaseResponse baseResponse) {
                callback.onFail(baseResponse);
            }
        });
    }




}
