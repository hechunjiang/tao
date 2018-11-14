package com.application.sven.huinews.main.home.contract;

import com.application.sven.huinews.base.BaseModel;
import com.application.sven.huinews.base.BasePresenter;
import com.application.sven.huinews.config.http.DataCallBack;
import com.application.sven.huinews.config.http.DataResponseCallback;
import com.application.sven.huinews.entity.Comment;
import com.application.sven.huinews.entity.PushTask;
import com.application.sven.huinews.entity.request.AdCommentRequest;
import com.application.sven.huinews.entity.request.ChannelTypeRequest;
import com.application.sven.huinews.entity.request.VideoCommentLikeRequest;
import com.application.sven.huinews.entity.request.VideoCommentRequest;
import com.application.sven.huinews.entity.request.VideoReportRequest;
import com.application.sven.huinews.entity.response.GiveGoldResponse;
import com.application.sven.huinews.entity.response.VideoChannelResponse;

import java.util.List;

import io.reactivex.Observable;

/**
 * auther: sunfuyi
 * data: 2018/5/15
 * effect:
 */
public interface HomeContract {
    abstract class Model extends BaseModel {
        public abstract void getActivatePush(DataCallBack callBack);

        public abstract void getRedBag(DataCallBack callBack);

        //视频评论列表
        public abstract void getVideoComment(VideoCommentRequest request, DataResponseCallback callback);

        //添加评论
        public abstract void videoAdComment(AdCommentRequest request, DataCallBack callBack);

        //评论点赞
        public abstract void videoCommentLike(VideoCommentLikeRequest request, DataCallBack callBack);

        //评论举报
        public abstract void videoCommentReport(VideoReportRequest request, DataCallBack callBack);

        // 赠送金币
        public abstract void onGiveGold(DataResponseCallback callback);

        //领取金币
        public abstract void onToGiveGold(DataResponseCallback callback);

    }


    interface View {
        void showNewsPersonalTask(PushTask task);

        void showOtherTask(PushTask task);

        void onOpenRedBag();

        VideoCommentRequest getVideoCommentRequest(int page, int videoId, String sort);

        AdCommentRequest getAdCommentRequest();


        void setComment(List<Comment> mComment);

        void adCommentOk();

        void setGiveGold(GiveGoldResponse.GoldData goldData);

        void settGiveGold();
    }

    abstract class Presenter extends BasePresenter<HomeContract.View, HomeContract.Model> {
        public abstract void onActiatePush();

        public abstract void onRedBag();


        //视频评论列表
        public abstract void onVideoComment(VideoCommentRequest request);

        public abstract void onVideoAdComment();


        public abstract void onVideoCommentLike(String id);

        public abstract void onVideoCommentReport(String id);

        public abstract void onGiveGold();

        public abstract void onToGiveGold();
    }
}
