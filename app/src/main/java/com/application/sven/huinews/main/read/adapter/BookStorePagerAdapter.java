package com.application.sven.huinews.main.read.adapter;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.ViewGroup;

import com.application.sven.huinews.entity.response.BookStoreCate;
import com.application.sven.huinews.entity.response.BookUpdateWeek;
import com.application.sven.huinews.entity.response.VideoChannelResponse;
import com.application.sven.huinews.main.read.fragment.BookStoreFragment;
import com.application.sven.huinews.main.read.fragment.BookUpdateFragment;
import com.application.sven.huinews.main.read.fragment.ReadTabFragment;

import java.util.List;

/**
 * auther: sunfuyi
 * data: 2018/7/11
 * effect:
 */
public class BookStorePagerAdapter extends FragmentPagerAdapter {
    public FragmentManager fm;
    private List<BookStoreCate> mReadChannels;


    public BookStorePagerAdapter(FragmentManager fm, List<BookStoreCate> mReadChannels) {
        super(fm);
        this.fm = fm;
        this.mReadChannels = mReadChannels;

    }


    @Override
    public int getCount() {
        return mReadChannels.size();
    }


    @Override
    public Fragment getItem(int position) {
        return BookStoreFragment.getInstance(mReadChannels, position);
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mReadChannels.get(position).getName();
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
