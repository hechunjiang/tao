package com.application.sven.huinews.main.video.presenter;

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
import com.application.sven.huinews.mob.share.ShareResponse;
import com.application.sven.huinews.utils.LogUtil;
import com.application.sven.huinews.utils.ToastUtils;
import com.google.gson.Gson;


import cn.sharesdk.wechat.friends.Wechat;

/**
 * auther: sunfuyi
 * data: 2018/5/18
 * effect:
 */
public class VideoPresenter extends VideoContract.Presenter {
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
                mView.showErrorTip(response.getCode(), response.getMsg());
            }

            @Override
            public void onComplete() {

            }
        });
    }

    @Override
    public void onFollowUser(int userId) {
        UserMsgRequest request = new UserMsgRequest();
        request.setUser_id(userId);
        mModel.setFollowUser(request, new DataResponseCallback() {
            @Override
            public void onSucceed(Object o) {

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
    public void onCancelFollowUser(int userId) {
        UserMsgRequest request = new UserMsgRequest();
        request.setUser_id(userId);
        mModel.setCancelFollowUser(request, new DataResponseCallback() {
            @Override
            public void onSucceed(Object o) {

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
    public void onLikeVideo(VideoLikeRequest request, final VideoListResponse.VideoList.ListBean mData) {
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
    public void onVideoGoldTask(final VideoListResponse.VideoList.ListBean mData, final int type) {
        final VideoTaskRequest request = new VideoTaskRequest();
        request.setId(type + "");
        mModel.videoGoldTask(request, new DataResponseCallback<VideoTaskResponse.Data>() {

            @Override
            public void onSucceed(VideoTaskResponse.Data data) {
               /* if (!request.getId().equals(VideoTaskRequest.TASK_ID_READ_AD)) {
                    int count = 30 - data.getmCount();
                    if (count > 0) {
                        ToastUtils.showLong(mContext, "您还可以观看" + (30 - data.getmCount()) + "次金币视频");
                    }
                }*/
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

    /**
     * 判断是否还显示金币效果
     *
     * @return
     */
    public boolean setGoldCount() {
        int count = UserSpCache.getInstance(mContext).getInt(UserSpCache.V_AT_COUNT);
        int openGoldCount = UserSpCache.getInstance(mContext).getInt(UserSpCache.OPEN_GOLD_COUNT);
        if (openGoldCount > count) {
            return false;
        }
        return true;
    }

    /**
     * 判定是否还显示红包效果
     *
     * @return
     */
    public boolean setRedCount() {
        int count = UserSpCache.getInstance(mContext).getInt(UserSpCache.V_AT_RED);
        int openRedCount = UserSpCache.getInstance(mContext).getInt(UserSpCache.OPEN_RED_COUNT);
        if (openRedCount > count) {
            return false;
        }
        return true;
    }

    /**
     * 保存金币次数
     *
     * @return
     */
    public void saveGoldOpenCount() {
        int count = UserSpCache.getInstance(mContext).getInt(UserSpCache.OPEN_GOLD_COUNT);
        count++;
        UserSpCache.getInstance(mContext).putInt(UserSpCache.OPEN_GOLD_COUNT, count);

    }

    /**
     * 保存红包次数
     *
     * @return
     */
    public void saveRedCount() {
        int count = UserSpCache.getInstance(mContext).getInt(UserSpCache.OPEN_RED_COUNT);
        count++;
        UserSpCache.getInstance(mContext).putInt(UserSpCache.OPEN_RED_COUNT, count);
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
