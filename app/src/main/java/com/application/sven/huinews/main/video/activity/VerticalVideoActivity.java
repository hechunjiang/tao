package com.application.sven.huinews.main.video.activity;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewParent;
import android.view.ViewTreeObserver;
import android.view.animation.Animation;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.application.sven.huinews.R;
import com.application.sven.huinews.base.BaseActivity;
import com.application.sven.huinews.config.Constant;
import com.application.sven.huinews.config.api.Api;
import com.application.sven.huinews.db.UserSpCache;
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
import com.application.sven.huinews.entity.response.VideoListResponse;
import com.application.sven.huinews.entity.response.VideoTaskResponse;
import com.application.sven.huinews.main.home.activity.UserInfoActivity;
import com.application.sven.huinews.main.video.adapter.VideoCommentAdapter;
import com.application.sven.huinews.main.video.adapter.VideoVerticalAdapter;
import com.application.sven.huinews.main.video.contract.VideoContract;
import com.application.sven.huinews.main.web.activity.WebActivity;
import com.application.sven.huinews.utils.KeyBoradUtils;
import com.application.sven.huinews.utils.ToastUtils;
import com.application.sven.huinews.utils.umeng.UMengUtils;
import com.application.sven.huinews.view.EdittextUtlis;
import com.application.sven.huinews.view.EmptyLayout;
import com.application.sven.huinews.view.MyVideoProgress;
import com.application.sven.huinews.view.RefreshLayout;
import com.application.sven.huinews.view.dialog.GoldRuleDialog;
import com.application.sven.huinews.view.dialog.ReportDialog;
import com.application.sven.huinews.view.dialog.VShareDialog;
import com.application.sven.huinews.view.video.controller.VerticalVideoController;
import com.application.sven.huinews.main.video.model.VideoModel;
import com.application.sven.huinews.main.video.presenter.VideoPresenter;
import com.application.sven.huinews.mob.share.MobShare;
import com.application.sven.huinews.mob.share.ShareCallBack;
import com.application.sven.huinews.utils.CommonUtils;
import com.application.sven.huinews.utils.FrescoUtil;
import com.application.sven.huinews.utils.LogUtil;
import com.application.sven.huinews.utils.statusbar.Eyes;
import com.application.sven.huinews.view.VerticalViewPager;
import com.application.sven.huinews.view.dialog.ShareDialog;
import com.dueeeke.videoplayer.listener.VideoListener;
import com.dueeeke.videoplayer.player.IjkVideoView;
import com.dueeeke.videoplayer.player.PlayerConfig;
import com.facebook.drawee.view.SimpleDraweeView;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.umeng.analytics.MobclickAgent;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

/**
 * auther: sunfuyi
 * data: 2018/5/11
 * effect: 垂直滑动的视频播放器
 */
public class VerticalVideoActivity extends BaseActivity<VideoPresenter, VideoModel> implements VideoContract.View {
    private static final String TAG = "VerticalVideoActivity";
    private ImageButton btn_back, btn_close;
    private IjkVideoView mIjkVideoView;
    private LinearLayout video_comment_ll;
    private RecyclerView comment_rv;
    private VerticalVideoController mVerticalVideoController;
    private VerticalViewPager mVerticalViewPager;
    private VideoVerticalAdapter mAdapter;
    private SeekBar mSeekBar;
    private List<View> mViews = new ArrayList<>();
    private List<VideoListResponse.VideoList.ListBean> mVideoList = new ArrayList<>();
    private VideoInfoRequest mVideoInfoRequest;
    private Animation animationIn, animationOut;
    private VideoCommentAdapter mVideoCommentAdapter;
    private VShareDialog mShareDialog;
    private int mCurrentPosition;
    private int mPlayingPosition;
    private int mIntentPosition;
    private VideoListResponse.VideoList.ListBean mShareData, mGoldData, mCommentData;
    private SimpleDraweeView user_head;
    private TextView video_content, tv_like, btn_comment, btn_share, tv_follw, user_name, tv_collection;
    private Drawable mCollectDrawable, mCollectedDrawable, mLikedDrawable, mLikeDrawable;

    private EmptyLayout comment_empty;
    private RefreshLayout refresh_view;
    private TextView tv_comment_count; //评论数
    private TextView et_video_comment; //评论编辑
    private TextView btn_send;// 发送
    private String etComment;
    private int videoId;
    private RelativeLayout video_progress_layout;
    private MyVideoProgress video_progress;
    private TextView tv_gold_count;
    private GoldRuleDialog mGoldRuleDialog; //金币规则dialog

