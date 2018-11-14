package com.application.sven.huinews.main.my.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.Toast;

import com.application.sven.huinews.R;
import com.application.sven.huinews.base.BaseActivity;
import com.application.sven.huinews.config.ChannelBean;
import com.application.sven.huinews.config.ChannelConfig;
import com.application.sven.huinews.main.home.adapter.TabPagerAdapter;
import com.application.sven.huinews.main.my.fragment.HistoryVideoFragment;
import com.application.sven.huinews.utils.ToastUtils;
import com.flyco.tablayout.SlidingTabLayout;

import java.util.ArrayList;

/**
 * auther: sunfuyi
 * data: 2018/5/10
 * effect:  观看历史
 */
public class HistoryVideoActivity extends BaseActivity {
    private SlidingTabLayout mTabLayout;
    private ViewPager mViewPager;


    public static void toThis(Context mContext) {
        Intent intent = new Intent(mContext, HistoryVideoActivity.class);
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
        mTitleBar.setTitle(getString(R.string.history_video));
        mTitleBar.setRightTv(getString(R.string.clear));
    }

    @Override
    public void initEvents() {
        mTitleBar.setBackOnClick(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        mTitleBar.setRightTvOnClick(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ToastUtils.showShort(mContext, "清空");
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
      /*  ArrayList<ChannelBean> arrayList = ChannelConfig.getHistoryChanel();
        TabPagerAdapter myContentPagerAdapter = new TabPagerAdapter(getSupportFragmentManager(), arrayList, TabPagerAdapter.TYPE_HISTORY);
        mViewPager.setAdapter(myContentPagerAdapter);
        mTabLayout.setViewPager(mViewPager);*/
    }
}
