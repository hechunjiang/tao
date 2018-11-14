package com.application.sven.huinews.main.preemption.presenter;

import android.os.Environment;
import android.view.View;

import com.application.sven.huinews.config.Constant;
import com.application.sven.huinews.config.MovieData;
import com.application.sven.huinews.config.api.Api;
import com.application.sven.huinews.config.http.DataCallBack;
import com.application.sven.huinews.config.http.DataResponseCallback;
import com.application.sven.huinews.db.BaseDB;
import com.application.sven.huinews.db.UserSpCache;
import com.application.sven.huinews.entity.VideoModel;
import com.application.sven.huinews.entity.jspush.JsShareType;
import com.application.sven.huinews.entity.request.AdsRequest;
import com.application.sven.huinews.entity.request.MovieDetailRequest;
import com.application.sven.huinews.entity.request.MoviePayRequest;
import com.application.sven.huinews.entity.request.MovieResPlayRequest;
import com.application.sven.huinews.entity.request.MovieShareRequest;
import com.application.sven.huinews.entity.request.MovieVisitRequest;
import com.application.sven.huinews.entity.request.ShareVisitRequest;
import com.application.sven.huinews.entity.request.VideoCollectionCancelRequest;
import com.application.sven.huinews.entity.request.VideoCollectionRequest;
import com.application.sven.huinews.entity.request.VideoShareUrlRequest;
import com.application.sven.huinews.entity.response.AdsReponse;
import com.application.sven.huinews.entity.response.BaseResponse;
import com.application.sven.huinews.entity.response.MovieDetailResponse;
import com.application.sven.huinews.entity.response.MovieShareResponse;
import com.application.sven.huinews.entity.response.MovieSource;
import com.application.sven.huinews.entity.response.VideoListResponse;
import com.application.sven.huinews.entity.response.VideoShareUrlResponse;
import com.application.sven.huinews.main.preemption.contract.MovieDetailContract;
import com.application.sven.huinews.main.web.activity.WebActivity;
import com.application.sven.huinews.mob.share.ShareResponse;
import com.application.sven.huinews.utils.CommonUtils;
import com.application.sven.huinews.utils.FrescoUtil;
import com.application.sven.huinews.utils.LogUtil;
import com.application.sven.huinews.utils.NetWorkUtils;
import com.application.sven.huinews.utils.ToastUtils;
import com.application.sven.huinews.utils.ads.AdsConfig;
import com.application.sven.huinews.utils.download.DownloadData;
import com.application.sven.huinews.view.video.ListIjkVideoView;
import com.application.sven.huinews.view.video.controller.AdContoller;
import com.application.sven.huinews.view.video.controller.MovieController;
import com.baidu.mobad.nativevideo.BaiduVideoResponse;
import com.dueeeke.videoplayer.player.IjkPlayer;
import com.dueeeke.videoplayer.player.IjkVideoView;
import com.dueeeke.videoplayer.player.PlayerConfig;
import com.dueeeke.videoplayer.util.ProgressUtil;
import com.google.gson.Gson;
import com.hdl.m3u8.M3U8InfoManger;
import com.hdl.m3u8.bean.M3U8;
import com.hdl.m3u8.bean.OnM3U8InfoListener;
import com.qq.e.ads.nativ.NativeExpressADView;

import org.greenrobot.eventbus.EventBus;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import cn.sharesdk.wechat.friends.Wechat;
import tv.danmaku.ijk.media.player.IjkMediaPlayer;

/**
 * auther: sunfuyi
 * data: 2018/5/17
 * effect:
 */
public class MovieDetailPresenter extends MovieDetailContract.Presenter {
    @Override
    public void onMovieDetail() {
        MovieDetailRequest request = mView.getMovieDetailRequest();
        mModel.getMovieDetail(request, new DataResponseCallback<MovieDetailResponse.MovieDetailData>() {


            @Override
            public void onSucceed(MovieDetailResponse.MovieDetailData movieDetailData) {

                mView.setMovieDetail(movieDetailData);
            }

            @Override
            public void onFail(BaseResponse response) {

            }

            @Override
            public void onComplete() {

            }
        });
    }

