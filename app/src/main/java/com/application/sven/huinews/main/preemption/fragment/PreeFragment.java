package com.application.sven.huinews.main.preemption.fragment;

import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageButton;


import com.application.sven.huinews.R;
import com.application.sven.huinews.base.BaseFragment;
import com.application.sven.huinews.config.ChannelBean;
import com.application.sven.huinews.config.ChannelConfig;
import com.application.sven.huinews.entity.response.VideoChannelResponse;
import com.application.sven.huinews.main.home.adapter.TabPagerAdapter;
import com.application.sven.huinews.main.preemption.adapter.PreePageAdapter;
import com.application.sven.huinews.main.search.activity.SearchActivity;
import com.flyco.tablayout.SlidingTabLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sfy. on 2018/5/8 0008.
 * 抢先
 */

public class PreeFragment extends BaseFragment {
    private SlidingTabLayout mTabLayout;
    private ViewPager mViewPager;
    private ImageButton btn_search;

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_pree;
    }

    @Override
    public void initObject() {
        initTab();
    }

    @Override
    protected void initView(View v) {
        mTabLayout = v.findViewById(R.id.tablayout);
        mViewPager = v.findViewById(R.id.view_pager);
        btn_search = v.findViewById(R.id.btn_search);
    }

    @Override
    public void initEvents() {
        btn_search.setOnClickListener(this);
    }

    @Override
    public void OnClickEvents(View v) {
        if (v == btn_search) {
            SearchActivity.toThis(mContext);
        }
    }

    private void initTab() {
        List<VideoChannelResponse.ChannelData.MBean> arrayList;
        if (ChannelConfig.getMovieChannelList(mContext) == null || ChannelConfig.getMovieChannelList(mContext).size() == 0) {
            arrayList = ChannelConfig.getMovieDefChannelList(mContext);
        } else {
            arrayList = ChannelConfig.getMovieChannelList(mContext);
        }
        PreePageAdapter myContentPagerAdapter = new PreePageAdapter(getChildFragmentManager(), arrayList);
        mViewPager.setAdapter(myContentPagerAdapter);
        mTabLayout.setViewPager(mViewPager);
    }
}
