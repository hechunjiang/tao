package com.application.sven.huinews.main.home.presenter;

import android.content.Context;
import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.application.sven.huinews.R;
import com.application.sven.huinews.config.http.DataCallBack;
import com.application.sven.huinews.config.http.DataResponseCallback;
import com.application.sven.huinews.db.BaseDB;
import com.application.sven.huinews.db.UserSpCache;
import com.application.sven.huinews.entity.jspush.JsShareType;
import com.application.sven.huinews.entity.request.AdCommentRequest;
import com.application.sven.huinews.entity.request.AdsRequest;
import com.application.sven.huinews.entity.request.ShareVisitRequest;
import com.application.sven.huinews.entity.request.VideoCollectionCancelRequest;
import com.application.sven.huinews.entity.request.VideoCollectionRequest;
import com.application.sven.huinews.entity.request.VideoCommentRequest;
import com.application.sven.huinews.entity.request.VideoLikeRequest;
import com.application.sven.huinews.entity.request.VideoListRequest;
import com.application.sven.huinews.entity.request.VideoReportRequest;
import com.application.sven.huinews.entity.request.VideoShareUrlRequest;
import com.application.sven.huinews.entity.request.VideoTaskRequest;
import com.application.sven.huinews.entity.response.AdsReponse;
import com.application.sven.huinews.entity.response.BaseResponse;
import com.application.sven.huinews.entity.response.CommentReponse;
import com.application.sven.huinews.entity.response.VideoCountResponse;
import com.application.sven.huinews.entity.response.VideoListResponse;
import com.application.sven.huinews.entity.response.VideoShareUrlResponse;
import com.application.sven.huinews.entity.response.VideoTaskResponse;
import com.application.sven.huinews.main.home.contract.HomeTabContract;
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
 * data: 2018/5/15
 * effect:
 */
public class HomeTabPresenter extends HomeTabContract.Presenter {
    @Override
    public void onVideoList() {
        VideoListRequest request = null;
        if (mView.videoListRequest() != null) {
            request = mView.videoListRequest();
        } else {
            LogUtil.showJson("msg", "VideoListRequest:缺少必要参数");
        }
        mModel.getVideoList(request, new DataResponseCallback<VideoListResponse.VideoList>() {

            @Override
            public void onSucceed(VideoListResponse.VideoList videoLists) {
                if (videoLists != null) {
                    mView.setVideoList(videoLists);
                }
            }

            @Override
            public void onFail(BaseResponse response) {
                LogUtil.showLog("onVideoList", response.toString());
                mView.showErrorTip(response.getCode(), response.getMsg());
            }

            @Override
            public void onComplete() {

            }
        });
    }

