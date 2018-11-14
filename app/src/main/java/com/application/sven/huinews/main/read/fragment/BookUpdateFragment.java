package com.application.sven.huinews.main.read.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.application.sven.huinews.R;
import com.application.sven.huinews.base.BaseFragment;
import com.application.sven.huinews.config.Constant;
import com.application.sven.huinews.entity.request.BookListRequest;
import com.application.sven.huinews.entity.response.BookUpdateResponse;
import com.application.sven.huinews.entity.response.BookUpdateWeek;
import com.application.sven.huinews.entity.response.VideoChannelResponse;
import com.application.sven.huinews.main.read.adapter.ReadAdapter;
import com.application.sven.huinews.main.read.contract.BookUpdateContract;
import com.application.sven.huinews.main.read.model.BookUpdateModel;
import com.application.sven.huinews.main.read.presenter.BookUpdatePresenter;
import com.application.sven.huinews.view.EmptyLayout;
import com.application.sven.huinews.view.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

/**
 * auther: sunfuyi
 * data: 2018/7/11
 * effect:
 */
public class BookUpdateFragment extends BaseFragment<BookUpdatePresenter, BookUpdateModel> implements BookUpdateContract.View {

    private BookUpdateWeek mBookUpdateWeek;
    private RefreshLayout refresh_view;

    private RecyclerView rv;
    private ReadAdapter mReadAdapter;
    private EmptyLayout mEmptyLayout;
    private boolean isViewCreated;  //Fragment的View加载完毕的标记
    private boolean isUIVisible;    //Fragment对用户可见的标记

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        isViewCreated = true;
        lazyLoad();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        //isVisibleToUser这个boolean值表示:该Fragment的UI 用户是否可见
        if (isVisibleToUser) {
            isUIVisible = true;
            lazyLoad();
        } else {
            isUIVisible = false;
        }
    }


    private void lazyLoad() {
        //这里进行双重标记判断,是因为setUserVisibleHint会多次回调,并且会在onCreateView执行前回调,必须确保onCreateView加载完毕且页面可见,才加载数据
        if (isViewCreated && isUIVisible) {
            mEmptyLayout.setErrorType(EmptyLayout.LOADING, -1);
            bookList();
            //数据加载完毕,恢复标记,防止重复加载
            isViewCreated = false;
            isUIVisible = false;
        }
    }

    public static BookUpdateFragment getInstance(BookUpdateWeek channelData) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(Constant.BOOK_UPDATE_TAB_PAGE_INDEX, channelData);
        //   bundle.putInt(Constant.WX_LOGIN_KEY_CODE);
        BookUpdateFragment fragment = new BookUpdateFragment();
        fragment.setArguments(bundle);
        return fragment;
    }


    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_list;
    }

    @Override
    public void initObject() {
        setMVP();
        mBookUpdateWeek = (BookUpdateWeek) getArguments().getSerializable(Constant.BOOK_UPDATE_TAB_PAGE_INDEX);
        initRecycler();

    }

    @Override
    protected void initView(View v) {
        mEmptyLayout = v.findViewById(R.id.mEmptyLayout);
        refresh_view = v.findViewById(R.id.refresh_view);
        refresh_view.setEnableLoadmore(false);
        rv = v.findViewById(R.id.rv);
    }

    @Override
    public void initEvents() {
        refresh_view.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(com.scwang.smartrefresh.layout.api.RefreshLayout refreshlayout) {
                isRefresh = true;
                bookList();
            }
        });
    }

    @Override
    public void OnClickEvents(View v) {

    }

    private void bookList() {
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
        request.setTypeId(18);
        request.setWtime(mBookUpdateWeek.getDay());
        return request;
    }

    @Override
    public void setBookList(BookUpdateResponse.BookUpdate mBookUpdate) {
        if (mBookUpdate.getData().size() > 0) {
            refresh_view.finishRefresh();
            mEmptyLayout.setErrorType(EmptyLayout.HIDE_LAYOUT, -1);
        } else {
            mEmptyLayout.setErrorType(EmptyLayout.NO_DATA, EmptyLayout.NO_BOOK);
        }

        mReadAdapter.setData(mBookUpdate.getData(), isRefresh);
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
