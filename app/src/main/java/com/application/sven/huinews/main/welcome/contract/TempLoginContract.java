package com.application.sven.huinews.main.welcome.contract;

import com.application.sven.huinews.base.BaseModel;
import com.application.sven.huinews.base.BasePresenter;
import com.application.sven.huinews.base.BaseView;
import com.application.sven.huinews.config.http.DataCallBack;
import com.application.sven.huinews.config.http.DataResponseCallback;
import com.application.sven.huinews.entity.AdsList;
import com.application.sven.huinews.entity.request.AdsRequest;
import com.application.sven.huinews.entity.request.TempLoginRequest;

import java.util.List;

import io.reactivex.Observable;


/**
 * auther: sunfuyi
 * data: 2018/5/14
 * effect:
 */
public interface TempLoginContract {
    abstract class Model extends BaseModel {
        //临时登录
        public abstract void onTempLogin(DataCallBack callBack);

        //频道列表
        public abstract void getChannelType(DataResponseCallback callBack);

        //开屏广告
        public abstract void onOpenAd(AdsRequest request, DataResponseCallback callback);
    }

    interface View extends BaseView {
        void tempLoginOk();

        void setAds(List<AdsList> ads);
    }

    abstract class Presenter extends BasePresenter<TempLoginContract.View, TempLoginContract.Model> {
        //临时用户登录
        public abstract void onTempLogin();

        //频道列表
        public abstract void onChannelList();

        //开屏广告
        public abstract void onAdsType();
    }
}
