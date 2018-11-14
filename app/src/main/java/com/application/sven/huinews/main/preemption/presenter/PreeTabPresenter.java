package com.application.sven.huinews.main.preemption.presenter;

import com.application.sven.huinews.config.http.DataResponseCallback;
import com.application.sven.huinews.db.UserSpCache;
import com.application.sven.huinews.entity.MovieHistory;
import com.application.sven.huinews.entity.request.AdsRequest;
import com.application.sven.huinews.entity.request.MovieWatchHistoryRequest;
import com.application.sven.huinews.entity.response.AdsReponse;
import com.application.sven.huinews.entity.response.BaseResponse;
import com.application.sven.huinews.entity.response.MovieContinuedResponse;
import com.application.sven.huinews.entity.response.MovieListResponse;
import com.application.sven.huinews.main.preemption.contract.PreeTabContract;
import com.application.sven.huinews.utils.LogUtil;
import com.application.sven.huinews.utils.ads.AdsConfig;
import com.qq.e.ads.nativ.NativeExpressADView;

import java.util.List;

/**
 * auther: sunfuyi
 * data: 2018/5/15
 * effect:
 */
public class PreeTabPresenter extends PreeTabContract.Presenter {
    @Override
    public void onMovieList() {
        mModel.getMovieList(mView.movieListRequest(), new DataResponseCallback<List<MovieListResponse.MovieData>>() {

            @Override
            public void onSucceed(List<MovieListResponse.MovieData> movieData) {
                mView.setMovieList(movieData);
            }

            @Override
            public void onFail(BaseResponse response) {
                mView.showErrorTip(response.getCode(), response.getMsg());
            }

            @Override
            public void onComplete() {

            }
        });
    }

    @Override
    public void onMovieHistory() {
        MovieWatchHistoryRequest request = mView.getMovieWatchHistoryRequest();
        mModel.getMovieHistory(request, new DataResponseCallback<MovieContinuedResponse>() {

            @Override
            public void onSucceed(MovieContinuedResponse movieContinuedResponse) {
                mView.setMovieHistory(movieContinuedResponse.getData());
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
    public void onPreeAds(int page) {
        String position = UserSpCache.getInstance(mContext).getAdType().getF_c_list();
        AdsRequest request = AdsConfig.getRequest(mContext, page, position);
        mModel.getPreeAds(request, new DataResponseCallback<AdsReponse>() {
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
    public void onTencentAds(AdsConfig mAdsConfig, int count) {
        mAdsConfig.tencentNativeAD(count, AdsConfig.TENCENT_AD_ID);
        mAdsConfig.setSetAdLoadedLisenter(new AdsConfig.setAdLoadedLisenter() {
            @Override
            public void onLoadAd(List<NativeExpressADView> list) {
                mView.setTencentAds(list);
            }

            @Override
            public void onLoadRightImg(List<NativeExpressADView> list) {
               // mView.setTencentRightImgAds(list);
            }

            @Override
            public void onADClosed(NativeExpressADView nativeExpressADView) {

            }

            @Override
            public void onADClicked(NativeExpressADView nativeExpressADView) {

            }
        });

    }
   /* @Override
    public void onTencentAds(AdsConfig mAdsConfig) {
        mAdsConfig.setSetAdLoadedLisenter(new AdsConfig.setAdLoadedLisenter() {
            @Override
            public void onLoadAd(List<NativeExpressADView> list) {
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
    }*/
}
