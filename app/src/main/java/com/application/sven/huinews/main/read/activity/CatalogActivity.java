package com.application.sven.huinews.main.read.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.application.sven.huinews.R;
import com.application.sven.huinews.base.BaseActivity;
import com.application.sven.huinews.config.Constant;
import com.application.sven.huinews.config.http.DataCallBack;
import com.application.sven.huinews.entity.request.BookChapterRequest;
import com.application.sven.huinews.entity.response.BookChapters;
import com.application.sven.huinews.main.read.adapter.CataLogSortAdapter;
import com.application.sven.huinews.main.read.contract.BookChapterContract;
import com.application.sven.huinews.main.read.model.BookChapterModel;
import com.application.sven.huinews.main.read.presenter.BookChapterPresenter;
import com.application.sven.huinews.view.EmptyLayout;

/**
 * auther: sunfuyi
 * data: 2018/7/7
 * effect: 目录
 */
public class CatalogActivity extends BaseActivity<BookChapterPresenter, BookChapterModel> implements BookChapterContract.View {
    private RecyclerView sortRv;
    private CataLogSortAdapter mSortAdapter;
    private TextView btn_sort, tv_total_chapter;
    private String bookName;
    private int book_id;
    private boolean isSortUp;

    public static void toThis(Context mContext, int book_id, String bookName) {
        Intent intent = new Intent(mContext, CatalogActivity.class);
        Bundle bundle = new Bundle();
        bundle.putInt(Constant.BOOK_ID, book_id);
        bundle.putString(Constant.BUNDLE_TO_BOOK_NAME, bookName);
        intent.putExtras(bundle);
        mContext.startActivity(intent);
    }

    @Override
    public int getLayoutId() {
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            book_id = bundle.getInt(Constant.BOOK_ID);
            bookName = bundle.getString(Constant.BUNDLE_TO_BOOK_NAME);
        }
        return R.layout.activity_catalog;
    }

    @Override
    public void initView() {
        mTitleBar = findViewById(R.id.title_bar);
        mTitleBar.setTitle("目录");
        sortRv = findViewById(R.id.sort_rv);
        btn_sort = findViewById(R.id.btn_sort);
        tv_total_chapter = findViewById(R.id.tv_total_chapter);
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
        btn_sort.setOnClickListener(this);

        mSortAdapter.setOnItemClickLisenter(new CataLogSortAdapter.OnItemClickLisenter() {
            @Override
            public void toOpenBook(int chapterId) {
                BookReadActivity.toThis(mContext, book_id, chapterId, bookName, true);
                finish();
            }
        });

        mEmptyLayout.setOnEmptyRefreshLisenter(new EmptyLayout.onEmptyRefreshLisenter() {
            @Override
            public void onEmptyRefresh() {
                onBookChapter();
            }
        });
    }

    @Override
    public void onClickEvent(View v) {
        if (v == btn_sort) {
            sortData();
        }
    }

    @Override
    public void initObject() {
        setMVP();

        initRecyler();
        mEmptyLayout.setErrorType(EmptyLayout.LOADING, -1);
        onBookChapter();
    }

    private void sortData() {
        isSortUp = !isSortUp;
        Drawable drawable = ContextCompat.getDrawable(this, isSortUp ? R.mipmap.icon_sort_down : R.mipmap.icon_sort_up);
        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
        btn_sort.setCompoundDrawables(null, null, drawable, null);
        btn_sort.setText(isSortUp ? "倒序" : "正序");

        mSortAdapter.setSort();
    }

    private void initRecyler() {
        mSortAdapter = new CataLogSortAdapter(mContext);
        sortRv.setLayoutManager(new LinearLayoutManager(mContext));
        sortRv.setAdapter(mSortAdapter);

    }

    private void onBookChapter() {
        mPresenter.onBookChapters();
    }

    @Override
    public BookChapterRequest getBookChapterRequest() {
        BookChapterRequest request = new BookChapterRequest();
        request.setId(book_id);
        request.setLimit(Integer.MAX_VALUE);
        request.setSort(isSortUp ? Constant.BOOK_CHAPTER_DESC : Constant.BOOK_CHAPTER_ASC);
        return request;
    }

    @Override
    public void setBookChapters(BookChapters mBookChapters) {
        mEmptyLayout.setErrorType(EmptyLayout.HIDE_LAYOUT, -1);
        tv_total_chapter.setText(String.format(getString(R.string.book_chapter_num), mBookChapters.getChapter_number()));
        mSortAdapter.setData(mBookChapters.getLists(), true);
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
    }
}
