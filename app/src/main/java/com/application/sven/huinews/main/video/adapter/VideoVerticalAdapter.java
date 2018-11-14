package com.application.sven.huinews.main.video.adapter;

import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * auther: sunfuyi
 * data: 2018/5/14
 * effect:
 */
public class VideoVerticalAdapter extends PagerAdapter {

    private static final String TAG = "VideoVerticalAdapter";

    private List<View> mViews;

    public VideoVerticalAdapter(List<View> views) {
        this.mViews = views;
    }



    @Override
    public int getCount() {
        return mViews.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        Log.d(TAG, "instantiateItem: called:" + position);
        container.addView(mViews.get(position));
        return mViews.get(position);
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        Log.d(TAG, "destroyItem: destroyItem：" + position);
        container.removeView(mViews.get(position));
    }
}
