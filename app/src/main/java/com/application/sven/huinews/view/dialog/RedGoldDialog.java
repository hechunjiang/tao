package com.application.sven.huinews.view.dialog;

import android.app.Dialog;
import android.content.Context;
import android.media.MediaPlayer;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.application.sven.huinews.R;
import com.application.sven.huinews.utils.GoldFramAnimation;

/**
 * auther: sunfuyi
 * data: 2018/6/1
 * effect:
 */
public class RedGoldDialog extends Dialog implements View.OnClickListener {
    private Context mContext;
    private RelativeLayout rl_opened;//红包打开后的view
    private RelativeLayout rl_parent;//父布局
    private ImageView btn_open; //开红包view
    private TextView tv_gold; //金币数量
    private TextView red_video_msg; //红包描述
    private TextView no_red_bag_msg; //红包描述
    private ImageButton btn_cancel; //关闭
    private TextView tv_info;
    private GoldFramAnimation mGoldFramAnimation;
    private MediaPlayer mediaPlayer;

    public RedGoldDialog(@NonNull Context context) {
        super(context, R.style.my_dialog);
        this.mContext = context;
        setContentView(R.layout.activity_red_gold);
        initView();

    }


    private void initView() {
        mGoldFramAnimation = new GoldFramAnimation();
        rl_opened = findViewById(R.id.rl_opened);
        btn_open = findViewById(R.id.btn_open);
        rl_parent = findViewById(R.id.rl);
        tv_gold = findViewById(R.id.tv_gold);
        red_video_msg = findViewById(R.id.red_video_msg);
        no_red_bag_msg = findViewById(R.id.no_red_bag_msg);
        btn_cancel = findViewById(R.id.btn_cancel);
        tv_info = findViewById(R.id.tv_info);
        onEvents();
    }

    private void onEvents() {
        btn_open.setOnClickListener(this);
        btn_cancel.setOnClickListener(this);
        //监听打开动画
        // showRedGoldInfo(rl_opened);
    }

    /**
     * 观看视频显示红包
     */
    public void showRedGoldDialog() {
        tv_info.setVisibility(View.INVISIBLE);
        show();
    }

    /**
     * 观看视频打开红包
     *
     * @param count
     */
    public void showRedGoldBagDialog(int count) {
        tv_gold.setText(count + "");
        // red_video_msg.setText(msg+"");
        show();
    }

    /**
     * 个人中心弹出红包
     */
    public void showDialotSetTitle() {
        red_video_msg.setText("恭喜发财,大吉大利");
        tv_info.setVisibility(View.VISIBLE);
        show();
    }

    /**
     * 个人中心打开金币红包
     */
    public void showDialogGoldInfo() {

    }

    public void hideDialog() {
        btn_open.clearAnimation();
        dismiss();
    }


    public interface OnOpenBagListener {
        void onOpen();
    }

    private OnOpenBagListener mOnOpenBagListener;

    public void setOnOpenBagListener(OnOpenBagListener onOpenBagListener) {
        mOnOpenBagListener = onOpenBagListener;
    }


    /**
     * 位移动画
     */
    public void showRedGoldInfo(final boolean isGold) {
        TranslateAnimation translateAnimation = new TranslateAnimation(0, 0, 0, -rl_parent.getHeight() / 2.5f);
        translateAnimation.setDuration(1200);
        translateAnimation.setFillAfter(true);
        rl_opened.startAnimation(translateAnimation);
        translateAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                red_video_msg.setVisibility(View.GONE);
                if (isGold) {
                    no_red_bag_msg.setVisibility(View.GONE);
                    tv_gold.setVisibility(View.VISIBLE);
                    if (mediaPlayer == null) {
                        mediaPlayer = MediaPlayer.create(mContext, R.raw.gold_come);
                    }
                    mediaPlayer.start();
                } else {
                    no_red_bag_msg.setVisibility(View.VISIBLE);
                    tv_gold.setVisibility(View.GONE);
                }

                btn_open.clearAnimation();
                btn_open.setVisibility(View.GONE);
            }

            @Override
            public void onAnimationEnd(Animation animation) {

            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });
    }


    @Override
    public void dismiss() {
        super.dismiss();
        mGoldFramAnimation.stopAnim();
        btn_open.setVisibility(View.VISIBLE);
        red_video_msg.setVisibility(View.VISIBLE);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btn_open) {
            //  btn_open.startAnimation(mMyRotateZAnim);
            mGoldFramAnimation.startAnim(btn_open);
            if (mOnOpenBagListener != null) {
                mOnOpenBagListener.onOpen();
            }

        } else if (view.getId() == R.id.btn_cancel) {
            dismiss();
        }
    }
}
