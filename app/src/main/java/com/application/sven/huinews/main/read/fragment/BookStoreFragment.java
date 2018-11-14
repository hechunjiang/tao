package com.application.sven.huinews.main.read.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.application.sven.huinews.R;
import com.application.sven.huinews.base.BaseFragment;
import com.application.sven.huinews.config.Constant;
import com.application.sven.huinews.entity.request.BookStoreRequest;
import com.application.sven.huinews.entity.response.BookList;
import com.application.sven.huinews.entity.response.BookStoreCate;
import com.application.sven.huinews.entity.response.BookStoreList;
import com.application.sven.huinews.entity.response.VideoChannelResponse;

import com.application.sven.huinews.main.read.adapter.BookStoreAdapter;
import com.application.sven.huinews.main.read.adapter.BookStoreCateAdapter;
import com.application.sven.huinews.main.read.adapter.BookStoreListAdapter;
import com.application.sven.huinews.main.read.contract.BookStoreContract;
import com.application.sven.huinews.main.read.model.BookStoreModel;
import com.application.sven.huinews.main.read.presenter.BookStorePresenter;
import com.application.sven.huinews.utils.LogUtil;
import com.application.sven.huinews.utils.ToastUtils;
import com.application.sven.huinews.utils.itemDecoration.HBookItemDecoration;
import com.application.sven.huinews.view.EmptyLayout;
import com.application.sven.huinews.view.HeadAndFooterRecyclerView;
import com.application.sven.huinews.view.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * auther: sunfuyi
 * data: 2018/7/11
 * effect:
 */
public class BookStoreFragment extends BaseFragment<BookStorePresenter, BookStoreModel> implements BookStoreContract.View {
    private RefreshLayout refresh_view;
    private RecyclerView rv_store;
    private RecyclerView rv_type, rv_is_end, rv_sort;
    private TextView btn_cate_all, btn_isend_all, btn_sort_all;
    private BookStoreCateAdapter mAdapter, mIsEndAdapter, mSortAdater;
    private BookStoreListAdapter mBookAdapter;
    //private BookStoreAdapter mBookStoreAdapter;
    private List<BookStoreCate> channelData;
    private List<BookStoreList.Book> mBooks = new ArrayList<>();
    private BookStoreCate mBookStoreCate, mTempBookStoreCate;
    private EmptyLayout mEmptyLayout;
    private View headView;
    private View footView;
    private ProgressBar loading;
    private int currPos;
    private int sort, is_end, type;
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
//            bookList();
            channelData = (List<BookStoreCate>) getArguments().getSerializable(Constant.READ_TAB_PAGE_INDEX);
            currPos = getArguments().getInt(Constant.BOOK_STORE_POSITION);

