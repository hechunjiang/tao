package com.application.sven.huinews.view;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.LinearInterpolator;

import com.application.sven.huinews.R;
import com.application.sven.huinews.utils.CommonUtils;
import com.application.sven.huinews.utils.LogUtil;


/**
 * Created by Sven on 2018/2/26.
 */

public class MyVideoProgress extends View {
    private int roundColor; //圆形进度条的颜色
    private int roundProgressColor;//圆形进度条进度的颜色
    private float roundWidth; //圆的宽度
    private int progress; //当前进度
    private Paint mPaint; //画笔
    private int startAngle = -90; //起始位置

    private long mCurrentPlayTime; //动画当前执行的时间

    public MyVideoProgress(Context context) {
        super(context);
        init(context);
    }

    public MyVideoProgress(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public MyVideoProgress(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }


    private void init(Context context) {
        //初始化一只笔
        mPaint = new Paint();
        //获取xml当中设置的属性，如果没有设置，则设置一个默认值
        roundColor = Color.parseColor("#B72123");
        roundProgressColor = context.getResources().getColor(R.color.c_ffe96f);
        roundWidth = CommonUtils.dip2px(context, 3);

        initAnim();
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        //画背景圆环
        int center = getWidth() / 2;
        //设置半径
        float radius = center - roundWidth / 2;
        //设置圆圈的颜色
        mPaint.setColor(roundColor);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(roundWidth);//圆环的宽度
        mPaint.setAntiAlias(true);//设置抗锯齿

        //画外圈
        canvas.drawCircle(center, center, radius, mPaint);
        //画圆弧
        RectF oval = new RectF(center - radius, center - radius, center + radius, center + radius);
        mPaint.setColor(roundProgressColor);
        mPaint.setStrokeWidth(roundWidth);
        mPaint.setStyle(Paint.Style.STROKE);
        //设置笔帽
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        //话进度
        canvas.drawArc(oval, startAngle, progress, false, mPaint);

    }

   /* public void setProgress(int progress){
        if(progress < 0){
            throw new IllegalArgumentException("进度progress不能小于0");
        }
        if(progress > max){
            progress = max;
        }
        if(progress <= max){
            this.progress = progress;
            postInvalidate();
        }
    }*/

    /**
     * 暂停后获取进度
     */
    public void stopProgress() {
        animStop();
    }

    private long mDuration;

    /**
     * 设置总时长
     */
    public void setDuration(long mDuration) {
        this.mDuration = mDuration;

        if (mValueAnimator != null && mDuration > 0) {
            mValueAnimator.setDuration(mDuration);
        }
    }

    ValueAnimator mValueAnimator;

    private void initAnim() {
        mValueAnimator = ValueAnimator.ofInt(progress, 360);
        mValueAnimator.setIntValues();
        mValueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                progress = (int) animation.getAnimatedValue();
               /* if (progress == 360) {
                    if (onVideoProgressLisenter != null) {
                        onVideoProgressLisenter.end();
                    }
                } else {*/
                mCurrentPlayTime = mValueAnimator.getCurrentPlayTime();
                postInvalidate();
                // }
            }
        });

        mValueAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationCancel(Animator animation) {
                super.onAnimationCancel(animation);
                mValueAnimator.setIntValues(progress, 360);
            }

            @Override
            public void onAnimationRepeat(Animator animation) {
                super.onAnimationRepeat(animation);
                LogUtil.showLog("msg----onAnimationRepeat:");
                if (onVideoProgressLisenter != null) {
                    onVideoProgressLisenter.end();
                }
            }

            @Override
            public void onAnimationPause(Animator animation) {
                super.onAnimationPause(animation);
                mValueAnimator.setIntValues(progress, 360);
            }
        });

        //设置匀速插值器
        mValueAnimator.setInterpolator(new LinearInterpolator());
        mValueAnimator.setDuration(mDuration);
        //设置重复次数 infinite 无限循环
        mValueAnimator.setRepeatCount(ValueAnimator.INFINITE);
        //设置重复模式 --restart 重新开始
        mValueAnimator.setRepeatMode(ValueAnimator.RESTART);


    }


    public void animStart() {
        if (mValueAnimator != null) {
            mValueAnimator.start();
        }
    }

    public long surplusTime() {
        if (mValueAnimator == null) {
            return 0;
        }
        LogUtil.showLog("msg----mDuration:" + (mDuration - mCurrentPlayTime));
        return mDuration - mCurrentPlayTime;
    }


    public void setProgress(int progress) {
        if (progress < 0) {
            return;
        }
        //stopProgress();
        this.progress = progress;
        mValueAnimator.setIntValues(progress, 360);
        postInvalidate();
    }

    public int getProgressCurrent() {
        return progress;
    }

    public boolean getAnimatorStatus() {
        return mValueAnimator.isRunning();
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void animStop() {
        mValueAnimator.pause();
    }

    public interface OnVideoProgressLisenter {
        void end();
    }

    private OnVideoProgressLisenter onVideoProgressLisenter;

    public void OnVideoProgressLisenter(OnVideoProgressLisenter o) {
        this.onVideoProgressLisenter = o;
    }
}
