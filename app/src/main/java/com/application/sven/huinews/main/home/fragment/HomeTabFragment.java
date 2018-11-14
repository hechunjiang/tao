package com.application.sven.huinews.main.home.fragment;

import android.content.Context;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.application.sven.huinews.R;
import com.application.sven.huinews.base.BaseFragment;
import com.application.sven.huinews.config.ChannelConfig;
import com.application.sven.huinews.config.Constant;
import com.application.sven.huinews.config.api.Api;
import com.application.sven.huinews.config.http.DataCallBack;
import com.application.sven.huinews.db.UserSpCache;
import com.application.sven.huinews.entity.AdsList;
import com.application.sven.huinews.entity.Comment;
import com.application.sven.huinews.entity.events.VideoLikeEvent;
import com.application.sven.huinews.entity.request.AdCommentRequest;
import com.application.sven.huinews.entity.request.ShareVisitRequest;
import com.application.sven.huinews.entity.request.VideoCollectionCancelRequest;
import com.application.sven.huinews.entity.request.VideoCollectionRequest;
import com.application.sven.huinews.entity.request.VideoCommentRequest;
import com.application.sven.huinews.entity.request.VideoLikeRequest;
import com.application.sven.huinews.entity.request.VideoListRequest;
import com.application.sven.huinews.entity.request.VideoReportRequest;
import com.application.sven.huinews.entity.request.VideoShareUrlRequest;
import com.application.sven.huinews.entity.request.VideoTaskRequest;
import com.application.sven.huinews.entity.response.VideoChannelResponse;
import com.application.sven.huinews.entity.response.VideoListResponse;
import com.application.sven.huinews.entity.response.VideoTaskResponse;
import com.application.sven.huinews.main.home.AdModel;
import com.application.sven.huinews.main.home.adapter.HomeAdapter;
import com.application.sven.huinews.main.home.contract.HomeTabContract;
import com.application.sven.huinews.main.home.model.HomeTabModel;
import com.application.sven.huinews.main.home.presenter.HomeTabPresenter;
import com.application.sven.huinews.main.video.adapter.VideoCommentAdapter;
import com.application.sven.huinews.main.video.adapter.VideoHorizontalAdapter;
import com.application.sven.huinews.main.web.activity.WebActivity;
import com.application.sven.huinews.mob.share.MobShare;
import com.application.sven.huinews.mob.share.ShareCallBack;
import com.application.sven.huinews.utils.CommonUtils;
import com.application.sven.huinews.utils.KeyBoradUtils;
import com.application.sven.huinews.utils.LogUtil;
import com.application.sven.huinews.utils.ToastUtils;
import com.application.sven.huinews.utils.ads.AdsConfig;
import com.application.sven.huinews.utils.itemDecoration.SmallVideoGridItem;
import com.application.sven.huinews.utils.itemDecoration.StaggerItemDecoration;
import com.application.sven.huinews.utils.umeng.UMengUtils;
import com.application.sven.huinews.view.EdittextUtlis;
import com.application.sven.huinews.view.EmptyLayout;
import com.application.sven.huinews.view.MyVideoProgress;
import com.application.sven.huinews.view.dialog.GoldRuleDialog;
import com.application.sven.huinews.view.dialog.ShareDialog;
import com.dueeeke.videoplayer.player.IjkVideoView;
import com.dueeeke.videoplayer.player.VideoViewManager;
import com.qq.e.ads.nativ.NativeExpressADView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.umeng.analytics.MobclickAgent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by sfy. on 2018/5/8 0008.
 */

public class HomeTabFragment extends BaseFragment<HomeTabPresenter, HomeTabModel> implements HomeTabContract.View {
    private RefreshLayout refreshView;
    private RecyclerView homeRv;


    private EmptyLayout mEmptyLayout;
    private HomeAdapter mAdapter;

