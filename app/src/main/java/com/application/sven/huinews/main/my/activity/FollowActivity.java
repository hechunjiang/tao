package com.application.sven.huinews.main.my.activity;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.application.sven.huinews.R;
import com.application.sven.huinews.base.BaseActivity;
import com.application.sven.huinews.config.Constant;
import com.application.sven.huinews.config.http.DataCallBack;
import com.application.sven.huinews.entity.FollowList;
import com.application.sven.huinews.entity.request.FollowListRequest;
import com.application.sven.huinews.entity.response.FollowListResponse;
import com.application.sven.huinews.main.my.adapter.FollowAdapter;
import com.application.sven.huinews.main.my.adapter.OneKeyFollowAdapter;
import com.application.sven.huinews.main.my.contract.FollowContract;
import com.application.sven.huinews.main.my.model.FollowListModel;
import com.application.sven.huinews.main.my.presenter.FollowListPresenter;
import com.application.sven.huinews.utils.LogUtil;
import com.application.sven.huinews.utils.ToastUtils;
import com.application.sven.huinews.view.EmptyLayout;
import com.application.sven.huinews.view.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

/**
 * auther: sunfuyi
 * data: 2018/5/10
 * effect:
 */
public class FollowActivity extends BaseActivity<FollowListPresenter, FollowListModel> implements FollowContract.View {
    private RecyclerView userRv;
    private FollowAdapter mFollowAdapter;
    private RefreshLayout refresh_view;

    public static void toThis(Context mContext) {
        Intent intent = new Intent(mContext, FollowActivity.class);
        mContext.startActivity(intent);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_follow;
    }

    @Override
    public void initView() {
        mTitleBar = findViewById(R.id.titlebar);
        userRv = findViewById(R.id.follow_rv);
        mTitleBar.setTitle(getString(R.string.my_follow));
        mEmptyLayout = findViewById(R.id.mEmptyLayout);
        refresh_view = findViewById(R.id.refresh_view);
    }

    @Override
    public void initEvents() {
        mTitleBar.setBackOnClick(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        refresh_view.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull com.scwang.smartrefresh.layout.api.RefreshLayout refreshLayout) {
                isRefresh = true;
                PAGE = 1;
                mPresenter.onFollowList();
            }
        });
        refresh_view.setOnLoadmoreListener(new OnLoadmoreListener() {
            @Override
            public void onLoadmore(com.scwang.smartrefresh.layout.api.RefreshLayout refreshlayout) {
                isRefresh = false;
                PAGE += 1;
                mPresenter.onFollowList();
            }
        });
        mFollowAdapter.setOnCancelFollowLisenter(new FollowAdapter.OnCancelFollowLisenter() {
            @Override
            public void onCancel(int user_id) {
                mPresenter.onCancelFollowUser(user_id);
            }
        });

        mEmptyLayout.setOnEmptyRefreshLisenter(new EmptyLayout.onEmptyRefreshLisenter() {
            @Override
            public void onEmptyRefresh() {
                isRefresh = true;
                PAGE = 1;
                mPresenter.onFollowList();
            }
        });
    }

    @Override
    public void onClickEvent(View v) {

    }

    @Override
    public void initObject() {
        mEmptyLayout.setErrorType(EmptyLayout.LOADING, -1);
        setMVP();
        setFollowInfo();
        mPresenter.onFollowList();
    }

    private void setFollowInfo() {
        /*mAdapter = new OneKeyFollowAdapter(mContext);
        LinearLayoutManager manager = new LinearLayoutManager(mContext);
        manager.setOrientation(LinearLayoutManager.HORIZONTAL);
        onkeyRv.setLayoutManager(manager);
        onkeyRv.setAdapter(mAdapter);*/


        mFollowAdapter = new FollowAdapter(mContext);
        userRv.setLayoutManager(new LinearLayoutManager(mContext));
        userRv.setAdapter(mFollowAdapter);
    }

    @Override
    public FollowListRequest getFollowListRequest() {
        FollowListRequest request = new FollowListRequest();
        request.setLimit(LIMIT);
        request.setPage(PAGE);
        return request;
    }

    @Override
    public void setFollowData(List<FollowList> mDatas) {
        mEmptyLayout.setErrorType(EmptyLayout.HIDE_LAYOUT, -1);
        if (isRefresh) {
            refresh_view.finishRefresh();
            refresh_view.setEnableLoadmore(true);
        } else {
            refresh_view.finishLoadmore();
        }
        mFollowAdapter.setData(mDatas, isRefresh);
    }

    @Override
    public void setFollowNoData() {
        mEmptyLayout.setErrorType(EmptyLayout.NO_DATA, EmptyLayout.NO_FOLLOW);
    }

    @Override
    public void setCancelFollow() {
        isRefresh = true;
        mPresenter.onFollowList();
        EventBus.getDefault().post(Constant.CANCEL_FOLLOW);
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
