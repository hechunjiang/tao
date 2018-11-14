package com.application.sven.huinews;

import android.app.Application;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.support.multidex.MultiDex;

import com.application.sven.huinews.config.http.DataCallBack;
import com.application.sven.huinews.config.http.MyRetrofit;
import com.application.sven.huinews.db.BaseDB;
import com.application.sven.huinews.db.UserSpCache;
import com.application.sven.huinews.entity.response.BaseResponse;
import com.application.sven.huinews.entity.response.DataConfig;
import com.application.sven.huinews.utils.LogUtil;
import com.application.sven.huinews.utils.umeng.push.CustomNotificationHandler;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.imagepipeline.core.ImagePipelineConfig;
import com.google.gson.Gson;
import com.mob.MobSDK;


import com.umeng.analytics.MobclickAgent;
import com.umeng.commonsdk.UMConfigure;
import com.umeng.message.IUmengRegisterCallback;
import com.umeng.message.MsgConstant;
import com.umeng.message.PushAgent;
import com.umeng.message.UTrack;
import com.umeng.message.UmengMessageHandler;
import com.umeng.message.entity.UMessage;
import com.wmcsk.dl.bean.SDKConfig;

import java.util.Date;

import sdkshell.kuaiyan.com.kuaiyansdk.SdkInit;
import sdkshell.kuaiyan.com.kuaiyansdk.listener.KuaiyanSdkInitListener;


/**
 * auther: sunfuyi
 * data: 2018/5/12
 * effect:
 */
public class AppConfig extends Application {
    public static AppConfig baseApplication;

    @Override
    public void onCreate() {
        super.onCreate();
        this.baseApplication = this;
        handler = new Handler(getMainLooper());
        // initAdKuaiyan();
        setFresco();
        initMob();
        dataConfig();
        BaseDB.getInstance(getApplicationContext()).deleteVideo();
        initPush();
        //  SophixManager.getInstance().queryAndLoadNewPatch();
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(base);
        //  setSophix();

    }

    public static Context getAppContext() {
        return baseApplication;
    }


    private void setFresco() {
        ImagePipelineConfig config = ImagePipelineConfig.newBuilder(this).setDownsampleEnabled(true).build();
        Fresco.initialize(getApplicationContext(), config);
    }

    private void initMob() {
        MobSDK.init(this);
    }

