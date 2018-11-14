package com.application.sven.huinews.main.read.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.application.sven.huinews.R;
import com.application.sven.huinews.base.BaseActivity;
import com.application.sven.huinews.entity.request.BookListRequest;
import com.application.sven.huinews.entity.response.BookList;
import com.application.sven.huinews.entity.response.VideoChannelResponse;
import com.application.sven.huinews.main.read.adapter.ReadAdapter;
import com.application.sven.huinews.main.read.contract.BookListContract;
import com.application.sven.huinews.main.read.model.BookListModel;
import com.application.sven.huinews.main.read.presenter.BookListPresenter;
import com.application.sven.huinews.utils.LogUtil;
import com.application.sven.huinews.utils.TimeUtils;
import com.application.sven.huinews.view.EmptyLayout;
import com.application.sven.huinews.view.RefreshLayout;

import java.util.List;


/**
 * auther: sunfuyi
 * data: 2018/7/11
 * effect:
 */
public class BookFreeActivity extends BaseActivity<BookListPresenter, BookListModel> implements BookListContract.View {
    private RefreshLayout refresh_view;
    private RecyclerView rv;
    private ReadAdapter mReadAdapter;
    private EmptyLayout mEmptyLayout;

    public static void toThis(Context mContext) {
        Intent intent = new Intent(mContext, BookFreeActivity.class);
        mContext.startActivity(intent);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_book_free;
    }

    @Override
    public void initView() {
        mTitleBar = findViewById(R.id.title_bar);
        mTitleBar.setTitle("免费");
        mEmptyLayout = findViewById(R.id.mEmptyLayout);
        refresh_view = findViewById(R.id.refresh_view);
        refresh_view.setEnableLoadmore(false);
        rv = findViewById(R.id.rv);

        TimeUtils timeUtils = new TimeUtils();
        timeUtils.phoneTime(mContext);
        long time = timeUtils.phoneTime(mContext);
        long timeNight = timeUtils.getTimesnight();
        LogUtil.showLog("msg---time:" + time + " timeNight:" + timeNight);
    }

    @Override
    public void initEvents() {
        mTitleBar.setBackOnClick(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public void onClickEvent(View v) {

    }

    @Override
    public void initObject() {
        setMVP();
        initRecycler();
        bookList();
    }


    private void bookList() {
        mEmptyLayout.setErrorType(EmptyLayout.LOADING, -1);
        mPresenter.onBookList();
    }

    private void initRecycler() {
        mReadAdapter = new ReadAdapter(mContext);
        rv.setLayoutManager(new LinearLayoutManager(mContext));
        rv.setAdapter(mReadAdapter);


    }

    @Override
    public BookListRequest getBookListRequest() {
        BookListRequest request = new BookListRequest();
        request.setTypeId(17);
        return request;
    }

    @Override
    public void setBookList(List<BookList> mBookList) {
        mEmptyLayout.setErrorType(EmptyLayout.HIDE_LAYOUT, -1);
        mReadAdapter.setData(mBookList);
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showErrorTip(int code, String msg) {

    }
}
