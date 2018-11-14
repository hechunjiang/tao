package com.application.sven.huinews.main.home.presenter;

import com.application.sven.huinews.R;
import com.application.sven.huinews.config.http.DataCallBack;
import com.application.sven.huinews.config.http.DataResponseCallback;
import com.application.sven.huinews.db.UserSpCache;
import com.application.sven.huinews.entity.jspush.JsShareType;
import com.application.sven.huinews.entity.request.AdCommentRequest;
import com.application.sven.huinews.entity.request.AdsRequest;
import com.application.sven.huinews.entity.request.ShareVisitRequest;
import com.application.sven.huinews.entity.request.VideoCommentLikeRequest;
import com.application.sven.huinews.entity.request.VideoCommentRequest;
import com.application.sven.huinews.entity.request.VideoDetailsRequest;
import com.application.sven.huinews.entity.request.VideoLikeRequest;
import com.application.sven.huinews.entity.request.VideoReportRequest;
import com.application.sven.huinews.entity.request.VideoShareUrlRequest;
import com.application.sven.huinews.entity.response.AdsReponse;
import com.application.sven.huinews.entity.response.BaseResponse;
import com.application.sven.huinews.entity.response.CommentReponse;
import com.application.sven.huinews.entity.response.VideoDetailsResponse;
import com.application.sven.huinews.entity.response.VideoListResponse;
import com.application.sven.huinews.entity.response.VideoShareUrlResponse;
import com.application.sven.huinews.main.home.contract.VideoDetailsContract;
import com.application.sven.huinews.mob.share.ShareResponse;
import com.application.sven.huinews.utils.LogUtil;
import com.application.sven.huinews.utils.ToastUtils;
import com.application.sven.huinews.utils.ads.AdsConfig;
import com.google.gson.Gson;
import com.qq.e.ads.nativ.NativeExpressADView;

import java.util.List;

import cn.sharesdk.wechat.friends.Wechat;

/**
 * auther: sunfuyi
 * data: 2018/6/7
 * effect:
 */
public class VideoDetailsPresenter extends VideoDetailsContract.Presenter {
    @Override
    public void onVideoInfo() {
        VideoDetailsRequest request = mView.getVideoDetailsRequest();
        mModel.getVideoInfo(request, new DataResponseCallback<VideoDetailsResponse>() {

            @Override
            public void onSucceed(VideoDetailsResponse response) {
                mView.setVideoDetaisInfo(response);
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
    public void onVideoShare(VideoShareUrlRequest request, final int type) {
        mModel.shareVideo(request, new DataResponseCallback<VideoShareUrlResponse>() {

            @Override
            public void onSucceed(VideoShareUrlResponse videoShareUrlResponse) {
                mView.setVideoShareUrl(videoShareUrlResponse.getData().getUrl(), type);
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
    public void onShareVisit(String response, String videoType, int type) {
        ShareResponse mJsShareResponse = new Gson().fromJson(response, ShareResponse.class);
        ShareVisitRequest request = new ShareVisitRequest();
        request.setActivityType(videoType);
        request.setCode(mJsShareResponse.getCode() + "");
        request.setShareChannel(type + "");

        mModel.shareVisit(request, new DataCallBack() {
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
    public void onLikeVideo(VideoLikeRequest request, final VideoDetailsResponse.VideoInfo mData) {
        mModel.setLikeVideo(request, new DataCallBack() {
            @Override
            public void onComplete() {

            }

            @Override
            public void onSucceed(String json) {
                mView.setVideoLikedOk(mData);
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
    public void onVideoReport(VideoReportRequest request) {
        mModel.videoReport(request, new DataCallBack() {
            @Override
            public void onComplete() {

            }

            @Override
            public void onSucceed(String json) {
                ToastUtils.showLong(mContext, mContext.getString(R.string.report_ok));
            }

            @Override
            public void onFail(BaseResponse baseResponse) {

            }
        });
    }

    @Override
    public void onAdsList(int page) {
        String position = UserSpCache.getInstance(mContext).getAdType().getV_detail();
        AdsRequest request = AdsConfig.getRequest(mContext, page, position);
        mModel.getAdList(request, new DataResponseCallback<AdsReponse>() {
            @Override
            public void onSucceed(AdsReponse adsReponse) {
                mView.setAdsLit(adsReponse.getData());
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
    public void onTencentAds(AdsConfig mAdsConfig) {
        mAdsConfig.setSetAdLoadedLisenter(new AdsConfig.setAdLoadedLisenter() {
            @Override
            public void onLoadAd(List<NativeExpressADView> list) {
                LogUtil.showLog("msg--腾讯广点通");
                mView.setTencentAds(list);
            }

            @Override
            public void onLoadRightImg(List<NativeExpressADView> list) {

            }

            @Override
            public void onADClosed(NativeExpressADView nativeExpressADView) {

            }

            @Override
            public void onADClicked(NativeExpressADView nativeExpressADView) {

            }
        });
    }

    @Override
    public void onVideoVisit(int id) {
        VideoShareUrlRequest request = new VideoShareUrlRequest();
        request.setVideoId(id + "");
        mModel.videoVisit(request, new DataCallBack() {
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

    /**
     * 视频分享
     *
     * @param mData
     * @param type
     * @return
     */
    public String getShareJson(VideoDetailsResponse.VideoInfo mData, int type) {
        JsShareType jsShareType = new JsShareType();
        jsShareType.setType(type);
        jsShareType.setWechatShareType(Wechat.SHARE_VIDEO);
        jsShareType.setTitle(mData.getTitle());
        jsShareType.setUrl(mData.getVideo_url());
        jsShareType.setContent(mData.getTitle());
        jsShareType.setImgUrl(mData.getVideo_cover());
        return new Gson().toJson(jsShareType);
    }

}
