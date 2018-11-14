package com.application.sven.huinews.main.home.fragment;

import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.animation.Animation;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.application.sven.huinews.R;
import com.application.sven.huinews.base.BaseFragment;
import com.application.sven.huinews.config.Constant;
import com.application.sven.huinews.config.api.Api;
import com.application.sven.huinews.db.UserSpCache;
import com.application.sven.huinews.entity.AdsList;
import com.application.sven.huinews.entity.Comment;
import com.application.sven.huinews.entity.events.VideoLikeEvent;
import com.application.sven.huinews.entity.request.AdCommentRequest;
import com.application.sven.huinews.entity.request.ShareVisitRequest;
import com.application.sven.huinews.entity.request.VideoCollectionCancelRequest;
import com.application.sven.huinews.entity.request.VideoCollectionRequest;
import com.application.sven.huinews.entity.request.VideoCommentRequest;
import com.application.sven.huinews.entity.request.VideoInfoRequest;
import com.application.sven.huinews.entity.request.VideoLikeRequest;
import com.application.sven.huinews.entity.request.VideoListRequest;
import com.application.sven.huinews.entity.request.VideoReportRequest;
import com.application.sven.huinews.entity.request.VideoShareUrlRequest;
import com.application.sven.huinews.entity.request.VideoTaskRequest;
import com.application.sven.huinews.entity.response.VideoChannelResponse;
import com.application.sven.huinews.entity.response.VideoListResponse;
import com.application.sven.huinews.entity.response.VideoTaskResponse;
import com.application.sven.huinews.main.home.contract.HomeTabContract;
import com.application.sven.huinews.main.home.model.HomeTabModel;
import com.application.sven.huinews.main.home.presenter.HomeTabPresenter;
import com.application.sven.huinews.main.video.activity.HorizontalVideoActivity;
import com.application.sven.huinews.main.video.adapter.VideoCommentAdapter;
import com.application.sven.huinews.main.video.adapter.VideoHorizontalAdapter;
import com.application.sven.huinews.main.video.contract.VideoContract;
import com.application.sven.huinews.main.video.model.VideoModel;
import com.application.sven.huinews.main.video.presenter.VideoPresenter;
import com.application.sven.huinews.main.web.activity.WebActivity;
import com.application.sven.huinews.mob.share.MobShare;
import com.application.sven.huinews.mob.share.ShareCallBack;
import com.application.sven.huinews.utils.CommonUtils;
import com.application.sven.huinews.utils.KeyBoradUtils;
import com.application.sven.huinews.utils.LogUtil;
import com.application.sven.huinews.utils.ToastUtils;
import com.application.sven.huinews.utils.ads.AdsConfig;
import com.application.sven.huinews.utils.umeng.UMengUtils;
import com.application.sven.huinews.view.EdittextUtlis;
import com.application.sven.huinews.view.EmptyLayout;
import com.application.sven.huinews.view.MyVideoProgress;
import com.application.sven.huinews.view.RefreshLayout;
import com.application.sven.huinews.view.dialog.GoldRuleDialog;
import com.application.sven.huinews.view.dialog.ReportDialog;
import com.application.sven.huinews.view.dialog.ShareDialog;
import com.dueeeke.videoplayer.player.IjkVideoView;
import com.dueeeke.videoplayer.player.VideoViewManager;
import com.qq.e.ads.nativ.NativeExpressADView;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.umeng.analytics.MobclickAgent;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

/**
 * auther: sunfuyi
 * data: 2018/7/31
 * effect:
 */
