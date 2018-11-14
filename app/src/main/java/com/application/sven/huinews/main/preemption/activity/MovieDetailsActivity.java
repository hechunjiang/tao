package com.application.sven.huinews.main.preemption.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.application.sven.huinews.R;
import com.application.sven.huinews.base.BaseActivity;
import com.application.sven.huinews.config.Constant;
import com.application.sven.huinews.config.LocationUtils;
import com.application.sven.huinews.config.MovieData;
import com.application.sven.huinews.config.PhoneUtils;
import com.application.sven.huinews.config.api.Api;
import com.application.sven.huinews.db.BaseDB;
import com.application.sven.huinews.db.UserSpCache;
import com.application.sven.huinews.entity.AdsList;
import com.application.sven.huinews.entity.request.MovieDetailRequest;
import com.application.sven.huinews.entity.request.MoviePayRequest;
import com.application.sven.huinews.entity.request.MovieResPlayRequest;
import com.application.sven.huinews.entity.request.MovieShareRequest;
import com.application.sven.huinews.entity.request.MovieVisitRequest;
import com.application.sven.huinews.entity.request.ShareVisitRequest;
import com.application.sven.huinews.entity.request.VideoCollectionCancelRequest;
import com.application.sven.huinews.entity.request.VideoCollectionRequest;
import com.application.sven.huinews.entity.request.VideoShareUrlRequest;
import com.application.sven.huinews.entity.response.MovieDetailResponse;
import com.application.sven.huinews.entity.response.MovieShareResponse;
import com.application.sven.huinews.entity.response.MovieSource;
import com.application.sven.huinews.main.login.activity.LoginActivity;
import com.application.sven.huinews.main.my.activity.DownloadActivity;
import com.application.sven.huinews.main.preemption.adapter.MovieCountAdapter;
import com.application.sven.huinews.main.preemption.adapter.MovieCountDownloadAdapter;
import com.application.sven.huinews.main.preemption.adapter.RecommedAdapter;
import com.application.sven.huinews.main.preemption.contract.MovieDetailContract;
import com.application.sven.huinews.main.preemption.model.MovieDetailModel;
import com.application.sven.huinews.main.preemption.presenter.MovieDetailPresenter;
import com.application.sven.huinews.main.web.activity.WebActivity;
import com.application.sven.huinews.mob.share.MobShare;
import com.application.sven.huinews.mob.share.ShareCallBack;
import com.application.sven.huinews.mob.share.ShareResponse;
import com.application.sven.huinews.utils.CommonUtils;
import com.application.sven.huinews.utils.FrescoUtil;
import com.application.sven.huinews.utils.LogUtil;
import com.application.sven.huinews.utils.NetWorkUtils;
import com.application.sven.huinews.utils.ToastUtils;
import com.application.sven.huinews.utils.ads.AdsConfig;
import com.application.sven.huinews.utils.itemDecoration.MovieCountGridItem;
import com.application.sven.huinews.utils.statusbar.Eyes;
import com.application.sven.huinews.utils.umeng.UMengUtils;
import com.application.sven.huinews.view.dialog.MovieShareDialog;
import com.application.sven.huinews.view.dialog.ShareDialog;
import com.application.sven.huinews.view.video.ListIjkVideoView;
import com.baidu.mobad.nativevideo.BaiduVideoResponse;
import com.dueeeke.videoplayer.listener.VideoListener;
import com.dueeeke.videoplayer.player.IjkVideoView;
import com.facebook.drawee.view.SimpleDraweeView;
import com.google.gson.Gson;
import com.hdl.m3u8.M3U8DownloadTask;
import com.hdl.m3u8.M3U8InfoManger;
import com.hdl.m3u8.bean.M3U8;
import com.hdl.m3u8.bean.OnDownloadListener;
import com.hdl.m3u8.bean.OnM3U8InfoListener;
import com.qq.e.ads.nativ.NativeExpressADView;
import com.umeng.analytics.MobclickAgent;

import java.util.HashMap;
import java.util.List;

/**
 * Created by sfy. on 2018/5/9 0009.
 * 抢先--视频详情
 */

