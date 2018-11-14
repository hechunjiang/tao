package com.application.sven.huinews.main.welcome.presenter;

import android.text.TextUtils;

import com.application.sven.huinews.config.Constant;
import com.application.sven.huinews.config.http.DataCallBack;
import com.application.sven.huinews.config.http.DataResponseCallback;
import com.application.sven.huinews.db.SpUtil;
import com.application.sven.huinews.db.UserSpCache;
import com.application.sven.huinews.entity.AdsList;
import com.application.sven.huinews.entity.request.AdsRequest;
import com.application.sven.huinews.entity.request.TempLoginRequest;
import com.application.sven.huinews.entity.response.AdsReponse;
import com.application.sven.huinews.entity.response.BaseResponse;
import com.application.sven.huinews.entity.response.VideoChannelResponse;
import com.application.sven.huinews.main.welcome.contract.TempLoginContract;
import com.application.sven.huinews.utils.LogUtil;
import com.application.sven.huinews.utils.ToastUtils;
import com.application.sven.huinews.utils.ads.AdsConfig;

import java.util.List;


/**
 * auther: sunfuyi
 * data: 2018/5/14
 * effect:
 */
public class TempPresenter extends TempLoginContract.Presenter {

    @Override
    public void onTempLogin() {
        mModel.onTempLogin(new DataCallBack() {
            @Override
            public void onComplete() {

            }

            @Override
            public void onSucceed(String json) {
                mView.tempLoginOk();
            }

            @Override
            public void onFail(BaseResponse baseResponse) {

            }
        });
    }

    @Override
    public void onChannelList() {
        mModel.getChannelType(new DataResponseCallback<VideoChannelResponse.ChannelData>() {
            @Override
            public void onComplete() {
                LogUtil.showLog("msg ==onComplete");
            }

            @Override
            public void onSucceed(VideoChannelResponse.ChannelData videoChannelResponse) {
                if (videoChannelResponse != null) {
                    SpUtil.putList(mContext, Constant.CHANNEL_VIDEO_LIST, videoChannelResponse.getV());
                    SpUtil.putList(mContext, Constant.CHANNEL_MOVIE_LIST, videoChannelResponse.getM());
                    SpUtil.putList(mContext, Constant.CHANNEL_BOOK_LIST, videoChannelResponse.getB());
                }
            }

            @Override
            public void onFail(BaseResponse baseResponse) {
                mView.showErrorTip(baseResponse.getCode(), baseResponse.getMsg());
            }
        });
    }

    @Override
    public void onAdsType() {


        AdsRequest request = AdsConfig.getRequest(mContext, 1, "open");

        LogUtil.showLog("msg--adsType：" + request.toString());
        mModel.onOpenAd(request, new DataResponseCallback<AdsReponse>() {

            @Override
            public void onSucceed(AdsReponse adsReponse) {
                //判断开屏广告，洛米，腾讯广点通，百度开屏
                mView.setAds(adsReponse.getData());
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
}