public class HomeVideoFragment extends BaseFragment<VideoPresenter, VideoModel> implements VideoContract.View {
    private RecyclerView rv;
    private RecyclerView comment_rv;
    private ImageButton btn_back, btn_close;
    private LinearLayout video_comment_ll;
    private VideoHorizontalAdapter mAdapter;
    private VideoCommentAdapter mVideoCommentAdapter;
    private Animation animationIn, animationOut;
    private ShareDialog mShareDialog;
    private List<VideoListResponse.VideoList.ListBean> mVideoList = new ArrayList<>();
    private VideoInfoRequest mVideoInfoRequest;
    private VideoListResponse.VideoList.ListBean mShareData, mGoldData;
    private VideoChannelResponse.ChannelData.VBean channelData;
    private EmptyLayout comment_empty;
    private RefreshLayout refresh_view;
    private TextView tv_comment_count; //评论数
    private TextView et_video_comment; //评论编辑
    private TextView btn_send;//发送
    private String etComment;
    private int videoId;
    private ReportDialog reportDialog; //举报
    private GoldRuleDialog mGoldRuleDialog; //金币规则dialog
    private RelativeLayout video_progress_layout;
    private MyVideoProgress video_progress;
    private TextView tv_gold_count;
    private int mCurrentProgress; //停止时 的进度
    private long mDuration;
    private VideoListResponse.VideoList.ListBean mCommentData; //评论数据
    private TextView tvMsg;
    private int videoCount, mCount; //播放次数
    private int mCommentPage = 1;
    private boolean isViewCreated;  //Fragment的View加载完毕的标记
    private boolean isUIVisible;    //Fragment对用户可见的标记