    @Override
    public void onMovieResPlay(final MovieResPlayRequest request) {
        mModel.getMovieResPlay(request, new DataResponseCallback<MovieSource>() {

            @Override
            public void onSucceed(final MovieSource movieSource) {
                mView.setMovieSource(movieSource);
            }

            @Override
            public void onFail(BaseResponse response) {
                LogUtil.showLog("msg---onMovieResPlay onFail:" + response.toString());
                if (response.getCode() == Constant.RESPONSE_ERROR) {
                    mView.setMoiveSourceError();
                } else if (response.getCode() == DataCallBack.NET_TIME_OUT) {
                    mView.setMoiveSourceError();
                }

            }

            @Override
            public void onComplete() {

            }
        });
    }

    MovieController mMovieController;
    AdContoller mAdContoller;

    @Override
    public void setMovieConfig(final ListIjkVideoView mIjkVideoView, final MovieDetailResponse.MovieDetailData mMovieData, MovieSource mMovieSource, final BaiduVideoResponse mNrAd, int playTime, final int currentPoisiton) {
        if (mMovieController == null) {
            mMovieController = new MovieController(mContext);
        }
        mMovieController.setMovieData(mMovieData, currentPoisiton);
        mMovieController.setMovieStatus(mMovieSource);
        mIjkVideoView.setVideoController(mMovieController);

        PlayerConfig.Builder builder = new PlayerConfig.Builder();
        mIjkVideoView.setPlayerConfig(new PlayerConfig.Builder().autoRotate()//自动旋转屏幕
                //.enableCache()//启用边播边存
                //.enableMediaCodec()//启动硬解码
//                .usingSurfaceView()//使用SurfaceView
                .savingProgress()
                .setCustomMediaPlayer(new IjkPlayer() {
                    @Override
                    public void setOptions() {
                        super.setOptions();
                        //支持concat
                        mMediaPlayer.setOption(IjkMediaPlayer.OPT_CATEGORY_FORMAT, "safe", 0);
                        mMediaPlayer.setOption(IjkMediaPlayer.OPT_CATEGORY_FORMAT, "protocol_whitelist", "rtmp,concat,ffconcat,file,subfile,http,https,tls,rtp,tcp,udp,crypto,rtsp");
                        //支持rtsp
                        mMediaPlayer.setOption(IjkMediaPlayer.OPT_CATEGORY_FORMAT, "rtsp_transport", "tcp");
                    }
                })
                .build());
        List<VideoModel> mVideoModels = new ArrayList<>();
        if (mNrAd != null) {
            //添加广告控制视图
            mAdContoller = new AdContoller(mContext);
            FrescoUtil.loadDefImg(mAdContoller.getThumb(), mNrAd.getImageUrl());
            mAdContoller.setBaiduVideoResponse(mNrAd);
            mVideoModels.add(new VideoModel(mNrAd.getVideoUrl(), "广告", mAdContoller));
        }
        ProgressUtil.saveProgress(mMovieSource.getSource(), playTime * 1000);


        String testUrl = "http://videocdn.php.cn/video/liuqi/%E4%BB%BF%E7%88%B1%E5%A5%87%E8%89%BA/ys-6%E5%B0%81%E8%A3%85%E6%95%B0%E6%8D%AE%E5%BA%93%E8%AE%BF%E9%97%AE%E7%B1%BB.mp4";
        LogUtil.showLog("msg----播放源:" + mMovieSource.getSource() + " ----测试源:" + testUrl);
        mVideoModels.add(new VideoModel(mMovieSource.getSource(), "影视", mMovieController));
        //   mVideoModels.add(new VideoModel(testUrl, "影视", mMovieController));
        mIjkVideoView.setVideos(mVideoModels);

        if (mAdContoller != null) {
            // 警告：调用该函数来发送展现，勿漏！
            mNrAd.recordImpression(mAdContoller);
            mAdContoller.setControllerListener(new AdContoller.AdControllerListener() {
                @Override
                public void onAdClick() {
                    //广告点击
                    mNrAd.handleClick(mAdContoller);
                }
            });
        }

        mIjkVideoView.seekTo(playTime * 1000);
        mIjkVideoView.start();

        mMovieController.setmSwchLineOnClickLisenter(new MovieController.swchLineOnClickLisenter() {
            @Override
            public void swichLine(MovieDetailResponse.MovieDetailData.AnalyBean analy) {
                MovieResPlayRequest request = mView.getMovieResPlayRequest(analy.getId(), mMovieData.getExt().get(currentPoisiton).getId(), mMovieData.getExt().get(currentPoisiton).getUrl_resourece()[0]);
                onMovieResPlay(request);
            }

            @Override
            public void videoShare() {
                mView.onVideoShare();
            }

            @Override
            public void onMoviePay() {

                mView.onMoviePay();
            }

            @Override
            public void onGetGold() {
                mView.onGetGold();
            }

            @Override
            public void showShareDialog() {
                mView.onShowShareDialog();
            }
        });
    }

