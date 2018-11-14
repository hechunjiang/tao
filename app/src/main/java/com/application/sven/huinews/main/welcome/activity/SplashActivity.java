package com.application.sven.huinews.main.welcome.activity;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.application.sven.huinews.R;
import com.application.sven.huinews.base.BaseActivity;
import com.application.sven.huinews.config.LocationUtils;
import com.application.sven.huinews.config.MovieData;
import com.application.sven.huinews.config.PhoneUtils;
import com.application.sven.huinews.config.http.DataCallBack;
import com.application.sven.huinews.config.http.MyRetrofit;
import com.application.sven.huinews.db.UserSpCache;
import com.application.sven.huinews.entity.AdsList;
import com.application.sven.huinews.entity.request.TempLoginRequest;
import com.application.sven.huinews.entity.response.BaseResponse;
import com.application.sven.huinews.main.home.activity.MainActivity;
import com.application.sven.huinews.main.login.activity.LoginActivity;
import com.application.sven.huinews.main.preemption.activity.MovieDetailsActivity;
import com.application.sven.huinews.main.welcome.contract.TempLoginContract;
import com.application.sven.huinews.main.welcome.model.TempLoginModel;
import com.application.sven.huinews.main.welcome.presenter.TempPresenter;
import com.application.sven.huinews.utils.AppStatusManager;
import com.application.sven.huinews.utils.FrescoUtil;
import com.application.sven.huinews.utils.LogUtil;
import com.application.sven.huinews.utils.NetWorkUtils;
import com.application.sven.huinews.utils.ToastUtils;
import com.application.sven.huinews.utils.ads.AdsConfig;
import com.application.sven.huinews.utils.glidUtils.GlideUtils;
import com.application.sven.huinews.utils.statusbar.Eyes;
import com.application.sven.huinews.utils.umeng.UMengUtils;
import com.baidu.mobads.SplashAd;
import com.baidu.mobads.SplashAdListener;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.facebook.drawee.view.SimpleDraweeView;
import com.google.gson.Gson;
import com.qq.e.ads.splash.SplashAD;
import com.qq.e.ads.splash.SplashADListener;
import com.qq.e.comm.util.AdError;
import com.umeng.analytics.MobclickAgent;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

/**
 * auther: sunfuyi
 * data: 2018/5/14
 * effect: 欢迎页
 */
public class SplashActivity extends BaseActivity<TempPresenter, TempLoginModel> implements TempLoginContract.View {
    private TextView tv_count_down;