    @Override
    public void onVideoLike(VideoLikeRequest request) {

        mModel.setVideoLike(request, new DataCallBack() {
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
    public void onHomeAds(int page) {
        String position = UserSpCache.getInstance(mContext).getAdType().getV_transverse_screen();
        AdsRequest request = AdsConfig.getRequest(mContext, page, position);
        mModel.getHomeAds(request, new DataResponseCallback<AdsReponse>() {
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
    public void onTencentAds(AdsConfig mAdsConfig, int count) {
        mAdsConfig.tencentNativeAD(count, AdsConfig.TENCENT_AD_ID);
        mAdsConfig.setSetAdLoadedLisenter(new AdsConfig.setAdLoadedLisenter() {
            @Override
            public void onLoadAd(List<NativeExpressADView> list) {
                mView.setTencentAds(list);
            }

            @Override
            public void onLoadRightImg(List<NativeExpressADView> list) {
                mView.setTencentRightImgAds(list);
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
    public void onCollection(VideoCollectionRequest request) {
        mModel.setCollection(request, new DataCallBack() {
            @Override
            public void onComplete() {

            }

            @Override
            public void onSucceed(String json) {
                mView.setVideoCollectionOk(true);
            }

            @Override
            public void onFail(BaseResponse baseResponse) {

            }
        });
    }

    @Override
    public void onCancelCollection(VideoCollectionCancelRequest request) {
        mModel.setCancelCollection(request, new DataCallBack() {
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

    @Override
    public void onVideoGoldTask(final VideoListResponse.VideoList.ListBean mData, final int type) {
        final VideoTaskRequest request = new VideoTaskRequest();
        request.setId(type + "");
        mModel.videoGoldTask(request, new DataResponseCallback<VideoTaskResponse.Data>() {

            @Override
            public void onSucceed(VideoTaskResponse.Data data) {
                mView.setGoldView(mData, data, type);
            }

            @Override
            public void onFail(BaseResponse response) {
                if (response.getCode() == 10002) {
                    ToastUtils.showLong(mContext, response.getMsg());
                } else if (response.getCode() == DataCallBack.NETWORK_ERROR) {
                    ToastUtils.showLong(mContext, response.getMsg());
                    mView.setInGoldError();
                }
            }

            @Override
            public void onComplete() {

            }
        });
    }

    @Override
    public void onVideoWatchCount() {
        mModel.videoWatchCount(new DataResponseCallback<VideoCountResponse>() {

            @Override
            public void onSucceed(VideoCountResponse videoCountResponse) {
                mView.setVideoCount(videoCountResponse.getData().getCount());
            }

            @Override
            public void onFail(BaseResponse response) {

            }

            @Override
            public void onComplete() {

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
    public String getShareJson(VideoListResponse.VideoList.ListBean mData, int type) {
        JsShareType jsShareType = new JsShareType();
        jsShareType.setType(type);
        jsShareType.setWechatShareType(Wechat.SHARE_VIDEO);
        jsShareType.setTitle(mData.getTitle());
        jsShareType.setUrl(mData.getVideo_url());
        jsShareType.setContent(mData.getTitle());
        jsShareType.setImgUrl(mData.getVideo_cover());
        return new Gson().toJson(jsShareType);
    }

    /**
     * 判断是否存储了该视频
     *
     * @param mData
     * @return
     */
    public boolean isCanGetCoinByVideo(VideoListResponse.VideoList.ListBean mData) {
        return readNewsCanGetCoin(mData);
    }

    /**
     * 将该视频存入数据库
     *
     * @param mData
     */
    public void addVideoToDB(VideoListResponse.VideoList.ListBean mData) {
        addVideo(mData.getId() + "");
    }


    private void addVideo(String newsId) {
        BaseDB mBaseDB = BaseDB.getInstance(mContext);
        mBaseDB.insertInVideoId(newsId);
    }

    private boolean readNewsCanGetCoin(VideoListResponse.VideoList.ListBean mData) {
        //判断数据库中是否存在改视频，如果有返回false
        if (hasReadVideo(mData.getId() + "")) {
            return false;
        }
        return true;
    }

    private boolean hasReadVideo(String newsId) {
        BaseDB mBaseDB = BaseDB.getInstance(mContext);
        return mBaseDB.getAllVideoList().contains(newsId);
    }


    Handler handler = new Handler();


    TranslateAnimation translateAnimation;

    /**
     * 加金币动画
     *
     * @param context
     * @param tv
     * @param count
     */

    private TextView tv;
    private RelativeLayout rl;

    public void goldCountAnim(final Context context, final TextView tv, final RelativeLayout rl, final int count) {

        translateAnimation = new TranslateAnimation(0, 0, 0, -150);
        translateAnimation.setDuration(2000);
        translateAnimation.setFillAfter(true);
        tv.startAnimation(translateAnimation);
        this.tv = tv;
        this.rl = rl;
        translateAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                tv.setText(String.format(context.getResources().getString(R.string.plus_gold), count));
                tv.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                if (!isHide) {
                    handler.postDelayed(runnable, 1000);
                }
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }

        });
    }

    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            tv.clearAnimation();
            tv.setVisibility(View.GONE);
            rl.setVisibility(View.INVISIBLE);
        }
    };


    private boolean isHide;

    public void cacnleGoldCountAnim(TextView tv, boolean b) {
        isHide = b;
        if (b) {
            tv.setVisibility(View.GONE);
            tv.clearAnimation();
        }
    }
}
