package com.application.sven.huinews.main.home.presenter;

import com.application.sven.huinews.config.Constant;
import com.application.sven.huinews.config.http.DataCallBack;
import com.application.sven.huinews.config.http.DataResponseCallback;
import com.application.sven.huinews.db.SpUtil;
import com.application.sven.huinews.db.UserSpCache;
import com.application.sven.huinews.entity.request.AdCommentRequest;
import com.application.sven.huinews.entity.request.ChannelTypeRequest;
import com.application.sven.huinews.entity.request.VideoCommentLikeRequest;
import com.application.sven.huinews.entity.request.VideoCommentRequest;
import com.application.sven.huinews.entity.request.VideoReportRequest;
import com.application.sven.huinews.entity.response.BaseResponse;
import com.application.sven.huinews.entity.response.CommentReponse;
import com.application.sven.huinews.entity.response.GiveGoldResponse;
import com.application.sven.huinews.entity.response.PushTaskResponse;
import com.application.sven.huinews.entity.response.VideoChannelResponse;
import com.application.sven.huinews.main.home.contract.HomeContract;
import com.application.sven.huinews.utils.LogUtil;
import com.application.sven.huinews.utils.ToastUtils;
import com.google.gson.Gson;

import java.util.List;

/**
 * auther: sunfuyi
 * data: 2018/5/15
 * effect:
 */
public class HomePresenter extends HomeContract.Presenter {

    @Override
    public void onActiatePush() {
        mModel.getActivatePush(new DataCallBack() {
            @Override
            public void onComplete() {

            }

            @Override
            public void onSucceed(String json) {
                PushTaskResponse response = new Gson().fromJson(json, PushTaskResponse.class);
                if (response.getData().getData().getType() == 1) {
                    mView.showNewsPersonalTask(response.getData().getData());
                } else {
                    mView.showOtherTask(response.getData().getData());
                }
            }

            @Override
            public void onFail(BaseResponse baseResponse) {

            }
        });
    }

    @Override
    public void onRedBag() {
        mModel.getRedBag(new DataCallBack() {
            @Override
            public void onComplete() {

            }

            @Override
            public void onSucceed(String json) {
                mView.onOpenRedBag();
            }

            @Override
            public void onFail(BaseResponse baseResponse) {

            }
        });
    }

    @Override
    public void onVideoComment(VideoCommentRequest request) {
        mModel.getVideoComment(request, new DataResponseCallback<CommentReponse>() {
            @Override
            public void onSucceed(CommentReponse reponse) {
                mView.setComment(reponse.getData());
            }

            @Override
            public void onFail(BaseResponse response) {

            }

            @Override
            public void onComplete() {

            }
        });
    }

    @Override
    public void onVideoAdComment() {
        AdCommentRequest request = mView.getAdCommentRequest();
        mModel.videoAdComment(request, new DataCallBack() {
            @Override
            public void onComplete() {

            }

            @Override
            public void onSucceed(String json) {
                mView.adCommentOk();
            }

            @Override
            public void onFail(BaseResponse baseResponse) {

            }
        });
    }

    @Override
    public void onVideoCommentLike(String id) {
        VideoCommentLikeRequest request = new VideoCommentLikeRequest();
        request.setId(id);
        mModel.videoCommentLike(request, new DataCallBack() {
            @Override
            public void onComplete() {

            }

            @Override
            public void onSucceed(String json) {

            }

            @Override
            public void onFail(BaseResponse baseResponse) {

            }
        });
    }

    @Override
    public void onVideoCommentReport(String id) {
        VideoReportRequest request = new VideoReportRequest();
        request.setVideoId(id);
        mModel.videoCommentReport(request, new DataCallBack() {
            @Override
            public void onComplete() {

            }

            @Override
            public void onSucceed(String json) {
                ToastUtils.showLong(mContext, "举报成功");
            }

            @Override
            public void onFail(BaseResponse baseResponse) {

            }
        });
    }

    @Override
    public void onGiveGold() {
        mModel.onGiveGold(new DataResponseCallback<GiveGoldResponse.GoldData>() {


            @Override
            public void onSucceed(GiveGoldResponse.GoldData goldData) {
                mView.setGiveGold(goldData);
            }

            @Override
            public void onFail(BaseResponse response) {

            }

            @Override
            public void onComplete() {

            }
        });
    }

    @Override
    public void onToGiveGold() {
        mModel.onToGiveGold(new DataResponseCallback() {
            @Override
            public void onSucceed(Object o) {
                mView.settGiveGold();
            }

            @Override
            public void onFail(BaseResponse response) {

            }

            @Override
            public void onComplete() {

            }
        });
    }
}