public class MovieDetailsActivity extends BaseActivity<MovieDetailPresenter, MovieDetailModel> implements MovieDetailContract.View {
    private ImageButton btn_back, btn_share;
    private SimpleDraweeView iv_thumb;
    private TextView movie_title, movie_tostart;
    private TextView tv_movie_dis, tv_play;
    private TextView btn_collection;
    private RecyclerView select_movie_rv; //左右滑的选集
    private RecyclerView count_view_rv, count_download_view_rv; //选集弹窗 //下载弹窗
    private RecyclerView recommend_rv; //为你推荐
    private MovieCountAdapter mMovieCountAdapter;
    private MovieCountDownloadAdapter mMovieCountDownloadAdapter;
    private RecommedAdapter mRecommedAdapter;
    private RelativeLayout ad_layout;
    private ProgressBar def_pro;
    private LinearLayout rl_count_view, rl_count_download_view, movie_num;//选集  和  缓存
    private ImageButton count_view_close, count_download_view_close;
    private TextView btn_select_movie, tv_download, btn_cache;
    private ListIjkVideoView player;
    private ShareDialog mShareDialog;
    private boolean isOpen = false;
    private boolean isCollection;
    private boolean isShowDialog = false;
    private MovieCountGridItem mMovieCountGridItem;
    private MovieDetailResponse.MovieDetailData mMovieData;
    private MovieSource mMovieSource;
    private LinearLayout loading_view;
    private int movieId;
    private int mCurrentPlayId = 0; //当前播放集数下表
    private int mCurrentTime = 0;
    private int mCurrentExtId = 0;//传递进来的当前集数id
    private int bundlePlayTime; //历史记录进来的开始播放时间
    private List<MovieDetailResponse.MovieDetailData.ExtBean> mMovieCounts;
    private BaseDB mBaseDB;
    private AdsConfig mAdsConfig;
    private List<NativeExpressADView> mAdViewList;
    private HashMap<NativeExpressADView, Integer> mAdViewPositionMap = new HashMap<>();
    private MovieShareDialog movieShareDialog;

    public static void toThis(Context mContext, int movieId, int play_time, int extId) {
        Intent intent = new Intent(mContext, MovieDetailsActivity.class);
        Bundle bundle = new Bundle();
        bundle.putInt(Constant.BUNDLE_MOVIE_ID, movieId);
        bundle.putInt(Constant.BUNDLE_MOVIE_PLAY_TIME, play_time);
        bundle.putInt(Constant.BUNDLE_MOVIE_PLAY_POSITION, extId);
        intent.putExtras(bundle);
        mContext.startActivity(intent);
    }

    @Override
    public int getLayoutId() {
        Eyes.setStatusBarColor(this, getResources().getColor(R.color.color_black));
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            movieId = bundle.getInt(Constant.BUNDLE_MOVIE_ID);
            bundlePlayTime = bundle.getInt(Constant.BUNDLE_MOVIE_PLAY_TIME);
            mCurrentExtId = bundle.getInt(Constant.BUNDLE_MOVIE_PLAY_POSITION);
        }

        MobclickAgent.onEvent(mContext, UMengUtils.MOVIE_DETAILS);
        MobclickAgent.onEvent(mContext, UMengUtils.MOVIE_DETAILS, UMengUtils.MOVIE_DETAILS);

