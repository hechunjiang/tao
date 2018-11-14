package com.application.sven.huinews.main.my.adapter;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.ViewGroup;

import com.application.sven.huinews.config.ChannelBean;
import com.application.sven.huinews.main.my.fragment.CollectionFragment;
import com.application.sven.huinews.main.my.fragment.DownloadFragment;
import com.application.sven.huinews.main.my.fragment.HistoryVideoFragment;
import com.application.sven.huinews.utils.LogUtil;

import java.util.ArrayList;

/**
 * auther: sunfuyi
 * data: 2018/5/23
 * effect:
 */
public class MineTabPagerAdapter extends FragmentPagerAdapter {
    public static final int TYPE_HISTORY = 3; //观看历史
    public static final int TYPE_DOWNLOAD = 4; //我的下载
    public static final int TYPE_COLLECTION = 5; //我的收藏
    private ArrayList<ChannelBean> mChannelBeans;
    private int type;
    private CollectionFragment mCurrentFragment;

    public MineTabPagerAdapter(FragmentManager fm, ArrayList<ChannelBean> mChannelBeans, int type) {
        super(fm);
        this.mChannelBeans = mChannelBeans;
        this.type = type;
    }


    @Override
    public int getCount() {
        if (mChannelBeans != null && mChannelBeans.size() > 0) {
            return mChannelBeans.size();
        }
        return 0;
    }

    @Override
    public int getItemPosition(@NonNull Object object) {
        return POSITION_NONE;
    }

    @Override
    public Fragment getItem(int position) {
        if (type == TYPE_HISTORY) {
            return HistoryVideoFragment.getInstance(position);
        } else if (type == TYPE_DOWNLOAD) {
            return DownloadFragment.getInstance(position);
        } else if (type == TYPE_COLLECTION) {
            return CollectionFragment.getInstance(position);
        }
        return null;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mChannelBeans.get(position).getChannelTitle();
    }


    @Override
    public void setPrimaryItem(ViewGroup container, int position, Object object) {
        if (type == TYPE_COLLECTION) {
            mCurrentFragment = (CollectionFragment) object;
        }
       super.setPrimaryItem(container, position, object);
    }

    public CollectionFragment getFragment() {
        return mCurrentFragment;
    }
}
