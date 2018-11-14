package com.application.sven.huinews.view.video.controller;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewDebug;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.SeekBar;

import com.application.sven.huinews.R;
import com.application.sven.huinews.utils.LogUtil;
import com.application.sven.huinews.utils.ScreensUitls;
import com.application.sven.huinews.view.video.StatusView;
import com.dueeeke.videoplayer.controller.BaseVideoController;
import com.dueeeke.videoplayer.player.IjkVideoView;
import com.dueeeke.videoplayer.util.L;
import com.dueeeke.videoplayer.util.PlayerConstants;
import com.facebook.drawee.drawable.ScalingUtils;
import com.facebook.drawee.view.SimpleDraweeView;

/**
 * auther: sunfuyi
 * data: 2018/5/14
 * effect:
 */
public class VerticalVideoController extends BaseVideoController implements SeekBar.OnSeekBarChangeListener {
    private SimpleDraweeView thumb;
    private SeekBar seek_bar;
    private ImageView iv_start;
    private StatusView mStatusView;
    private ProgressBar loading;
    private boolean isDragging;

    public VerticalVideoController(@NonNull Context context) {
        super(context);
    }

    public VerticalVideoController(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public VerticalVideoController(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.layout_vertical_controller;
    }

    @Override
    protected void initView() {
        super.initView();
        controllerView = LayoutInflater.from(getContext()).inflate(getLayoutId(), this);
        thumb = controllerView.findViewById(R.id.iv_thumb);
        seek_bar = controllerView.findViewById(R.id.seek_bar);
        iv_start = controllerView.findViewById(R.id.iv_start);
        loading = controllerView.findViewById(R.id.loading);

        mStatusView = new StatusView(getContext());
        setParams();
      /*  controllerView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mediaPlayer.isPlaying()) {
                    iv_start.setVisibility(View.VISIBLE);
                    mediaPlayer.pause();
                } else {
                    mediaPlayer.start();
                    iv_start.setVisibility(View.GONE);
                }
            }
        });*/
        iv_start.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                mediaPlayer.start();
                iv_start.setVisibility(View.GONE);
            }
        });
    }

    private void setParams() {
        thumb.getHierarchy().setActualImageScaleType(ScalingUtils.ScaleType.FOCUS_CROP);
        FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) thumb.getLayoutParams();
        params.height = ScreensUitls.getScreenHeight(getContext());
        params.width = ScreensUitls.getScreenWidth(getContext());
        thumb.setLayoutParams(params);
    }

    float x;
    float y;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int centerX = getWidth() / 2;
        int centerY = getHeight() / 2;
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                x = event.getX();
                y = event.getY();
                break;
            case MotionEvent.ACTION_UP:
                if ((x > centerX / 2 && x < centerX + (centerX / 2)) &&
                        (y > centerY / 2 && y < centerY + (centerY / 2))) {
                    if (mediaPlayer.isPlaying()) {
                        iv_start.setVisibility(View.VISIBLE);
                        mediaPlayer.pause();
                    } else {
                        mediaPlayer.start();
                        iv_start.setVisibility(View.GONE);
                    }
                }
                break;
        }
        return true;
    }

    @Override
    public void setPlayState(int playState) {
        super.setPlayState(playState);

        switch (playState) {
            case IjkVideoView.STATE_IDLE:
                L.e("STATE_IDLE");
                thumb.setVisibility(VISIBLE);
                iv_start.setVisibility(View.GONE);
                loading.setVisibility(View.GONE);
                break;
            case IjkVideoView.STATE_PLAYING:
                L.e("STATE_PLAYING");
                thumb.setVisibility(GONE);
                post(mShowProgress);
                this.removeView(mStatusView);
                loading.setVisibility(View.GONE);
                break;
            case IjkVideoView.STATE_PREPARED:
                L.e("STATE_PREPARED");
                iv_start.setVisibility(View.GONE);
                loading.setVisibility(View.GONE);
                break;
            case IjkVideoView.STATE_ERROR:
                loading.setVisibility(View.GONE);
                setErrorStatus();
                break;
            case IjkVideoView.STATE_PREPARING:
                loading.setVisibility(View.VISIBLE);
                break;
        }
    }

    public SimpleDraweeView getThumb() {
        return thumb;
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        if (!fromUser) {
            return;
        }
        long duration = mediaPlayer.getDuration();
        long newPosition = (duration * progress) / seek_bar.getMax();

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

    @Override
    protected int setProgress() {
        if (mediaPlayer == null || isDragging) {
            return 0;
        }
        int position = (int) mediaPlayer.getCurrentPosition();
        int duration = (int) mediaPlayer.getDuration();
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


        return position;
    }

    /**
     * 播放出错，复写该setPlayState方法
     * 主要更改界面
     */
    private void setErrorStatus() {
        this.removeView(mStatusView);
        this.addView(mStatusView, 0);
    }

    @Override
    public void showStatusView() {
        super.showStatusView();
        // setNetWorkStatus();
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
     *  int centerX = getWidth() / 2;
     int centerY = getHeight() / 2;

     switch (event.getAction()) {
     case MotionEvent.ACTION_DOWN:
     x = event.getX();
     y = event.getY();
     LogUtil.showLog("msg---手指按下");

     break;
     case MotionEvent.ACTION_UP:
     LogUtil.showLog("msg---手指抬起");
     if ((x > centerX / 2 && x < centerX + (centerX / 2)) &&
     (y > centerY / 2 && y < centerY + (centerY / 2))) {

     if (mediaPlayer.isPlaying()) {
     iv_start.setVisibility(View.VISIBLE);
     mediaPlayer.pause();
     } else {
     mediaPlayer.start();
     iv_start.setVisibility(View.GONE);
     }
     }
     break;
     }
     */
}