    public static HomeVideoFragment getInstance(VideoChannelResponse.ChannelData.VBean channelData) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(Constant.HOME_TAB_PAGE_INDEX, channelData);
        HomeVideoFragment fragment = new HomeVideoFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        isViewCreated = true;
        lazyLoad();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        //isVisibleToUser这个boolean值表示:该Fragment的UI 用户是否可见
        if (isVisibleToUser) {
            isUIVisible = true;
            lazyLoad();

        } else {
            isUIVisible = false;
        }
    }


    private void lazyLoad() {
        //这里进行双重标记判断,是因为setUserVisibleHint会多次回调,并且会在onCreateView执行前回调,必须确保onCreateView加载完毕且页面可见,才加载数据
        if (isViewCreated && isUIVisible) {
            // mAdsConfig = new AdsConfig(mContext);
            // mAdsConfig.tencentNativeAD(5, AdsConfig.TENCENT_AD_ID);
            //  mAdsConfig.tencentNativeRightImgAD(1, AdsConfig.TENCENT_AD_RIGHT_IMG);
            //   mAdsConfig.kuaiyanAd(ad_fl);
            mPresenter.onVideoWatchCount();
            mPresenter.onVideoList();
            //数据加载完毕,恢复标记,防止重复加载
            isViewCreated = false;
            isUIVisible = false;
        }
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_horizontal_video;
    }

    @Override
    public void initObject() {
        setMVP();
        setVideoInfo();
        setVideoCommentInfo();
        channelData = (VideoChannelResponse.ChannelData.VBean) getArguments().getSerializable(Constant.HOME_TAB_PAGE_INDEX);
        MobclickAgent.onEvent(mContext, UMengUtils.HOME_TAB_INDEX_ + channelData.getId());
        MobclickAgent.onEvent(mContext, UMengUtils.HOME_TAB_INDEX_ + channelData.getId(), UMengUtils.HOME_TAB_INDEX_ + channelData.getId());


        // mEmptyLayout.setErrorType(EmptyLayout.LOADING, -1);
        if (mGoldRuleDialog == null) {
            mGoldRuleDialog = new GoldRuleDialog(mContext);
        }
    }

    @Override
    protected void initView(View v) {
        btn_back = v.findViewById(R.id.btn_back);
        btn_close = v.findViewById(R.id.btn_close);
        rv = v.findViewById(R.id.video_rv);
        video_comment_ll = v.findViewById(R.id.video_comment_ll);
        comment_rv = v.findViewById(R.id.comment_rv);
        comment_empty = v.findViewById(R.id.comment_empty);
        tv_comment_count = v.findViewById(R.id.tv_comment_count);
        et_video_comment = v.findViewById(R.id.et_video_comment);
        btn_send = v.findViewById(R.id.btn_send);
        refresh_view = v.findViewById(R.id.refresh_view);
        video_progress_layout = v.findViewById(R.id.video_progress_layout);
        video_progress = v.findViewById(R.id.video_progress);
        tv_gold_count = v.findViewById(R.id.tv_gold_count);
        // mEmptyLayout =v.findViewById(R.id.mEmptyLayout);
        // mEmptyLayout.setBackgroundColor(getResources().getColor(R.color.color_black));
        mDuration = UserSpCache.getInstance(mContext).getInt(UserSpCache.WATCH_TIME);
        if (mDuration < 1) {
            mDuration = 20;
        }
        mDuration = mDuration * 1000;
        video_progress.setDuration(mDuration);
    }

    @Override
    public void initEvents() {
        btn_back.setOnClickListener(this);
        btn_close.setOnClickListener(this);
        btn_send.setOnClickListener(this);
        et_video_comment.setOnClickListener(this);
        video_progress_layout.setOnClickListener(this);
        rv.addOnChildAttachStateChangeListener(new RecyclerView.OnChildAttachStateChangeListener() {
            @Override
            public void onChildViewAttachedToWindow(View view) {

            }

            @Override
            public void onChildViewDetachedFromWindow(View view) {
                IjkVideoView ijkVideoView = view.findViewById(R.id.video_player);
                View mMask = view.findViewById(R.id.mask_view);
                if (ijkVideoView != null && !ijkVideoView.isFullScreen()) {
                    ijkVideoView.stopPlayback();
                    LogUtil.showLog("msg---onChildViewDetachedFromWindow");
                   /* if (!ijkVideoView.isPlaying()){
                        video_progress.stopProgress();
                    }*/
                    //   AnimationUtils.showAndHiddenAnimation(mMask, AnimationUtils.AnimationState.STATE_HIDDEN, 300);
                }
            }
        });
        rv.addOnScrollListener(new RecyclerView.OnScrollListener() {

            int firstVisibleItem, lastVisibleItem, visibleCount;

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    autoPlayVideo(recyclerView);
                    mPresenter.cacnleGoldCountAnim(tv_gold_count, false);
                } else if (newState == RecyclerView.SCROLL_STATE_DRAGGING) {
                    mPresenter.cacnleGoldCountAnim(tv_gold_count, true);
                } else if (newState == RecyclerView.SCROLL_STATE_SETTLING) {
                    video_progress.stopProgress(); //快速上下滑动时金币动画需要暂停
                    mPresenter.cacnleGoldCountAnim(tv_gold_count, true);
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
                firstVisibleItem = layoutManager.findFirstVisibleItemPosition();
                lastVisibleItem = layoutManager.findLastVisibleItemPosition();
                visibleCount = lastVisibleItem - firstVisibleItem;//记录可视区域item个数

                //当滑动到倒数第三位的时候加载数据
                if (lastVisibleItem == mVideoList.size() - 3) {
                    isRefresh = false;
                    PAGE += 1;
                    mPresenter.onVideoList();
                }
            }

            private void setVisible(RecyclerView view) {
                View v;
                if (visibleCount >= lastVisibleItem) {
                    v = view.getChildAt(visibleCount - 1).findViewById(R.id.mask_view);
                } else {
                    v = view.getChildAt(visibleCount).findViewById(R.id.mask_view);
                }
                // AnimationUtils.showAndHiddenAnimation(v, AnimationUtils.AnimationState.STATE_HIDDEN, 300);

            }

            private void autoPlayVideo(RecyclerView view) {

                for (int i = 0; i < visibleCount + 1; i++) {
                    if (view == null && view.getChildAt(i) == null) {
                        continue;
                    }

                    IjkVideoView ijkVideoView = view.getChildAt(i).findViewById(R.id.video_player);
                    View mMask = view.getChildAt(i).findViewById(R.id.mask_view);
                    if (ijkVideoView != null) {
                        Rect rect = new Rect();
                        ijkVideoView.getLocalVisibleRect(rect);
                        int videoHeight = ijkVideoView.getHeight();
                        if (rect.top == 0 && rect.bottom == videoHeight) {
                            if (!ijkVideoView.isPlaying()) {
                                mDuration = video_progress.surplusTime();

                                video_progress.stopProgress();
                                mCurrentProgress = video_progress.getProgressCurrent();
                                LogUtil.showLog("msg----mDuration:" + mDuration);
                                ijkVideoView.start();
                                //  AnimationUtils.showAndHiddenAnimation(mMask, AnimationUtils.AnimationState.STATE_HIDDEN, 300);
                            } /*  else {
                                AnimationUtils.showAndHiddenAnimation(mMask, AnimationUtils.AnimationState.STATE_SHOW, 300);
                            }*/
                            return;
                        }
                    }
                }
            }
        });


        mAdapter.setOnShareClickLisenter(new VideoHorizontalAdapter.onItemFuncationClickLisenter() {
            @Override
            public void onShare(VideoListResponse.VideoList.ListBean mData) {
                mShareData = mData;
                showShareDialog();
            }

            @Override
            public void onFollow(VideoListResponse.VideoList.ListBean mData) {
                if (mData.isIs_follow()) {
                    mPresenter.onFollowUser(mData.getUser_id());
                } else {
                    mPresenter.onCancelFollowUser(mData.getUser_id());
                }
                EventBus.getDefault().post(new VideoLikeEvent(mData));
            }

            @Override
            public void onLike(VideoListResponse.VideoList.ListBean mData) {
                VideoLikeRequest request = new VideoLikeRequest();
                request.setId(mData.getId());
                request.setType(mData.isIs_up() ? Constant.LIKE_TYPE : Constant.UN_LIKE_TYPE);
                mPresenter.onLikeVideo(request, mData);
            }


            @Override
            public void onComment(VideoListResponse.VideoList.ListBean mData, TextView tv_msg) {
                tvMsg = tv_msg;
                mCommentPage = 1;
                showCommentView(mData);
            }

            @Override
            public void onCollection(VideoListResponse.VideoList.ListBean mData) {
                if (mData.isIs_collected()) {
                    mPresenter.onCollection(getVideoCollectionRequest(mData.getId()));
                } else {
                    mPresenter.onCancelCollection(getVideoCollectionCancelRequest(mData.getId()));
                }
                EventBus.getDefault().post(new VideoLikeEvent(mData));
            }
        });


        mAdapter.setOnVideoPlayStatusLisenter(new VideoHorizontalAdapter.onVideoPlayStatusLisenter()

        {
            @Override
            public void onVideoComplete(VideoListResponse.VideoList.ListBean mData) {
                video_progress.stopProgress();
                mCurrentProgress = video_progress.getProgressCurrent();
            }

            @Override
            public void onVideoPlaying(VideoListResponse.VideoList.ListBean mData) {
                mPresenter.onVideoVisit(mData.getId());
                mGoldData = mData;
                if (!mPresenter.isCanGetCoinByVideo(mData)) {
                    video_progress_layout.setVisibility(View.GONE);
                    return;
                }

                if (mData.getIs_gold() == 0 && mData.getIs_redpack() == 0) {
                    video_progress_layout.setVisibility(View.GONE);
                } else if (videoCount > 0) {
                    video_progress_layout.setVisibility(View.VISIBLE);
                    video_progress.setProgress(mCurrentProgress);
                    video_progress.setDuration(mDuration);
                    video_progress.animStart();
                } else {
                    video_progress_layout.setVisibility(View.GONE);
                }

            }

            @Override
            public void onVideoPaused() {
                video_progress.stopProgress();
                mCurrentProgress = video_progress.getProgressCurrent();
            }

            @Override
            public void onVideoError() {

            }
        });

        video_progress.OnVideoProgressLisenter(new MyVideoProgress.OnVideoProgressLisenter()

        {
            @Override
            public void end() {
                mCurrentProgress = 0;
                mDuration = 20000;
                video_progress.setDuration(mDuration);
                video_progress.setProgress(mCurrentProgress);
                video_progress.stopProgress();
                //进度完成后增加金币
                //视频播放完成
                if (!mPresenter.isCanGetCoinByVideo(mGoldData)) {
                    return;
                }
                if (mGoldData.getIs_gold() == 1) {
                    mPresenter.onVideoGoldTask(mGoldData, VideoTaskRequest.TASK_ID_READ_NEWS);
                } else if (mGoldData.getIs_redpack() == 1) {
                    mPresenter.onVideoGoldTask(mGoldData, VideoTaskRequest.TASK_ID_READ_RED_NEWS);
                }

            }
        });

        refresh_view.setOnLoadmoreListener(new OnLoadmoreListener() {
            @Override
            public void onLoadmore(com.scwang.smartrefresh.layout.api.RefreshLayout refreshlayout) {
                isRefresh = false;
                mCommentPage += 1;
                mPresenter.onVideoComment(getVideoCommentRequest(mCommentPage, videoId, Constant.COMMENT_UP));
            }
        });

        refresh_view.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(com.scwang.smartrefresh.layout.api.RefreshLayout refreshlayout) {
                isRefresh = true;
                mCommentPage = 1;
                mPresenter.onVideoComment(getVideoCommentRequest(mCommentPage, videoId, Constant.COMMENT_UP));
            }
        });


        mVideoCommentAdapter.setItemOnLongClickLisenter(new VideoCommentAdapter.OnLongClickLisenter()


        {
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

        mVideoCommentAdapter.setOnLikeListener(new VideoCommentAdapter.OnLikeListener()

        {
            @Override
            public void onLikeChange(Comment comment) {
                mPresenter.onVideoCommentLike(comment.getId());
            }
        });

        mGoldRuleDialog.setOnDialogEnterListener(new GoldRuleDialog.OnDialogEnterListener()

        {
            @Override
            public void toWeb() {
                WebActivity.toThis(mContext, Api.MY_INCOME_TYPE1);
                if (video_progress != null) {
                    video_progress.stopProgress();
                    mCurrentProgress = video_progress.getProgressCurrent();
                    mDuration = video_progress.surplusTime();
                }
            }
        });

    }

    @Override
    public void OnClickEvents(View v) {
        if (v == btn_back) {
            //  finish();
        } else if (v == btn_close) {
            hideCommentView();
        } else if (v == btn_send) {
            sendComment();
        } else if (v == et_video_comment) {
            setEtListener();
        } else if (v == video_progress_layout) {

            mGoldRuleDialog.show();
        }
    }

    private void setVideoInfo() {
        mAdapter = new VideoHorizontalAdapter(mContext);
        rv.setLayoutManager(new LinearLayoutManager(mContext));
        rv.setAdapter(mAdapter);

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
                    request.setVideoId(mShareData.getId() + "");
                    mPresenter.onVideoShare(request, type);
                } else {
                    VideoReportRequest request = new VideoReportRequest();
                    request.setVideoId(mShareData.getId() + "");
                    mPresenter.onVideoReport(request);
                }

            }
        });
    }

    /**
     * 显示评论界面
     */
    private void showCommentView(VideoListResponse.VideoList.ListBean mData) {
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
        mPresenter.onVideoComment(getVideoCommentRequest(mCommentPage, mData.getId(), Constant.COMMENT_UP));
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

    private void cachePorgress() {
        mDuration = video_progress.surplusTime();
        video_progress.stopProgress();
        mCurrentProgress = video_progress.getProgressCurrent();
    }


    @Override
    public VideoListRequest videoListRequest() {
        VideoListRequest request = new VideoListRequest();
        request.setPage(PAGE);
        request.setLimit(LIMIT);
        request.setTypeId(channelData.getId());
        return request;
    }

    @Override
    public void setVideoList(VideoListResponse.VideoList videoLists) {
        // mEmptyLayout.setErrorType(EmptyLayout.HIDE_LAYOUT, -1);
        final List<VideoListResponse.VideoList.ListBean> responseData = videoLists.getList();
        for (int i = 0; i < responseData.size(); i++) {
            if (responseData.get(i).getId() == mVideoInfoRequest.getmVideoInfo().getId()) {
                responseData.remove(i);
            }
        }
        if (isRefresh) {
            responseData.add(0, mVideoInfoRequest.getmVideoInfo());
        }
        mVideoList.addAll(responseData);
        mAdapter.setData(mVideoList);
        mAdapter.notifyDataSetChanged();

        if (isRefresh) {
            rv.post(new Runnable() {
                @Override
                public void run() {
                    mDuration = video_progress.surplusTime();
                    //自动播放第一个
                    View view = rv.getChildAt(0);
                    if (view != null) {
                        IjkVideoView ijkVideoView = view.findViewById(R.id.video_player);
                        ijkVideoView.start();
                    }
                    // AnimationUtils.showAndHiddenAnimation(mMask, AnimationUtils.AnimationState.STATE_HIDDEN, 300);
                }
            });
        }
    }

    @Override
    public VideoCommentRequest getVideoCommentRequest(int page, int videoId, String sort) {
        VideoCommentRequest request = new VideoCommentRequest();
        request.setPage(page + "");
        request.setVideo_id(videoId + "");
        request.setOrder(sort);

        return request;
    }

    @Override
    public VideoCollectionRequest getVideoCollectionRequest(int videoId) {
        VideoCollectionRequest request = new VideoCollectionRequest();
        request.setrId(videoId);
        request.setType(Constant.CHANNEL_TYPE_VIDEO);
        return request;
    }

    @Override
    public VideoCollectionCancelRequest getVideoCollectionCancelRequest(int videoId) {
        VideoCollectionCancelRequest request = new VideoCollectionCancelRequest();
        request.setType(Constant.CHANNEL_TYPE_VIDEO);
        request.setrId(videoId + "");
        return request;
    }

    @Override
    public AdCommentRequest getAdCommentRequest() {
        AdCommentRequest request = new AdCommentRequest();
        request.setCommentContent(etComment);
        request.setVideoId(videoId);
        return request;
    }


    @Override
    public void setVideoCollectionOk(boolean isCollection) {

    }

    @Override
    public void setVideoLikedOk(VideoListResponse.VideoList.ListBean mData) {
        EventBus.getDefault().post(new VideoLikeEvent(mData));
    }

    @Override
    public void setVideoShareUrl(String url, final int type) {
        mShareData.setVideo_url(url);
        String shareJson = mPresenter.getShareJson(mShareData, type);
        MobShare mMobShare = new MobShare(mContext);
        mMobShare.shareToOne(mContext, shareJson, new ShareCallBack() {
            @Override
            public void onResponse(String response) {
                mPresenter.onShareVisit(response, ShareVisitRequest.VIDEO_LIST, type);
            }
        });
    }


    @Override
    public void setGoldView(
            final VideoListResponse.VideoList.ListBean mData, VideoTaskResponse.Data data, int type) {
        //将视频加入数据库
        mPresenter.addVideoToDB(mData);
        videoCount--;
        if (videoCount > 0) {
            ToastUtils.showLong(mContext, "您还可以观看" + videoCount + "次金币视频");
        }

        //  if (videoCount < data.getmCount()) {
        //     video_progress_layout.setVisibility(View.GONE);
        //    return;
        // }

        mPresenter.goldCountAnim(mContext, tv_gold_count, video_progress_layout, data.getCount());
        mData.setSaveRedVideo(true);
        EventBus.getDefault().post(new VideoLikeEvent(mData));
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
        mPresenter.onVideoComment(getVideoCommentRequest(mCommentPage, videoId, Constant.COMMENT_TIME));
        mCommentData.setComment_count(mCommentData.getComment_count() + 1);
        tvMsg.setText(CommonUtils.getLikeCount(mCommentData.getComment_count()));
        tv_comment_count.setText("评论" + CommonUtils.getLikeCount(mCommentData.getComment_count()));
        EventBus.getDefault().post(new VideoLikeEvent(mCommentData));
    }

    @Override
    public void setVideoCount(int count) {
        videoCount = count;
        LogUtil.showLog("msg====setVideoCount:" + count);
    }

    @Override
    public void setInGoldError() {
        video_progress_layout.setVisibility(View.GONE);
    }


    private void sendComment() {
        if (TextUtils.isEmpty(etComment)) {
            ToastUtils.showLong(mContext, getString(R.string.null_commnet));
            return;
        }
        mPresenter.onVideoAdComment();
    }


    private void setEtListener() {
        EdittextUtlis.showCommentEdit(getActivity(), et_video_comment, new EdittextUtlis.liveCommentResult() {
            @Override
            public void onResult(boolean confirmed, String comment) {
                if (confirmed) {
                    etComment = comment;
                    sendComment();
                }
            }
        });
    }



/*
    @Override
    protected void onPause() {
        super.onPause();
        VideoViewManager.instance().releaseVideoPlayer();
        if (video_progress != null) {
            cachePorgress();
            video_progress.stopProgress();
        }
    }*/


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        VideoViewManager.instance().releaseVideoPlayer();
        if (video_progress != null) {
            cachePorgress();
            video_progress.stopProgress();
        }
    }


    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showErrorTip(int code, String msg) {

    }
}
