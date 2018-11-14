package com.application.sven.huinews.main.home.contract;

import com.application.sven.huinews.base.BaseModel;
import com.application.sven.huinews.base.BasePresenter;
import com.application.sven.huinews.config.http.DataCallBack;
import com.application.sven.huinews.config.http.DataResponseCallback;
import com.application.sven.huinews.entity.AdsList;
import com.application.sven.huinews.entity.Comment;
import com.application.sven.huinews.entity.request.AdCommentRequest;
import com.application.sven.huinews.entity.request.AdsRequest;
import com.application.sven.huinews.entity.request.ShareVisitRequest;
import com.application.sven.huinews.entity.request.VideoCommentLikeRequest;
import com.application.sven.huinews.entity.request.VideoCommentRequest;
import com.application.sven.huinews.entity.request.VideoDetailsRequest;
import com.application.sven.huinews.entity.request.VideoLikeRequest;
import com.application.sven.huinews.entity.request.VideoReportRequest;
import com.application.sven.huinews.entity.request.VideoShareUrlRequest;
import com.application.sven.huinews.entity.response.VideoDetailsResponse;
import com.application.sven.huinews.entity.response.VideoListResponse;
import com.application.sven.huinews.utils.ads.AdsConfig;
import com.qq.e.ads.nativ.NativeExpressADView;

import java.util.List;

/**
 * auther: sunfuyi
 * data: 2018/6/7
 * effect:
 */
public interface VideoDetailsContract {
    abstract class Model extends BaseModel {
        public abstract void getVideoInfo(VideoDetailsRequest request, DataResponseCallback callback);

        public abstract void shareVideo(VideoShareUrlRequest request, DataResponseCallback callback);

        public abstract void shareVisit(ShareVisitRequest request, DataCallBack callBack);

        public abstract void setLikeVideo(VideoLikeRequest request, DataCallBack callBack);

        public abstract void getVideoComment(VideoCommentRequest request, DataResponseCallback callback);

        public abstract void videoAdComment(AdCommentRequest request, DataCallBack callBack);

        public abstract void videoCommentLike(VideoCommentLikeRequest request, DataCallBack callBack);

        public abstract void videoCommentReport(VideoReportRequest request, DataCallBack callBack);

        public abstract void videoReport(VideoReportRequest request, DataCallBack callBack);

        public abstract void getAdList(AdsRequest request, DataResponseCallback callBack);

        public abstract void videoVisit(VideoShareUrlRequest request, DataCallBack callBack);

    }

    interface View {
        VideoDetailsRequest getVideoDetailsRequest();

        VideoCommentRequest getVideoCommentRequest(int page, int videoId,String sort);

        void setVideoDetaisInfo(VideoDetailsResponse response);

        void setVideoShareUrl(String url, int type);

        void setVideoLikedOk(VideoDetailsResponse.VideoInfo mData);

        AdCommentRequest getAdCommentRequest();

        void setComment(List<Comment> mComment);

        void adCommentOk();

        void setAdsLit(List<AdsList> ads);

        void setTencentAds(List<NativeExpressADView> list);
    }

    abstract class Presenter extends BasePresenter<VideoDetailsContract.View, VideoDetailsContract.Model> {
        public abstract void onVideoInfo();

        public abstract void onVideoShare(VideoShareUrlRequest request, int type);

        public abstract void onShareVisit(String response, String videoType, int type);

        //点赞 取消点赞
        public abstract void onLikeVideo(VideoLikeRequest request, VideoDetailsResponse.VideoInfo mData);

        //视频评论列表
        public abstract void onVideoComment(VideoCommentRequest request);

        public abstract void onVideoAdComment();

        public abstract void onVideoCommentLike(String id);

        public abstract void onVideoCommentReport(String id);

        public abstract void onVideoReport(VideoReportRequest request);

        public abstract void onAdsList(int page);

        public abstract void onTencentAds(AdsConfig mAdsConfig);

        public abstract void onVideoVisit(int id);

    }
}
