package com.application.sven.huinews.view.video.controller;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;

import com.application.sven.huinews.R;
import com.application.sven.huinews.config.Constant;
import com.application.sven.huinews.entity.response.MovieDetailResponse;
import com.application.sven.huinews.entity.response.MovieSource;
import com.application.sven.huinews.utils.LogUtil;
import com.application.sven.huinews.utils.ToastUtils;
import com.application.sven.huinews.view.video.StatusView;
import com.dueeeke.videoplayer.controller.GestureVideoController;
import com.dueeeke.videoplayer.player.IjkVideoView;
import com.dueeeke.videoplayer.util.PlayerConstants;
import com.dueeeke.videoplayer.util.WindowUtil;

import java.util.List;

/**
 * auther: sunfuyi
 * data: 2018/5/19
 * effect: 小视频播放控制器
 */
public class MovieController extends GestureVideoController implements View.OnClickListener, SeekBar.OnSeekBarChangeListener, StatusView.onErrorStatusLisenter {
    public static final String TAG = MovieController.class.getSimpleName();
    public static final String PAY_TYPE_NONE = "none";
    public static final String PAY_TYPE_LOOK = "look";
    public static final String PAY_TYPE_SHARE = "share";
    public static final String PAY_TYPE_GOLD = "gold";
    public static final String PAY_TYPE_OBTAIN = "obtain";
    private ImageView mStart_play;
    private ProgressBar loading;
    private ImageButton fullscreen;
    private ImageButton btn_back;
    private ImageButton small_start;
    private ImageButton btn_share;
    private TextView totalTime;
    private TextView currTime;
    private TextView tv_look_time; //观看时长
    private TextView tv_movie_share; //观看时长分享按钮
    private TextView tv_play_msg, tv_play_gold, tv_play_enters;  //金币支付msg，多少金币msg，分享按钮，
    private TextView movie_title;
    private SeekBar seek_bar;
    private ImageView thumb;
    private LinearLayout bottom_container, bottom_seek, ll_movie_look_time;
    private LinearLayout ll_free_layout;

    private Animation showAnim = AnimationUtils.loadAnimation(getContext(), R.anim.anim_player_alpha_in);
    private Animation hideAnim = AnimationUtils.loadAnimation(getContext(), R.anim.anim_player_alpha_out);
    private StatusView mStatusView;
    private boolean isDragging;
    private long freeTime;
    private boolean isShowPayType;
    private String payType; //视频观看规则状态
    private MovieSource mMovieStatus;


    public MovieController(@NonNull Context context) {
        this(context, null);
    }

