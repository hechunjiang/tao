package com.application.sven.huinews.main.video.model;

import com.application.sven.huinews.AppConfig;
import com.application.sven.huinews.config.http.DataCallBack;
import com.application.sven.huinews.config.http.DataResponseCallback;
import com.application.sven.huinews.config.http.MyRetrofit;
import com.application.sven.huinews.entity.request.AdCommentRequest;
import com.application.sven.huinews.entity.request.ShareVisitRequest;
import com.application.sven.huinews.entity.request.UserMsgRequest;
import com.application.sven.huinews.entity.request.VideoCollectionCancelRequest;
import com.application.sven.huinews.entity.request.VideoCollectionRequest;
import com.application.sven.huinews.entity.request.VideoCommentLikeRequest;
import com.application.sven.huinews.entity.request.VideoCommentRequest;
import com.application.sven.huinews.entity.request.VideoLikeRequest;
import com.application.sven.huinews.entity.request.VideoListRequest;
import com.application.sven.huinews.entity.request.VideoReportRequest;
import com.application.sven.huinews.entity.request.VideoShareUrlRequest;
import com.application.sven.huinews.entity.request.VideoTaskRequest;
import com.application.sven.huinews.entity.response.BaseResponse;
import com.application.sven.huinews.entity.response.CommentReponse;
import com.application.sven.huinews.entity.response.VideoCountResponse;
import com.application.sven.huinews.entity.response.VideoListResponse;
import com.application.sven.huinews.entity.response.VideoShareUrlResponse;
import com.application.sven.huinews.entity.response.VideoTaskResponse;
import com.application.sven.huinews.main.video.contract.VideoContract;
import com.application.sven.huinews.utils.LogUtil;
import com.application.sven.huinews.utils.ToastUtils;
import com.google.gson.Gson;

/**
 * auther: sunfuyi
 * data: 2018/5/18
 * effect:
 */
public class VideoModel extends VideoContract.Model {

    @Override
    public void getVideoList(VideoListRequest request, final DataResponseCallback callBack) {
        getRetrofit().onVideoList(request, new DataCallBack() {
            @Override
            public void onComplete() {
                callBack.onComplete();
            }

            @Override
            public void onSucceed(String json) {
                LogUtil.showJson("HomeTabModel", json);
                VideoListResponse videoListResponse = new Gson().fromJson(json, VideoListResponse.class);
                callBack.onSucceed(videoListResponse.getData());
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

    @Override
    public void setLikeVideo(VideoLikeRequest request, final DataCallBack callBack) {
        getRetrofit().onVideoLike(request, new DataCallBack() {
            @Override
            public void onComplete() {
                callBack.onComplete();
            }

            @Override
            public void onSucceed(String json) {
                LogUtil.showJson("setLikeVideo", json);
                callBack.onSucceed(json);
            }

            @Override
            public void onFail(BaseResponse baseResponse) {
                callBack.onFail(baseResponse);
            }
        });
    }


    @Override
    public void setCollection(VideoCollectionRequest request, final DataCallBack callBack) {
        getRetrofit().onVideoCollection(request, new DataCallBack() {
            @Override
            public void onComplete() {
                callBack.onComplete();
            }

            @Override
            public void onSucceed(String json) {
                LogUtil.showJson("setCollection", json);
                callBack.onSucceed(json);
            }

            @Override
            public void onFail(BaseResponse baseResponse) {
                callBack.onFail(baseResponse);
            }
        });
    }

    @Override
    public void setCancelCollection(VideoCollectionCancelRequest request, final DataCallBack callBack) {
        getRetrofit().onCollectionCancel(request, new DataCallBack() {
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
    public void videoReport(VideoReportRequest request, final DataCallBack callBack) {
        getRetrofit().onVideoReport(request, new DataCallBack() {
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
    public void videoGoldTask(final VideoTaskRequest request, final DataResponseCallback callback) {
        getRetrofit().onGoldByTask(request, new DataCallBack() {
            @Override
            public void onComplete() {
                callback.onComplete();
            }

            @Override
            public void onSucceed(String json) {
                LogUtil.showJson("videoGoldTask", json);
                VideoTaskResponse response = new Gson().fromJson(json, VideoTaskResponse.class);
                callback.onSucceed(response.getData());
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
    public void videoVisit(VideoShareUrlRequest request, final DataCallBack callBack) {
        getRetrofit().onVideoVisit(request, new DataCallBack() {
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
    public void videoWatchCount(final DataResponseCallback callBack) {
        getRetrofit().onVideoWatchCount(new DataCallBack() {
            @Override
            public void onComplete() {
                callBack.onComplete();
            }

            @Override
            public void onSucceed(String json) {
                VideoCountResponse response = new Gson().fromJson(json,VideoCountResponse.class);
                callBack.onSucceed(response);
                LogUtil.showJson("videoWatchCount", json);
            }

            @Override
            public void onFail(BaseResponse baseResponse) {
                callBack.onFail(baseResponse);
            }
        });
    }
}