        return R.layout.activity_movie_details;
    }

    @Override
    public void initView() {
        btn_back = findViewById(R.id.btn_back);
        player = findViewById(R.id.player);
        iv_thumb = findViewById(R.id.iv_thumb);
        tv_movie_dis = findViewById(R.id.tv_movie_dis);
        select_movie_rv = findViewById(R.id.select_movie_rv);
        recommend_rv = findViewById(R.id.recommend_rv);
        count_view_rv = findViewById(R.id.count_view_rv);
        count_download_view_rv = findViewById(R.id.count_download_view_rv);
        btn_select_movie = findViewById(R.id.btn_select_movie);
        rl_count_view = findViewById(R.id.rl_count_view);
        rl_count_download_view = findViewById(R.id.rl_count_download_view);
        count_view_close = findViewById(R.id.count_view_close);
        count_download_view_close = findViewById(R.id.count_download_view_close);
        tv_download = findViewById(R.id.tv_download);
        btn_share = findViewById(R.id.btn_share);
        btn_cache = findViewById(R.id.btn_cache);
        movie_title = findViewById(R.id.movie_title);
        movie_tostart = findViewById(R.id.movie_tostar);
        tv_play = findViewById(R.id.tv_play);
        movie_num = findViewById(R.id.movie_num);
        loading_view = findViewById(R.id.loading_view);
        btn_collection = findViewById(R.id.btn_collection);
        ad_layout = findViewById(R.id.ad_layout);
        def_pro = findViewById(R.id.def_pro);

    }

    @Override
    public void initEvents() {
        tv_movie_dis.setOnClickListener(this);
        btn_select_movie.setOnClickListener(this);
        count_view_close.setOnClickListener(this);
        tv_download.setOnClickListener(this);
        count_download_view_close.setOnClickListener(this);
        btn_share.setOnClickListener(this);
        btn_cache.setOnClickListener(this);
        btn_back.setOnClickListener(this);
        btn_collection.setOnClickListener(this);
    }

    @Override
    public void onClickEvent(View v) {
        if (v == tv_movie_dis) {
            isOpen = !isOpen;
            tv_movie_dis.setMaxLines(isOpen ? Integer.MAX_VALUE : 2);
        } else if (v == btn_select_movie) {
            showMovieCountView(rl_count_view);
        } else if (v == count_view_close) {
            hideMovieCountView(rl_count_view);
        } else if (v == tv_download) {
            setDownload();
        } else if (v == count_download_view_close) {
            hideMovieCountView(rl_count_download_view);
        } else if (v == btn_cache) {
            DownloadActivity.toThis(mContext);
        } else if (v == btn_share) {
            showShareDialog();
        } else if (v == btn_back) {
            finish();
        } else if (v == btn_collection) {
            setCollection();
        }
    }

    @Override
    public void initObject() {
        setMVP();
        setMovieCount();
        setRecommendInfo();
        mAdsConfig = new AdsConfig(mContext);
        mPresenter.onMovieDetail();
        mBaseDB = BaseDB.getInstance(mContext);


    }


    /**
     * 选集
     */
    private void setMovieCount() {

        if (mMovieCountGridItem == null) {
            mMovieCountGridItem = new MovieCountGridItem(mContext);
        }
        //横向选集
        mMovieCountAdapter = new MovieCountAdapter(mContext);
        LinearLayoutManager manager = new LinearLayoutManager(mContext);
        manager.setOrientation(LinearLayoutManager.HORIZONTAL);
        select_movie_rv.addItemDecoration(mMovieCountGridItem);
        select_movie_rv.setLayoutManager(manager);
        select_movie_rv.setAdapter(mMovieCountAdapter);

        //选集
        GridLayoutManager mGridLayoutManager = new GridLayoutManager(mContext, 5);
        count_view_rv.setLayoutManager(mGridLayoutManager);
        count_view_rv.addItemDecoration(mMovieCountGridItem);
        count_view_rv.setAdapter(mMovieCountAdapter);

        mMovieCountAdapter.setOnMovieCountClickLisenter(new MovieCountAdapter.onMovieCountClickLisenter() {
            @Override
            public void setSelected(int postiion) {
                for (MovieDetailResponse.MovieDetailData.ExtBean movieCount : mMovieCounts) {
                    movieCount.setSelected(false);
                }
                //切换集数
                mPresenter.movieSwitch();
                mMovieCounts.get(postiion).setSelected(true);
                mMovieCountAdapter.notifyDataSetChanged();
                bundlePlayTime = 0;
                mCurrentExtId = 0;
                setCurrentPlay(postiion);

            }
        });


        GridLayoutManager mGridLayoutManager1 = new GridLayoutManager(mContext, 5);
        mMovieCountDownloadAdapter = new MovieCountDownloadAdapter(mContext);
        count_download_view_rv.setLayoutManager(mGridLayoutManager1);
        count_download_view_rv.addItemDecoration(mMovieCountGridItem);
        count_download_view_rv.setAdapter(mMovieCountDownloadAdapter);
        mMovieCountDownloadAdapter.setOnMovieCountClickLisenter(new MovieCountDownloadAdapter.onMovieDownloadCountClickLisenter() {
            @Override
            public void setSelected(int postiion) {
                mMovieCounts.get(postiion).setSelected(true);
                mMovieCountDownloadAdapter.notifyDataSetChanged();
            }
        });

    }

    private void setRecommendInfo() {
        mRecommedAdapter = new RecommedAdapter(mContext);
        recommend_rv.setNestedScrollingEnabled(false);
        recommend_rv.setLayoutManager(new LinearLayoutManager(mContext));
        recommend_rv.setAdapter(mRecommedAdapter);

        mRecommedAdapter.setmOnItemClickLisenter(new RecommedAdapter.OnItemClickLisenter() {
            @Override
            public void onItemClick(int movieId) {
                mAdsConfig.destoryBdAd();
                MovieDetailsActivity.toThis(mContext, movieId, 0, 0);
                finish();
            }
        });
    }

    private void showMovieCountView(View targetView) {
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.select_pic_anim_in);
        targetView.startAnimation(animation);//开始动画
        targetView.setVisibility(View.VISIBLE);
    }

    private void hideMovieCountView(View targetView) {
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.select_pic_anim_out);
        targetView.startAnimation(animation);//开始动画
        targetView.setVisibility(View.GONE);
    }

    private void showShareDialog() {
        if (mShareDialog == null) {
            mShareDialog = new ShareDialog(mContext);

        }
        mShareDialog.hideReport();
        mShareDialog.show();
        mShareDialog.setOnShareListener(new ShareDialog.OnShareListener() {
            @Override
            public void onShare(int type) {
            /*  VideoShareUrlRequest request = new VideoShareUrlRequest();
                request.setVideoId(mMovieData.getId() + "");
                mPresenter.onVideoShare(request, type);*/
                if (type == Constant.SHARE_TYPE_REPORT) {

                } else {
                    mPresenter.onMovieShate(type);
                }
            }
        });
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
    }

    @Override
    public MovieDetailRequest getMovieDetailRequest() {
        MovieDetailRequest request = new MovieDetailRequest();
        request.setMovieId(movieId);
        return request;
    }

    @Override
    public MovieResPlayRequest getMovieResPlayRequest(int aId, int playId, int rId) {
        MovieResPlayRequest request = new MovieResPlayRequest();
        request.setaId(aId);
        request.setPlayId(playId);
        request.setrId(rId);
        return request;
    }

    @Override
    public VideoCollectionRequest getVideoCollectionRequest() {
        VideoCollectionRequest request = new VideoCollectionRequest();
        request.setType(Constant.CHANNEL_TYPE_MOVIE);
        request.setrId(mMovieData.getId());
        return request;
    }

    @Override
    public VideoCollectionCancelRequest getVideoCollectionCancelRequest() {
        VideoCollectionCancelRequest request = new VideoCollectionCancelRequest();
        request.setrId(mMovieData.getId() + "");
        request.setType(Constant.CHANNEL_TYPE_MOVIE);
        return request;
    }

    @Override
    public MovieVisitRequest getMovieVisitRequest() {
        MovieVisitRequest request = new MovieVisitRequest();
        request.setMovies_id(mMovieData.getId());
        request.setPlay_id(mMovieData.getExt().get(mCurrentPlayId).getId());
        request.setPlay_time(mCurrentTime);
        return request;
    }

    @Override
    public MovieShareRequest getmMovieShareRequest() {
        MovieShareRequest request = new MovieShareRequest();
        request.setMovies_id(mMovieData.getId());
        request.setPlay_id(mMovieData.getExt().get(mCurrentPlayId).getId());
        return request;
    }

    @Override
    public MoviePayRequest getMoviePayRequest() {
        MoviePayRequest request = new MoviePayRequest();
        request.setMoviesId(mMovieData.getId());
        request.setPlayId(mMovieData.getExt().get(mCurrentPlayId).getId());
        return request;
    }


    /**
     * movie detail info data
     *
     * @param mMovieData response
     */
    @Override
    public void setMovieDetail(MovieDetailResponse.MovieDetailData mMovieData) {
        loading_view.setVisibility(View.GONE);
        this.mMovieData = mMovieData;
        setMovieInfo(mMovieData);
        setRecommend(mMovieData);
        setNum(mMovieData);
        //getResPlay();
        //  setBannerAds();
    }


    Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            if (msg.what == 1) {
                MovieSource movieSource = (MovieSource) msg.obj;
                MovieDetailsActivity.this.mMovieSource = movieSource;
                setPlayerInfo(mMovieSource, mMovieData);
            }
            return false;
        }
    });

    @Override
    public void setMovieSource(final MovieSource mMovieSource) {
        def_pro.setVisibility(View.GONE);
        if (mMovieSource.getStatus() == 2) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    MovieSource movieSource = new MovieSource();
                    String urlDetails = NetWorkUtils.getUrlInfo(mMovieSource.getSource());
                    MovieData mMovieData = new Gson().fromJson(urlDetails, MovieData.class);
                    movieSource.setSource(mMovieData.getData().getM3utx());
                    movieSource.setStatus(1);
                    movieSource.setFree_time(mMovieSource.getFree_time());
                    movieSource.setPay_gold(mMovieSource.getPay_gold());
                    movieSource.setPay_msg(mMovieSource.getPay_msg());
                    movieSource.setPay_type(mMovieSource.getPay_type());
                    movieSource.setStatus(mMovieSource.getStatus());
                    Message msg = Message.obtain();
                    msg.what = 1;
                    msg.obj = movieSource;
                    handler.sendMessage(msg);

                }
            }).start();

        } else {
            this.mMovieSource = mMovieSource;
            setPlayerInfo(mMovieSource, mMovieData);
        }


    }


    @Override
    public void setMoiveSourceError() {
        def_pro.setVisibility(View.GONE);
        LogUtil.showLog("msg----视频解析出错");
        //视频解析出错
        // player.setUrl("defaultUrl");
        player.setScreenScale(IjkVideoView.SCREEN_SCALE_DEFAULT);
        MovieSource movieSource = new MovieSource();
        movieSource.setSource("defaultUrl");
        mPresenter.setMovieConfig(player, mMovieData, movieSource, mNrAd, bundlePlayTime, mCurrentPlayId);
    }

    @Override
    public void setMoiveCollectionOk() {

    }

    @Override
    public void setMovieCancelCollectionOk() {

    }

    @Override
    public void setVideoShareUrl(String url, final int type) {
       /* String shareJson = mPresenter.getShar2eJson(mMovieData, url, type);
        MobShare mMobShare = new MobShare(mContext);
        mMobShare.shareToOne(mContext, shareJson, new ShareCallBack() {
            @Override
            public void onResponse(String response) {
                mPresenter.onShareVisit(response, ShareVisitRequest.VIDEO_LIST, type);
            }
        });*/
    }

    @Override
    public void setMovieShare(MovieShareResponse.Data mShareData, final int type) {
        String shareJson = mPresenter.getShareJson(mShareData, mShareData.getUrl(), type);
        LogUtil.showJson("shareJson", shareJson);
        MobShare mMobShare = new MobShare(mContext);
        mMobShare.shareToOne(mContext, shareJson, new ShareCallBack() {
            @Override
            public void onResponse(String response) {
                LogUtil.showLog("msg----response:" + response);
                if (movieShareDialog != null) {
                    movieShareDialog.dismiss();
                }
                ShareResponse mShareResponse = new Gson().fromJson(response, ShareResponse.class);
                if (mShareResponse.getCode() == Constant.JS_RESPONSE_CODE_SUCCEED) {
                    LogUtil.showLog("msg---分享成功");
                    isShowDialog = true;
                    mPresenter.onShareVisit(response, ShareVisitRequest.MOVIE_DETAIL, type);
                    //分享成功后 支付金币
                    mPresenter.onMoviePay();
                } else if (mShareResponse.getCode() == Constant.JS_RESPONSE_CODE_FAIL) {
                    ToastUtils.showLong(mContext, "分享失败，请稍后再试");
                    isShowDialog = false;
                } else if (mShareResponse.getCode() == Constant.JS_RESPONSE_CODE_CANCEL) {
                    ToastUtils.showLong(mContext, "取消分享");
                    isShowDialog = false;
                }
            }
        });
    }

    @Override
    public void onVideoShare() {
        showShareDialog();
    }

    private List<AdsList> ads;

    @Override
    public void setAdsLit(final List<AdsList> ads) {
        this.ads = ads;
        mAdsConfig.tencentNativeAD(1, AdsConfig.TENCENT_AD_BIG_IMG);
        mPresenter.onTencentAds(mAdsConfig);
    }

    @Override
    public void setTencentAds(List<NativeExpressADView> list) {
        for (int i = 0; i < ads.size(); i++) {
            if (ads.get(i).getAd_type().equals(AdsConfig.AD_TYPE_TENCENT)) {
                //设置腾讯广告
                mRecommedAdapter.datas().add(ads.get(i).getIn_position(), list.get(i));
            }
        }
        mRecommedAdapter.notifyDataSetChanged();
    }

    @Override
    public void onMoviePay() {
        if (TextUtils.isEmpty(UserSpCache.getInstance(mContext).getStringData(UserSpCache.KEY_PHONE))) {
            ToastUtils.showShort(mContext, "请先注册登录");
            LoginActivity.toThis(mContext, false, false);
            return;
        }
        mPresenter.onMoviePay();
    }

    @Override
    public void onMoviePayOk() {
        mPresenter.movieToPlay(player);
    }

    @Override
    public void onGetGold() {
        WebActivity.toThis(mContext, Api.MY_DIAMOND);
        finish();
    }

    @Override
    public void onShowShareDialog() {
        if (movieShareDialog == null) {
            movieShareDialog = new MovieShareDialog(mContext);
            movieShareDialog.show();
            movieShareDialog.setOnOpenBagListener(new MovieShareDialog.OnMovieShareOnClickLisenter() {
                @Override
                public void onShare() {
                    showShareDialog();
                }
            });
        } else {
            if (!movieShareDialog.isShowing() && !isShowDialog) {
                movieShareDialog.show();
            }
        }
    }

    /**
     * 设置视频信息
     *
     * @param mMovieData
     */
    private void setMovieInfo(MovieDetailResponse.MovieDetailData mMovieData) {
        FrescoUtil.loadDefImg(iv_thumb, mMovieData.getM_img());
        movie_title.setText(mMovieData.getM_name());
        movie_tostart.setText(getString(R.string.to_star) + mMovieData.getA_star());
        tv_movie_dis.setText(getString(R.string.profiles) + mMovieData.getM_desc());
        tv_play.setText(mMovieData.getPlay_count());
        btn_select_movie.setText("更新" + mMovieData.getExt().size() + "集/共" + mMovieData.getAll_set_number() + "集");
        movie_num.setVisibility(mMovieData.getExt().size() > 1 ? View.VISIBLE : View.GONE);
        isCollection = mMovieData.isIs_collected();
        setUiCollection();
        setHidDownloadTv();
    }


    private void setHidDownloadTv() {
        if (mMovieData.getExt().size() <= 1) {
            Drawable download = mContext.getResources().getDrawable(R.mipmap.icon_down);
            download.setBounds(0, 0, download.getMinimumWidth(), download.getMinimumHeight());
            Drawable downloaded = mContext.getResources().getDrawable(R.mipmap.icon_downloaded);
            downloaded.setBounds(0, 0, downloaded.getMinimumWidth(), downloaded.getMinimumHeight());
            tv_download.setCompoundDrawables(mBaseDB.isContainsMovie(mMovieData.getId()) ? downloaded : download, null, null, null);
            tv_download.setTextColor(mBaseDB.isContainsMovie(mMovieData.getId()) ? getResources().getColor(R.color.c_cdcdcd) : getResources().getColor(R.color.c_333333));
            tv_download.setEnabled(mBaseDB.isContainsMovie(mMovieData.getId()) ? false : true);
        }
    }

    /**
     * 设置推荐
     *
     * @param mMovieData
     */
    private void setRecommend(MovieDetailResponse.MovieDetailData mMovieData) {
        mRecommedAdapter.setData(mMovieData.getRecommen());
        mPresenter.onAdsList(PAGE);
    }

    /**
     * 解析源
     */
    private void getResPlay() {
        // TODO: 2018/5/21   解析源请求逻辑
        MovieResPlayRequest request = new MovieResPlayRequest();
        request.setaId(mMovieData.getAnaly().get(0).getId());
        request.setrId(mMovieData.getExt().get(0).getUrl_resourece()[0]);
        request.setPlayId(mCurrentExtId == 0 ? mMovieData.getExt().get(mCurrentPlayId).getId() : mCurrentExtId);
        def_pro.setVisibility(View.VISIBLE);
        mPresenter.onMovieResPlay(request);
    }

    /**
     * 设置集数
     *
     * @param mMovieData
     */
    private void setNum(MovieDetailResponse.MovieDetailData mMovieData) {
        this.mMovieCounts = mMovieData.getExt();
        if (mCurrentExtId == 0) {
            mMovieCounts.get(mCurrentExtId).setSelected(true);
            mMovieCountAdapter.notifyDataSetChanged();
            mCurrentTime = 0;
            setCurrentPlay(mCurrentExtId);

        } else {
            for (int i = 0; i < mMovieCounts.size(); i++) {
                if (mMovieCounts.get(i).getId() == mCurrentExtId) {
                    mMovieCounts.get(i).setSelected(true);
                    setCurrentPlay(i);
                    break;
                }
            }
        }

        mMovieCountAdapter.setData(mMovieData.getExt());
        mMovieCountDownloadAdapter.setData(mMovieData.getExt());


    }

    /**
     * 设置当前播放集数
     *
     * @param currentPlayId 集数下标
     */
    private void setCurrentPlay(int currentPlayId) {
        player.release();
        mCurrentPlayId = currentPlayId;
        getResPlay();
//         setBannerAds();
        select_movie_rv.scrollToPosition(mCurrentPlayId);
    }


    /**
     * 设置视频播放
     *
     * @param mMovieSource
     * @param mMovieData
     */
    private void setPlayerInfo(MovieSource mMovieSource, MovieDetailResponse.MovieDetailData mMovieData) {
        player.release();
        player.setScreenScale(IjkVideoView.SCREEN_SCALE_CENTER_CROP);
        mPresenter.setMovieConfig(player, mMovieData, mMovieSource, mNrAd, bundlePlayTime, mCurrentPlayId);
    }

    /**
     * 收藏取消收藏
     */
    private void setCollection() {
        isCollection = !isCollection;
        if (isCollection) {
            mPresenter.onMovieCollection();
        } else {
            mPresenter.onMovieCancelCollection();
        }
        setUiCollection();
    }

    /**
     * 开始下载按钮，
     */
    private void setDownload() {
        if (mMovieCounts == null) {
            return;
        }
        if (mMovieCounts.size() > 1) {
            showMovieCountView(rl_count_download_view);
        } else {
            //电影下载
            //  DownloadManger.getInstance(mContext).start(this.mMovieSource.getSource());
            // mPresenter.readM3u8(mMovieSource.getSource());
            mPresenter.insertMovie(mMovieData, mMovieSource.getSource());
            setHidDownloadTv();
            ToastUtils.showLong(mContext, "已开始缓存,请在我的下载中查看");

        }
    }

    private void setMovieVisit() {
        if (mMovieData == null) {
            return;
        }
        //   mPresenter.onMovieVisit();
        setMoviesVisit(getMovieVisitRequest());
    }

    private void setUiCollection() {
        btn_collection.setText(isCollection ? "已收藏" : "收藏");
        btn_collection.setTextColor(isCollection ? getResources().getColor(R.color.c_eb3e44) : getResources().getColor(R.color.c_333333));
        btn_collection.setCompoundDrawables(isCollection ? CommonUtils.setLeftDrawable(mContext, R.mipmap.movie_collectioned) : CommonUtils.setLeftDrawable(mContext, R.mipmap.movie_collection), null, null, null);
    }


    BaiduVideoResponse mNrAd;

    /**
     * 获取广告
     */
    private void setBannerAds() {

        //banner广告
        //  mAdsConfig.getBaiduBannerAd(mContext, ad_layout);
        mAdsConfig.tencentAdBanner(this, ad_layout);
        //百度贴片
        mAdsConfig.baiduPreroll(this);
        mAdsConfig.setOnBaiduPrerollLisenter(new AdsConfig.OnBaiduPrerollLisenter() {
            @Override
            public void baiduAdPaly(BaiduVideoResponse response) {
                mNrAd = response;
                getResPlay();
            }

            @Override
            public void baiduAdFail() {
                //广告获取失败处理
                mNrAd = null;
                getResPlay();
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        player.pause();
        //获取当前播放的时长
        mCurrentTime = (int) (player.getCurrentPosition() / 1000);
        LogUtil.showLog("msg---当前播放时间:" + mCurrentTime);
        setMovieVisit();
        if (movieShareDialog != null) {
            movieShareDialog.dismiss();
        }
    }


    @Override
    protected void onResume() {
        super.onResume();
        player.resume();

        // LogUtil.showLog("msg---onResume");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        player.release();
        //LogUtil.showLog("msg---onDestroy");
        mAdsConfig.destoryBdAd();
        mAdsConfig.destroyTencentAdBanner();
        if (mAdViewList != null) {
            //释放腾讯广告
            for (NativeExpressADView view : mAdViewList) {
                view.destroy();
            }
        }
    }


    @Override
    public void onBackPressed() {
        if (!player.onBackPressed()) {
            super.onBackPressed();
        }
    }
}
