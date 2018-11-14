package com.application.sven.huinews.view.video.controller;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.application.sven.huinews.R;
import com.application.sven.huinews.utils.LogUtil;
import com.application.sven.huinews.view.video.interf.ListMediaPlayerControl;
import com.baidu.mobad.nativevideo.BaiduVideoResponse;
import com.dueeeke.videoplayer.controller.BaseVideoController;
import com.dueeeke.videoplayer.listener.VideoListener;
import com.dueeeke.videoplayer.player.IjkVideoView;
import com.dueeeke.videoplayer.util.WindowUtil;
import com.facebook.drawee.controller.ControllerListener;
import com.facebook.drawee.view.SimpleDraweeView;

/**
 * auther: sunfuyi
 * data: 2018/6/5
 * effect:
 */
public class AdContoller extends BaseVideoController {
    private Context mContext;
    private SimpleDraweeView thumb;
    private TextView adTime, adDetail;
    private ProgressBar loading;
    protected AdControllerListener listener;

    public AdContoller(@NonNull Context context) {
        super(context);
        this.mContext = context;
    }

    public AdContoller(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.layout_ad_controller;
    }

    @Override
    protected void initView() {
        super.initView();
        adTime = controllerView.findViewById(R.id.ad_time);
        adDetail = controllerView.findViewById(R.id.ad_detail);
        thumb = controllerView.findViewById(R.id.thumb);
        loading = controllerView.findViewById(R.id.loading);
        adDetail.setText("了解详情>");
        adTime.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                ((ListMediaPlayerControl) mediaPlayer).skipToNext();
                //百度跳过广告
                mNrAd.onClose(mContext, (int) mediaPlayer.getCurrentPosition());
            }
        });
        adDetail.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onAdClick();
                }
            }
        });
        controllerView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onAdClick();
                }
            }
        });

    }

    BaiduVideoResponse mNrAd;

    public void setBaiduVideoResponse(BaiduVideoResponse mNrAd) {
        this.mNrAd = mNrAd;
    }

    @Override
    public void setPlayState(int playState) {
        // super.setPlayState(playState);
        switch (playState) {
            case IjkVideoView.STATE_PLAYING:
                post(mShowProgress);
                //百度广告开始播放
                mNrAd.onStart(mContext);
                thumb.setVisibility(View.GONE);
                loading.setVisibility(View.GONE);
            case IjkVideoView.STATE_ERROR:
                //错误
                mNrAd.onError(mContext, 0, 1);
                thumb.setVisibility(View.GONE);
                loading.setVisibility(View.GONE);
                break;
            case IjkVideoView.STATE_PLAYBACK_COMPLETED:

                //播放完成  //百度广告播放完成
                mNrAd.onComplete(mContext);
                thumb.setVisibility(View.GONE);
                loading.setVisibility(View.GONE);
                break;
            case IjkVideoView.STATE_PREPARING:
                //开始准备
                thumb.setVisibility(View.VISIBLE);
                loading.setVisibility(View.VISIBLE);
                break;
        }
    }


    public void onCloseAdVideo() {
        if (mNrAd != null) {
            LogUtil.showLog("msg----关闭广告视频");
            mNrAd.onClose(mContext, (int) mediaPlayer.getCurrentPosition());
        }
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
            mNrAd.onFullScreen(mContext, (int) mediaPlayer.getCurrentPosition());
        }
    }

    @Override
    protected int setProgress() {
        if (mediaPlayer == null) {
            return 0;
        }
        int position = (int) mediaPlayer.getCurrentPosition();
        int duration = (int) mediaPlayer.getDuration();

        if (adTime != null)
            adTime.setText(String.format("%s", (duration - position) / 1000));
        return position;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (listener != null) listener.onAdClick();
                break;
        }
        return false;
    }

    @Override
    public boolean onBackPressed() {
        if (mediaPlayer.isFullScreen()) {
            WindowUtil.scanForActivity(getContext()).setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
            mediaPlayer.stopFullScreen();
            setPlayerState(IjkVideoView.PLAYER_NORMAL);
            return true;
        }
        return super.onBackPressed();
    }


    public interface AdControllerListener {
        void onAdClick();

    }

    public void setControllerListener(AdControllerListener listener) {
        this.listener = listener;
    }


    public SimpleDraweeView getThumb() {
        return thumb;
    }
}
