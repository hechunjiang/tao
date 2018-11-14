package com.application.sven.huinews.utils.ads;

import android.app.Activity;
import android.content.Context;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import com.application.sven.huinews.R;
import com.application.sven.huinews.config.AppInfo;
import com.application.sven.huinews.db.UserSpCache;
import com.application.sven.huinews.entity.request.AdsRequest;
import com.application.sven.huinews.utils.LogUtil;
import com.application.sven.huinews.utils.NetWorkUtils;
import com.application.sven.huinews.utils.ScreensUitls;
import com.baidu.mobad.feeds.NativeErrorCode;
import com.baidu.mobad.feeds.RequestParameters;
import com.baidu.mobad.nativevideo.BaiduVideoNative;
import com.baidu.mobad.nativevideo.BaiduVideoResponse;
import com.baidu.mobads.AdView;
import com.baidu.mobads.AdViewListener;
import com.qq.e.ads.banner.AbstractBannerADListener;
import com.qq.e.ads.banner.BannerView;
import com.qq.e.ads.nativ.ADSize;
import com.qq.e.ads.nativ.NativeAD;
import com.qq.e.ads.nativ.NativeADDataRef;
import com.qq.e.ads.nativ.NativeExpressAD;
import com.qq.e.ads.nativ.NativeExpressADView;
import com.qq.e.comm.util.AdError;
import com.wmcsk.KuaiyanSdk;
import com.wmcsk.defaultImp.DefaultGuangaoAdapter;
import com.wmcsk.dl.bean.SDKConfig;

import org.json.JSONObject;

import java.util.List;

import sdkshell.kuaiyan.com.kuaiyansdk.SdkInit;
import sdkshell.kuaiyan.com.kuaiyansdk.listener.KuaiyanSdkInitListener;

/**
 * auther: sunfuyi
 * data: 2018/6/4
 * effect:
 */
public class AdsConfig {
    private Context mContext;
    public static String SPLASH_ID = "5589110"; //开屏广告位  百度
    public static String BANNER_ID = "5595705";  //横屏广告
    public static String VIDEO_ID = "5835415";//视频贴片

    public static String TENCENT__ID = "1106761213";
    public static String TENCENT_SPLASH_ID = "4000032446395806"; //开屏广告
    public static String TENCENT_AD_ID = "3040438256167994"; //腾讯广点通  广告位id 大图
    public static String TENCENT_AD_RIGHT_IMG = "2070435407501213"; //腾讯广点通 左文右图
    public static String TENCENT_AD_LEFT_IMG = "2030335457078159"; //腾讯广点通 左图右文
    public static String TENCENT_AD_BIG_IMG = "3040438256167994"; //腾讯广点通 纯图片
    public static String TENCENT_AD_BANNER = "3040739595287462"; //腾讯广点通 banner


    public static final String AD_TYPE_TENCENT = "tx_gdt";
    public static final String AD_TYPE_TENCENT_RIGHT = "right_pic"; //右图左文
    public static final String AD_TYPE_TENCENT_SINGLE = "single"; //大图

    public static final String AD_TYPE_KUAIYAN_KEY = "6h97GsY2RCWbg0RvBoo8gF27IjIKK4jf"; //快眼key
    public static final String AD_TYPE_KUAIYAN_ID = "18052"; //快眼广告id
    public static final String AD_TYPE_KUAIYAN_THREE_ID = "1456";//上文下三图

    public static final String AD_TYPE_LM = "cferw";
    public static final String AD_TYPE_BAIDU = "baidu";


    public AdsConfig(Context mContext) {
        this.mContext = mContext;
    }