    public void movieSwitch() {
        if (mMovieController != null) {
            mMovieController.setPlayState(0);
        }

    }

    /**
     * 事务处理完毕开始播放
     *
     * @param mIjkVideoView
     */
    public void movieToPlay(ListIjkVideoView mIjkVideoView) {
        mIjkVideoView.start();
        mMovieController.setPlayType(MovieController.PAY_TYPE_NONE);
    }

    @Override
    public void onMovieCollection() {
        VideoCollectionRequest request = mView.getVideoCollectionRequest();
        mModel.getMovieCollection(request, new DataCallBack() {
            @Override
            public void onComplete() {

            }

            @Override
            public void onSucceed(String json) {

            }

            @Override
            public void onFail(BaseResponse baseResponse) {

            }
        });
    }

    @Override
    public void onMovieCancelCollection() {
        VideoCollectionCancelRequest request = mView.getVideoCollectionCancelRequest();
        mModel.setCancelMovieCollection(request, new DataCallBack() {
            @Override
            public void onComplete() {

            }

            @Override
            public void onSucceed(String json) {

            }

            @Override
            public void onFail(BaseResponse baseResponse) {

            }
        });
    }

    @Override
    public void onMovieVisit() {
        MovieVisitRequest request = mView.getMovieVisitRequest();
        mModel.setMovieVisit(request, new DataCallBack() {
            @Override
            public void onComplete() {

            }

            @Override
            public void onSucceed(String json) {
                LogUtil.showLog("msg---保存播放历史记录成功");
                EventBus.getDefault().post(Constant.REFRESH_MINE_MOVIDE_HISTORY);
            }

            @Override
            public void onFail(BaseResponse baseResponse) {

            }
        });
    }

    @Override
    public void onVideoShare(VideoShareUrlRequest request, final int type) {
        mModel.shareVideo(request, new DataResponseCallback<VideoShareUrlResponse>() {

            @Override
            public void onSucceed(VideoShareUrlResponse videoShareUrlResponse) {
                mView.setVideoShareUrl(videoShareUrlResponse.getData().getUrl(), type);
            }

            @Override
            public void onFail(BaseResponse response) {

            }

            @Override
            public void onComplete() {

            }
        });
    }

    @Override
    public void onMovieShate(final int type) {
        MovieShareRequest request = mView.getmMovieShareRequest();
        mModel.shareMovie(request, new DataResponseCallback<MovieShareResponse>() {
            @Override
            public void onSucceed(MovieShareResponse movieShareResponse) {
                mView.setMovieShare(movieShareResponse.getData(), type);
            }

            @Override
            public void onFail(BaseResponse response) {

            }

            @Override
            public void onComplete() {

            }
        });
    }

    @Override
    public void onShareVisit(String response, String videoType, int type) {
        ShareResponse mJsShareResponse = new Gson().fromJson(response, ShareResponse.class);
        ShareVisitRequest request = new ShareVisitRequest();
        request.setActivityType(videoType);
        request.setCode(mJsShareResponse.getCode() + "");
        request.setShareChannel(type + "");

        mModel.shareVisit(request, new DataCallBack() {
            @Override
            public void onComplete() {

            }

            @Override
            public void onSucceed(String json) {

            }

            @Override
            public void onFail(BaseResponse baseResponse) {

            }
        });
    }

