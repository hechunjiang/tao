/*
package com.application.sven.huinews.view.video;

import android.content.Context;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.ImageView;

import com.application.sven.huinews.R;
import com.bumptech.glide.Glide;


import java.util.Timer;
import java.util.TimerTask;

*/
/**
 * auther: sunfuyi
 * data: 2018/5/11
 * effect: 垂直播放器
 *//*

public class VerticalVideoPlayer extends StandardGSYVideoPlayer {
    private static final String TAG = "VerticalVideoPlayer";
    private Handler myHandler;
    private MyTimerTask myTask;
    private Timer myTimer;
    private OnPlayedCall onPlayedCall;
    private int onPlayedCallNum;
    private ImageView videoImageView;

    public interface OnPlayedCall {
        void OnCall(int i);
    }

    private class MyTimerTask extends TimerTask {
        private MyTimerTask() {
        }

        public void run() {
            if (mCurrentState == 2 || mCurrentState == 5) {
                post(new Runnable() {
                    public void run() {
                        int position = getCurrentPositionWhenPlaying();
                        int duration = getDuration();
                        int residue = duration - position;
                        if (duration > 300 && residue > 0 && residue <= 300 && onPlayedCall != null) {
                            myHandler.postDelayed(new Runnable() {
                                public void run() {
                                    onPlayedCallNum = onPlayedCallNum + 1;
                                    onPlayedCall.OnCall(onPlayedCallNum);
                                }
                            }, (long) residue);
                        }
                    }
                });
            }
        }
    }

    public VerticalVideoPlayer(Context context, Boolean fullFlag) {
        super(context, fullFlag);
    }

    public VerticalVideoPlayer(Context context) {
        super(context);
    }

    public VerticalVideoPlayer(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    protected void init(Context context) {
        super.init(context);
        initView();
        this.myHandler = new Handler();
    }

    protected void touchSurfaceMoveFullLogic(float absDeltaX, float absDeltaY) {
        super.touchSurfaceMoveFullLogic(absDeltaX, absDeltaY);
        this.mChangePosition = false;
        this.mChangeVolume = false;
        this.mBrightness = false;
    }

    public void onPrepared() {
        super.onPrepared();
        setThumbGone();
        startMyTimer();
    }

    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        cancelMyTimer();
    }

    public int getLayoutId() {
        return R.layout.video_empty_control;
    }

    private void initView() {
        this.videoImageView = findViewById(R.id.videoImageView);
    }

    public void setUp(String url, boolean cacheWithPlay) {
        setUp(url, cacheWithPlay, "");
    }

    public void setThumbUrl(String url) {
        Glide.with(getContext()).load(url).into(this.videoImageView);
        this.videoImageView.setVisibility(View.VISIBLE
        );
    }

    public void setThumbVisible() {
        this.videoImageView.setVisibility(View.VISIBLE);
    }

    public void setThumbGone() {
        AlphaAnimation mHidden = new AlphaAnimation(2.0f, 0.0f);
        mHidden.setDuration(100);
        this.videoImageView.startAnimation(mHidden);
        this.videoImageView.setVisibility(View.GONE);

    }

    private void startMyTimer() {
        cancelMyTimer();
        this.myTimer = new Timer();
        this.myTask = new MyTimerTask();
        this.myTimer.schedule(this.myTask, 0, 300);
    }

    private void cancelMyTimer() {
        if (this.myTimer != null) {
            this.myTimer.cancel();
            this.myTimer = null;
        }
        if (this.myTask != null) {
            this.myTask.cancel();
            this.myTask = null;
        }
    }

    public void setOnPlayedCall(OnPlayedCall call) {
        this.onPlayedCallNum = 0;
        this.onPlayedCall = call;
    }

}
*/