    private long mDuration;
    private boolean isPlayEnd; //是否播放完成
    private ReportDialog reportDialog;
    private int videoCount;
    private int mCurrentProgress; //停止时 的进度
    private int mCommentPage = 1;


    public static void toThis(Context mContext, VideoInfoRequest request, SimpleDraweeView iv) {
        Intent intent = new Intent(mContext, VerticalVideoActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable(Constant.BUNDLE_VIDEO_INFO, request);
        intent.putExtras(bundle);
        mContext.startActivity(intent);
    }


    @Override
    public int getLayoutId() {
        Eyes.translucentStatusBar(this, true);
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            mVideoInfoRequest = (VideoInfoRequest) bundle.getSerializable(Constant.BUNDLE_VIDEO_INFO);
            mVideoList.add(mVideoInfoRequest.getmVideoInfo());
        }
        MobclickAgent.onEvent(mContext, UMengUtils.VERTICAL_VIDEO);
        MobclickAgent.onEvent(mContext, UMengUtils.VERTICAL_VIDEO, UMengUtils.VERTICAL_VIDEO);
        return R.layout.activity_vertical_video;
    }

    @Override
    public void initView() {
        mVerticalViewPager = findViewById(R.id.viewpager);
        btn_back = findViewById(R.id.btn_back);
        btn_close = findViewById(R.id.btn_close);
        video_comment_ll = findViewById(R.id.video_comment_ll);
        comment_rv = findViewById(R.id.comment_rv);
        comment_empty = findViewById(R.id.comment_empty);
        tv_comment_count = findViewById(R.id.tv_comment_count);
        et_video_comment = findViewById(R.id.et_video_comment);
        btn_send = findViewById(R.id.btn_send);
        refresh_view = findViewById(R.id.refreshView);
        video_progress = findViewById(R.id.video_progress);
        video_progress_layout = findViewById(R.id.video_progress_layout);
        tv_gold_count = findViewById(R.id.tv_gold_count);


        mCollectDrawable = getResources().getDrawable(R.mipmap.icon_zan4);
        mCollectDrawable.setBounds(0, 0, mCollectDrawable.getMinimumWidth(), mCollectDrawable.getMinimumHeight());
        mCollectedDrawable = getResources().getDrawable(R.mipmap.icon_zan4ed);
        mCollectedDrawable.setBounds(0, 0, mCollectedDrawable.getMinimumWidth(), mCollectedDrawable.getMinimumHeight());
        mLikedDrawable = getResources().getDrawable(R.mipmap.video_liked);
        mLikedDrawable.setBounds(0, 0, mLikedDrawable.getMinimumWidth(), mLikedDrawable.getMinimumHeight());
        mLikeDrawable = getResources().getDrawable(R.mipmap.video_like);
        mLikeDrawable.setBounds(0, 0, mLikeDrawable.getMinimumWidth(), mLikeDrawable.getMinimumHeight());


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

        mVideoCommentAdapter.setOnLikeListener(new VideoCommentAdapter.OnLikeListener() {
            @Override
            public void onLikeChange(Comment comment) {
                mPresenter.onVideoCommentLike(comment.getId());
            }
        });

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

        mGoldRuleDialog.setOnDialogEnterListener(new GoldRuleDialog.OnDialogEnterListener() {
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
    public void onClickEvent(View v) {
        if (v == btn_back) {
            finish();
        } else if (v == btn_close) {
            hideCommentView();
        } else if (v == btn_send) {
            sendComment();
        } else if (v == et_video_comment) {
            setEtListener();
        }
    }

    @Override
    public void initObject() {
        setMVP();
        mPresenter.onVideoList();
        mPresenter.onVideoWatchCount();
        setVideo();
        setVideoCommentInfo();
        if (mGoldRuleDialog == null) {
            mGoldRuleDialog = new GoldRuleDialog(mContext);
        }
    }

    private void setVideo() {
        mIjkVideoView = new IjkVideoView(this);
        PlayerConfig config = new PlayerConfig.Builder().enableCache().setLooping().build();
        mIjkVideoView.setPlayerConfig(config);
        mVerticalVideoController = new VerticalVideoController(this);
        mIjkVideoView.setVideoController(mVerticalVideoController);


        VideoListResponse.VideoList.ListBean item = mVideoInfoRequest.getmVideoInfo();
        View view = LayoutInflater.from(mContext).inflate(R.layout.fragment_video, null);
        SimpleDraweeView imageView = view.findViewById(R.id.thumb);
        FrescoUtil.loadDefImg(imageView, item.getVideo_cover());
        setVideoInfo(view, item);
        mViews.add(view);

        mAdapter = new VideoVerticalAdapter(mViews);
        mVerticalViewPager.setAdapter(mAdapter);
        mVerticalViewPager.setCurrentItem(mCurrentPosition);
        mVerticalViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {


            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
//                Log.e(TAG, "mCurrentId == " + position + ", positionOffset == " + positionOffset + ", positionOffsetPixels == " + positionOffsetPixels);
            }

            @Override
            public void onPageSelected(int position) {
                if (videoCount > 0) {
                    video_progress_layout.setVisibility(View.VISIBLE);
                } else {
                    video_progress_layout.setVisibility(View.GONE);
                }
                mCurrentPosition = position;
                mDuration = video_progress.surplusTime();
                mCurrentProgress = video_progress.getProgressCurrent();
                video_progress.stopProgress();

                //当数据拉到倒数第三个时请求接口
                if (mCurrentPosition == mVideoList.size() - 3) {
                    PAGE += 1;
                    mPresenter.onVideoList();
                }
                mPresenter.cacnleGoldCountAnim(tv_gold_count, true);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                Log.d(TAG, "onPageScrollStateChanged: " + state);
                if (mPlayingPosition == mCurrentPosition) return;
                if (state == VerticalViewPager.SCROLL_STATE_IDLE) {
                    mIjkVideoView.release();
                    ViewParent parent = mIjkVideoView.getParent();
                    if (parent != null && parent instanceof FrameLayout) {
                        ((FrameLayout) parent).removeView(mIjkVideoView);
                    }
                    mPresenter.cacnleGoldCountAnim(tv_gold_count, false);

                    startPlay();
                }
            }
        });
        //自动播放
        mVerticalViewPager.post(new Runnable() {
            @Override
            public void run() {
                startPlay();
            }
        });

        mIjkVideoView.setVideoListener(new VideoListener() {
            @Override
            public void onVideoStarted() {

                mPresenter.onVideoVisit(mGoldData.getId());

                if (!mPresenter.isCanGetCoinByVideo(mGoldData)) {
                    LogUtil.showLog("msg---该视频已存在数据库");
                    video_progress_layout.setVisibility(View.INVISIBLE);
                    return;
                }

                if (mGoldData.getIs_gold() == 0 && mGoldData.getIs_redpack() == 0) {
                    video_progress_layout.setVisibility(View.GONE);
                }
                if (videoCount > 0) {
                    video_progress_layout.setVisibility(View.VISIBLE);
                    video_progress.setProgress(mCurrentProgress);
                    video_progress.setDuration(mDuration);
                    video_progress.animStart();
                }
            }

            @Override
            public void onVideoPaused() {
                LogUtil.showLog("msg---:onVideoPaused");
                video_progress.stopProgress();
                mCurrentProgress = video_progress.getProgressCurrent();
            }

            @Override
            public void onComplete() {
                LogUtil.showLog("msg----onComplete");
            }

            @Override
            public void onPrepared() {

                LogUtil.showLog("msg----onPrepared");
            }

            @Override
            public void onError() {
                LogUtil.showLog("msg----onError");
            }

            @Override
            public void onInfo(int what, int extra) {
                LogUtil.showLog("msg----onInfo");
            }
        });
    }

    private void startPlay() {

        View view = mViews.get(mCurrentPosition);
        FrameLayout frameLayout = view.findViewById(R.id.container);

        mVerticalVideoController.getThumb().setImageURI(mVideoList.get(mCurrentPosition).getVideo_cover());
        frameLayout.addView(mIjkVideoView, 1);
        mIjkVideoView.setUrl(mVideoList.get(mCurrentPosition).getVideo_url());
        mIjkVideoView.setScreenScale(IjkVideoView.SCREEN_SCALE_CENTER_CROP);
        mIjkVideoView.start();
        mPlayingPosition = mCurrentPosition;

        mGoldData = mVideoList.get(mCurrentPosition);
    /*    video_progress_layout = frameLayout.findViewById(R.id.video_progress_layout);
        video_progress = frameLayout.findViewById(R.id.video_progress);
        tv_gold_count = frameLayout.findViewById(R.id.tv_gold_count);*/

        video_progress.OnVideoProgressLisenter(new MyVideoProgress.OnVideoProgressLisenter() {
            @Override
            public void end() {
                isPlayEnd = true;
                mCurrentProgress = 0;
                video_progress.setProgress(mCurrentProgress);
                video_progress.stopProgress();
                video_progress.setDuration(20000);
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

        video_progress_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mGoldRuleDialog.show();


            }
        });
    }

    private void setVideoInfo(View v, final VideoListResponse.VideoList.ListBean item) {
        user_head = v.findViewById(R.id.user_head);
        video_content = v.findViewById(R.id.video_content);
        tv_like = v.findViewById(R.id.tv_like);
        btn_comment = v.findViewById(R.id.btn_comment);
        btn_share = v.findViewById(R.id.btn_share);
        tv_follw = v.findViewById(R.id.tv_follw);
        user_name = v.findViewById(R.id.user_name);
        tv_collection = v.findViewById(R.id.tv_collection);
        mSeekBar = v.findViewById(R.id.seek_bar);

        user_head.setImageURI(item.getUser_avatar());
        video_content.setText(item.getTitle());
        user_name.setText(item.getUser_nickname());
        tv_like.setText(CommonUtils.getLikeCount(item.getLike_count()));
        tv_collection.setText(CommonUtils.getLikeCount(item.getCollect_count()));
        btn_comment.setText(CommonUtils.getLikeCount(item.getComment_count()));
        btn_share.setText(CommonUtils.getLikeCount(item.getShare_count()));

        tv_like.setCompoundDrawables(null, item.isIs_up() ? mLikedDrawable : mLikeDrawable, null, null);
        tv_collection.setCompoundDrawables(null, item.isIs_collected() ? mCollectedDrawable : mCollectDrawable, null, null);

        tv_follw.setBackgroundResource(item.isIs_follow() ? R.drawable.follow_bg_cli : R.drawable.follow_bg_nor);
        tv_follw.setText(item.isIs_follow() ? R.string.followed : R.string.follow);

        video_content.setVisibility(TextUtils.isEmpty(item.getTitle()) ? View.GONE : View.VISIBLE);


        user_head.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserInfoActivity.toThis(mContext, item.getUser_id());
            }
        });

        user_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserInfoActivity.toThis(mContext, item.getUser_id());
            }
        });


        btn_comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showCommentView(item);
            }
        });

        tv_collection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView tv = v.findViewById(R.id.tv_collection);
                item.setIs_collected(!item.isIs_collected());
                item.setCollect_count(item.isIs_collected() ? item.getCollect_count() + 1 : item.getCollect_count() - 1);
                tv.setCompoundDrawables(null, item.isIs_collected() ? mCollectedDrawable : mCollectDrawable, null, null);
                tv.setText(CommonUtils.getLikeCount(item.getCollect_count()));
                if (item.isIs_collected()) {
                    mPresenter.onCollection(getVideoCollectionRequest(item.getId()));
                } else {
                    mPresenter.onCancelCollection(getVideoCollectionCancelRequest(item.getId()));
                }
                EventBus.getDefault().post(new VideoLikeEvent(item));
            }
        });

        tv_like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView tv = v.findViewById(R.id.tv_like);
                item.setIs_up(!item.isIs_up());
                item.setLike_count(item.isIs_up() ? item.getLike_count() + 1 : item.getLike_count() - 1);
                tv.setCompoundDrawables(null, item.isIs_up() ? mLikedDrawable : mLikeDrawable, null, null);
                tv.setText(CommonUtils.getLikeCount(item.getLike_count()));

                VideoLikeRequest request = new VideoLikeRequest();
                request.setId(item.getId());
                request.setType(item.isIs_up() ? Constant.LIKE_TYPE : Constant.UN_LIKE_TYPE);
                mPresenter.onLikeVideo(request, item);

                EventBus.getDefault().post(new VideoLikeEvent(item));
            }
        });

        tv_follw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView tv = v.findViewById(R.id.tv_follw);
                item.setIs_follow(!item.isIs_follow());
                tv.setBackgroundResource(item.isIs_follow() ? R.drawable.follow_bg_cli : R.drawable.follow_bg_nor);
                tv.setText(item.isIs_follow() ? R.string.followed : R.string.follow);
                tv.setTextColor(item.isIs_follow() ? getResources().getColor(R.color.c_333333) : getResources().getColor(R.color.color_white));
                if (item.isIs_follow()) {
                    mPresenter.onFollowUser(item.getUser_id());
                } else {
                    mPresenter.onCancelFollowUser(item.getUser_id());
                }
                EventBus.getDefault().post(new VideoLikeEvent(item));
            }
        });

        btn_share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mShareData = mVideoList.get(mCurrentPosition);
                showShareDialog();
            }
        });
    }


    private void setVideoCommentInfo() {
        comment_rv.setLayoutManager(new LinearLayoutManager(mContext));
        mVideoCommentAdapter = new VideoCommentAdapter(mContext);
        comment_rv.setAdapter(mVideoCommentAdapter);
    }


    /**
     * 显示评论界面
     */
    private void showCommentView(VideoListResponse.VideoList.ListBean mData) {
        this.mCommentData = mData;
        isRefresh = true;
        if (animationIn == null) {
            animationIn = android.view.animation.AnimationUtils.loadAnimation(mContext, R.anim.movie_count_in);
        }
        video_comment_ll.startAnimation(animationIn);
        video_comment_ll.setVisibility(View.VISIBLE);

        tv_comment_count.setText("评论" + CommonUtils.getLikeCount(mCommentData.getComment_count()));
        videoId = mData.getId();
        comment_empty.setErrorType(EmptyLayout.LOADING, -1);
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
        isRefresh = true;
    }

    /**
     * 分享
     */
    private void showShareDialog() {
        if (mShareDialog == null) {
            mShareDialog = new VShareDialog(mContext);
        }
        mShareDialog.show();

        mShareDialog.setOnShareListener(new VShareDialog.OnShareListener() {
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

    @Override
    protected void onPause() {
        super.onPause();
        mIjkVideoView.pause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mIjkVideoView.resume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mIjkVideoView.release();
    }

    @Override
    public VideoListRequest videoListRequest() {
        VideoListRequest request = new VideoListRequest();
        request.setPage(PAGE);
        request.setLimit(LIMIT);
        request.setTypeId(mVideoInfoRequest.getChannel_id());
        return request;
    }

    @Override
    public void setVideoList(VideoListResponse.VideoList videoLists) {
        final List<VideoListResponse.VideoList.ListBean> responseData = videoLists.getList();
        for (int i = 0; i < responseData.size(); i++) {
            if (responseData.get(i).getId() == mVideoInfoRequest.getmVideoInfo().getId()) {
                responseData.remove(i);
            }
        }

        mVideoList.addAll(responseData);
        for (int i = 0; i < responseData.size(); i++) {
            VideoListResponse.VideoList.ListBean item = responseData.get(i);
            View view = LayoutInflater.from(mContext).inflate(R.layout.fragment_video, null);
            SimpleDraweeView imageView = view.findViewById(R.id.thumb);
            FrescoUtil.loadDefImg(imageView, item.getVideo_cover());
            setVideoInfo(view, item);
            mViews.add(view);
        }

        mAdapter.notifyDataSetChanged();
    }

    @Override
    public VideoCommentRequest getVideoCommentRequest(int page, int videoId, String sort) {
        VideoCommentRequest request = new VideoCommentRequest();
        request.setOrder(sort);
        request.setPage(page + "");
        request.setVideo_id(videoId + "");
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

    }

    @Override
    public void setVideoShareUrl(String url, final int type) {
        LogUtil.showLog("msg======url:" + url);
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
    public void setGoldView(VideoListResponse.VideoList.ListBean mData, VideoTaskResponse.Data data, int type) {

        //将视频加入数据库
        mPresenter.addVideoToDB(mData);
        videoCount--;
        // int mCount = videoCount - data.getmCount();
        if (videoCount > 0) {
            ToastUtils.showLong(mContext, "您还可以观看" + videoCount + "次金币视频");
        }
      /*  if (videoCount < data.getmCount()) {
            video_progress_layout.setVisibility(View.GONE);
            return;
        }*/

        mPresenter.goldCountAnim(mContext, tv_gold_count, video_progress_layout, data.getCount());
        //  EventBus.getDefault().post(new VideoLikeEvent(mData));
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
        mCommentData.setComment_count(mCommentData.getComment_count() + 1);
        mPresenter.onVideoComment(getVideoCommentRequest(mCommentPage, videoId, Constant.COMMENT_TIME));
        tv_comment_count.setText("评论" + CommonUtils.getLikeCount(mCommentData.getComment_count()));
        TextView tvMsg = mViews.get(mCurrentPosition).findViewById(R.id.btn_comment);
        tvMsg.setText(CommonUtils.getLikeCount(mCommentData.getComment_count()));
        EventBus.getDefault().post(new VideoLikeEvent(mCommentData));

    }

    @Override
    public void setVideoCount(int count) {
        videoCount = count;
        if (videoCount > 0) {
            video_progress_layout.setVisibility(View.VISIBLE);
        } else {
            video_progress_layout.setVisibility(View.GONE);
        }
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
        EdittextUtlis.showCommentEdit(this, et_video_comment, new EdittextUtlis.liveCommentResult() {
            @Override
            public void onResult(boolean confirmed, String comment) {
                LogUtil.showLog("msg---confirmed:" + confirmed);
                if (confirmed) {
                    etComment = comment;
                    sendComment();
                }
            }
        });
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
