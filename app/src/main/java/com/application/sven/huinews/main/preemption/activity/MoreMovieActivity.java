package com.application.sven.huinews.main.preemption.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.application.sven.huinews.R;
import com.application.sven.huinews.base.BaseActivity;
import com.application.sven.huinews.config.Constant;
import com.application.sven.huinews.config.http.DataCallBack;
import com.application.sven.huinews.entity.request.MovieMoreListRequest;
import com.application.sven.huinews.entity.response.MoreMovieListResponse;
import com.application.sven.huinews.entity.response.MovieListResponse;
import com.application.sven.huinews.main.home.adapter.MoreMovieAdapter;
import com.application.sven.huinews.main.preemption.contract.MoreMovieContract;
import com.application.sven.huinews.main.preemption.model.MoreMovieModel;
import com.application.sven.huinews.main.preemption.presenter.MoreMoviePresenter;
import com.application.sven.huinews.main.search.activity.SearchActivity;
import com.application.sven.huinews.utils.ToastUtils;
import com.application.sven.huinews.utils.itemDecoration.DividerItemDecoration;
import com.application.sven.huinews.view.EmptyLayout;
import com.application.sven.huinews.view.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

/**
 * Created by sfy. on 2018/5/9 0009.
 * 更多
 */

public class MoreMovieActivity extends BaseActivity<MoreMoviePresenter, MoreMovieModel> implements MoreMovieContract.View {
    private RefreshLayout refresh_view;
    private RecyclerView rv;
    private MoreMovieAdapter mMovieAdapter;
    private ImageButton btn_back;
    private TextView et_input;
    private int buId;

    public static void toThis(Context mContext, int buId) {
        Intent intent = new Intent(mContext, MoreMovieActivity.class);
        Bundle bundle = new Bundle();
        bundle.putInt(Constant.BUNDLE_TO_MORE_MOVIE_BID, buId);
        intent.putExtras(bundle);
        mContext.startActivity(intent);
    }

    @Override
    public int getLayoutId() {
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            buId = bundle.getInt(Constant.BUNDLE_TO_MORE_MOVIE_BID);
        }
        return R.layout.activity_more_movie;
    }

    @Override
    public void initView() {
        mEmptyLayout = findViewById(R.id.mEmptyLayout);
        rv = findViewById(R.id.rv);
        refresh_view = findViewById(R.id.refresh_view);
        btn_back = findViewById(R.id.btn_back);
        et_input = findViewById(R.id.et_input);
        refresh_view = findViewById(R.id.refresh_view);
        refresh_view.setEnableRefresh(true);
    }

    @Override
    public void initEvents() {
        et_input.setOnClickListener(this);
        btn_back.setOnClickListener(this);

        refresh_view.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull com.scwang.smartrefresh.layout.api.RefreshLayout refreshLayout) {
                isRefresh = true;
                PAGE = 1;
                mPresenter.onMoreMovie();
            }
        });
        refresh_view.setOnLoadmoreListener(new OnLoadmoreListener() {
            @Override
            public void onLoadmore(com.scwang.smartrefresh.layout.api.RefreshLayout refreshlayout) {
                isRefresh = false;
                PAGE += 1;
                mPresenter.onMoreMovie();
            }
        });
        mEmptyLayout.setOnEmptyRefreshLisenter(new EmptyLayout.onEmptyRefreshLisenter() {
            @Override
            public void onEmptyRefresh() {
                isRefresh = true;
                PAGE = 1;
                mPresenter.onMoreMovie();
            }
        });
    }

    @Override
    public void onClickEvent(View v) {
        if (v == btn_back) {
            finish();
        } else if (v == et_input) {
            SearchActivity.toThis(mContext);
        }
    }

    @Override
    public void initObject() {
        mEmptyLayout.setErrorType(EmptyLayout.LOADING, -1);
        setMVP();
        setMoreInfo();
        mPresenter.onMoreMovie();
    }

    private void setMoreInfo() {
        mMovieAdapter = new MoreMovieAdapter(mContext);
        rv.setLayoutManager(new GridLayoutManager(mContext, 3));
        rv.addItemDecoration(new DividerItemDecoration(mContext));
        rv.setAdapter(mMovieAdapter);
    }

    @Override
    public MovieMoreListRequest getMovieMoreListRequest() {
        MovieMoreListRequest request = new MovieMoreListRequest();
        request.setLimit(LIMIT);
        request.setPage(PAGE);
        request.setBuId(buId);
        return request;
    }

    @Override
    public void setMoreMovie(MoreMovieListResponse.MoreMovieList moreMovieList) {
        mEmptyLayout.setErrorType(EmptyLayout.HIDE_LAYOUT, -1);
        if (isRefresh) {
            refresh_view.finishRefresh();
            refresh_view.setEnableLoadmore(true);
        } else {
            refresh_view.finishLoadmore();
        }

        mMovieAdapter.setData(moreMovieList.getLists(), isRefresh);

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
            if (!isRefresh) {
                ToastUtils.showLong(mContext, msg);
                refresh_view.finishLoadmore();
                return;
            }
            mEmptyLayout.setErrorType(EmptyLayout.LOAD_ERROR, EmptyLayout.LOAD_ERROR);
        } else if (code == DataCallBack.INTERNAL_SERVER_ERROR) {
            if (!isRefresh) {
                ToastUtils.showLong(mContext, msg);
                refresh_view.finishLoadmore();
                return;
            }
            mEmptyLayout.setErrorType(EmptyLayout.LOAD_ERROR, EmptyLayout.LOAD_ERROR);
        } else if (code == DataCallBack.NETWORK_ERROR) {
            if (!isRefresh) {
                ToastUtils.showLong(mContext, msg);
                refresh_view.finishLoadmore();
                return;
            }
            mEmptyLayout.setErrorType(EmptyLayout.NETWORK_ERROR, EmptyLayout.NETWORK_ERROR);
        }
    }
}