    public MovieController(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MovieController(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    @Override
    protected int getLayoutId() {
        return R.layout.small_video_controller;
    }


    @Override
    protected void initView() {
        super.initView();
        mStart_play = controllerView.findViewById(R.id.start_play);
        btn_back = controllerView.findViewById(R.id.btn_back);
        loading = controllerView.findViewById(R.id.loading);
        small_start = controllerView.findViewById(R.id.small_start);
        fullscreen = controllerView.findViewById(R.id.fullscreen);
        totalTime = controllerView.findViewById(R.id.total_time);
        currTime = controllerView.findViewById(R.id.curr_time);
        seek_bar = controllerView.findViewById(R.id.seek_bar);
        thumb = controllerView.findViewById(R.id.thumb);
        bottom_container = controllerView.findViewById(R.id.bottom_container);
        bottom_seek = controllerView.findViewById(R.id.bottom_seek);
        btn_share = controllerView.findViewById(R.id.btn_share);
        tv_look_time = controllerView.findViewById(R.id.tv_look_time);
        tv_movie_share = controllerView.findViewById(R.id.tv_movie_share);
        ll_free_layout = controllerView.findViewById(R.id.ll_free_layout);
        tv_play_msg = controllerView.findViewById(R.id.tv_play_msg);
        tv_play_gold = controllerView.findViewById(R.id.tv_play_gold);
        tv_play_enters = controllerView.findViewById(R.id.tv_play_enters);
        ll_movie_look_time = controllerView.findViewById(R.id.ll_movie_look_time);
        movie_title = controllerView.findViewById(R.id.movie_title);


        mStatusView = new StatusView(getContext());
        initEvents();
    }

    private void initEvents() {
        mStart_play.setOnClickListener(this);
        fullscreen.setOnClickListener(this);
        btn_back.setOnClickListener(this);
        seek_bar.setOnSeekBarChangeListener(this);
        thumb.setOnClickListener(this);
        small_start.setOnClickListener(this);
        mStatusView.setOnErrorStatusLisenter(this);
        btn_share.setOnClickListener(this);
        tv_movie_share.setOnClickListener(this);
        tv_play_enters.setOnClickListener(this);
        ll_free_layout.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.fullscreen || id == R.id.btn_back) {
            doStartStopFullScreen();
        } else if (id == R.id.small_start || id == R.id.start_play || id == R.id.thumb) {
            doPauseResume();
        } else if (id == R.id.btn_share) {
            if (mSwchLineOnClickLisenter != null) {
                mSwchLineOnClickLisenter.videoShare();
            }
        } else if (id == R.id.tv_movie_share) {
            //播放器底部分享按钮点击
            if (mSwchLineOnClickLisenter != null) {
                mSwchLineOnClickLisenter.videoShare();
            }
        } else if (id == R.id.tv_play_enters) {
            if (payType.equals(PAY_TYPE_OBTAIN)) {
                if (mSwchLineOnClickLisenter != null) {
                    mSwchLineOnClickLisenter.onGetGold();
                }
            } else if (payType.equals(PAY_TYPE_GOLD)) {
                if (mSwchLineOnClickLisenter != null) {
                    mSwchLineOnClickLisenter.onMoviePay();
                }
            } else if (payType.equals(PAY_TYPE_SHARE)) {
                if (mSwchLineOnClickLisenter != null) {
                    mSwchLineOnClickLisenter.videoShare();
                }
            }

        } else if (id == R.id.ll_free_layout) {

        }
    }

    @Override
    protected void doPauseResume() {
        super.doPauseResume();
        if (mediaPlayer.isPlaying()) {
            small_start.setImageResource(R.mipmap.small_stop);
        } else {
            small_start.setImageResource(R.mipmap.small_start);
        }
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        if (!fromUser) {
            return;
        }
        long duration = mediaPlayer.getDuration();
        long newPosition = (duration * progress) / seek_bar.getMax();
        if (currTime != null)
            currTime.setText(stringForTime((int) newPosition));
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {
        isDragging = true;
        removeCallbacks(mShowProgress);
        removeCallbacks(mFadeOut);
    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        long duration = mediaPlayer.getDuration();
        long newPosition = (duration * seekBar.getProgress()) / seek_bar.getMax();
        mediaPlayer.seekTo((int) newPosition);
        isDragging = false;
        post(mShowProgress);
        show();
    }


    /**
     * 横竖屏切换
     */
    protected void doStartStopFullScreen() {
        if (mediaPlayer.isFullScreen()) {
            WindowUtil.scanForActivity(getContext()).setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
            mediaPlayer.stopFullScreen();
        } else {
            WindowUtil.scanForActivity(getContext()).setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
            mediaPlayer.startFullScreen();
        }
    }


    @Override
    public void showStatusView() {
        super.showStatusView();
        // setNetWorkStatus();
    }


    @Override
    public void setPlayState(int playState) {
        // super.setPlayState(playState);
        switch (playState) {
            case IjkVideoView.STATE_IDLE:
                LogUtil.showLog(TAG, "STATE_IDLE");
                hide();
                seek_bar.setProgress(0);
                seek_bar.setSecondaryProgress(0);
                loading.setVisibility(View.GONE);
                mStart_play.setVisibility(GONE);
                thumb.setVisibility(VISIBLE);
                break;
            case IjkVideoView.STATE_PLAYING:
                //开始播放
                LogUtil.showLog(TAG, "STATE_PLAYING");
                hideStatusView();
                this.removeView(mStatusView);
                post(mShowProgress);
                loading.setVisibility(View.GONE);
                mStart_play.setVisibility(GONE);
                thumb.setVisibility(GONE);
                small_start.setImageResource(R.mipmap.small_stop);

                break;
            case IjkVideoView.STATE_PAUSED:
                //暂停
                LogUtil.showLog(TAG, "STATE_PAUSED");
                mStart_play.setVisibility(VISIBLE);
                small_start.setImageResource(R.mipmap.small_start);
                break;
            case IjkVideoView.STATE_PREPARING:
                LogUtil.showLog("msg----:::" + mediaPlayer.getBufferPercentage());
                //开始准备
                LogUtil.showLog(TAG, "STATE_PREPARING");
                loading.setVisibility(View.VISIBLE);
                thumb.setVisibility(View.VISIBLE);
                mStart_play.setVisibility(View.GONE);
                break;
            case IjkVideoView.STATE_PREPARED:
                //准备完毕
                LogUtil.showLog(TAG, "STATE_PREPARED");
                mStart_play.setVisibility(GONE);
                break;
            case IjkVideoView.STATE_ERROR:
                //错误
                LogUtil.showLog(TAG, "STATE_ERROR");
                loading.setVisibility(View.GONE);
                mStart_play.setVisibility(GONE);
                thumb.setVisibility(GONE);
                bottom_seek.setVisibility(View.GONE);
                ll_free_layout.setVisibility(View.GONE);
                setErrorStatus();
                break;
            case IjkVideoView.STATE_BUFFERING:
                //缓冲
                LogUtil.showLog(TAG, "STATE_BUFFERING");
                loading.setVisibility(View.VISIBLE);
                mStart_play.setVisibility(GONE);
                thumb.setVisibility(GONE);
                break;
            case IjkVideoView.STATE_BUFFERED:
                //缓冲完毕
                LogUtil.showLog(TAG, "STATE_BUFFERED");
                loading.setVisibility(View.GONE);
                mStart_play.setVisibility(GONE);
                thumb.setVisibility(GONE);
                break;
            case IjkVideoView.STATE_PLAYBACK_COMPLETED:
                //
                LogUtil.showLog(TAG, "STATE_PLAYBACK_COMPLETED");
                hide();
                removeCallbacks(mShowProgress);
                loading.setVisibility(View.GONE);
                mStart_play.setVisibility(VISIBLE);
                thumb.setVisibility(VISIBLE);
                break;
        }
    }

    int currentPosition = 0;

    @Override
    protected int setProgress() {
        if (mediaPlayer == null || isDragging) {
            return 0;
        }


        int position = (int) mediaPlayer.getCurrentPosition();
        int duration = (int) mediaPlayer.getDuration();
        this.currentPosition = position;
        if (isShowPayType) {
            setPlayFreeType();
        }

        if (seek_bar != null) {
            if (duration > 0) {
                seek_bar.setEnabled(true);
                int pos = (int) (position * 1.0 / duration * seek_bar.getMax());
                seek_bar.setProgress(pos);
                //bottomProgress.setProgress(pos);
            } else {
                seek_bar.setEnabled(false);
            }
            int percent = mediaPlayer.getBufferPercentage();
            if (percent >= 95) { //修复第二进度不能100%问题
                seek_bar.setSecondaryProgress(seek_bar.getMax());
                //    bottomProgress.setSecondaryProgress(bottomProgress.getMax());
            } else {
                seek_bar.setSecondaryProgress(percent * 10);
                //   bottomProgress.setSecondaryProgress(percent * 10);
            }
        }

        if (totalTime != null)
            totalTime.setText(stringForTime(duration));
        if (currTime != null)
            currTime.setText(stringForTime(position));
        return position;
    }


    @Override
    public void show() {
        if (!mShowing) {
            if (mediaPlayer.isFullScreen()) {
                /*if (!isLocked) {
                    bottom_container.setVisibility(VISIBLE);
                    bottom_container.startAnimation(showAnim);
                }*/
                bottom_seek.setVisibility(VISIBLE);
                bottom_seek.startAnimation(showAnim);
            } else {
                bottom_seek.setVisibility(View.VISIBLE);
                bottom_seek.startAnimation(showAnim);
            }

            mShowing = true;
        }

        removeCallbacks(mFadeOut);
        if (sDefaultTimeout != 0) {
            postDelayed(mFadeOut, sDefaultTimeout);
        }
    }

    @Override
    public void hide() {
        if (mShowing) {
            if (mediaPlayer.isFullScreen()) {
                /*if (!isLocked) {
                    bottom_container.setVisibility(GONE);
                    bottom_container.startAnimation(hideAnim);
                }*/
                bottom_seek.setVisibility(GONE);
                bottom_seek.startAnimation(hideAnim);
            } else {
                bottom_seek.setVisibility(View.GONE);
                bottom_seek.startAnimation(hideAnim);
            }

            mShowing = false;
        }
    }


    @Override
    protected void slideToChangePosition(float deltaX) {
        super.slideToChangePosition(deltaX);
    }

    @Override
    public boolean onBackPressed() {
        if (mediaPlayer.isFullScreen()) {
            WindowUtil.scanForActivity(getContext()).setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
            mediaPlayer.stopFullScreen();
            return true;
        }
        return super.onBackPressed();
    }

    public ImageView getThumb() {
        return thumb;
    }


    /**
     * 播放出错，复写该setPlayState方法
     * 主要更改界面
     */
    private void setErrorStatus() {
        this.removeView(mStatusView);
        this.addView(mStatusView, 0);
    }

    /**
     * 移动网络  、、  播放器框架处理在 baseControll里面
     * 这里需要复写他的方法showStatusView()
     * 主要更改界面
     */
    private void setNetWorkStatus() {
        this.removeView(mStatusView);
        mStatusView.setMessage(getResources().getString(com.dueeeke.videoplayer.R.string.player_wifi_tip));
        mStatusView.setButtonTextAndAction(getResources().getString(com.dueeeke.videoplayer.R.string.player_continue_play), new OnClickListener() {
            @Override
            public void onClick(View v) {
                hideStatusView();
                PlayerConstants.IS_PLAY_ON_MOBILE_NETWORK = true;
                mediaPlayer.start();
            }
        });
        this.addView(mStatusView);
    }


    /**
     * 线路资源
     *
     * @param mMovieData
     */
    public void setMovieData(MovieDetailResponse.MovieDetailData mMovieData, int currentPosition) {

        mStatusView.setLineRv(mMovieData.getAnaly());
        if (mMovieData.getExt().size() > 1) {
            movie_title.setText(mMovieData.getM_name() + " 第" + mMovieData.getExt().get(currentPosition).getNow_set_number() + "集");
        } else {
            movie_title.setText(mMovieData.getM_name());
        }
    }

    public void setLoading() {
        loading.setVisibility(View.VISIBLE);
        mStart_play.setVisibility(View.GONE);
    }

    /**
     * 设置视频免费观看时长，分享观看完整版，金币不足等状态
     *
     * @param mMovieStatus
     */
    public void setMovieStatus(MovieSource mMovieStatus) {
        ll_free_layout.setVisibility(View.GONE);
        if (mMovieStatus.getSource().equals("defaultUrl")) {
            loading.setVisibility(View.GONE);
            ll_movie_look_time.setVisibility(View.GONE);
            payType = "defaultUrl";
            setErrorStatus();
            return;
        }

        //设置观看时长
        this.freeTime = mMovieStatus.getFree_time();
        this.mMovieStatus = mMovieStatus;
        payType = mMovieStatus.getPay_type();
        if (payType.equals(PAY_TYPE_NONE) || payType.equals(PAY_TYPE_LOOK)) {
            //免费观看
            ll_free_layout.setVisibility(View.GONE);
            ll_movie_look_time.setVisibility(View.GONE);
            isShowPayType = false;
        } else if (payType.equals(PAY_TYPE_GOLD)) {
            //金币观看
            isShowPayType = true;
            //金币支付观看
            ll_movie_look_time.setVisibility(View.GONE);
        } else if (payType.equals(PAY_TYPE_SHARE)) {
            //分享观看
            isShowPayType = true;
            ll_movie_look_time.setVisibility(View.GONE);
            tv_look_time.setText(String.format(getContext().getResources().getString(R.string.movie_time), (freeTime / 60)));
            hideStatusView();
        } else if (payType.equals(PAY_TYPE_OBTAIN)) {
            //金币不足，请先赚取金币
            isShowPayType = true;
        }
    }

    /**
     * 支付成功后的处理
     *
     * @param str
     */
    public void setPlayType(String str) {
        payType = str;
        ll_free_layout.setVisibility(View.GONE);
        ll_movie_look_time.setVisibility(View.GONE);
        isShowPayType = false;
    }

    /**
     * 设置  金币支付信息
     */
    private void setPlayFreeType() {
        if (mMovieStatus.getSource().equals("defaultUrl")) {
            return;
        }
        if (payType.equals(PAY_TYPE_NONE)) {
            //免费观看
        } else if (payType.equals(PAY_TYPE_GOLD)) {
            if (currentPosition >= freeTime * 1000) {
                //金币支付观看
                ll_free_layout.setVisibility(View.VISIBLE);
                tv_play_msg.setVisibility(View.GONE);
                tv_play_gold.setText(mMovieStatus.getPay_msg());
                tv_play_enters.setText(getContext().getResources().getString(R.string.movie_enter_play));
                ll_movie_look_time.setVisibility(View.GONE);
                mediaPlayer.pause();
            }

        } else if (payType.equals(PAY_TYPE_SHARE)) {
            if (currentPosition >= freeTime * 1000) {
                //分享观看
                /*ll_free_layout.setVisibility(View.VISIBLE);
                tv_play_msg.setVisibility(View.GONE);
                ll_movie_look_time.setVisibility(View.VISIBLE);
                tv_play_gold.setText(String.format(getContext().getResources().getString(R.string.movie_play_time), (freeTime / 60)));
                tv_play_enters.setText(getContext().getResources().getString(R.string.share_movie));*/
                if (mSwchLineOnClickLisenter != null) {
                    mSwchLineOnClickLisenter.showShareDialog();
                }
                mediaPlayer.pause();
            }

        } else if (payType.equals(PAY_TYPE_OBTAIN)) {
            if (currentPosition >= freeTime * 1000) {
                mediaPlayer.pause();
                ll_free_layout.setVisibility(View.VISIBLE);
                ll_movie_look_time.setVisibility(View.GONE);
                tv_play_msg.setVisibility(View.VISIBLE);
                tv_play_gold.setText(mMovieStatus.getPay_msg());
                tv_play_enters.setText(getContext().getResources().getString(R.string.movie_gold));
            }

        }
    }

    @Override
    public void swichLine(MovieDetailResponse.MovieDetailData.AnalyBean analy) {
        //切换线路
        loading.setVisibility(View.VISIBLE);
        ll_free_layout.setVisibility(View.GONE);
        this.removeView(mStatusView);
        //切换线路
        if (mSwchLineOnClickLisenter != null) {
            mSwchLineOnClickLisenter.swichLine(analy);
        }
    }


    @Override
    public void onRetry() {
        //重试
        this.removeView(mStatusView);
        mediaPlayer.retry();
    }

    private swchLineOnClickLisenter mSwchLineOnClickLisenter;

    public interface swchLineOnClickLisenter {
        void swichLine(MovieDetailResponse.MovieDetailData.AnalyBean analy);

        void videoShare();

        void onMoviePay();

        void onGetGold();

        void showShareDialog();
    }

    public void setmSwchLineOnClickLisenter(swchLineOnClickLisenter mSwchLineOnClickLisenter) {
        this.mSwchLineOnClickLisenter = mSwchLineOnClickLisenter;
    }
}
