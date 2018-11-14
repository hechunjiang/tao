package com.application.sven.huinews.utils;

import android.util.Log;
import android.widget.ImageView;

import com.application.sven.huinews.R;

/**
 * Created by sfy. on 2018/3/17 0017.
 */

public class GoldFramAnimation {

    private FrameAnimation mFrameAnimation;

    public GoldFramAnimation() {

    }

    public void startAnim(final ImageView ivOpen) {
        if (mFrameAnimation != null) {
            return;
        }
        mFrameAnimation = new FrameAnimation(ivOpen, mImgResIds, 90, true);
        mFrameAnimation.setAnimationListener(new FrameAnimation.AnimationListener() {
            @Override
            public void onAnimationStart() {
                Log.i("", "start");
            }

            @Override
            public void onAnimationEnd() {
                Log.i("", "end");
            }

            @Override
            public void onAnimationRepeat() {
                Log.i("", "repeat");
            }

            @Override
            public void onAnimationPause() {
                ivOpen.setBackgroundResource(R.mipmap.icon_open_red_packet1);
            }
        });
    }

    public void stopAnim() {
        if (mFrameAnimation != null) {
            mFrameAnimation.release();
            mFrameAnimation = null;
        }
    }

    private int[] mImgResIds = new int[]{
            R.mipmap.icon_open_red_packet1,
            R.mipmap.icon_open_red_packet2,
            R.mipmap.icon_open_red_packet3,
            R.mipmap.icon_open_red_packet4,
            R.mipmap.icon_open_red_packet5,
            R.mipmap.icon_open_red_packet6,
            R.mipmap.icon_open_red_packet7,
            R.mipmap.icon_open_red_packet7,
            R.mipmap.icon_open_red_packet8,
            R.mipmap.icon_open_red_packet9,
            R.mipmap.icon_open_red_packet4,
            R.mipmap.icon_open_red_packet10,
            R.mipmap.icon_open_red_packet11,
    };
}
