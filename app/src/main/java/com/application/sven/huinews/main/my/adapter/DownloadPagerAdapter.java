package com.application.sven.huinews.main.my.adapter;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.ViewGroup;

import com.application.sven.huinews.config.ChannelBean;
import com.application.sven.huinews.entity.response.VideoChannelResponse;
import com.application.sven.huinews.main.home.fragment.HomeTabFragment;
import com.application.sven.huinews.main.my.fragment.CollectionFragment;
import com.application.sven.huinews.main.my.fragment.DownloadFragment;

import java.util.List;

/**
 * auther: sunfuyi
 * data: 2018/5/29
 * effect:
 */
public class DownloadPagerAdapter extends FragmentPagerAdapter {
    private List<ChannelBean> channelList;
    private int type;

    private DownloadFragment mFragment;

    public DownloadPagerAdapter(FragmentManager fm, List<ChannelBean> list, int type) {
        super(fm);
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
        return DownloadFragment.getInstance(position);

    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return channelList.get(position).getChannelTitle();
    }

    @Override
    public void setPrimaryItem(ViewGroup container, int position, Object object) {
        mFragment = (DownloadFragment) object;
        super.setPrimaryItem(container, position, object);
    }

    public DownloadFragment getmFragment() {
        return mFragment;
    }
}
