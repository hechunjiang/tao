package com.application.sven.huinews.main.home.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.animation.Animation;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.application.sven.huinews.R;
import com.application.sven.huinews.base.BaseActivity;
import com.application.sven.huinews.config.Constant;
import com.application.sven.huinews.entity.AdsList;
import com.application.sven.huinews.entity.Comment;
import com.application.sven.huinews.entity.request.AdCommentRequest;
import com.application.sven.huinews.entity.request.ShareVisitRequest;
import com.application.sven.huinews.entity.request.VideoCommentRequest;
import com.application.sven.huinews.entity.request.VideoDetailsRequest;
import com.application.sven.huinews.entity.request.VideoLikeRequest;
import com.application.sven.huinews.entity.request.VideoReportRequest;
import com.application.sven.huinews.entity.request.VideoShareUrlRequest;
import com.application.sven.huinews.entity.response.MovieDetailResponse;
import com.application.sven.huinews.entity.response.VideoDetailsResponse;
import com.application.sven.huinews.entity.response.VideoListResponse;
import com.application.sven.huinews.main.home.adapter.VideoDetailsRecommedAdapter;
import com.application.sven.huinews.main.home.contract.VideoDetailsContract;
import com.application.sven.huinews.main.home.model.VideoDetailsModel;
import com.application.sven.huinews.main.home.presenter.VideoDetailsPresenter;
import com.application.sven.huinews.main.preemption.activity.MovieDetailsActivity;
import com.application.sven.huinews.main.preemption.adapter.RecommedAdapter;
import com.application.sven.huinews.main.video.adapter.VideoCommentAdapter;
import com.application.sven.huinews.mob.share.MobShare;
import com.application.sven.huinews.mob.share.ShareCallBack;
import com.application.sven.huinews.utils.CommonUtils;
import com.application.sven.huinews.utils.FrescoUtil;
import com.application.sven.huinews.utils.KeyBoradUtils;
import com.application.sven.huinews.utils.ToastUtils;
import com.application.sven.huinews.utils.ads.AdsConfig;
import com.application.sven.huinews.utils.statusbar.Eyes;
import com.application.sven.huinews.view.EdittextUtlis;
import com.application.sven.huinews.view.EmptyLayout;
import com.application.sven.huinews.view.RefreshLayout;
import com.application.sven.huinews.view.dialog.ReportDialog;
import com.application.sven.huinews.view.dialog.ShareDialog;
import com.application.sven.huinews.view.video.controller.HorizontalVideoController;
import com.application.sven.huinews.view.video.controller.MovieController;
import com.application.sven.huinews.view.video.controller.VideoDetailsController;
import com.dueeeke.videoplayer.listener.VideoListener;
import com.dueeeke.videoplayer.player.IjkPlayer;
import com.dueeeke.videoplayer.player.IjkVideoView;
import com.dueeeke.videoplayer.player.PlayerConfig;
import com.facebook.drawee.view.SimpleDraweeView;
import com.qq.e.ads.nativ.NativeExpressADView;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.List;

import tv.danmaku.ijk.media.player.IjkMediaPlayer;

/**
 * auther: sunfuyi
 * data: 2018/6/7
 * effect: 小视频详情
 */
public class VideoDetailsActivity extends BaseActivity<VideoDetailsPresenter, VideoDetailsModel> implements VideoDetailsContract.View {
    private IjkVideoView player;
    private ImageButton btn_back, btn_share, btn_close;

    private TextView tv_title, tv_user_name;
    private TextView tv_like, tv_msg;
    private ImageView iv_like;
    private SimpleDraweeView user_head;
    private RelativeLayout ad_layout;
    private LinearLayout btn_like, btn_msg;
    private EmptyLayout mEmptyLayout, comment_empty;
    private RefreshLayout refresh_view;
    private RecyclerView comment_rv;
    private RecyclerView recommend_rv; //为你推荐
    private LinearLayout video_comment_ll;
    private VideoDetailsRecommedAdapter mRecommedAdapter;
    private int videoId;
    private ShareDialog mShareDialog;
    private VideoDetailsController mController;
    private PlayerConfig mPlayerConfig;
    private AdsConfig mAdsConfig;
    private VideoDetailsResponse.VideoInfo mVideoInfo;
    private Animation animationIn, animationOut;
    private TextView tv_comment_count; //评论数
    private TextView et_video_comment; //评论编辑
    private TextView btn_send;// 发送
    private String etComment;
    private ReportDialog reportDialog;
    private VideoCommentAdapter mVideoCommentAdapter;
    private VideoDetailsResponse.VideoInfo mCommentData; //评论数据


