package com.application.sven.huinews.main.my.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.LinearLayout;

import com.application.sven.huinews.R;
import com.application.sven.huinews.base.BaseActivity;
import com.application.sven.huinews.config.ChannelBean;
import com.application.sven.huinews.config.ChannelConfig;
import com.application.sven.huinews.entity.response.VideoChannelResponse;
import com.application.sven.huinews.main.home.adapter.TabPagerAdapter;
import com.application.sven.huinews.main.my.adapter.DownloadPagerAdapter;
import com.flyco.tablayout.SlidingTabLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * auther: sunfuyi
 * data: 2018/5/11
 * effect: 我的下载
 */
public class DownloadActivity extends BaseActivity {
    private SlidingTabLayout mTabLayout;
    private ViewPager mViewPager;
    private DownloadPagerAdapter myContentPagerAdapter;
    private int mCurrentPosition;

    public static void toThis(Context mContext) {
        Intent intent = new Intent(mContext, DownloadActivity.class);
        mContext.startActivity(intent);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_recycler_view;
    }

    @Override
    public void initView() {
        mTitleBar = findViewById(R.id.titlebar);
        mTabLayout = findViewById(R.id.tablayout);
        mViewPager = findViewById(R.id.viewpager);
        mTitleBar.setTitle(getString(R.string.my_download));
        mTitleBar.setRightTv(getString(R.string.clear));
    }

    @Override
    public void initEvents() {
        mTitleBar.setBackOnClick(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        mTitleBar.setRightTvOnClick(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myContentPagerAdapter.getmFragment().clearAll();
            }
        });

        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                mCurrentPosition = position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    public void onClickEvent(View v) {

    }

    @Override
    public void initObject() {
        initTab();
    }


    private void initTab() {
        List<ChannelBean> arrayList = ChannelConfig.getDownloadChannel();
        myContentPagerAdapter = new DownloadPagerAdapter(getSupportFragmentManager(), arrayList, TabPagerAdapter.TYPE_DOWNLOAD);
        mViewPager.setAdapter(myContentPagerAdapter);
        mTabLayout.setViewPager(mViewPager);
    }

}
