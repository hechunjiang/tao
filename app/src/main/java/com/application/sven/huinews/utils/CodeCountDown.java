package com.application.sven.huinews.utils;

import android.graphics.Color;
import android.os.CountDownTimer;
import android.widget.Button;
import android.widget.TextView;

import com.application.sven.huinews.R;

/**
 * auther: sunfuyi
 * data: 2018/5/23
 * effect:
 */
public class CodeCountDown extends CountDownTimer {
    private TextView mTextView;
    private static final long mMillisInFuture = 60 * 1000;
    private static final long mCountDownInterval = 1000;

    public void setNotClick() {
        mTextView.setTextColor(Color.parseColor("#bababa"));
        mTextView.setClickable(false);
    }

    public CodeCountDown(TextView textView) {
        super(mMillisInFuture, mCountDownInterval);
        mTextView = textView;
    }

    @Override
    public void onTick(long millisUntilFinished) {
        mTextView.setText(millisUntilFinished / 1000 + "重新获取");
    }

    @Override
    public void onFinish() {
        mTextView.setText("获取验证码");
        mTextView.setTextColor(Color.parseColor("#ff5645"));
        mTextView.setClickable(true);
    }
}