    /**
     * 广告请求参数
     *
     * @param mContext
     * @param page
     * @param position
     * @return
     */
    public static AdsRequest getRequest(Context mContext, int page, String position) {
        AdsRequest request = new AdsRequest();
        request.setUser_agent(AppInfo.getUserAgent());
        request.setWidth(ScreensUitls.getScreenWidth(mContext) + "");
        request.setHeight(ScreensUitls.getScreenHeight(mContext) + "");
        request.setMobile_brand(AppInfo.getPhoneBrand(mContext));
        request.setMobile_model(AppInfo.getPhoneModel(mContext));
        request.setMobile_version(AppInfo.getVersion(mContext));
        request.setIp(UserSpCache.getInstance(mContext).getStringData(UserSpCache.IP));
        request.setPage(page);
        request.setPosition(position);
        String netWorkType = NetWorkUtils.getNetWorkType(mContext);
        if (netWorkType.equals("WIFI")) {
            request.setNetwork_type(1);
        } else if (netWorkType.equals("4G")) {
            request.setNetwork_type(5);
        } else if (netWorkType.equals("3G")) {
            request.setNetwork_type(4);
        } else if (netWorkType.equals("2G")) {
            request.setNetwork_type(3);
        }

        return request;
    }


    AdView adView;

    /**
     * baidu  banner广告
     *
     * @param context
     * @param rl
     */
    public void getBaiduBannerAd(Context context, final RelativeLayout rl) {
        adView = new AdView(context, BANNER_ID);
        adView.setListener(new AdViewListener() {
            @Override
            public void onAdReady(AdView adView) {
                rl.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAdShow(JSONObject jsonObject) {

            }

            @Override
            public void onAdClick(JSONObject jsonObject) {

            }

            @Override
            public void onAdFailed(String s) {
                rl.setVisibility(View.GONE);
            }

            @Override
            public void onAdSwitch() {

            }

            @Override
            public void onAdClose(JSONObject jsonObject) {

            }
        });
        DisplayMetrics dm = new DisplayMetrics();
        ((WindowManager) context.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay().getMetrics(dm);
        int winW = dm.widthPixels;
        int winH = dm.heightPixels;
        int width = Math.min(winW, winH);
        int height = width * 3 / 20;
//将 adView 添加到父控件中（注：该父控件不一定为您的根控件，只要该控件能通过 addView添加广告视图即可）
        RelativeLayout.LayoutParams rllp = new RelativeLayout.LayoutParams(width, height);
        rllp.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        rl.addView(adView, rllp);
    }

    /**
     * 释放广告view
     */
    public void destoryBdAd() {
        if (adView != null) {
            adView.destroy();
        }
    }


    /**
     * 百度视频贴片广告
     *
     * @param activity
     */
    public void fetchBdVideoPatch(Activity activity) {

    }


    private setAdLoadedLisenter setAdLoadedLisenter;

    public interface setAdLoadedLisenter {
        void onLoadAd(List<NativeExpressADView> list);

        void onLoadRightImg(List<NativeExpressADView> list);

        void onADClosed(NativeExpressADView nativeExpressADView);

        void onADClicked(NativeExpressADView nativeExpressADView);


    }

    public void setSetAdLoadedLisenter(setAdLoadedLisenter setAdLoadedLisenter) {
        this.setAdLoadedLisenter = setAdLoadedLisenter;
    }

    /**
     * 腾讯大图信息流
     *
     * @param count
     */
    public void tencentNativeAD(int count, String adsId) {
        int width = ScreensUitls.getScreenWidth(mContext);
        NativeExpressAD expressAD = new NativeExpressAD(mContext, new ADSize(width, ADSize.AUTO_HEIGHT), TENCENT__ID, adsId, new NativeExpressAD.NativeExpressADListener() {
            @Override
            public void onNoAD(AdError adError) {
                LogUtil.showLog("NativeExpressAD---onNoAD");
            }

            @Override
            public void onADLoaded(List<NativeExpressADView> list) {
                LogUtil.showLog("msg----onADLoaded:" + list.size());
                if (setAdLoadedLisenter != null) {
                    setAdLoadedLisenter.onLoadAd(list);
                }


            }

            @Override
            public void onRenderFail(NativeExpressADView nativeExpressADView) {
                LogUtil.showLog("NativeExpressAD---onRenderFail");
            }

            @Override
            public void onRenderSuccess(NativeExpressADView nativeExpressADView) {
                LogUtil.showLog("NativeExpressAD---onRenderSuccess");
            }

            @Override
            public void onADExposure(NativeExpressADView nativeExpressADView) {
                LogUtil.showLog("NativeExpressAD---onADExposure");

            }

            @Override
            public void onADClicked(NativeExpressADView nativeExpressADView) {
                LogUtil.showLog("NativeExpressAD---onADClicked");
                if (setAdLoadedLisenter != null) {
                    setAdLoadedLisenter.onADClicked(nativeExpressADView);
                }
            }

            @Override
            public void onADClosed(NativeExpressADView nativeExpressADView) {
                LogUtil.showLog("NativeExpressAD---onADClosed");
                if (setAdLoadedLisenter != null) {
                    setAdLoadedLisenter.onADClosed(nativeExpressADView);
                }
            }

            @Override
            public void onADLeftApplication(NativeExpressADView nativeExpressADView) {
                LogUtil.showLog("NativeExpressAD---onADLeftApplication");

            }

            @Override
            public void onADOpenOverlay(NativeExpressADView nativeExpressADView) {
                LogUtil.showLog("NativeExpressAD---onADOpenOverlay");
            }

            @Override
            public void onADCloseOverlay(NativeExpressADView nativeExpressADView) {
                LogUtil.showLog("NativeExpressAD---onADCloseOverlay");
            }
        });
        expressAD.loadAD(count);
    }


    public void tencentNativeRightImgAD(int count, String adsId) {
        int width = ScreensUitls.getScreenWidth(mContext);
        NativeExpressAD expressAD = new NativeExpressAD(mContext, new ADSize(width, ADSize.AUTO_HEIGHT), TENCENT__ID, adsId, new NativeExpressAD.NativeExpressADListener() {
            @Override
            public void onNoAD(AdError adError) {
                LogUtil.showLog("NativeExpressAD---onNoAD");
            }

            @Override
            public void onADLoaded(List<NativeExpressADView> list) {
                if (setAdLoadedLisenter != null) {
                    setAdLoadedLisenter.onLoadRightImg(list);
                }

                for (int i = 0; i < list.size(); i++) {
                    LogUtil.showLog("msg----左文广告>:" + list.get(i).getBoundData().getTitle());
                }
            }

            @Override
            public void onRenderFail(NativeExpressADView nativeExpressADView) {
                LogUtil.showLog("NativeExpressAD---onRenderFail");
            }

            @Override
            public void onRenderSuccess(NativeExpressADView nativeExpressADView) {
                LogUtil.showLog("NativeExpressAD---onRenderSuccess");
            }

            @Override
            public void onADExposure(NativeExpressADView nativeExpressADView) {
                LogUtil.showLog("NativeExpressAD---onADExposure");

            }

            @Override
            public void onADClicked(NativeExpressADView nativeExpressADView) {
                LogUtil.showLog("NativeExpressAD---onADClicked");
                if (setAdLoadedLisenter != null) {
                    setAdLoadedLisenter.onADClicked(nativeExpressADView);
                }
            }

            @Override
            public void onADClosed(NativeExpressADView nativeExpressADView) {
                LogUtil.showLog("NativeExpressAD---onADClosed");
                if (setAdLoadedLisenter != null) {
                    setAdLoadedLisenter.onADClosed(nativeExpressADView);
                }
            }

            @Override
            public void onADLeftApplication(NativeExpressADView nativeExpressADView) {
                LogUtil.showLog("NativeExpressAD---onADLeftApplication");

            }

            @Override
            public void onADOpenOverlay(NativeExpressADView nativeExpressADView) {
                LogUtil.showLog("NativeExpressAD---onADOpenOverlay");
            }

            @Override
            public void onADCloseOverlay(NativeExpressADView nativeExpressADView) {
                LogUtil.showLog("NativeExpressAD---onADCloseOverlay");
            }
        });
        expressAD.loadAD(count);
    }


    BannerView mBannerView;

    public void tencentAdBanner(Activity activity, final RelativeLayout rl) {
        mBannerView = new BannerView(activity, com.qq.e.ads.banner.ADSize.BANNER, TENCENT__ID, TENCENT_AD_BANNER);
        mBannerView.setRefresh(30);
        mBannerView.setADListener(new AbstractBannerADListener() {

            @Override
            public void onNoAD(AdError error) {
                rl.setVisibility(View.GONE);
                Log.i("AD_DEMO", "BannerNoAD，eCode=" + error.getErrorCode());
            }

            @Override
            public void onADReceiv() {
                rl.setVisibility(View.VISIBLE);
                rl.removeAllViews();
                rl.addView(mBannerView);
                Log.i("AD_DEMO", "ONBannerReceive");
            }
        });
        mBannerView.loadAD();
    }

    public void destroyTencentAdBanner() {
        if (mBannerView != null) {
            mBannerView.destroy();
        }
    }


    /**
     * 百度贴片广告
     */
    public void baiduPreroll(Activity activity) {
        BaiduVideoNative baidu = new BaiduVideoNative(activity, VIDEO_ID, new BaiduVideoNative.BaiduVideoNetworkListener() {
            @Override
            public void onAdLoad(List<BaiduVideoResponse> list) {
                if (list != null && list.size() > 0) {
                    BaiduVideoResponse response = list.get(0);
                    LogUtil.showLog("msg----baidu:" + "response.getMaterialType() is" + response.getMaterialType());
                    if (response.getMaterialType() == BaiduVideoResponse.PrerollMaterialType.VIDEO) {
                        //视频广告
                        LogUtil.showLog("msg----baidu:收到视频广告:" + response.getVideoUrl() + ",duration:" + response.getDuration());
                        if (mOnBaiduPrerollLisenter != null) {
                            mOnBaiduPrerollLisenter.baiduAdPaly(response);
                        }
                    } else if (response.getMaterialType() == BaiduVideoResponse.PrerollMaterialType.NORMAL) {
                        //图片广告
                    } else if (response.getMaterialType() == BaiduVideoResponse.PrerollMaterialType.GIF) {
                        //gif广告
                    } else {
                        LogUtil.showLog("msg---无聊类型不支持");
                    }
                }
            }

            @Override
            public void onAdFail(NativeErrorCode nativeErrorCode) {
                LogUtil.showLog("msg----没有收到广告，请检查");
                if (mOnBaiduPrerollLisenter != null) {
                    mOnBaiduPrerollLisenter.baiduAdFail();
                }
            }
        });

        /**
         * step 2、创建requestParameter 对象
         */
        RequestParameters requestParameters = new RequestParameters.Builder().setWidth(activity.getResources().getDisplayMetrics().widthPixels)
                .setHeight((int) (250 * activity.getResources().getDisplayMetrics().density)).build();
        baidu.makeRequest(requestParameters);

    }


    public void kuaiyanAd(FrameLayout fl) {
        KuaiyanSdk.getInstance().requestAd("Index3Activity_six", fl, AD_TYPE_KUAIYAN_THREE_ID, new DefaultGuangaoAdapter() {
            @Override
            public void onSuccess(Object type) {
                LogUtil.showLog("msg---onSuccess：" + type.toString());
            }

            @Override
            public void onNetWorkError() {
                LogUtil.showLog("msg---onNetWorkError");

            }

            @Override
            public void threadError(int code, String message, Exception e) {
                LogUtil.showLog("msg---threadError: code:" + code + "   message:" + message);
            }
        });
    }

    private OnBaiduPrerollLisenter mOnBaiduPrerollLisenter;

    public interface OnBaiduPrerollLisenter {
        void baiduAdPaly(BaiduVideoResponse response);

        void baiduAdFail();
    }

    public void setOnBaiduPrerollLisenter(OnBaiduPrerollLisenter mOnBaiduPrerollLisenter) {
        this.mOnBaiduPrerollLisenter = mOnBaiduPrerollLisenter;
    }

}
