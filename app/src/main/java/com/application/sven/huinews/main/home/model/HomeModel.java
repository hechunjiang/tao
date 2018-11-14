package com.application.sven.huinews.main.home.model;

import com.application.sven.huinews.AppConfig;
import com.application.sven.huinews.config.http.DataCallBack;
import com.application.sven.huinews.config.http.DataResponseCallback;
import com.application.sven.huinews.config.http.MyRetrofit;
import com.application.sven.huinews.entity.request.AdCommentRequest;
import com.application.sven.huinews.entity.request.ChannelTypeRequest;
import com.application.sven.huinews.entity.request.VideoCommentLikeRequest;
import com.application.sven.huinews.entity.request.VideoCommentRequest;
import com.application.sven.huinews.entity.request.VideoReportRequest;
import com.application.sven.huinews.entity.response.BaseResponse;
import com.application.sven.huinews.entity.response.CommentReponse;
import com.application.sven.huinews.entity.response.GiveGoldResponse;
import com.application.sven.huinews.entity.response.VideoChannelResponse;
import com.application.sven.huinews.main.home.contract.HomeContract;
import com.application.sven.huinews.utils.LogUtil;
import com.google.gson.Gson;


/**
 * auther: sunfuyi
 * data: 2018/5/15
 * effect:
 */
public class HomeModel extends HomeContract.Model {

    @Override
    public void getActivatePush(final DataCallBack callBack) {
        getRetrofit().onTaskPush(new DataCallBack() {
            @Override
            public void onComplete() {
                callBack.onComplete();
            }

            @Override
            public void onSucceed(String json) {
                LogUtil.showJson("getActivatePush", json);
                callBack.onSucceed(json);
            }

            @Override
            public void onFail(BaseResponse baseResponse) {
                callBack.onFail(baseResponse);
            }
        });
    }

    @Override
    public void getRedBag(final DataCallBack callBack) {
        getRetrofit().onRedBag(new DataCallBack() {
            @Override
            public void onComplete() {
                callBack.onComplete();
            }

            @Override
            public void onSucceed(String json) {
                LogUtil.showJson("getRedBag", json);
                callBack.onSucceed(json);
            }

            @Override
            public void onFail(BaseResponse baseResponse) {
                callBack.onFail(baseResponse);
            }
        });
    }

    @Override
    public void getVideoComment(VideoCommentRequest request, final DataResponseCallback callback) {
        getRetrofit().onVideoComment(request, new DataCallBack() {
            @Override
            public void onComplete() {
                callback.onComplete();
            }

            @Override
            public void onSucceed(String json) {
                LogUtil.showJson("getVideoComment", json);
                CommentReponse reponse = new Gson().fromJson(json, CommentReponse.class);
                callback.onSucceed(reponse);
            }

            @Override
            public void onFail(BaseResponse baseResponse) {
                callback.onFail(baseResponse);
            }
        });
    }

    @Override
    public void videoAdComment(AdCommentRequest request, final DataCallBack callBack) {
        getRetrofit().onVideoAdComment(request, new DataCallBack() {
            @Override
            public void onComplete() {
                callBack.onComplete();
            }

            @Override
            public void onSucceed(String json) {
                LogUtil.showJson("videoAdComment", json);
                callBack.onSucceed(json);
            }

            @Override
            public void onFail(BaseResponse baseResponse) {
                callBack.onFail(baseResponse);
            }
        });
    }

    @Override
    public void videoCommentLike(VideoCommentLikeRequest request, final DataCallBack callBack) {
        getRetrofit().videoCommentLike(request, new DataCallBack() {
            @Override
            public void onComplete() {
                callBack.onComplete();
            }

            @Override
            public void onSucceed(String json) {
                LogUtil.showJson("videoCommentLike", json);
                callBack.onSucceed(json);
            }

            @Override
            public void onFail(BaseResponse baseResponse) {
                callBack.onFail(baseResponse);
            }
        });
    }

    @Override
    public void videoCommentReport(VideoReportRequest request, final DataCallBack callBack) {
        getRetrofit().videoCommentRepost(request, new DataCallBack() {
            @Override
            public void onComplete() {
                callBack.onComplete();
            }

            @Override
            public void onSucceed(String json) {
                LogUtil.showJson("videoCommentReport", json);
                callBack.onSucceed(json);
            }

            @Override
            public void onFail(BaseResponse baseResponse) {
                callBack.onFail(baseResponse);
            }
        });
    }

    @Override
    public void onGiveGold(final DataResponseCallback callback) {
        getRetrofit().onGiveGold(new DataCallBack() {
            @Override
            public void onComplete() {
                callback.onComplete();
            }

            @Override
            public void onSucceed(String json) {
                GiveGoldResponse response = new Gson().fromJson(json, GiveGoldResponse.class);
                callback.onSucceed(response.getData());
            }

            @Override
            public void onFail(BaseResponse baseResponse) {
                callback.onFail(baseResponse);
            }
        });
    }

    @Override
    public void onToGiveGold(final DataResponseCallback callback) {
        getRetrofit().onToGiveGold(new DataCallBack() {
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
