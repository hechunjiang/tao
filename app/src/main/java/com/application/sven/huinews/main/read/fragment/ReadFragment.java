package com.application.sven.huinews.main.read.fragment;

import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageButton;

import com.application.sven.huinews.R;
import com.application.sven.huinews.base.BaseFragment;
import com.application.sven.huinews.config.ChannelConfig;
import com.application.sven.huinews.entity.response.VideoChannelResponse;
import com.application.sven.huinews.main.read.adapter.ReadPageAdapter;
import com.application.sven.huinews.main.search.activity.SearchBookActivity;
import com.flyco.tablayout.SlidingTabLayout;

import java.util.List;

/**
 * auther: sunfuyi
 * data: 2018/7/5
 * effect:
 */
public class ReadFragment extends BaseFragment {
    private SlidingTabLayout mTabLayout;
    private ViewPager mViewPager;
    private ImageButton btn_search;

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_read;
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
            SearchBookActivity.toThis(mContext);
        }
    }

    private void initTab() {
        List<VideoChannelResponse.ChannelData.BBean> mReadChannel;
        if (ChannelConfig.getBookChannelList(mContext) == null || ChannelConfig.getBookChannelList(mContext).size() == 0) {
            mReadChannel = ChannelConfig.getBookDefChannelList(mContext);
        } else {
            mReadChannel = ChannelConfig.getBookChannelList(mContext);
        }
        ReadPageAdapter mReadPageAdapter = new ReadPageAdapter(getChildFragmentManager(), mReadChannel);
        mViewPager.setAdapter(mReadPageAdapter);
        mTabLayout.setViewPager(mViewPager);
    }
}