    @Override
    public void onTencentAds(AdsConfig mAdsConfig) {
        mAdsConfig.setSetAdLoadedLisenter(new AdsConfig.setAdLoadedLisenter() {
            @Override
            public void onLoadAd(List<NativeExpressADView> list) {
                LogUtil.showLog("msg--腾讯广点通");
                mView.setTencentAds(list);
            }

            @Override
            public void onLoadRightImg(List<NativeExpressADView> list) {

            }

            @Override
            public void onADClosed(NativeExpressADView nativeExpressADView) {

            }

            @Override
            public void onADClicked(NativeExpressADView nativeExpressADView) {

            }
        });
    }


    @Override
    public void onAdsList(int page) {
        String position = UserSpCache.getInstance(mContext).getAdType().getF_detail();
        AdsRequest request = AdsConfig.getRequest(mContext, page, position);
        mModel.getAdList(request, new DataResponseCallback<AdsReponse>() {
            @Override
            public void onSucceed(AdsReponse adsReponse) {
                mView.setAdsLit(adsReponse.getData());
            }

            @Override
            public void onFail(BaseResponse response) {

            }

            @Override
            public void onComplete() {

            }
        });
    }

    @Override
    public void onMoviePay() {
        MoviePayRequest request = mView.getMoviePayRequest();
        mModel.setMoviePay(request, new DataResponseCallback() {
            @Override
            public void onSucceed(Object o) {
                mView.onMoviePayOk();
            }

            @Override
            public void onFail(BaseResponse response) {
                if (response.getCode() == 10001) {
                    ToastUtils.show(mContext, response.getMsg(), 1);
                }
            }

            @Override
            public void onComplete() {

            }
        });
    }


    /**
     * 插入数据库
     *
     * @param movieDetailData
     * @param url
     */
    public void insertMovie(MovieDetailResponse.MovieDetailData movieDetailData, String url) {

        BaseDB baseDB = BaseDB.getInstance(mContext);
        baseDB.insertMovie(getMovie(movieDetailData, url));
        String suffix = CommonUtils.getFileExtensionFromUrl(url);
        LogUtil.showLog("msg----文件后缀:" + suffix);
        //  if (suffix.equals(Constant.M3U8)) {
        //是m3u8 文件格式
        ///   readM3u8(url);
        //}

        readM3u8(url);

    }


    public DownloadData getMovie(MovieDetailResponse.MovieDetailData movieDetailData, String
            url) {
        DownloadData data = new DownloadData();
        data.setVideo_id(movieDetailData.getId());
        data.setUrl(url);
        data.setName(movieDetailData.getM_name());
        data.setImagePath(movieDetailData.getM_img());
        return data;
    }

    /**
     * 视频分享
     *
     * @param type
     * @return
     */
    public String getShareJson(MovieShareResponse.Data mShareData, String url, int type) {
        JsShareType jsShareType = new JsShareType();
        jsShareType.setType(type);
        jsShareType.setWechatShareType(Wechat.SHARE_VIDEO);
        jsShareType.setTitle(mShareData.getTitle());
        jsShareType.setUrl(url);
        jsShareType.setContent(mShareData.getContent());
        jsShareType.setImgUrl(mShareData.getImg());
        return new Gson().toJson(jsShareType);
    }


    public void readM3u8(final String mUrl) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    downloadM3u8(mUrl);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }


    private void downloadM3u8(String mUrl) throws IOException {


        final String path = Constant.SAVE_MOVIE_PATH;
        URL url = new URL(mUrl);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setConnectTimeout(3000);
        InputStream inputStream = conn.getInputStream();
        byte[] mData = readInputStream(inputStream);

        File saveDir = new File(path);
        if (!saveDir.exists()) {
            saveDir.mkdirs();
        }

        File mFile = new File(saveDir + File.separator + "testM3u8.m3u8");
        FileOutputStream fos = new FileOutputStream(mFile);
        fos.write(mData);
        if (fos != null) {
            fos.close();
        }
        if (inputStream != null) {
            inputStream.close();
        }


    }

    /**
     * 从输入流中获取字节数组
     *
     * @param inputStream
     * @return
     * @throws IOException
     */
    public static byte[] readInputStream(InputStream inputStream) throws IOException {
        byte[] buffer = new byte[1024];
        int len = 0;
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        while ((len = inputStream.read(buffer)) != -1) {
            bos.write(buffer, 0, len);
        }
        bos.close();
        return bos.toByteArray();
    }
}