    public static void toThis(Context mContext, int videoId) {
        Intent intent = new Intent(mContext, VideoDetailsActivity.class);
        Bundle bundle = new Bundle();
        bundle.putInt(Constant.BUNDLE_TO_VIDEO_ID, videoId);
        intent.putExtras(bundle);
        mContext.startActivity(intent);
    }

    @Override
    public int getLayoutId() {
        Eyes.setStatusBarColor(this, getResources().getColor(R.color.color_black));
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            videoId = bundle.getInt(Constant.BUNDLE_TO_VIDEO_ID);
        }
        return R.layout.activity_video_details;
    }

    @Override
    public void initView() {
        tv_title = findViewById(R.id.tv_title);
        tv_user_name = findViewById(R.id.tv_user_name);
        tv_like = findViewById(R.id.tv_like);
        tv_msg = findViewById(R.id.tv_msg);
        iv_like = findViewById(R.id.iv_like);
        user_head = findViewById(R.id.user_head);
        btn_like = findViewById(R.id.btn_like);
        btn_msg = findViewById(R.id.btn_msg);
        player = findViewById(R.id.video_player);
        mEmptyLayout = findViewById(R.id.mEmptyLayout);
        ad_layout = findViewById(R.id.ad_layout);
        recommend_rv = findViewById(R.id.recommend_rv);
        btn_back = findViewById(R.id.btn_back);
        btn_share = findViewById(R.id.btn_share);
        btn_close = findViewById(R.id.btn_close);
        video_comment_ll = findViewById(R.id.video_comment_ll);
        comment_rv = findViewById(R.id.comment_rv);
        comment_empty = findViewById(R.id.comment_empty);
        tv_comment_count = findViewById(R.id.tv_comment_count);
        et_video_comment = findViewById(R.id.et_video_comment);
        btn_send = findViewById(R.id.btn_send);
        refresh_view = findViewById(R.id.refreshView);
    }

    @Override
    public void initEvents() {
        btn_like.setOnClickListener(this);
        btn_msg.setOnClickListener(this);
        user_head.setOnClickListener(this);
        tv_user_name.setOnClickListener(this);
        btn_back.setOnClickListener(this);
        btn_share.setOnClickListener(this);
        btn_send.setOnClickListener(this);
        btn_close.setOnClickListener(this);
        et_video_comment.setOnClickListener(this);


        mVideoCommentAdapter.setItemOnLongClickLisenter(new VideoCommentAdapter.OnLongClickLisenter() {
            @Override
            public void onLongClick(final Comment comment) {
                if (reportDialog == null) {
                    reportDialog = new ReportDialog(mContext);
                }
                reportDialog.show();
                reportDialog.setOnReportLisenter(new ReportDialog.onReportLisenter() {
                    @Override
                    public void report() {
                        mPresenter.onVideoCommentReport(comment.getId());
                        reportDialog.dismiss();
                    }
                });
            }
        });

        mVideoCommentAdapter.setOnLikeListener(new VideoCommentAdapter.OnLikeListener() {
            @Override
            public void onLikeChange(final Comment comment) {
                mPresenter.onVideoCommentLike(comment.getId());
            }
        });


        refresh_view.setOnLoadmoreListener(new OnLoadmoreListener() {
            @Override
            public void onLoadmore(com.scwang.smartrefresh.layout.api.RefreshLayout refreshlayout) {
                isRefresh = false;
                PAGE += 1;
                mPresenter.onVideoComment(getVideoCommentRequest(PAGE, videoId,Constant.COMMENT_UP));
            }
        });
        refresh_view.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(com.scwang.smartrefresh.layout.api.RefreshLayout refreshlayout) {
                isRefresh = true;
                PAGE = 1;
                mPresenter.onVideoComment(getVideoCommentRequest(PAGE, videoId,Constant.COMMENT_UP));
            }
        });

    }

    @Override
    public void onClickEvent(View v) {
        if (v == btn_back) {
            finish();
        } else if (v == btn_share) {
            showShareDialog();
        } else if (v == btn_like) {
            setVideoLike();
        } else if (v == btn_msg) {
            showCommentView(mVideoInfo);
        } else if (v == btn_send) {
            sendComment();
        } else if (v == btn_close) {
            hideCommentView();
        } else if (v == et_video_comment) {
            setEtListener();
        }
    }

    @Override
    public void initObject() {
        setMVP();
        mEmptyLayout.setErrorType(EmptyLayout.LOADING, -1);
        mAdsConfig = new AdsConfig(mContext);
        mPresenter.onVideoInfo();
        setRecommendInfo();
        setVideoCommentInfo();
        //banner广告
        mAdsConfig.getBaiduBannerAd(mContext, ad_layout);
    }

    @Override
    public VideoDetailsRequest getVideoDetailsRequest() {
        VideoDetailsRequest request = new VideoDetailsRequest();
        request.setrId(videoId);
        return request;
    }

    @Override
    public VideoCommentRequest getVideoCommentRequest(int page, int videoId,String sort) {
        VideoCommentRequest request = new VideoCommentRequest();
        request.setPage(page + "");
        request.setVideo_id(videoId + "");
        request.setOrder(sort);
        return request;
    }


    @Override
    public void setVideoDetaisInfo(VideoDetailsResponse response) {

        setVideoInfo(response);
        setRecommend(response.getData());
    }

    @Override
    public void setVideoShareUrl(String url, final int type) {
        mVideoInfo.setVideo_url(url);
        String shareJson = mPresenter.getShareJson(mVideoInfo, type);
        MobShare mMobShare = new MobShare(mContext);
        mMobShare.shareToOne(mContext, shareJson, new ShareCallBack() {
            @Override
            public void onResponse(String response) {
                mPresenter.onShareVisit(response, ShareVisitRequest.VIDEO_DETAIL, type);
            }
        });
    }

    @Override
    public void setVideoLikedOk(VideoDetailsResponse.VideoInfo mData) {

    }

    @Override
    public AdCommentRequest getAdCommentRequest() {
        AdCommentRequest request = new AdCommentRequest();
        request.setCommentContent(etComment);
        request.setVideoId(videoId);
        return request;
    }

    @Override
    public void setComment(List<Comment> mComment) {
        if (isRefresh) {
            if (mComment.size() > 0) {
                comment_empty.setErrorType(EmptyLayout.HIDE_LAYOUT, -1);
            } else {
                comment_empty.setErrorType(EmptyLayout.NO_DATA, EmptyLayout.NO_COMMENT);
            }
            refresh_view.finishRefresh();
        } else {
            if (mComment.size() <= 0) {
                ToastUtils.showLong(mContext, "没有更多数据了");
            }
            refresh_view.finishLoadmore();
        }

        mVideoCommentAdapter.setData(mComment, isRefresh);
    }

    @Override
    public void adCommentOk() {
        ToastUtils.showLong(mContext, "评论成功");
        KeyBoradUtils.HideKeyboard(et_video_comment);
        mPresenter.onVideoComment(getVideoCommentRequest(PAGE, videoId,Constant.COMMENT_TIME));
        mCommentData.setComment_count(mCommentData.getComment_count() + 1);
        tv_msg.setText(CommonUtils.getLikeCount(mCommentData.getComment_count()));
        tv_comment_count.setText("评论" + CommonUtils.getLikeCount(mCommentData.getComment_count()));

    }

    List<AdsList> ads;

    @Override
    public void setAdsLit(List<AdsList> ads) {
        this.ads = ads;
        mAdsConfig.tencentNativeAD(1, AdsConfig.TENCENT_AD_BIG_IMG);
        mPresenter.onTencentAds(mAdsConfig);
    }

    @Override
    public void setTencentAds(List<NativeExpressADView> list) {
        for (int i = 0; i < ads.size(); i++) {
            if (ads.get(i).getAd_type().equals(AdsConfig.AD_TYPE_TENCENT)) {
                //设置腾讯广告
                mRecommedAdapter.datas().add(ads.get(i).getIn_position(), list.get(i));
            }
        }
        mRecommedAdapter.notifyDataSetChanged();
    }


    private void sendComment() {
        if (TextUtils.isEmpty(etComment)) {
            ToastUtils.showLong(mContext, getString(R.string.null_commnet));
            return;
        }
        mPresenter.onVideoAdComment();
    }


    private void setEtListener() {
        EdittextUtlis.showCommentEdit(this, et_video_comment, new EdittextUtlis.liveCommentResult() {
            @Override
            public void onResult(boolean confirmed, String comment) {
                if (confirmed) {
                    etComment = comment;
                    sendComment();
                }
            }
        });
    }

    private void setVideoInfo(final VideoDetailsResponse response) {
        mVideoInfo = response.getData();
        mEmptyLayout.setErrorType(EmptyLayout.HIDE_LAYOUT, -1);
        FrescoUtil.loadDefImg(user_head, response.getData().getUser_avatar());
        tv_title.setText(response.getData().getTitle());
        tv_user_name.setText(response.getData().getUser_nickname());
        tv_like.setText(CommonUtils.getLikeCount(response.getData().getLike_count()));
        tv_msg.setText(CommonUtils.getLikeCount(response.getData().getComment_count()));

        iv_like.setImageResource(mVideoInfo.isIs_up() ? R.mipmap.comment_liked : R.mipmap.comment_like);
        tv_like.setTextColor(mVideoInfo.isIs_up() ? mContext.getResources().getColor(R.color.c_eb3e44) : mContext.getResources().getColor(R.color.color_line));

        if (mController == null) {
            mController = new VideoDetailsController(mContext);
        }

        // FrescoUtil.loadDefImg(mController.getThumb(), response.getData().getVideo_cover());
        player.setPlayerConfig(mPlayerConfig);

        player.setUrl(response.getData().getVideo_url());
        player.setVideoController(mController);
        player.setPlayerConfig(new PlayerConfig.Builder().autoRotate()//自动旋转屏幕
                .enableCache()//启用边播边存
                //.enableMediaCodec()//启动硬解码
//                .usingSurfaceView()//使用SurfaceView
                .setLooping()
                .setCustomMediaPlayer(new IjkPlayer() {
                    @Override
                    public void setOptions() {
                        super.setOptions();
                        //支持concat
                        mMediaPlayer.setOption(IjkMediaPlayer.OPT_CATEGORY_FORMAT, "safe", 0);
                        mMediaPlayer.setOption(IjkMediaPlayer.OPT_CATEGORY_FORMAT, "protocol_whitelist", "rtmp,concat,ffconcat,file,subfile,http,https,tls,rtp,tcp,udp,crypto,rtsp");
                        //支持rtsp
                        mMediaPlayer.setOption(IjkMediaPlayer.OPT_CATEGORY_FORMAT, "rtsp_transport", "tcp");
                    }
                })
                .build());
        player.start();

        player.setVideoListener(new VideoListener() {
            @Override
            public void onVideoStarted() {
                mPresenter.onVideoVisit(videoId);
            }

            @Override
            public void onVideoPaused() {

            }

            @Override
            public void onComplete() {

            }

            @Override
            public void onPrepared() {

            }

            @Override
            public void onError() {

            }

            @Override
            public void onInfo(int what, int extra) {

            }
        });


        user_head.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserInfoActivity.toThis(mContext, response.getData().getUser_id());
            }
        });
    }

    private void setRecommendInfo() {
        mRecommedAdapter = new VideoDetailsRecommedAdapter(mContext);
        recommend_rv.setNestedScrollingEnabled(false);
        recommend_rv.setLayoutManager(new LinearLayoutManager(mContext));
        recommend_rv.setAdapter(mRecommedAdapter);

        mRecommedAdapter.setmOnItemClickLisenter(new VideoDetailsRecommedAdapter.OnItemClickLisenter() {
            @Override
            public void onItemClick(int movieId) {
                VideoDetailsActivity.toThis(mContext, movieId);
                finish();
            }
        });
    }

    /**
     * 设置推荐
     */
    private void setRecommend(VideoDetailsResponse.VideoInfo mVideoInfo) {
        mRecommedAdapter.setData(mVideoInfo.getRecommen());
        mPresenter.onAdsList(PAGE);
    }

    private void setVideoLike() {
        mVideoInfo.setIs_up(!mVideoInfo.isIs_up());
        mVideoInfo.setLike_count(mVideoInfo.isIs_up() ? mVideoInfo.getLike_count() + 1 : mVideoInfo.getLike_count() - 1);
        iv_like.setImageResource(mVideoInfo.isIs_up() ? R.mipmap.comment_liked : R.mipmap.comment_like);
        tv_like.setTextColor(mVideoInfo.isIs_up() ? mContext.getResources().getColor(R.color.c_eb3e44) : mContext.getResources().getColor(R.color.color_line));

        tv_like.setText(CommonUtils.getLikeCount(mVideoInfo.getLike_count()));
        VideoLikeRequest request = new VideoLikeRequest();
        request.setId(mVideoInfo.getId());
        request.setType(mVideoInfo.isIs_up() ? Constant.LIKE_TYPE : Constant.UN_LIKE_TYPE);
        mPresenter.onLikeVideo(request, mVideoInfo);
    }

    private void setVideoCommentInfo() {
        comment_rv.setLayoutManager(new LinearLayoutManager(mContext));
        mVideoCommentAdapter = new VideoCommentAdapter(mContext);
        comment_rv.setAdapter(mVideoCommentAdapter);
    }

    private void showShareDialog() {
        if (mShareDialog == null) {
            mShareDialog = new ShareDialog(mContext);

        }
        mShareDialog.show();
        mShareDialog.setOnShareListener(new ShareDialog.OnShareListener() {
            @Override
            public void onShare(int type) {
                if (type != Constant.SHARE_TYPE_REPORT) {
                    VideoShareUrlRequest request = new VideoShareUrlRequest();
                    request.setVideoId(videoId + "");
                    mPresenter.onVideoShare(request, type);
                } else {
                    VideoReportRequest request = new VideoReportRequest();
                    request.setVideoId(videoId + "");
                    mPresenter.onVideoReport(request);
                }

            }
        });
    }

    /**
     * 显示评论界面
     */
    private void showCommentView(VideoDetailsResponse.VideoInfo mData) {
        this.mCommentData = mData;
        isRefresh = true;
        comment_empty.setErrorType(EmptyLayout.LOADING, -1);
        if (animationIn == null) {
            animationIn = android.view.animation.AnimationUtils.loadAnimation(mContext, R.anim.movie_count_in);
        }
        video_comment_ll.startAnimation(animationIn);
        video_comment_ll.setVisibility(View.VISIBLE);

        tv_comment_count.setText("评论" + mData.getComment_count());
        videoId = mData.getId();
        mPresenter.onVideoComment(getVideoCommentRequest(PAGE, mData.getId(),Constant.COMMENT_UP));
    }

    /**
     * 隐藏评论界面
     */
    private void hideCommentView() {
        if (animationOut == null) {
            animationOut = android.view.animation.AnimationUtils.loadAnimation(mContext, R.anim.movie_count_out);
        }
        video_comment_ll.startAnimation(animationOut);//开始动画
        video_comment_ll.setVisibility(View.GONE);
    }

    @Override
    protected void onResume() {
        super.onResume();
        player.resume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        player.release();

    }


    @Override
    public void onBackPressed() {
        if (!player.onBackPressed()) {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            if (video_comment_ll.getVisibility() == View.VISIBLE) {
                hideCommentView();
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }

}
