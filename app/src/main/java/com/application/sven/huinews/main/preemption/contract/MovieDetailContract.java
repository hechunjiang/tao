package com.application.sven.huinews.main.preemption.contract;

import com.application.sven.huinews.base.BaseModel;
import com.application.sven.huinews.base.BasePresenter;
import com.application.sven.huinews.config.http.DataCallBack;
import com.application.sven.huinews.config.http.DataResponseCallback;
import com.application.sven.huinews.entity.AdsList;
import com.application.sven.huinews.entity.request.AdsRequest;
import com.application.sven.huinews.entity.request.CollectionRequest;
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
import com.application.sven.huinews.utils.ads.AdsConfig;
import com.application.sven.huinews.view.video.ListIjkVideoView;
import com.baidu.mobad.nativevideo.BaiduVideoResponse;
import com.dueeeke.videoplayer.player.IjkVideoView;
import com.qq.e.ads.nativ.NativeExpressADView;

import java.util.List;

/**
 * auther: sunfuyi
 * data: 2018/5/17
 * effect:
 */
public interface MovieDetailContract {
    abstract class Model extends BaseModel {
        public abstract void getMovieDetail(MovieDetailRequest request, DataResponseCallback callBack);

        public abstract void getMovieResPlay(MovieResPlayRequest request, DataResponseCallback callBack);

        public abstract void getMovieCollection(VideoCollectionRequest request, DataCallBack callBack);

        public abstract void setCancelMovieCollection(VideoCollectionCancelRequest request, DataCallBack callBack);

        public abstract void setMovieVisit(MovieVisitRequest request, DataCallBack callBack);

        public abstract void shareVideo(VideoShareUrlRequest request, DataResponseCallback callback);

        public abstract void shareMovie(MovieShareRequest request, DataResponseCallback callback);

        public abstract void shareVisit(ShareVisitRequest request, DataCallBack callBack);

        public abstract void getAdList(AdsRequest request, DataResponseCallback callBack);

        public abstract void setMoviePay(MoviePayRequest request, DataResponseCallback callback);

    }

    interface View {
        MovieDetailRequest getMovieDetailRequest();

        MovieResPlayRequest getMovieResPlayRequest(int aId, int playId, int rId);

        VideoCollectionRequest getVideoCollectionRequest();

        VideoCollectionCancelRequest getVideoCollectionCancelRequest();

        MovieVisitRequest getMovieVisitRequest();

        MovieShareRequest getmMovieShareRequest();

        MoviePayRequest getMoviePayRequest();

        void setMovieDetail(MovieDetailResponse.MovieDetailData mMovieData);

        void setMovieSource(MovieSource mMovieSource);

        //视频解析出错
        void setMoiveSourceError();


        //收藏成功
        void setMoiveCollectionOk();

        //取消收藏成功
        void setMovieCancelCollectionOk();

        void setVideoShareUrl(String url, int type);

        void setMovieShare(MovieShareResponse.Data mShareData, int type);

        void onVideoShare();

        void setAdsLit(List<AdsList> ads);

        void setTencentAds(List<NativeExpressADView> list);

        void onMoviePay();

        void onMoviePayOk();

        void onGetGold();

        void onShowShareDialog();
    }


    abstract class Presenter extends BasePresenter<MovieDetailContract.View, MovieDetailContract.Model> {
        public abstract void onMovieDetail();

        public abstract void onMovieResPlay(MovieResPlayRequest request);

        public abstract void setMovieConfig(ListIjkVideoView mIjkVideoView, MovieDetailResponse.MovieDetailData mMovieData, MovieSource mMovieSource, BaiduVideoResponse mNrAd, int playTime, int currentPoisiton);

        public abstract void onMovieCollection();

        public abstract void onMovieCancelCollection();

        public abstract void onMovieVisit();

        public abstract void onVideoShare(VideoShareUrlRequest request, int type);

        public abstract void onMovieShate(int type);

        public abstract void onShareVisit(String response, String videoType, int type);

        public abstract void onTencentAds(AdsConfig mAdsConfig);

        public abstract void onAdsList(int page);

        public abstract void onMoviePay();

    }
}
