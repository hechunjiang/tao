package com.application.sven.huinews.main.video.contract;

import com.application.sven.huinews.base.BaseModel;
import com.application.sven.huinews.base.BasePresenter;
import com.application.sven.huinews.base.BaseView;
import com.application.sven.huinews.config.Constant;
import com.application.sven.huinews.config.http.DataCallBack;
import com.application.sven.huinews.config.http.DataResponseCallback;
import com.application.sven.huinews.entity.Comment;
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
import com.application.sven.huinews.entity.response.VideoListResponse;
import com.application.sven.huinews.entity.response.VideoTaskResponse;

import java.util.List;

/**
 * auther: sunfuyi
 * data: 2018/5/18
 * effect:
 */
public interface VideoContract {
    abstract class Model extends BaseModel {

        public abstract void getVideoList(VideoListRequest request, DataResponseCallback callBack);

        public abstract void setFollowUser(UserMsgRequest request, DataResponseCallback callback);

        public abstract void setCancelFollowUser(UserMsgRequest request, DataResponseCallback callback);

        public abstract void setLikeVideo(VideoLikeRequest request, DataCallBack callBack);

        public abstract void setCollection(VideoCollectionRequest request, final DataCallBack callBack);

        public abstract void setCancelCollection(VideoCollectionCancelRequest request, DataCallBack callBack);

        public abstract void getVideoComment(VideoCommentRequest request, DataResponseCallback callback);

        public abstract void shareVideo(VideoShareUrlRequest request, DataResponseCallback callback);

        public abstract void shareVisit(ShareVisitRequest request, DataCallBack callBack);

        public abstract void videoReport(VideoReportRequest request, DataCallBack callBack);

        public abstract void videoGoldTask(VideoTaskRequest request, DataResponseCallback callback);

        public abstract void videoAdComment(AdCommentRequest request, DataCallBack callBack);

        public abstract void videoCommentLike(VideoCommentLikeRequest request, DataCallBack callBack);

        public abstract void videoCommentReport(VideoReportRequest request, DataCallBack callBack);

        public abstract void videoVisit(VideoShareUrlRequest request, DataCallBack callBack);

        public abstract void videoWatchCount(DataResponseCallback callBack);

    }

    interface View extends BaseView {

        VideoListRequest videoListRequest();


        void setVideoList(VideoListResponse.VideoList videoLists);

        VideoCommentRequest getVideoCommentRequest(int page, int videoId,String sort);

        VideoCollectionRequest getVideoCollectionRequest(int videoId);

        VideoCollectionCancelRequest getVideoCollectionCancelRequest(int videoId);

        AdCommentRequest getAdCommentRequest();

        void setVideoCollectionOk(boolean isCollection);

        void setVideoLikedOk(VideoListResponse.VideoList.ListBean mData);

        void setVideoShareUrl(String url, int type);

        void setGoldView(VideoListResponse.VideoList.ListBean mDat, VideoTaskResponse.Data mData, int type);

        void setComment(List<Comment> mComment);

        void adCommentOk();

        void setVideoCount(int count);

        void setInGoldError();

    }

    abstract class Presenter extends BasePresenter<VideoContract.View, VideoContract.Model> {

        public abstract void onVideoList();

        //关注用户
        public abstract void onFollowUser(int userId);

        //取消关注
        public abstract void onCancelFollowUser(int userId);

        //点赞 取消点赞
        public abstract void onLikeVideo(VideoLikeRequest request, VideoListResponse.VideoList.ListBean mData);


        //收藏
        public abstract void onCollection(VideoCollectionRequest request);

        //取消收藏
        public abstract void onCancelCollection(VideoCollectionCancelRequest request);

        //视频评论列表
        public abstract void onVideoComment(VideoCommentRequest request);

        public abstract void onVideoShare(VideoShareUrlRequest request, int type);

        public abstract void onShareVisit(String response, String videoType, int type);

        public abstract void onVideoReport(VideoReportRequest request);

        public abstract void onVideoGoldTask(VideoListResponse.VideoList.ListBean mData, int type);

        public abstract void onVideoAdComment();

        public abstract void onVideoCommentLike(String id);

        public abstract void onVideoCommentReport(String id);

        public abstract void onVideoVisit(int id);

        public abstract void onVideoWatchCount();

    }
}
