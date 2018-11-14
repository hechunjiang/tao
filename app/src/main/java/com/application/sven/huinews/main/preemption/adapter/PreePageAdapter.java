package com.application.sven.huinews.main.preemption.adapter;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import com.application.sven.huinews.entity.response.VideoChannelResponse;
import com.application.sven.huinews.main.home.fragment.HomeTabFragment;
import com.application.sven.huinews.main.my.fragment.DownloadFragment;
import com.application.sven.huinews.main.my.fragment.HistoryVideoFragment;
import com.application.sven.huinews.main.preemption.fragment.PreeTabFragment;

import java.util.List;

/**
 * auther: sunfuyi
 * data: 2018/5/15
 * effect:
 */
public class PreePageAdapter extends FragmentPagerAdapter {
    public FragmentManager fm;
    private List<VideoChannelResponse.ChannelData.MBean> channelMovieList;


    public PreePageAdapter(FragmentManager fm, List<VideoChannelResponse.ChannelData.MBean> list) {
        super(fm);
        this.fm = fm;
        this.channelMovieList = list;

    }


    @Override
    public int getCount() {
        if (channelMovieList != null && channelMovieList.size() > 0) {
            return channelMovieList.size();
        }
        return 0;
    }


    @Override
    public Fragment getItem(int position) {
        return PreeTabFragment.getInstance(channelMovieList.get(position));
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return channelMovieList.get(position).getName();
    }

    @Override
    public Fragment instantiateItem(ViewGroup container, int position) {
        Fragment fragment = (Fragment) super.instantiateItem(container, position);
        fm.beginTransaction().show(fragment).commit();
        return fragment;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        // super.destroyItem(container, position, object);
        Fragment fragment = (Fragment) object;
        fm.beginTransaction().hide(fragment).commit();
    }
}
