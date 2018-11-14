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
import com.application.sven.huinews.config.http.DataCallBack;
import com.application.sven.huinews.entity.request.BookListRequest;
import com.application.sven.huinews.entity.response.BookList;
import com.application.sven.huinews.entity.response.VideoChannelResponse;
import com.application.sven.huinews.main.read.adapter.ReadAdapter;
import com.application.sven.huinews.main.read.contract.BookListContract;
import com.application.sven.huinews.main.read.model.BookListModel;
import com.application.sven.huinews.main.read.presenter.BookListPresenter;
import com.application.sven.huinews.utils.ads.AdsConfig;
import com.application.sven.huinews.view.EmptyLayout;
import com.application.sven.huinews.view.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

/**
 * auther: sunfuyi
 * data: 2018/7/9
 * effect:
 */
public class ReadTabFragment extends BaseFragment<BookListPresenter, BookListModel> implements BookListContract.View {
    private RefreshLayout refresh_view;
    private RecyclerView rv;
    private ReadAdapter mReadAdapter;
    private EmptyLayout mEmptyLayout;
    private VideoChannelResponse.ChannelData.BBean channelData;

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
            if (mReadAdapter != null) {
                mReadAdapter.onStartHandler();
            }
        } else {
            isUIVisible = false;
            if (mReadAdapter != null) {
                mReadAdapter.onDestoryHandler();
            }
        }


    }


    private void lazyLoad() {
        //这里进行双重标记判断,是因为setUserVisibleHint会多次回调,并且会在onCreateView执行前回调,必须确保onCreateView加载完毕且页面可见,才加载数据
        if (isViewCreated && isUIVisible) {
            bookList();
            //数据加载完毕,恢复标记,防止重复加载
            isViewCreated = false;
            isUIVisible = false;
        }
    }

    public static ReadTabFragment getInstance(VideoChannelResponse.ChannelData.BBean channelData) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(Constant.READ_TAB_PAGE_INDEX, channelData);
        //   bundle.putInt(Constant.WX_LOGIN_KEY_CODE);
        ReadTabFragment fragment = new ReadTabFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_list;
    }

    @Override
    public void initObject() {
        mEmptyLayout.setErrorType(EmptyLayout.LOADING, -1);
        channelData = (VideoChannelResponse.ChannelData.BBean) getArguments().getSerializable(Constant.READ_TAB_PAGE_INDEX);
        setMVP();
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
                bookList();
            }
        });

        mEmptyLayout.setOnEmptyRefreshLisenter(new EmptyLayout.onEmptyRefreshLisenter() {
            @Override
            public void onEmptyRefresh() {
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
        request.setTypeId(channelData.getId());
        return request;
    }

    @Override
    public void setBookList(List<BookList> mBookList) {
        refresh_view.finishRefresh();
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
        if (code == DataCallBack.NET_TIME_OUT) {
            mEmptyLayout.setErrorType(EmptyLayout.LOAD_ERROR, EmptyLayout.LOAD_ERROR);
        } else if (code == DataCallBack.INTERNAL_SERVER_ERROR) {
            mEmptyLayout.setErrorType(EmptyLayout.LOAD_ERROR, EmptyLayout.LOAD_ERROR);
        } else if (code == DataCallBack.NETWORK_ERROR) {
            mEmptyLayout.setErrorType(EmptyLayout.NETWORK_ERROR, EmptyLayout.NETWORK_ERROR);
        }
    }
}
