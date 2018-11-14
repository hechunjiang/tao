package com.application.sven.huinews.main.home.adapter;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.ViewGroup;

import com.application.sven.huinews.config.ChannelBean;
import com.application.sven.huinews.entity.response.VideoChannelResponse;
import com.application.sven.huinews.main.home.fragment.HomeTabFragment;
import com.application.sven.huinews.main.home.fragment.HomeVideoFragment;
import com.application.sven.huinews.main.my.fragment.DownloadFragment;
import com.application.sven.huinews.main.my.fragment.HistoryVideoFragment;
import com.application.sven.huinews.main.preemption.fragment.PreeTabFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sfy. on 2018/5/8 0008.
 */

public class TabPagerAdapter extends FragmentPagerAdapter {
    public static final int TYPE_HOME = 1; //首页
    public static final int TYPE_HISTORY = 3; //观看历史
    public static final int TYPE_DOWNLOAD = 4; //我的下载
    private List<VideoChannelResponse.ChannelData.VBean> channelList;
    private int type;
    private FragmentManager fm;


    public TabPagerAdapter(FragmentManager fm, List<VideoChannelResponse.ChannelData.VBean> list, int type) {
        super(fm);
        this.fm = fm;
        this.channelList = list;
        this.type = type;
    }


    @Override
    public int getCount() {
        if (channelList != null && channelList.size() > 0) {
            return channelList.size();
        }
        return 0;
    }

    @Override
    public Fragment getItem(int position) {
        return HomeTabFragment.getInstance(channelList.get(position));
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return channelList.get(position).getName();
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