            setCate();
            //数据加载完毕,恢复标记,防止重复加载
            isViewCreated = false;
            isUIVisible = false;
        }
    }

    public static BookStoreFragment getInstance(List<BookStoreCate> channelData, int position) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(Constant.READ_TAB_PAGE_INDEX, (Serializable) channelData);
        bundle.putInt(Constant.BOOK_STORE_POSITION, position);
        BookStoreFragment fragment = new BookStoreFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_book_store;
    }

    @Override
    public void initObject() {
        setMVP();

    }

    @Override
    protected void initView(View v) {
        refresh_view = v.findViewById(R.id.refresh_view);
        rv_store = v.findViewById(R.id.rv_store);
        mEmptyLayout = v.findViewById(R.id.mEmptyLayout);
        loading = v.findViewById(R.id.loading);


        // headView = LayoutInflater.from(mContext).inflate(R.layout.book_cate_list_item, rv_store, false);

        rv_type = v.findViewById(R.id.rv_type);
        rv_is_end = v.findViewById(R.id.rv_is_end);
        rv_sort = v.findViewById(R.id.rv_sort);
        btn_cate_all = v.findViewById(R.id.btn_cate_all);
        btn_isend_all = v.findViewById(R.id.btn_isend_all);
        btn_sort_all = v.findViewById(R.id.btn_sort_all);

        rv_store.setNestedScrollingEnabled(false);
        rv_type.setNestedScrollingEnabled(false);
        rv_is_end.setNestedScrollingEnabled(false);
        rv_sort.setNestedScrollingEnabled(false);
    }

    @Override
    public void initEvents() {
        btn_cate_all.setOnClickListener(this);
        btn_isend_all.setOnClickListener(this);
        btn_sort_all.setOnClickListener(this);

        refresh_view.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(com.scwang.smartrefresh.layout.api.RefreshLayout refreshlayout) {
                isRefresh = true;
                PAGE = 1;
                getBookList();
            }
        });
        refresh_view.setOnLoadmoreListener(new OnLoadmoreListener() {
            @Override
            public void onLoadmore(com.scwang.smartrefresh.layout.api.RefreshLayout refreshlayout) {
                isRefresh = false;
                PAGE += 1;
                getBookList();
            }
        });

        mEmptyLayout.setOnEmptyRefreshLisenter(new EmptyLayout.onEmptyRefreshLisenter() {
            @Override
            public void onEmptyRefresh() {
                isRefresh = true;
                PAGE = 1;
                showLoading();
                getBookList();
            }
        });
    }

    @Override
    public void OnClickEvents(View v) {
        if (v == btn_cate_all) {
            cateAllClick();
        } else if (v == btn_isend_all) {
            isEndAllClick();
        } else if (v == btn_sort_all) {
            sortAllClick();
        }
    }


    private void setCate() {
        mBookStoreCate = channelData.get(currPos);
        mTempBookStoreCate = channelData.get(currPos);
        btn_cate_all.setText(mBookStoreCate.getSearchMsg().get(0).getData().get(0).getName());
        btn_cate_all.setSelected(true);
        btn_isend_all.setText(mBookStoreCate.getSearchMsg().get(1).getData().get(0).getName());
        btn_isend_all.setSelected(true);
        btn_sort_all.setText(mBookStoreCate.getSearchMsg().get(2).getData().get(0).getName());
        btn_sort_all.setSelected(true);
        type = mTempBookStoreCate.getSearchMsg().get(0).getData().get(0).getId();
        is_end = mTempBookStoreCate.getSearchMsg().get(1).getData().get(0).getId();
        sort = mTempBookStoreCate.getSearchMsg().get(2).getData().get(0).getId();

        mAdapter = new BookStoreCateAdapter(mContext);
        rv_type.setLayoutManager(new GridLayoutManager(mContext, 3));
        rv_type.setAdapter(mAdapter);
        mAdapter.setData(mBookStoreCate.getSearchMsg().get(0).getData());

        mAdapter.setOnItemClickListener(new BookStoreCateAdapter.onItemClickListener() {
            @Override
            public void onItemClick(int position) {
                isRefresh = true;
                showLoading();
                mAdapter.setCurPosisiton(position);
                btn_cate_all.setSelected(false);
                PAGE = 1;
                type = mBookStoreCate.getSearchMsg().get(0).getData().get(position).getId();
                getBookList();

            }
        });
        mIsEndAdapter = new BookStoreCateAdapter(mContext);
        rv_is_end.setLayoutManager(new GridLayoutManager(mContext, 4));
        rv_is_end.setAdapter(mIsEndAdapter);
        mIsEndAdapter.setData(mBookStoreCate.getSearchMsg().get(1).getData());

        mIsEndAdapter.setOnItemClickListener(new BookStoreCateAdapter.onItemClickListener() {
            @Override
            public void onItemClick(int position) {
                isRefresh = true;
                showLoading();
                mIsEndAdapter.setCurPosisiton(position);
                btn_isend_all.setSelected(false);
                PAGE = 1;
                is_end = mBookStoreCate.getSearchMsg().get(1).getData().get(position).getId();
                getBookList();
            }
        });
        mSortAdater = new BookStoreCateAdapter(mContext);
        rv_sort.setLayoutManager(new GridLayoutManager(mContext, 4));
        rv_sort.setAdapter(mSortAdater);
        mSortAdater.setData(mBookStoreCate.getSearchMsg().get(2).getData());

        mSortAdater.setOnItemClickListener(new BookStoreCateAdapter.onItemClickListener() {
            @Override
            public void onItemClick(int position) {
                isRefresh = true;
                showLoading();
                mSortAdater.setCurPosisiton(position);
                btn_sort_all.setSelected(false);
                PAGE = 1;
                sort = mBookStoreCate.getSearchMsg().get(2).getData().get(position).getId();
                getBookList();
            }
        });

        onBookList();
        showLoading();
        mPresenter.onBookStore();
    }


    private void cateAllClick() {
        showLoading();
        btn_cate_all.setSelected(true);
        mAdapter.setCurPosisiton(-1);
        PAGE = 1;
        type = 0;
        getBookList();
    }

    private void isEndAllClick() {
        showLoading();
        btn_isend_all.setSelected(true);
        mIsEndAdapter.setCurPosisiton(-1);
        PAGE = 1;
        is_end = 0;
        getBookList();
    }

    private void sortAllClick() {
        showLoading();
        btn_sort_all.setSelected(true);
        mSortAdater.setCurPosisiton(-1);
        PAGE = 1;
        sort = 0;
        getBookList();
    }


    private void getBookList() {
        mPresenter.onBookStore();
    }

    private void onBookList() {
        mBookAdapter = new BookStoreListAdapter(mContext);
        rv_store.setLayoutManager(new LinearLayoutManager(mContext));
      //  mBookStoreAdapter = new BookStoreAdapter(R.layout.book_store_list_item, mBooks);
        // rv_store.addItemDecoration(new HBookItemDecoration());
        rv_store.setAdapter(mBookAdapter);
        //mBookStoreAdapter.addHeaderView(headView);


    }

    @Override
    public BookStoreRequest getBookStoreRequest() {
        BookStoreRequest request = new BookStoreRequest();
        request.setCaty_id(channelData.get(currPos).getId());
        request.setIs_end(is_end);
        request.setSort(sort);
        request.setType(type);
        request.setLimit(LIMIT);
        request.setPage(PAGE);
        return request;
    }

    @Override
    public void setCategroy(List<BookStoreCate> mBookStoreCates) {

    }

    @Override
    public void setBookList(BookStoreList bookStoreList) {
        hideLoading();
        if (isRefresh) {
            if (bookStoreList.getLists().size() > 0) {
                mEmptyLayout.setErrorType(EmptyLayout.HIDE_LAYOUT, -1);
                // mBookStoreAdapter.replaceData(bookStoreList.getLists());
            } else {
                mEmptyLayout.setErrorType(EmptyLayout.NO_DATA, EmptyLayout.NO_BOOK);
            }
            refresh_view.finishRefresh();
        } else {
            if (!bookStoreList.isIs_more()) {
                ToastUtils.showLong(mContext, "没有更多数据了");
            }
            // mBookStoreAdapter.addData(bookStoreList.getLists());
            refresh_view.finishLoadmore();
        }
        mBookAdapter.setDatas(bookStoreList.getLists(), isRefresh);
    }


    @Override
    public void showLoading() {
        loading.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoading() {
        loading.setVisibility(View.GONE);
    }

    @Override
    public void showErrorTip(int code, String msg) {

    }
}
