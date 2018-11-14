package com.application.sven.huinews.main.read.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageButton;

import com.application.sven.huinews.R;
import com.application.sven.huinews.base.BaseActivity;
import com.application.sven.huinews.config.ChannelConfig;
import com.application.sven.huinews.config.http.DataCallBack;
import com.application.sven.huinews.entity.request.BookStoreRequest;
import com.application.sven.huinews.entity.response.BookStoreCate;
import com.application.sven.huinews.entity.response.BookStoreList;
import com.application.sven.huinews.entity.response.VideoChannelResponse;
import com.application.sven.huinews.main.read.adapter.BookStorePagerAdapter;
import com.application.sven.huinews.main.read.contract.BookStoreContract;
import com.application.sven.huinews.main.read.model.BookStoreModel;
import com.application.sven.huinews.main.read.presenter.BookStorePresenter;
import com.application.sven.huinews.view.EmptyLayout;
import com.flyco.tablayout.SegmentTabLayout;
import com.flyco.tablayout.listener.OnTabSelectListener;

import java.util.List;

/**
 * auther: sunfuyi
 * data: 2018/7/11
 * effect:
 */
public class BookStoreActivity extends BaseActivity<BookStorePresenter, BookStoreModel> implements BookStoreContract.View {
    private SegmentTabLayout tabLayout;
    private ViewPager mViewPager;
    private ImageButton btn_back;

    public static void toThis(Context mContext) {
        Intent intent = new Intent(mContext, BookStoreActivity.class);
        mContext.startActivity(intent);
    }


    @Override
    public int getLayoutId() {
        return R.layout.activity_book_store;
    }

    @Override
    public void initView() {
        tabLayout = findViewById(R.id.tablayout);
        mViewPager = findViewById(R.id.viewpager);
        btn_back = findViewById(R.id.btn_back);

    }

    @Override
    public void initEvents() {
        btn_back.setOnClickListener(this);
    }

    @Override
    public void onClickEvent(View v) {
        if (v == btn_back) {
            finish();
        }
    }

    @Override
    public void initObject() {
        setMVP();
        mPresenter.onBookStoreCate();
    }

    @Override
    public BookStoreRequest getBookStoreRequest() {
        return null;
    }

    @Override
    public void setCategroy(List<BookStoreCate> mBookStoreCates) {
        String[] title = new String[mBookStoreCates.size()];
        BookStorePagerAdapter mBookStorePagerAdapter = new BookStorePagerAdapter(getSupportFragmentManager(), mBookStoreCates);
        mViewPager.setAdapter(mBookStorePagerAdapter);
        for (int i = 0; i < mBookStoreCates.size(); i++) {

            title[i] = mBookStoreCates.get(i).getName();
        }
        tabLayout.setTabData(title);

        tabLayout.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                mViewPager.setCurrentItem(position);
            }

            @Override
            public void onTabReselect(int position) {

            }
        });
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                tabLayout.setCurrentTab(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        mViewPager.setCurrentItem(0);
    }

    @Override
    public void setBookList(BookStoreList bookStoreList) {

    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showErrorTip(int code, String msg) {
        /*if (code == DataCallBack.NET_TIME_OUT) {
            mEmptyLayout.setErrorType(EmptyLayout.LOAD_ERROR, EmptyLayout.LOAD_ERROR);
        } else if (code == DataCallBack.INTERNAL_SERVER_ERROR) {
            mEmptyLayout.setErrorType(EmptyLayout.LOAD_ERROR, EmptyLayout.LOAD_ERROR);
        } else if (code == DataCallBack.NETWORK_ERROR) {
            mEmptyLayout.setErrorType(EmptyLayout.NETWORK_ERROR, EmptyLayout.NETWORK_ERROR);
        }*/
    }
}