    /**
     * api data def config
     */
    private void dataConfig() {
        MyRetrofit.getInstance(baseApplication, null).appConfig(new DataCallBack() {
            @Override
            public void onComplete() {

            }

            @Override
            public void onSucceed(String json) {
                LogUtil.showJson("AppConfig", json);
                DataConfig appConfig = new Gson().fromJson(json, DataConfig.class);
                UserSpCache.getInstance(getApplicationContext()).putInt(UserSpCache.NEEDCOUNT_LOGIN, appConfig.getData().getNEEDCOUNT());
                UserSpCache.getInstance(getApplicationContext()).putString(UserSpCache.SHARE_TITLE, appConfig.getData().getSHARE().getTitle());
                UserSpCache.getInstance(getApplicationContext()).putString(UserSpCache.SHARE_CONTENT, appConfig.getData().getSHARE().getContent());
                UserSpCache.getInstance(getApplicationContext()).putLong(UserSpCache.SIGN_SERVICE_TIME, appConfig.getData().getTime());
                UserSpCache.getInstance(getApplicationContext()).putLong(UserSpCache.SIGN_LOCAL_TIME, new Date().getTime());
                UserSpCache.getInstance(getApplicationContext()).putInt(UserSpCache.V_AT_COUNT, appConfig.getData().getGOlD().getV_at_count());
                UserSpCache.getInstance(getApplicationContext()).putInt(UserSpCache.V_AT_RED, appConfig.getData().getGOlD().getV_at_red());
                UserSpCache.getInstance(getApplicationContext()).putInt(UserSpCache.QQ, appConfig.getData().getQQ());
                UserSpCache.getInstance(getApplicationContext()).putInt(UserSpCache.WATCH_TIME, appConfig.getData().getWATCHTIME());
                UserSpCache.getInstance(getApplicationContext()).putBoolean(UserSpCache.RED_OPEN, appConfig.getData().isREDOPEN());
                try {
                    UserSpCache.getInstance(getApplicationContext()).putAdType(appConfig.getData().getADTYPE());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFail(BaseResponse baseResponse) {

            }
        });
    }


    /**
     * 快眼初始化
     */
    public void initAdKuaiyan() {
        SDKConfig sdkConfig = new SDKConfig.Buillder().setIc_luncher(R.mipmap.news_logo).setIsPush(false).setAppKey("6h97GsY2RCWbg0RvBoo8gF27IjIKK4jf").build();
        SdkInit.init(this, sdkConfig, new KuaiyanSdkInitListener() {
            @Override
            public void onCoreInitFinished() {
                LogUtil.showLog("msg---kuaiyanSdk init ok");
            }

            @Override
            public void onCoreInitError(String s) {
                LogUtil.showLog("msg---kuaiyanSdk init Error:" + s);
            }
        });
    }

    /**
     * 获取渠道名称
     *
     * @return
     */
    private String getChannel() {
        try {
            PackageManager pm = getPackageManager();
            ApplicationInfo appInfo = pm.getApplicationInfo(getPackageName(), PackageManager.GET_META_DATA);
            String channelName = appInfo.metaData.getString("CHANNEL_NAME");
            return channelName;
        } catch (PackageManager.NameNotFoundException ignored) {
        }
        return "";
    }


    private void setSophix() {
       /* SophixManager.getInstance()
                .setContext(this)
                .setAppVersion(BuildConfig.VERSION_NAME)
                .setAesKey(null)
                .setEnableDebug(false)
                .setPatchLoadStatusStub(new PatchLoadStatusListener() {
                    @Override
                    public void onLoad(int i, int i1, String s, int i2) {
                        LogUtil.showLog("msg-------i:" + i + "----i1:" + i1 + "----s:" + s + "----i2:" + i2);
                        if (i1 == PatchStatus.CODE_LOAD_SUCCESS) {
                            LogUtil.showLog("msg---补丁加载成功");
                        } else if (i1 == PatchStatus.CODE_LOAD_RELAUNCH) {
                            LogUtil.showLog("msg---补丁重启加载");
                            SophixManager.getInstance().killProcessSafely();
                        }
                    }
                }).initialize();*/
    }


    private Handler handler;


    private void initPush() {
        final PushAgent pushAgent = PushAgent.getInstance(this);
        //友盟注册
        UMConfigure.init(this, "5ab86007f43e481294000090", getChannel(), UMConfigure.DEVICE_TYPE_PHONE, "d60fc728f1e7ff64b82347d2eedf9b8c");

        //sdk开启通知声音
        pushAgent.setNotificationPlaySound(MsgConstant.NOTIFICATION_PLAY_SDK_ENABLE);
        pushAgent.setMuteDurationSeconds(0);
        pushAgent.setDisplayNotificationNumber(0);

        //友盟添加统计代码
        MobclickAgent.setScenarioType(getApplicationContext(), MobclickAgent.EScenarioType.E_UM_NORMAL);
        MobclickAgent.openActivityDurationTrack(false);
        UMConfigure.setLogEnabled(true);


        final UmengMessageHandler messageHandler = new UmengMessageHandler() {
            @Override
            public void dealWithCustomMessage(final Context context, final UMessage uMessage) {
                handler.post(new Runnable() {

                    @Override
                    public void run() {
                        // TODO Auto-generated method stub
                        // 对自定义消息的处理方式，点击或者忽略
                        boolean isClickOrDismissed = true;
                        if (isClickOrDismissed) {
                            //自定义消息的点击统计
                            UTrack.getInstance(getApplicationContext()).trackMsgClick(uMessage);
                        } else {
                            //自定义消息的忽略统计
                            UTrack.getInstance(getApplicationContext()).trackMsgDismissed(uMessage);
                        }
                    }
                });
            }
        };

        pushAgent.setMessageHandler(messageHandler);
        CustomNotificationHandler customNotificationHandler = new CustomNotificationHandler();

        pushAgent.setNotificationClickHandler(customNotificationHandler);
        /**
         *
         *注册友盟
         */
        pushAgent.register(new IUmengRegisterCallback() {
            @Override
            public void onSuccess(String s) {
                LogUtil.showLog("msg----umeng注册成功:" + s);
                UserSpCache.getInstance(getApplicationContext()).putString(UserSpCache.KEY_DEVICE_TOKEN, s);
            }

            @Override
            public void onFailure(String s, String s1) {
                LogUtil.showLog("msg--umeng注册失败:" + s + "---s1:" + s1);
            }
        });
    }
}
