package com.application.sven.huinews.main.home.contract;

import com.application.sven.huinews.base.BaseModel;
import com.application.sven.huinews.base.BasePresenter;
import com.application.sven.huinews.base.BaseView;
import com.application.sven.huinews.config.http.DataCallBack;
import com.application.sven.huinews.config.http.DataResponseCallback;
import com.application.sven.huinews.entity.AdsList;
import com.application.sven.huinews.entity.Comment;
import com.application.sven.huinews.entity.request.AdCommentRequest;
import com.application.sven.huinews.entity.request.AdsRequest;
import com.application.sven.huinews.entity.request.ChannelTypeRequest;
import com.application.sven.huinews.entity.request.SetUserMsgRequest;
import com.application.sven.huinews.entity.request.ShareVisitRequest;
import com.application.sven.huinews.entity.request.VideoCollectionCancelRequest;
import com.application.sven.huinews.entity.request.VideoCollectionRequest;
import com.application.sven.huinews.entity.request.VideoCommentRequest;
import com.application.sven.huinews.entity.request.VideoLikeRequest;
import com.application.sven.huinews.entity.request.VideoListRequest;
import com.application.sven.huinews.entity.request.VideoReportRequest;
import com.application.sven.huinews.entity.request.VideoShareUrlRequest;
import com.application.sven.huinews.entity.request.VideoTaskRequest;
import com.application.sven.huinews.entity.response.VideoListResponse;
import com.application.sven.huinews.entity.response.VideoTaskResponse;
import com.application.sven.huinews.utils.ads.AdsConfig;
import com.qq.e.ads.nativ.NativeExpressADView;

import java.util.List;

/**
 * auther: sunfuyi
 * data: 2018/5/15
 * effect:
 */
public interface HomeTabContract {
    abstract class Model extends BaseModel {
        /**
         * 视频列表
         *
         * @param request
         * @param callBack
         */
        public abstract void getVideoList(VideoListRequest request, DataResponseCallback callBack);

        public abstract void setVideoLike(VideoLikeRequest request, DataCallBack callBack);

        public abstract void getHomeAds(AdsRequest request, DataResponseCallback callback);

        public abstract void shareVideo(VideoShareUrlRequest request, DataResponseCallback callback);

        public abstract void shareVisit(ShareVisitRequest request, DataCallBack callBack);

        public abstract void videoReport(VideoReportRequest request, DataCallBack callBack);

        public abstract void setCollection(VideoCollectionRequest request, final DataCallBack callBack);

        public abstract void setCancelCollection(VideoCollectionCancelRequest request, DataCallBack callBack);

        public abstract void videoVisit(VideoShareUrlRequest request, DataCallBack callBack);

        public abstract void videoGoldTask(VideoTaskRequest request, DataResponseCallback callback);

        public abstract void videoWatchCount(DataResponseCallback callBack);

    }

    interface View extends BaseView {
        VideoListRequest videoListRequest();

        VideoCollectionRequest getVideoCollectionRequest(int videoId);

        VideoCollectionCancelRequest getVideoCollectionCancelRequest(int videoId);

        void setVideoCollectionOk(boolean isCollection);

        void setVideoList(VideoListResponse.VideoList videoLists);

        void setAdsLit(List<AdsList> ads);

        void setTencentAds(List<NativeExpressADView> list);

        void setTencentRightImgAds(List<NativeExpressADView> list);

        void setVideoShareUrl(String url, int type);

        void setGoldView(VideoListResponse.VideoList.ListBean mDat, VideoTaskResponse.Data mData, int type);

        void setVideoCount(int count);

        void setInGoldError();
    }

    abstract class Presenter extends BasePresenter<HomeTabContract.View, HomeTabContract.Model> {
        //视频列表
        public abstract void onVideoList();

        //视频点赞
        public abstract void onVideoLike(VideoLikeRequest request);

        //广告获取
        public abstract void onHomeAds(int page);

        public abstract void onTencentAds(AdsConfig mAdsConfig, int count);

        public abstract void onVideoReport(VideoReportRequest request);

        public abstract void onVideoShare(VideoShareUrlRequest request, int type);

        public abstract void onShareVisit(String response, String videoType, int type);

        //收藏
        public abstract void onCollection(VideoCollectionRequest request);

        //取消收藏
        public abstract void onCancelCollection(VideoCollectionCancelRequest request);

        public abstract void onVideoVisit(int id);

        public abstract void onVideoGoldTask(VideoListResponse.VideoList.ListBean mData, int type);

        public abstract void onVideoWatchCount();

    }

}
