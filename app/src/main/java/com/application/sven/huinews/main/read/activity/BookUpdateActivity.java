package com.application.sven.huinews.main.read.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.application.sven.huinews.R;
import com.application.sven.huinews.base.BaseActivity;
import com.application.sven.huinews.config.ChannelConfig;
import com.application.sven.huinews.config.http.DataCallBack;
import com.application.sven.huinews.entity.request.BookListRequest;
import com.application.sven.huinews.entity.response.BookList;
import com.application.sven.huinews.entity.response.BookUpdateResponse;
import com.application.sven.huinews.entity.response.VideoChannelResponse;
import com.application.sven.huinews.main.preemption.adapter.PreePageAdapter;
import com.application.sven.huinews.main.read.adapter.BookUpdatePagerAdapter;
import com.application.sven.huinews.main.read.contract.BookListContract;
import com.application.sven.huinews.main.read.contract.BookUpdateContract;
import com.application.sven.huinews.main.read.model.BookListModel;
import com.application.sven.huinews.main.read.model.BookUpdateModel;
import com.application.sven.huinews.main.read.presenter.BookListPresenter;
import com.application.sven.huinews.main.read.presenter.BookUpdatePresenter;
import com.application.sven.huinews.utils.ScreensUitls;
import com.application.sven.huinews.utils.ToastUtils;
import com.application.sven.huinews.view.EmptyLayout;
import com.flyco.tablayout.SlidingTabLayout;

import java.util.List;

/**
 * auther: sunfuyi
 * data: 2018/7/11
 * effect:
 */
public class BookUpdateActivity extends BaseActivity<BookUpdatePresenter, BookUpdateModel> implements BookUpdateContract.View {
    private SlidingTabLayout mTabLayout;
    private ViewPager mViewPager;

    public static void toThis(Context mContext) {
        Intent intent = new Intent(mContext, BookUpdateActivity.class);
        mContext.startActivity(intent);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_book_update;
    }

    @Override
    public void initView() {
        mTitleBar = findViewById(R.id.title_bar);
        mTitleBar.setTitle("连载更新");
        mTabLayout = findViewById(R.id.tablayout);
        mViewPager = findViewById(R.id.view_pager);
        mEmptyLayout = findViewById(R.id.mEmptyLayout);

    }

    @Override
    public void initEvents() {
        mTitleBar.setBackOnClick(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mEmptyLayout.setOnEmptyRefreshLisenter(new EmptyLayout.onEmptyRefreshLisenter() {
            @Override
            public void onEmptyRefresh() {
                mPresenter.onBookList();
            }
        });
    }

    @Override
    public void onClickEvent(View v) {

    }

    @Override
    public void initObject() {
        setMVP();
        mEmptyLayout.setErrorType(EmptyLayout.LOADING, -1);
        mPresenter.onBookList();
    }

    @Override
    public BookListRequest getBookListRequest() {
        BookListRequest request = new BookListRequest();
        request.setTypeId(18);
        return request;
    }

    @Override
    public void setBookList(BookUpdateResponse.BookUpdate mBookUpdate) {
        mEmptyLayout.setErrorType(EmptyLayout.HIDE_LAYOUT, -1);
        setViewPager(mBookUpdate);
    }


    private void setViewPager(BookUpdateResponse.BookUpdate mBookUpdate) {
        BookUpdatePagerAdapter myContentPagerAdapter = new BookUpdatePagerAdapter(getSupportFragmentManager(), mBookUpdate.getWeek());
        mViewPager.setAdapter(myContentPagerAdapter);
        mTabLayout.setViewPager(mViewPager);
        mViewPager.setCurrentItem(mBookUpdate.getWeek().size() - 1);
    }


    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showErrorTip(int code, String msg) {
        if (code == DataCallBack.NET_TIME_OUT) {
            mEmptyLayout.setErrorType(EmptyLayout.LOAD_ERROR, EmptyLayout.LOAD_ERROR);
        } else if (code == DataCallBack.INTERNAL_SERVER_ERROR) {
            mEmptyLayout.setErrorType(EmptyLayout.LOAD_ERROR, EmptyLayout.LOAD_ERROR);
        } else if (code == DataCallBack.NETWORK_ERROR) {
            mEmptyLayout.setErrorType(EmptyLayout.NETWORK_ERROR, EmptyLayout.NETWORK_ERROR);
        }
        //  ToastUtils.showLong(mContext, msg);
    }
}
