package com.application.sven.huinews.main.welcome.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Point;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.application.sven.huinews.R;
import com.application.sven.huinews.base.BaseActivity;
import com.application.sven.huinews.main.home.activity.MainActivity;
import com.application.sven.huinews.main.welcome.adapter.GuidePagerAdapter;
import com.application.sven.huinews.utils.CommonUtils;
import com.application.sven.huinews.utils.ScreensUitls;

import java.util.ArrayList;

/**
 * auther: sunfuyi
 * data: 2018/5/25
 * effect:引导页
 */
public class GuideActivity extends BaseActivity {
    private ViewPager mViewPager;
    private ImageView welcome_pointer_1, welcome_pointer_2, welcome_pointer_3;
    private ImageView guide1, guide2, guide3;
    private ArrayList<View> mBannersView = new ArrayList<>();
    private ImageView mBtnStart;
    private LinearLayout tips_ll;
    private int currentItem;


    public static void toThis(Context mContext) {
        Intent intent = new Intent(mContext, GuideActivity.class);
        mContext.startActivity(intent);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_guide;
    }

    @Override
    public void initView() {
        mViewPager = findViewById(R.id.viewpager);

        View banner1 = LayoutInflater.from(this).inflate(R.layout.welcome_pager_banner_1, null, false);
        View banner2 = LayoutInflater.from(this).inflate(R.layout.welcome_pager_banner_2, null, false);
        View banner3 = LayoutInflater.from(this).inflate(R.layout.welcome_pager_banner_3, null, false);
        mBannersView.add(banner1);
        mBannersView.add(banner2);
        mBannersView.add(banner3);
        welcome_pointer_1 = findViewById(R.id.welcome_pointer_1);
        welcome_pointer_2 = findViewById(R.id.welcome_pointer_2);
        welcome_pointer_3 = findViewById(R.id.welcome_pointer_3);

        guide1 = banner1.findViewById(R.id.guide1);
        guide2 = banner2.findViewById(R.id.guide2);
        guide3 = banner3.findViewById(R.id.guide3);
        mBtnStart = banner3.findViewById(R.id.btn_start);
        tips_ll = findViewById(R.id.tips_ll);

        setImage();
    }

    @Override
    public void initEvents() {
        mBtnStart.setOnClickListener(this);
    }

    @Override
    public void onClickEvent(View v) {
        if (v == mBtnStart) {
            MainActivity.toThis(mContext);
            finish();
        }
    }

    @Override
    public void initObject() {
        setPagerAdapter();
    }

    private void setPagerAdapter() {
        GuidePagerAdapter mAdapter = new GuidePagerAdapter(mBannersView);
        mViewPager.setAdapter(mAdapter);
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                currentItem = position;
                switchPointer(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }


    private void switchPointer(int position) {
        switch (position) {
            case 0:
                tips_ll.setVisibility(View.VISIBLE);
                welcome_pointer_1.setImageResource(R.drawable.guide_pointer_big);
                welcome_pointer_2.setImageResource(R.drawable.guide_pointer_small);
                welcome_pointer_3.setImageResource(R.drawable.guide_pointer_small);
                break;
            case 1:
                tips_ll.setVisibility(View.VISIBLE);
                welcome_pointer_1.setImageResource(R.drawable.guide_pointer_small);
                welcome_pointer_2.setImageResource(R.drawable.guide_pointer_big);
                welcome_pointer_3.setImageResource(R.drawable.guide_pointer_small);
                break;
            case 2:
                tips_ll.setVisibility(View.GONE);
                welcome_pointer_1.setImageResource(R.drawable.guide_pointer_small);
                welcome_pointer_2.setImageResource(R.drawable.guide_pointer_small);
                welcome_pointer_3.setImageResource(R.drawable.guide_pointer_big);

                break;
        }
    }

    private void setImage() {
        int height = ScreensUitls.getScreenHeight(mContext);
        LinearLayout.LayoutParams params1 = (LinearLayout.LayoutParams) guide1.getLayoutParams();
        params1.height = height / 2;
        params1.topMargin = CommonUtils.dip2px(mContext, 40);
        params1.rightMargin = CommonUtils.dip2px(mContext, 33);
        params1.leftMargin = CommonUtils.dip2px(mContext, 33);
        guide1.setLayoutParams(params1);
        LinearLayout.LayoutParams params2 = (LinearLayout.LayoutParams) guide2.getLayoutParams();
        params2.height = height / 2;
        params2.topMargin = CommonUtils.dip2px(mContext, 40);
        params2.rightMargin = CommonUtils.dip2px(mContext, 33);
        params2.leftMargin = CommonUtils.dip2px(mContext, 33);
        guide2.setLayoutParams(params2);
        LinearLayout.LayoutParams params3 = (LinearLayout.LayoutParams) guide3.getLayoutParams();
        params3.height = height / 2;
        params3.topMargin = CommonUtils.dip2px(mContext, 40);
        params3.rightMargin = CommonUtils.dip2px(mContext, 33);
        params3.leftMargin = CommonUtils.dip2px(mContext, 33);
        guide3.setLayoutParams(params3);
    }
}