    private VideoChannelResponse.ChannelData.VBean channelData;
    private VideoListResponse.VideoList.ListBean mShareData, mGoldData;
    private SmallVideoGridItem mSmallVideoGridItem;

    private int indexPos;
    private String mLayoutType;
    private AdsConfig mAdsConfig;
    private FrameLayout ad_fl;
    private ShareDialog mShareDialog;
    private TextView tvLike;


    private int videoCount, mCount; //播放次数
    private int mCurrentProgress; //停止时 的进度
    private long mDuration;

    private GoldRuleDialog mGoldRuleDialog; //金币规则dialog

    private boolean isViewCreated;  //Fragment的View加载完毕的标记
    private boolean isUIVisible;    //Fragment对用户可见的标记


    public static HomeTabFragment getInstance(VideoChannelResponse.ChannelData.VBean channelData) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(Constant.HOME_TAB_PAGE_INDEX, channelData);
        HomeTabFragment fragment = new HomeTabFragment();
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

        } else {
            isUIVisible = false;
        }
    }


    private void lazyLoad() {
        //这里进行双重标记判断,是因为setUserVisibleHint会多次回调,并且会在onCreateView执行前回调,必须确保onCreateView加载完毕且页面可见,才加载数据
        if (isViewCreated && isUIVisible) {
            mAdsConfig = new AdsConfig(mContext);
            // mAdsConfig.tencentNativeAD(5, AdsConfig.TENCENT_AD_ID);
            //  mAdsConfig.tencentNativeRightImgAD(1, AdsConfig.TENCENT_AD_RIGHT_IMG);
            //   mAdsConfig.kuaiyanAd(ad_fl);

            mPresenter.onVideoWatchCount();
            mPresenter.onVideoList();

            //数据加载完毕,恢复标记,防止重复加载
            isViewCreated = false;
            isUIVisible = false;
        }
    }

    @Override
    protected int getLayoutResource() {
        EventBus.getDefault().register(this);
        return R.layout.fragment_list;
    }


    @Override
    protected void initView(View v) {
        mEmptyLayout = v.findViewById(R.id.mEmptyLayout);
        homeRv = v.findViewById(R.id.rv);
        refreshView = v.findViewById(R.id.refresh_view);
        refreshView.setEnableRefresh(true);
        ad_fl = v.findViewById(R.id.ad_fl);
       /* video_progress_layout = v.findViewById(R.id.video_progress_layout);
        video_progress = v.findViewById(R.id.video_progress);
        tv_gold_count = v.findViewById(R.id.tv_gold_count);

        mDuration = UserSpCache.getInstance(mContext).getInt(UserSpCache.WATCH_TIME);
        if (mDuration < 1) {
            mDuration = 20;
        }
        mDuration = mDuration * 1000;
        video_progress.setDuration(mDuration)*/
        ;
    }


    @Override
    public void initObject() {
        setMVP();
        channelData = (VideoChannelResponse.ChannelData.VBean) getArguments().getSerializable(Constant.HOME_TAB_PAGE_INDEX);
        MobclickAgent.onEvent(mContext, UMengUtils.HOME_TAB_INDEX_ + channelData.getId());
        MobclickAgent.onEvent(mContext, UMengUtils.HOME_TAB_INDEX_ + channelData.getId(), UMengUtils.HOME_TAB_INDEX_ + channelData.getId());

        initRecycler();

        mEmptyLayout.setErrorType(EmptyLayout.LOADING, -1);
      /*  mAdsConfig = new AdsConfig(mContext);
        // mAdsConfig.tencentNativeAD(5, AdsConfig.TENCENT_AD_ID);
        //  mAdsConfig.tencentNativeRightImgAD(1, AdsConfig.TENCENT_AD_RIGHT_IMG);
        //   mAdsConfig.kuaiyanAd(ad_fl);

        mPresenter.onVideoList();*/

    }

    @Override
    public void initEvents() {
        //  video_progress_layout.setOnClickListener(this);
        refreshView.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                VideoViewManager.instance().releaseVideoPlayer();
                isRefresh = true;
                PAGE = 1;
                mPresenter.onVideoList();
            }
        });
        refreshView.setOnLoadmoreListener(new OnLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                VideoViewManager.instance().releaseVideoPlayer();
                isRefresh = false;
                PAGE += 1;
                mPresenter.onVideoList();
            }
        });


        mEmptyLayout.setOnEmptyRefreshLisenter(new EmptyLayout.onEmptyRefreshLisenter() {
            @Override
            public void onEmptyRefresh() {
                isRefresh = true;
                PAGE = 1;
                mPresenter.onVideoList();
            }
        });

        homeRv.addOnChildAttachStateChangeListener(new RecyclerView.OnChildAttachStateChangeListener() {
            @Override
            public void onChildViewAttachedToWindow(View view) {

            }

            @Override
            public void onChildViewDetachedFromWindow(View view) {
                IjkVideoView ijkVideoView = view.findViewById(R.id.video_player);
                if (ijkVideoView != null && !ijkVideoView.isFullScreen()) {
                    if (ijkVideoView.isPlaying()) {
                        ijkVideoView.stopPlayback();
                        LogUtil.showLog("msg---onChildViewDetachedFromWindow");
                        ((HomeFragment) getParentFragment()).onVideoPaused();
                    }
                }
            }
        });
    }

    @Override
    public void OnClickEvents(View v) {
        /*if (v == video_progress_layout) {
            mGoldRuleDialog.show();
        }*/
    }

    private void initRecycler() {
        mAdapter = new HomeAdapter(mContext, getActivity());
        if (mSmallVideoGridItem == null) {
            mSmallVideoGridItem = new SmallVideoGridItem(mContext);
        }
        if (channelData.getTemp_type().equals(Constant.TYPE_WATERFALL)) {
            mLayoutType = Constant.TYPE_WATERFALL;
            setGridLayoutManager();
        } else {
            mLayoutType = Constant.TYPE_TRANSVERSE;
            setLinearLayoutManager();
        }
        homeRv.setAdapter(mAdapter);

        setAdapterLisenter();

    }


    private void setAdapterLisenter() {
        mAdapter.setOnItemFunClickLisenter(new HomeAdapter.onItemFunClickLisenter() {
            @Override
            public void onLike(VideoListResponse.VideoList.ListBean mData) {
                VideoLikeRequest request = new VideoLikeRequest();
                request.setId(mData.getId());
                request.setType(mData.isIs_up() ? Constant.UN_LIKE_TYPE : Constant.LIKE_TYPE);
                mPresenter.onVideoLike(request);
            }

            @Override
            public void onShare(VideoListResponse.VideoList.ListBean mData, TextView tv_like) {
                tvLike = tv_like;
                mShareData = mData;
                showShareDialog();
            }


            @Override
            public void onComment(VideoListResponse.VideoList.ListBean mData, TextView tv_msg) {
                if (onComentViewLisenter != null) {
                    onComentViewLisenter.showCommentView(mData, tv_msg);
                }
            }

            @Override
            public void onCollection(VideoListResponse.VideoList.ListBean mData) {

            }
        });


        mAdapter.setOnVideoPlayStatusLisenter(new VideoHorizontalAdapter.onVideoPlayStatusLisenter() {
            @Override
            public void onVideoComplete(VideoListResponse.VideoList.ListBean mData) {
                ((HomeFragment) getParentFragment()).onVideoComplete();
            }

            @Override
            public void onVideoPlaying(VideoListResponse.VideoList.ListBean mData) {
                if (!mPresenter.isCanGetCoinByVideo(mData)) {
                    ((HomeFragment) getParentFragment()).hideGoldProgress();
                    return;
                }

                ((HomeFragment) getParentFragment()).setVideoPlay(mData, videoCount);
                mPresenter.onVideoVisit(mData.getId());
                mGoldData = mData;

            }

            @Override
            public void onVideoPaused() {
                ((HomeFragment) getParentFragment()).onVideoPaused();
            }

            @Override
            public void onVideoError() {

            }
        });

    }

    public void endGoldProgress() {
        //进度完成后增加金币
        //视频播放完成
        if (!mPresenter.isCanGetCoinByVideo(mGoldData)) {
            return;
        }
        if (mGoldData.getIs_gold() == 1) {
            mPresenter.onVideoGoldTask(mGoldData, VideoTaskRequest.TASK_ID_READ_NEWS);
        } else if (mGoldData.getIs_redpack() == 1) {
            mPresenter.onVideoGoldTask(mGoldData, VideoTaskRequest.TASK_ID_READ_RED_NEWS);
        }
    }

    @Override
    public VideoListRequest videoListRequest() {
        VideoListRequest request = new VideoListRequest();
        request.setPage(PAGE);
        request.setLimit(LIMIT);
        request.setTypeId(channelData.getId());
        return request;
    }

    @Override
    public VideoCollectionRequest getVideoCollectionRequest(int videoId) {
        VideoCollectionRequest request = new VideoCollectionRequest();
        request.setrId(videoId);
        request.setType(Constant.CHANNEL_TYPE_VIDEO);
        return request;
    }

    @Override
    public VideoCollectionCancelRequest getVideoCollectionCancelRequest(int videoId) {
        VideoCollectionCancelRequest request = new VideoCollectionCancelRequest();
        request.setType(Constant.CHANNEL_TYPE_VIDEO);
        request.setrId(videoId + "");
        return request;
    }

    @Override
    public void setVideoCollectionOk(boolean isCollection) {

    }

    int dataSize = 0;

    @Override
    public void setVideoList(VideoListResponse.VideoList videoList) {
        mEmptyLayout.setErrorType(EmptyLayout.HIDE_LAYOUT, -1);
        if (isRefresh) {
            refreshView.finishRefresh();
            refreshView.setEnableLoadmore(true);
        } else {
            refreshView.finishLoadmore();
        }

        mAdapter.setList(channelData.getId(), videoList.getList(), isRefresh, mLayoutType);

        // if (mLayoutType != Constant.TYPE_WATERFALL) {
        if (!videoList.isIs_has_more()) {
            mEmptyLayout.setErrorType(EmptyLayout.NO_DATA, EmptyLayout.NO_DATA);
        } else {
            mPresenter.onHomeAds(PAGE);
        }

        //}

    }

    private List<NativeExpressADView> mAdViewList = new ArrayList<>();
    private List<AdModel> adModels = new ArrayList<>();
    private List<AdsList> mAds;

    @Override
    public void setAdsLit(List<AdsList> ads) {
        this.mAds = ads;
        mPresenter.onTencentAds(mAdsConfig, ads.size());
    }

    Handler handler = new Handler();

    @Override
    public void setTencentAds(List<NativeExpressADView> list) {

        for (int i = 0; i < list.size(); i++) {
            AdModel adModel = new AdModel();
            adModel.setType(AdsConfig.AD_TYPE_TENCENT_SINGLE);
            adModel.setmViews(list.get(i));
            adModels.add(i, adModel);
        }


        int pos = mAdapter.getDataList();

        if (adModels.size() != mAds.size()) {
            return;
        }
        for (int i = 0; i < mAds.size(); i++) {
            int position = 0;
            if (isRefresh) {
                position = mAds.get(i).getIn_position() + i;
            } else {
                position = (pos - LIMIT) + mAds.get(i).getIn_position() + i;
            }

            if (i > adModels.size()) {
                break;
            }
            if (mAds.get(i).getAd_type().equals(AdsConfig.AD_TYPE_TENCENT)) {
                if (adModels.get(i).getType().equals(AdsConfig.AD_TYPE_TENCENT_SINGLE)) {
                    mAdapter.datas().add(position, adModels.get(i));
                }
                /*else if (adModels.get(i).getType().equals(AdsConfig.AD_TYPE_TENCENT_RIGHT)) {
                    mAdapter.datas().add(mAds.get(i).getIn_position() + i, adModels.get(i));
                }*/
            }
        }
    }

    @Override
    public void setTencentRightImgAds(List<NativeExpressADView> list) {
        //左文右图
        for (int i = 0; i < list.size(); i++) {
            AdModel adModel = new AdModel();
            adModel.setmViews(list.get(i));
            adModel.setType(AdsConfig.AD_TYPE_TENCENT_RIGHT);
            adModels.addAll(adModels);
        }

    }

    @Override
    public void setVideoShareUrl(String url, final int type) {
        mShareData.setVideo_url(url);
        String shareJson = mPresenter.getShareJson(mShareData, type);
        MobShare mMobShare = new MobShare(mContext);
        mMobShare.shareToOne(mContext, shareJson, new ShareCallBack() {
            @Override
            public void onResponse(String response) {
                mPresenter.onShareVisit(response, ShareVisitRequest.VIDEO_LIST, type);
            }
        });
    }

    @Override
    public void setGoldView(VideoListResponse.VideoList.ListBean mData, VideoTaskResponse.Data data, int type) {
        //将视频加入数据库
        mPresenter.addVideoToDB(mData);
        videoCount--;
        if (videoCount > 0) {
            ToastUtils.showLong(mContext, "您还可以观看" + videoCount + "次金币视频");
        }

        if (videoCount < data.getmCount()) {
            ((HomeFragment) getParentFragment()).hideGoldProgress();
            return;
        }


        mPresenter.goldCountAnim(mContext, ((HomeFragment) getParentFragment()).getTv_gold_count(), ((HomeFragment) getParentFragment()).getVideo_progress_layout(), data.getCount());
        mData.setSaveRedVideo(true);
        // EventBus.getDefault().post(new VideoLikeEvent(mData));
    }

    @Override
    public void setVideoCount(int count) {
        videoCount = count;
        LogUtil.showLog("msg====setVideoCount:" + count);
    }

    @Override
    public void setInGoldError() {
        ((HomeFragment) getParentFragment()).hideGoldProgress();
        //   video_progress_layout.setVisibility(View.GONE);
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
                refreshView.finishLoadmore();
                return;
            }
            mEmptyLayout.setErrorType(EmptyLayout.LOAD_ERROR, EmptyLayout.LOAD_ERROR);
        } else if (code == DataCallBack.INTERNAL_SERVER_ERROR) {
            if (!isRefresh) {
                ToastUtils.showLong(mContext, msg);
                refreshView.finishLoadmore();
                return;
            }
            mEmptyLayout.setErrorType(EmptyLayout.LOAD_ERROR, EmptyLayout.LOAD_ERROR);
        } else if (code == DataCallBack.NETWORK_ERROR) {
            if (!isRefresh) {
                ToastUtils.showLong(mContext, msg);
                refreshView.finishLoadmore();
                return;
            }
            mEmptyLayout.setErrorType(EmptyLayout.NETWORK_ERROR, EmptyLayout.NETWORK_ERROR);
        }
    }

    private void setGridLayoutManager() {
        homeRv.removeItemDecoration(mSmallVideoGridItem);
        homeRv.addItemDecoration(mSmallVideoGridItem);
        final StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        staggeredGridLayoutManager.setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_NONE);
        homeRv.setLayoutManager(staggeredGridLayoutManager);
        homeRv.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                staggeredGridLayoutManager.invalidateSpanAssignments();
            }
        });
    }

    private void setLinearLayoutManager() {
        homeRv.setLayoutManager(new LinearLayoutManager(mContext));
    }

    private void showShareDialog() {
        if (mShareDialog == null) {
            mShareDialog = new ShareDialog(mContext);
        }
        mShareDialog.setVideoShare(mShareData.isIs_up(), mShareData.isIs_collected());

        mShareDialog.setOnShareListener(new ShareDialog.OnShareListener() {
            @Override
            public void onShare(int type) {
                if (type != Constant.SHARE_TYPE_REPORT) {
                    VideoShareUrlRequest request = new VideoShareUrlRequest();
                    request.setVideoId(mShareData.getId() + "");
                    mPresenter.onVideoShare(request, type);
                } else {
                    VideoReportRequest request = new VideoReportRequest();
                    request.setVideoId(mShareData.getId() + "");
                    mPresenter.onVideoReport(request);
                }
            }
        });

        mShareDialog.OnDialogLisenter(new ShareDialog.OnDialogLisenter() {
            @Override
            public void onCollection(boolean isCollection) {
                if (isCollection) {
                    mPresenter.onCollection(getVideoCollectionRequest(mShareData.getId()));
                } else {
                    mPresenter.onCancelCollection(getVideoCollectionCancelRequest(mShareData.getId()));
                }
                mShareData.setIs_collected(isCollection);
                mAdapter.setOneChange(mShareData);
                mAdapter.refreshItem(true);
                //EventBus.getDefault().post(new VideoLikeEvent(mShareData));
            }

            @Override
            public void onLike(boolean isLiked) {
                VideoLikeRequest request = new VideoLikeRequest();
                request.setId(mShareData.getId());
                request.setType(isLiked ? Constant.UN_LIKE_TYPE : Constant.LIKE_TYPE);
                mPresenter.onVideoLike(request);

                mShareData.setIs_up(isLiked);

                Drawable mLikeCheck = mContext.getResources().getDrawable(R.mipmap.comment_liked);
                mLikeCheck.setBounds(0, 0, mLikeCheck.getMinimumWidth(), mLikeCheck.getMinimumHeight());
                Drawable mLikeNormal = mContext.getResources().getDrawable(R.mipmap.comment_like);
                mLikeNormal.setBounds(0, 0, mLikeNormal.getMinimumWidth(), mLikeNormal.getMinimumHeight());
                mShareData.setLike_count(mShareData.isIs_up() ? mShareData.getLike_count() + 1 : mShareData.getLike_count() - 1);
                tvLike.setCompoundDrawables(mShareData.isIs_up() ? mLikeCheck : mLikeNormal, null, null, null);
                tvLike.setTextColor(mShareData.isIs_up() ? mContext.getResources().getColor(R.color.c_eb3e44) : mContext.getResources().getColor(R.color.color_line));
                tvLike.setText(CommonUtils.getLikeCount(mShareData.getLike_count()));
                mAdapter.setOneChange(mShareData);
            }
        });
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onRefeshHome(VideoLikeEvent event) {
        mAdapter.notifyOneChange(event.getmData());
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onCancelFollowEvent(String str) {
        if (str.equals(Constant.CANCEL_FOLLOW)) {
            mPresenter.onVideoList();
        } else if (str.equals("progressEnd")) {
            ToastUtils.show(mContext, "动画执行完毕", 1);
            endGoldProgress();
        }
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
        VideoViewManager.instance().releaseVideoPlayer();
        // 使用完了每一个NativeExpressADView之后都要释放掉资源。
        if (mAdViewList != null) {
            for (NativeExpressADView view : mAdViewList) {
                view.destroy();
            }
        }
    }


    public interface OnComentViewLisenter {
        void showCommentView(VideoListResponse.VideoList.ListBean mData, TextView tvmsg);

        void hideCommentView();
    }


    OnComentViewLisenter onComentViewLisenter;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnComentViewLisenter) {
            onComentViewLisenter = (OnComentViewLisenter) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        VideoViewManager.instance().releaseVideoPlayer();
    }

    @Override
    public void onPause() {
        super.onPause();
        VideoViewManager.instance().releaseVideoPlayer();
    }


}