    private CountTimeUtils mCountTimeUtils;
    private UserSpCache mUserSpCache;
    private RelativeLayout adsRl;
    private int count = 1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        AppStatusManager.getInstance().setAppStatus(AppStatusManager.AppStatusConstant.APP_NORMAL);
        super.onCreate(savedInstanceState);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_splash;
    }

    @Override
    public void initView() {
        tv_count_down = findViewById(R.id.tv_count_down);
        adsRl = findViewById(R.id.adsRl);
    }

    @Override
    public void initEvents() {

    }

    @Override
    public void onClickEvent(View v) {

    }

    @Override
    public void initObject() {
        setMVP();
        mPresenter.onChannelList();
        mUserSpCache = UserSpCache.getInstance(getApplicationContext());
        mCountTimeUtils = new CountTimeUtils(10000, 1000);

        if (TextUtils.isEmpty(mUserSpCache.getStringData(UserSpCache.KEY_PHONE))) {
            mPresenter.onTempLogin();
        }

        if (Build.VERSION.SDK_INT >= 23) {
            checkAndRequestPermission();
        } else {
            // 如果是Android6.0以下的机器，默认在安装时获得了所有权限，可以直接调用SDK
            mCountTimeUtils.start();
            getNetIp();
        }
    }


    @Override
    public void tempLoginOk() {
        //toMain();
    }

    @Override
    public void setAds(List<AdsList> ads) {

        if (ads.get(0).getAd_type().equals(AdsConfig.AD_TYPE_BAIDU)) {
            //百度开屏
            baiduAds();
            tv_count_down.setVisibility(View.GONE);
        } else if (ads.get(0).getAd_type().equals(AdsConfig.AD_TYPE_TENCENT)) {
            //腾讯开屏
            tencentAds();
            tv_count_down.setVisibility(View.GONE);
        } else if (ads.get(0).getAd_type().equals(AdsConfig.AD_TYPE_LM)) {
            //洛米开屏
            lmAds(ads);
        }
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showErrorTip(int code, String msg) {
        LogUtil.showLog("msg----code:" + code + "   msg:" + msg);
        // mCountTimeUtils.start();
    }

    private class CountTimeUtils extends CountDownTimer {
        public CountTimeUtils(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onTick(long millisUntilFinished) {
            tv_count_down.setText(millisUntilFinished / 1000 + "秒");
        }

        @Override
        public void onFinish() {
            toMain();
        }
    }

    private void toMain() {

        saveOpenCount();

        //如果首次安装app，则跳转引导页
        if (TextUtils.isEmpty(mUserSpCache.getStringData(UserSpCache.KEY_IS_FIRST_OPEN_APP))) {
            mUserSpCache.putString(UserSpCache.KEY_IS_FIRST_OPEN_APP, "has_open_app");
            GuideActivity.toThis(mContext);
            finish();
            return;
        } else {
            int totalCount = mUserSpCache.getInt(UserSpCache.NEEDCOUNT_LOGIN);
            int count = mUserSpCache.getInt(UserSpCache.OPEN_COUNT) + 1;
            if (totalCount == -1) {
                totalCount = 0;
            }
            if (count >= totalCount) {
                //是否第五次打开app
                //如果是第五次打开，判断用户是否登陆过，已经登录直接跳转首页，未登录跳转登录
                if (!TextUtils.isEmpty(mUserSpCache.getStringData(UserSpCache.KEY_PHONE))) {
                    MainActivity.toThis(mContext);
                    finish();
                } else {
                    LoginActivity.toThis(mContext, true, true);
                    finish();
                }
            } else {
                MainActivity.toThis(mContext);
                finish();
            }
        }
    }


    private void getNetIp() {
        mCountTimeUtils.start();
        new Thread(new Runnable() {
            @Override
            public void run() {
            /*   LocationUtils locationUtils = new LocationUtils(mContext);
                locationUtils.init();*/
                String netIp = NetWorkUtils.getNetIp();
                LogUtil.showLog("msg---ip：" + netIp + "：---- imei:" + PhoneUtils.getPhoneIMEI(mContext) + "----- androidid:" + PhoneUtils.getAndroidId(mContext));
                UserSpCache.getInstance(mContext).putString(UserSpCache.IP, netIp);
                mPresenter.onAdsType();


            }
        }).start();
    }

    private void saveOpenCount() {
        count = mUserSpCache.getInt(UserSpCache.OPEN_COUNT);
        count++;
        UserSpCache.getInstance(this).putInt(UserSpCache.OPEN_COUNT, count);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mCountTimeUtils != null) {
            mCountTimeUtils.cancel();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1024 && hasAllPermissionsGranted(grantResults)) {
            //已有权限
            LogUtil.showLog("msg---已申请到权限");
            getNetIp();

        } else {
            checkAndRequestPermission();
        }
    }


    /**
     * ----------非常重要----------
     * <p>
     * Android6.0以上的权限适配简单示例：
     * <p>
     * 如果targetSDKVersion >= 23，那么必须要申请到所需要的权限，再调用广点通SDK，否则广点通SDK不会工作。
     * <p>
     * Demo代码里是一个基本的权限申请示例，请开发者根据自己的场景合理地编写这部分代码来实现权限申请。
     * 注意：下面的`checkSelfPermission`和`requestPermissions`方法都是在Android6.0的SDK中增加的API，如果您的App还没有适配到Android6.0以上，则不需要调用这些方法，直接调用广点通SDK即可。
     */
    @TargetApi(Build.VERSION_CODES.M)
    private void checkAndRequestPermission() {
        List<String> lackedPermission = new ArrayList<String>();
        if (!(checkSelfPermission(Manifest.permission.READ_PHONE_STATE) == PackageManager.PERMISSION_GRANTED)) {
            lackedPermission.add(Manifest.permission.READ_PHONE_STATE);
        }
        if (!(checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED)) {
            lackedPermission.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
        if (!(checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED)) {
            lackedPermission.add(Manifest.permission.ACCESS_FINE_LOCATION);
        }

        // 权限都已经有了，那么直接调用SDK
        if (lackedPermission.size() == 0) {
            //fetchSplashAD(this, container, skipView, Constants.APPID, getPosId(), this, 0);
            getNetIp();
        } else {
            // 请求所缺少的权限，在onRequestPermissionsResult中再看是否获得权限，如果获得权限就可以调用SDK，否则不要调用SDK。
            String[] requestPermissions = new String[lackedPermission.size()];
            lackedPermission.toArray(requestPermissions);
            requestPermissions(requestPermissions, 1024);
        }
    }

    private boolean hasAllPermissionsGranted(int[] grantResults) {
        for (int grantResult : grantResults) {
            if (grantResult == PackageManager.PERMISSION_DENIED) {
                return false;
            }
        }
        return true;
    }


    /**
     * 百度开屏
     */
    private void baiduAds() {
        SplashAdListener listener = new SplashAdListener() {
            @Override
            public void onAdPresent() {
                Log.i("RSplashActivity", "onAdPresent");
                mCountTimeUtils.cancel();
                MobclickAgent.onEvent(SplashActivity.this, UMengUtils.AD_BAIDU_SPLASH_PREVIEW);
                MobclickAgent.onEvent(SplashActivity.this, UMengUtils.AD_BAIDU_SPLASH_PREVIEW, "ad_baidu_splash_preview");
            }

            @Override
            public void onAdDismissed() {
                toMain();
                Log.i("RSplashActivity", "onAdDismissed");
            }

            @Override
            public void onAdFailed(String s) {
                toMain();
                Log.i("RSplashActivity", "onAdFailed");
            }

            @Override
            public void onAdClick() {
                Log.i("RSplashActivity", "onAdClick");
                MobclickAgent.onEvent(SplashActivity.this, UMengUtils.AD_BAIDU_SPLASH);
                MobclickAgent.onEvent(SplashActivity.this, UMengUtils.AD_BAIDU_SPLASH, "ad_baidu_splash");

            }
        };
        SplashAd.setMaxVideoCacheCapacityMb(30);
        new SplashAd(this, adsRl, listener, AdsConfig.SPLASH_ID, true);
    }

    /**
     * 洛米开屏
     */
    private void lmAds(final List<AdsList> ads) {
        final ImageView iv = new ImageView(mContext);
        GlideUtils.loadAdImg(mContext, ads.get(0).getImg(), new SimpleTarget<Drawable>() {
            @Override
            public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                iv.setImageDrawable(resource);
                lmAdExposure(ads.get(0).getReport().getImp());
            }
        });
        iv.setLayoutParams(new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        adsRl.removeAllViews();
        adsRl.addView(iv);

        iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setAdClick(ads.get(0).getReport().getClk());
            }
        });


    }

    /**
     * 广告曝光
     *
     * @param s
     */
    private void lmAdExposure(String[] s) {
        if (s != null && s.length > 0) {
            for (String imp : s) {
                MyRetrofit.getInstance(mContext, null).adImp(imp, new DataCallBack() {
                    @Override
                    public void onComplete() {
                        LogUtil.showLog("msg--onComplete-");
                    }

                    @Override
                    public void onSucceed(String json) {
                        LogUtil.showLog("msg--开屏洛米曝光-");
                    }

                    @Override
                    public void onFail(BaseResponse baseResponse) {
                        LogUtil.showLog("msg--baseResponse-");
                    }
                });
            }
        }
    }

    /**
     * 广告点击监听
     *
     * @param s
     */
    private void setAdClick(String[] s) {
        if (s != null && s.length > 0) {
            for (String clk : s) {
                MyRetrofit.getInstance(mContext, null).adClick(clk, new DataCallBack() {
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
        }
    }


    private SplashAD splashAD;

    /**
     * 腾讯开屏
     */
    private void tencentAds() {
        splashAD = new SplashAD(this, adsRl, AdsConfig.TENCENT__ID, AdsConfig.TENCENT_SPLASH_ID, new SplashADListener() {
            @Override
            public void onADDismissed() {
                toMain();
                finish();
            }

            @Override
            public void onNoAD(AdError adError) {
                toMain();
                finish();
            }

            @Override
            public void onADPresent() {
                mCountTimeUtils.cancel();
                MobclickAgent.onEvent(SplashActivity.this, UMengUtils.AD_TENCENT_SPLASH_PREVIEW);
                MobclickAgent.onEvent(SplashActivity.this, UMengUtils.AD_TENCENT_SPLASH_PREVIEW, "ad_tencent_splash_preview");
            }

            @Override
            public void onADClicked() {
                MobclickAgent.onEvent(SplashActivity.this, UMengUtils.AD_TENCENT_SPLASH);
                MobclickAgent.onEvent(SplashActivity.this, UMengUtils.AD_TENCENT_SPLASH, "ad_tencent_splash");
            }

            @Override
            public void onADTick(long l) {


            }
        }, 0);


    }

    //防止用户返回键退出APP
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK || keyCode == KeyEvent.KEYCODE_HOME) {
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }


    private String url = "http://cache.m.iqiyi.com/jp/tmts/1007619200/87dbcb12c7dda4732c8110f82bb56ef1/?uid=&cupid=qc_100001_100102&platForm=h5&qyid=2123ef4383823698d77ff336ca8a5136&agenttype=12&type=m3u8&rate=2&k_ft1=8&sgti=12_2123ef4383823698d77ff336ca8a5136_1528690963&qdv=1&qdx=n&qdy=x&qds=0&__jsT=sgve&t=1528690963&src=02020031010000000000&callback=tmtsCallback&vf=e18c8569df82f1bcb7b75482f2fbe204";


}
