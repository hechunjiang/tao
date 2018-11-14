package com.application.sven.huinews.main.search.adapter;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.application.sven.huinews.config.ChannelBean;
import com.application.sven.huinews.entity.response.VideoChannelResponse;
import com.application.sven.huinews.main.preemption.fragment.PreeTabFragment;
import com.application.sven.huinews.main.search.fragment.SearchFragment;

import java.util.List;

/**
 * auther: sunfuyi
 * data: 2018/5/15
 * effect:
 */
public class SearchPagerAdapter extends FragmentPagerAdapter {
    private List<ChannelBean> mChannelList;


    public SearchPagerAdapter(FragmentManager fm, List<ChannelBean> mChannelList) {
        super(fm);
        this.mChannelList = mChannelList;
    }


    @Override
    public int getCount() {
        return mChannelList.size();
    }


    @Override
    public Fragment getItem(int position) {
        return SearchFragment.getInstance(position);
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mChannelList.get(position).getChannelTitle();
    }
}
