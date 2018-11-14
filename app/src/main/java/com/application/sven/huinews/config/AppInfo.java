package com.application.sven.huinews.config;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;

import com.application.sven.huinews.R;

/**
 * auther: sunfuyi
 * data: 2018/5/14
 * effect:
 */
public class AppInfo {
    public static String getVersion(Context context)//获取版本号
    {
        try {
            PackageInfo pi = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            return pi.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return context.getString(R.string.version_unknown);
        }
    }

    public static int getVersionCode(Context context)//获取版本号(内部识别号)
    {
        try {
            PackageInfo pi = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            return pi.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return 0;
        }
    }

    //android 获取当前手机型号
    public static String getPhoneModel(Context context) {
        Build bd = new Build();
        return bd.MODEL;
    }

    //android 获取当前手机品牌
    public static String getPhoneBrand(Context context) {
        Build bd = new Build();
        return bd.BRAND;
    }

    public static String getSDKVersion() {
        return "Android" + Build.VERSION.RELEASE;
    }

    /**
     * 获取用户ua，用于点冠广告
     *
     * @return
     */
    public static String getUserAgent() {
        return System.getProperty("http.agent");
    }
}
