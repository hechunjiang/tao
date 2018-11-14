package com.application.sven.huinews.main.preemption.fragment;

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
import com.application.sven.huinews.entity.AdsList;
import com.application.sven.huinews.entity.MovieHistory;
import com.application.sven.huinews.entity.request.MovieListRequest;
import com.application.sven.huinews.entity.request.MovieWatchHistoryRequest;
import com.application.sven.huinews.entity.response.MovieContinuedResponse;
import com.application.sven.huinews.entity.response.MovieListResponse;
import com.application.sven.huinews.entity.response.VideoChannelResponse;
import com.application.sven.huinews.main.home.AdModel;
import com.application.sven.huinews.main.preemption.adapter.PreeAdapter;
import com.application.sven.huinews.main.preemption.contract.PreeTabContract;
import com.application.sven.huinews.main.preemption.model.PreeTabModel;
import com.application.sven.huinews.main.preemption.presenter.PreeTabPresenter;
import com.application.sven.huinews.utils.LogUtil;
import com.application.sven.huinews.utils.ToastUtils;
import com.application.sven.huinews.utils.ads.AdsConfig;
import com.application.sven.huinews.utils.umeng.UMengUtils;
import com.application.sven.huinews.view.EmptyLayout;
import com.application.sven.huinews.view.RefreshLayout;
import com.qq.e.ads.nativ.NativeExpressADView;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.umeng.analytics.MobclickAgent;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sfy. on 2018/5/8 0008.
 */

public class PreeTabFragment extends BaseFragment<PreeTabPresenter, PreeTabModel> implements PreeTabContract.View {
    private RefreshLayout refresh_view;
    private RecyclerView rv;
    private EmptyLayout mEmptyLayout;
    private PreeAdapter mPreeAdapter;
    private VideoChannelResponse.ChannelData.MBean channelData;
    private List<MovieListResponse.MovieData> movieList;
    private AdsConfig mAdsConfig;

    private boolean isViewCreated;  //Fragment的View加载完毕的标记
    private boolean isUIVisible;    //Fragment对用户可见的标记


    public static PreeTabFragment getInstance(VideoChannelResponse.ChannelData.MBean channelData) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(Constant.PREE_TAB_PAGE_INDEX, channelData);
        PreeTabFragment fragment = new PreeTabFragment();
        fragment.setArguments(bundle);
        return fragment;
    }


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
            if (mPreeAdapter != null) {
                mPreeAdapter.onStartHandler();
            }

        } else {
            if (mPreeAdapter != null) {
                mPreeAdapter.onDestoryHandler();
            }
            isUIVisible = false;
        }
    }


    private void lazyLoad() {
        //这里进行双重标记判断,是因为setUserVisibleHint会多次回调,并且会在onCreateView执行前回调,必须确保onCreateView加载完毕且页面可见,才加载数据
        if (isViewCreated && isUIVisible) {
            mAdsConfig = new AdsConfig(mContext);
            //  mAdsConfig.tencentNativeAD(5, AdsConfig.TENCENT_AD_BIG_IMG);

            mPresenter.onMovieList();

            //数据加载完毕,恢复标记,防止重复加载
            isViewCreated = false;
            isUIVisible = false;
        }
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_list;
    }

    @Override
    protected void initView(View v) {
        rv = v.findViewById(R.id.rv);
        refresh_view = v.findViewById(R.id.refresh_view);
        mEmptyLayout = v.findViewById(R.id.mEmptyLayout);
        refresh_view.setEnableLoadmore(false);
    }

    @Override
    public void initEvents() {
        refresh_view.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(com.scwang.smartrefresh.layout.api.RefreshLayout refreshlayout) {
                mPresenter.onMovieList();
            }
        });
        mEmptyLayout.setOnEmptyRefreshLisenter(new EmptyLayout.onEmptyRefreshLisenter() {
            @Override
            public void onEmptyRefresh() {
                mPresenter.onMovieList();
            }
        });
    }

    @Override
    public void OnClickEvents(View v) {

    }

    @Override
    public void initObject() {
        mEmptyLayout.setErrorType(EmptyLayout.LOADING, -1);
        channelData = (VideoChannelResponse.ChannelData.MBean) getArguments().getSerializable(Constant.PREE_TAB_PAGE_INDEX);
        MobclickAgent.onEvent(mContext, UMengUtils.PREE_TAB_INDEX_ + channelData.getId());
        MobclickAgent.onEvent(mContext, UMengUtils.PREE_TAB_INDEX_ + channelData.getId(), UMengUtils.PREE_TAB_INDEX_ + channelData.getId());
        setMVP();
        initRecyler();


    }


    private void initRecyler() {
        rv.setLayoutManager(new LinearLayoutManager(mContext));
        mPreeAdapter = new PreeAdapter(mContext);
        rv.setAdapter(mPreeAdapter);
    }


    @Override
    public MovieListRequest movieListRequest() {
        MovieListRequest request = new MovieListRequest();
        request.setTypeId(channelData.getId());
        return request;
    }

    @Override
    public void setMovieList(List<MovieListResponse.MovieData> movieList) {
        this.movieList = movieList;
        refresh_view.finishRefresh();
        //mPreeAdapter.setData(movieList, true);
        mPresenter.onMovieHistory();
    }

    @Override
    public MovieWatchHistoryRequest getMovieWatchHistoryRequest() {
        MovieWatchHistoryRequest request = new MovieWatchHistoryRequest();
        request.setType(Constant.MOVIE_HISTORY_LAST);
        return request;
    }

    @Override
    public void setMovieHistory(MovieContinuedResponse.Data response) {
        if (response != null) {
            MovieListResponse.MovieData movieData = new MovieListResponse.MovieData();
            movieData.setLayout(Constant.TYPE_CONTINUE);
            List<MovieListResponse.MovieData.ListsBean> listsBeans = new ArrayList<>();
            MovieListResponse.MovieData.ListsBean listsBean = new MovieListResponse.MovieData.ListsBean();
            listsBean.setId(response.getId());
            listsBean.setM_name(response.getM_name());
            listsBean.setM_img(response.getM_img());
            listsBean.setPlay_id(response.getPlay_id());
            listsBean.setPlay_time(response.getPlay_time());
            listsBeans.add(listsBean);
            movieData.setLists(listsBeans);
            this.movieList.add(1, movieData);
        }
        mEmptyLayout.setErrorType(EmptyLayout.HIDE_LAYOUT, -1);
        mPreeAdapter.setList(movieList);
        mPresenter.onPreeAds(PAGE);
    }

    @Override
    public void setAdsLit(List<AdsList> ads) {
        this.mAds = ads;

        mPresenter.onTencentAds(mAdsConfig, ads.size());

    }

    private List<NativeExpressADView> mAdViewList;
    private List<AdModel> adModels = new ArrayList<>();
    private List<AdsList> mAds;

    @Override
    public void setTencentAds(List<NativeExpressADView> list) {
        this.mAdViewList = list;
        mEmptyLayout.setErrorType(EmptyLayout.HIDE_LAYOUT, -1);
        if (mAdViewList.size() != mAds.size()) {
            return;
        }
        for (int i = 0; i < mAds.size(); i++) {
            if (mAds.get(i).getIn_position() > movieList.size()) {
                break;
            }
            if (i > mAdViewList.size()) {
                break;
            }
            if (mAds.get(i).getAd_type().equals(AdsConfig.AD_TYPE_TENCENT)) {
                mPreeAdapter.datas().add(mAds.get(i).getIn_position(), mAdViewList.get(i));
            }
        }
        //  mPreeAdapter.notifyDataSetChanged();
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
