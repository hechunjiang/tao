package com.application.sven.huinews.main.welcome.model;

import com.application.sven.huinews.AppConfig;
import com.application.sven.huinews.config.PhoneUtils;
import com.application.sven.huinews.config.http.DataCallBack;
import com.application.sven.huinews.config.http.DataResponseCallback;
import com.application.sven.huinews.config.http.MyRetrofit;
import com.application.sven.huinews.db.UserSpCache;
import com.application.sven.huinews.entity.request.AdsRequest;
import com.application.sven.huinews.entity.request.ChannelTypeRequest;
import com.application.sven.huinews.entity.request.TempLoginRequest;
import com.application.sven.huinews.entity.response.AdsReponse;
import com.application.sven.huinews.entity.response.BaseResponse;
import com.application.sven.huinews.entity.response.LoginResponse;
import com.application.sven.huinews.entity.response.VideoChannelResponse;
import com.application.sven.huinews.main.welcome.contract.TempLoginContract;
import com.application.sven.huinews.utils.LogUtil;
import com.google.gson.Gson;

/**
 * auther: sunfuyi
 * data: 2018/5/14
 * effect:
 */
public class TempLoginModel extends TempLoginContract.Model {

    @Override
    public void onTempLogin(final DataCallBack callBack) {
        TempLoginRequest request = new TempLoginRequest();
        request.setMobileBrand(PhoneUtils.getPhoneBrand());
        getRetrofit().onTempLogin(request, new DataCallBack() {
            @Override
            public void onComplete() {
                callBack.onComplete();
            }

            @Override
            public void onSucceed(String json) {
                LogUtil.showJson("msg----onTempLogin:", json);
                LoginResponse mResponse = new Gson().fromJson(json, LoginResponse.class);
                UserSpCache.getInstance(AppConfig.getAppContext()).putString(UserSpCache.KEY_TICKET, mResponse.getData().getLoginTicket());
                UserSpCache.getInstance(AppConfig.getAppContext()).putBoolean(UserSpCache.KEY_IS_USER_LOGIN, mResponse.getData().isUserLogin());
                UserSpCache.getInstance(AppConfig.getAppContext()).putUser(mResponse.getData().getUser());
                callBack.onSucceed(json);
            }

            @Override
            public void onFail(BaseResponse baseResponse) {
                callBack.onFail(baseResponse);

            }
        });
    }


    @Override
    public void getChannelType(final DataResponseCallback callBack) {
        getRetrofit().onChannelType(new ChannelTypeRequest(), new DataCallBack() {
            @Override
            public void onComplete() {
                callBack.onComplete();
            }

            @Override
            public void onSucceed(String json) {
                LogUtil.showJson("getChannelType", json);
                VideoChannelResponse response = new Gson().fromJson(json, VideoChannelResponse.class);
                callBack.onSucceed(response.getData());
            }

            @Override
            public void onFail(BaseResponse baseResponse) {
                callBack.onFail(baseResponse);
            }
        });
    }

    @Override
    public void onOpenAd(AdsRequest request, final DataResponseCallback callback) {
        getRetrofit().onAds(request, new DataCallBack() {
            @Override
            public void onComplete() {
                callback.onComplete();
            }

            @Override
            public void onSucceed(String json) {
                LogUtil.showJson("onOpenAd", json);
                AdsReponse reponse = new Gson().fromJson(json, AdsReponse.class);
                callback.onSucceed(reponse);
            }

            @Override
            public void onFail(BaseResponse baseResponse) {
                callback.onFail(baseResponse);
            }
        });
    }
}
